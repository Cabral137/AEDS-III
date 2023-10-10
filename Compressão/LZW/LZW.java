import java.util.Vector;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class LZW
{
    
    public static void diffChars (String linha, Vector <Character> array)
    {

        for(int i = 0; i < linha.length (); i++)
        {
            if(!array.contains(linha.charAt(i)))
            {
                array.add(linha.charAt(i)); 
            }
        }

    }
    
    
	public static void main(String[] args) 
	{
		
		try 
		{
            RandomAccessFile ra = new RandomAccessFile("./SpotifyMusic.csv", "r");
            RandomAccessFile rb = new RandomAccessFile("./Spotify.txt", "rw");

		   	BufferedReader br = new BufferedReader(new InputStreamReader (System.in, "ISO-8859-1"));
            String linha = ""; 

            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine();
            }

            Vector <Character> dicionario = new Vector <Character> ();

            for(int i = 0; i < 26; i++)
            {
                dicionario.add((char)('a' + i));
            }

            for(int i = 0; i < 26; i++)
            {
                dicionario.add((char)('A' + i));
            }
            
            diffChars(linha, dicionario);

            for(int i = 0; i < dicionario.size(); i++)
            {
                System.out.println(dicionario.get(i) + "\t" + Integer.toBinaryString(i)); 
            }

            for(int i = 0; i < linha.length(); i++)
            {
                rb.writeBytes((Integer.toBinaryString(dicionario.indexOf(linha.charAt(i))))); 
            }
            
		} 
		catch(Exception e) 
		{
		    e.printStackTrace();
		}

		
	}
}
