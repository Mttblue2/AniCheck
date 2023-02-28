import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import dev.katsute.mal4j.anime.Anime;

public class Driver
{

	public static void main(String[] args)
	{
		//PaginatedIterator<AnimePreview> sword = mal.getAnime().withQuery("Fate").searchAll();
		/*List<AnimePreview> title = mal.getAnime().withQuery("Bocchi the Rock!").search();*/				
		
		File key = new File("MAL_key.txt");
		File file = new File("D:\\Users\\Tater\\Desktop\\temp.ani");
		
		AnimeCheck aniCheck = new AnimeCheck(getKey(key));
		
		List<Anime> series = aniCheck.getSeries(51535, "a");
		
		for (Anime anime: series)
			System.out.println(anime.getTitle() + ": " + anime.getID());
		
		aniCheck.saveSeries(series, file);
		
		//aniCheck.getSchema(47917);
		

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
