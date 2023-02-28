import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;

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
	// "a" is abridged. Gets all main anime. Otherwise it gets all character
	// appearances in other media
	public List<Anime> getSeries(long id, String s)
	{
		List<Anime> AP = new ArrayList<Anime>();
		AP.add(mal.getAnime(id));

		for (int y = 0; y < AP.size(); y++)
		{

			RelatedAnime[] related = AP.get(y).getRelatedAnime();

			for (int x = 0; x < related.length; x++)
			{
				if (!containsID(AP, related[x].getID()))
				{
					if (s.equals("a"))
					{
						if (related[x].getRelationTypeFormat().equals("Character"))
							;
						else
							AP.add(mal.getAnime(related[x].getID()));
					} else
						AP.add(mal.getAnime(related[x].getID()));
				}
			}

		}

		AP.sort(Comparator.comparing(Anime::getID));

		return AP;
	}

	// writes List to a specific file in JSON using GSON.
	// currently working on
	public void saveObject(Anime series, File file)
	{

		try
		{
			FileWriter writer = new FileWriter(file);
			Gson gson = new Gson();

			writer.write(gson.toJson(getSchema(series.getID())));

			writer.close();
		} catch (Exception e)
		{
			System.err.println(e);
		}
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

	// gets JsonObject schema from Mal4J
	private JsonObject getSchema(long id)
	{
		return mal.getAnimeSchema(id);
	}

}