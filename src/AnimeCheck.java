import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dev.katsute.mal4j.Json;
import dev.katsute.mal4j.Json.JsonObject;
import dev.katsute.mal4j.MyAnimeList;
import dev.katsute.mal4j.anime.Anime;
import dev.katsute.mal4j.anime.RelatedAnime;

public class AnimeCheck
{
	static MyAnimeList mal;

	public AnimeCheck(String ClientID)
	{
		mal = MyAnimeList.withClientID(ClientID);
	}

	// This goes through MAL related anime getting all listing for a series
	// Passes into getSeries(id, abridged, file) without saving series
	public List<Anime> getSeries(long id, boolean abridged)
	{
		return getSeries(id, abridged, null);
	}

	// This goes through MAL related anime getting all listing for a series
	// If abridged, gets all main anime. Otherwise it gets all character
	// appearances in other media
	// also saves schema list to file if given a file path
	public List<Anime> getSeries(long id, boolean abridged, File file)
	{
		List<Anime> AP = new ArrayList<Anime>();
		List<JsonObject> objects = new ArrayList<JsonObject>();

		objects.add(getSchema(id));
		AP.add(mal.getAnime(objects.get(objects.size() - 1)));

		for (int y = 0; y < AP.size(); y++)
		{

			RelatedAnime[] related = AP.get(y).getRelatedAnime();

			for (int x = 0; x < related.length; x++)
			{
				if (!containsID(AP, related[x].getID()))
				{
					if (abridged)
					{
						if (related[x].getRelationTypeFormat().equals("Character"))
							;
						else
						{
							objects.add(getSchema(related[x].getID()));
							AP.add(mal.getAnime(objects.get(objects.size() - 1)));
						}
					} else
					{
						objects.add(getSchema(related[x].getID()));
						AP.add(mal.getAnime(objects.get(objects.size() - 1)));
					}
				}
			}

		}

		objects.sort(Comparator.comparing(JsonObject::getID));
		AP.sort(Comparator.comparing(Anime::getID));

		saveSchema(objects, file);

		return AP;
	}

	// writes Anime List to a specific file in JSON using GSON.
	public void saveSeries(List<Anime> series, File file)
	{
		try
		{
			FileWriter writer = new FileWriter(file);
			Gson gson = new Gson();

			for (Anime anime : series)
			{
				writer.write(gson.toJson(getSchema(anime.getID())) + "\n");
			}

			writer.close();
		} catch (Exception e)
		{
			System.err.println(e);
		}
	}

	// reads a local file, translated the lines back into JsonObject and returns
	// anime arraylist based on those
	public List<Anime> loadSeries(File file)
	{
		List<Anime> series = new ArrayList<Anime>();
		Gson gson = new Gson();

		try
		{
			Scanner scan = new Scanner(file);
			Type object = new TypeToken<JsonObject>()
			{
			}.getType();

			while (scan.hasNextLine())
				series.add(mal.getAnime(gson.fromJson(scan.nextLine(), object)));

			scan.close();
		} catch (Exception e)
		{
			System.out.println(e);
		}

		return series;
	}

	// goes through list and checks if an anime is already in the list
	private static boolean containsID(List<Anime> anime, Long id)
	{
		for (int x = 0; x < anime.size(); x++)
		{
			if (anime.get(x).getID().equals(id))
				return true;
		}

		return false;
	}

	// writes schema list to a specific file in JSON using GSON.
	private void saveSchema(List<JsonObject> object, File file)
	{
		if (file == null)
			return;
		try
		{
			FileWriter writer = new FileWriter(file);
			Gson gson = new Gson();

			for (JsonObject JsonObject : object)
			{
				writer.write(gson.toJson(JsonObject) + "\n");
			}

			writer.close();
		} catch (Exception e)
		{
			System.err.println(e);
		}
	}

	// gets JsonObject schema from Mal4J
	private JsonObject getSchema(long id)
	{
		return mal.getAnimeSchema(id);
	}

}