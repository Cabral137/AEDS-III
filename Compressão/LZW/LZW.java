import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class LZW
{
    

    public static void compress ()
    {
        try 
		{
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNome do arquivo: ");
            String path = sc.nextLine();

            RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
            String linha = "";

            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine();
            }

            Vector <String> dicionario = new Vector <String> ();

            for(int i = 0; i < 256; i++)
            {
                dicionario.add("" + (char) i);
            }

            String prefix = "";
            String comp   = "";

            for(int i = 0; i < linha.length(); i++)
            {
                prefix = prefix + linha.charAt(i);

                if(!dicionario.contains(prefix))
                {
                    dicionario.add(prefix);
                    comp   = comp + (dicionario.indexOf(prefix.substring(0, prefix.length()- 1)) + " ");
                    prefix = "" + linha.charAt(i);
                }

            }

            if(dicionario.contains(prefix))
            {
                comp = comp + prefix;
            }

            RandomAccessFile wa = new RandomAccessFile("./Compressed.lzw", "rw");
            wa.writeBytes(comp);

        }
        catch (Exception e) 
        {
            System.out.println("ERRO: Falha ao comprimir o arquivo");
        }
    }

    public static void decompress ()
    {
        try 
		{
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNome do arquivo: ");
            String path = sc.nextLine();

            RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
            String linha = ""; 

            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine();
            }

            String [] nums = linha.split(" ");
            String decomp  = "";
            String aux     = "";

            Vector <String> dicionario = new Vector <String> ();

            for(int i = 0; i < 256; i++)
            {
                dicionario.add("" + (char) i);
            }

            for(int i = 0; i < nums.length; i++)
            {

                aux = aux + dicionario.get(Integer.parseInt(nums[i])).charAt(0);

                decomp = decomp + dicionario.get(Integer.parseInt(nums[i]));

                if(!dicionario.contains(aux))
                {
                    dicionario.add(aux);
                    aux = dicionario.get(Integer.parseInt(nums[i]));
                }

            }

            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/Decompressed.lzw" , "rw");
            wa.writeBytes(decomp);
  
		} 
		catch(Exception e) 
		{
            System.out.println("ERRO: Falha ao descomprimir o arquivo");
		}  
    }
    
    public void run ()
    {
        compress();
        decompress();
    }


}
