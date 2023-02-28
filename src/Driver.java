import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import dev.katsute.mal4j.anime.Anime;

public class Driver
{

	public static void main(String[] args)
	{
		//PaginatedIterator<AnimePreview> sword = mal.getAnime().withQuery("Fate").searchAll();
		/*List<AnimePreview> title = mal.getAnime().withQuery("Bocchi the Rock!").search();
				
				
		File file = new File("D:\\Users\\Tater\\Desktop\\temp.ani");
				
				
		Anime anime = mal.getAnime(48417);
		Gson gson = new Gson();
		TypeToken animeType = new TypeToken<Anime>() {};*/
		
		File file = new File("MAL_key.txt");
		
		AnimeCheck aniCheck = new AnimeCheck(getKey(file));
		
		List<Anime> series = aniCheck.getSeries(47917, "a");
		
		for (Anime anime: series)
			System.out.println(anime.getTitle());
				

	}
	
	private static String getKey(File file)
	{
		Scanner scan;
		String clientID = "";
		try
		{
			scan = new Scanner(file);
			clientID = scan.next();
			scan.close();
		} catch (FileNotFoundException e)
		{
			System.out.println(e);
		}
		
		return clientID;
	}

}
