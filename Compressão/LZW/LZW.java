import java.util.Vector;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LZW
{
    
    public static Vector <Character> diffChars (String linha)
    {

        Vector <Character> resp = new Vector<Character> ();

        for(int i = 0; i < linha.length (); i++)
        {
            if(!resp.contains(linha.charAt(i)))
            {
                resp.add(linha.charAt(i));
            }
        }

        return (resp);

    }
    
    
	public static void main(String[] args) 
	{
		
		try 
		{
		   	BufferedReader br = new BufferedReader(new InputStreamReader (System.in, "ISO-8859-1"));
            String linha = br.readLine(); 

            System.out.println(linha);
            
            Vector <Character> dicionario = diffChars(linha); 

            for(int i = 0; i < dicionario.size(); i++)
            {
                System.out.println(dicionario.get(i) + "\t" + i);
            }
            
		} 
		catch(Exception e) 
		{
		    System.out.println("ERRO: ERRO");
		}

		
	}
}
