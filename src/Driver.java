import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dev.katsute.mal4j.anime.Anime;

public class Driver
{

	public static void main(String[] args)
	{
		//PaginatedIterator<AnimePreview> sword = mal.getAnime().withQuery("Fate").searchAll();
		//List<AnimePreview> title = mal.getAnime().withQuery("Bocchi the Rock!").search();
		
		File key = new File("MAL_key.txt");
		File file = new File("D:\\Users\\Tater\\Desktop\\temp.ani");
		
		AnimeCheck aniCheck = new AnimeCheck(getKey(key));
		
		List<Anime> series = new ArrayList<Anime>();
		
		series = aniCheck.getSeries(10087, false, file);
		
		//aniCheck.saveSeries(series, file);
		
		//series = aniCheck.loadSeries(file);
		
		for (Anime anime: series)
			System.out.println(anime.getTitle() + ": " + anime.getID());
		

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
