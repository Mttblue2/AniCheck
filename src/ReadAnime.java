import java.io.File;
import java.util.Scanner;

public class ReadAnime
{
	public static void main(String[] args)
	{
		Scanner kb = new Scanner(System.in);

		File dir = new File("F:\\C3");
		System.out.println(dir);

		File[] array = dir.listFiles();

		for (int x = 0; x < array.length; x++)
		{
			System.out.println(array[x]);
		}
	}
}
