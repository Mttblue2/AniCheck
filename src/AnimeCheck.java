import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dev.katsute.mal4j.MyAnimeList;
import dev.katsute.mal4j.anime.Anime;
import dev.katsute.mal4j.anime.AnimePreview;
import dev.katsute.mal4j.anime.RelatedAnime;

public class AnimeCheck
{
	static MyAnimeList mal = MyAnimeList.withClientID("");

	public static void main(String[] args) throws IOException, NoSuchFieldException, SecurityException
	{
		//PaginatedIterator<AnimePreview> sword = mal.getAnime().withQuery("Fate").searchAll();
		/*List<AnimePreview> title = mal.getAnime().withQuery("Bocchi the Rock!").search();
		
		for(int x = 0; x < title.size(); x++)
			System.out.println(title.get(x).getTitle());*/
		
		List<Anime> series = getSeries(47917, "a");
		File file = new File("D:\\Users\\Tater\\Desktop\\temp.ani");
		
		Anime anime = mal.getAnime(38790);
		Gson gson = new Gson();
		Type animeType = new TypeToken<Anime>() {}.getType();


	}
	
	//This goes through MAL related anime getting all listing for a series
	//"a" is abridged. Gets all main anime. Otherwise it gets all character appearances in other media
	private static List<Anime> getSeries(long id, String s)
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
					}
					else
						AP.add(mal.getAnime(related[x].getID()));
				}
			}

		}
		
		
		AP.sort(Comparator.comparing(Anime::getID));
		
		return AP;
	}
	
	//goes through list and checks if an anime is already in the list
	private static boolean containsID(List<Anime> anime, Long id)
	{
		for (int x = 0; x < anime.size(); x++)
		{
			if (anime.get(x).getID().equals(id))
				return true;
		}

		return false;
	}
	
	//writes List to a specific file in JSON using GSON.
	//currently working on
	private static void saveObject(Anime series, File file)
	{
		
		try
		{
			FileWriter writer = new FileWriter(file);
			//Type animeType = new TypeToken<Anime>(){}.getType();
			Gson gson = new Gson();
			
			
			writer.write(gson.toJson(series));
			
			writer.close();
		}
		catch(Exception e)
		{
			System.err.println(e);
		}
	}

}