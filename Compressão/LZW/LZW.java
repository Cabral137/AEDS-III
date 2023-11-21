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
                comp = comp + (int) linha.charAt(linha.length() - 1);
            }

            String [] verify = comp.split(" ");

            for(int i = 0; i < verify.length; i++)
            {
                System.out.print(dicionario.get(Integer.parseInt(verify[i])));
            }

            System.out.println("\n"+dicionario.size());
            System.out.println(comp);

            RandomAccessFile wa = new RandomAccessFile("./CompLZW.txt", "rw");
            wa.writeBytes(comp);

        }
        catch (Exception e) 
        {
            e.printStackTrace();
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

            System.out.println(decomp);
  
		} 
		catch(Exception e) 
		{
            e.printStackTrace();
		}  
    }
    

	public static void main(String[] args) 
	{
		
		// try 
		// {
        //     Scanner sc = new Scanner(System.in);

        //     // System.out.print("\nNome do arquivo: ");
        //     // String path = sc.nextLine();

        //     // RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
        //     // String linha = "";

        //     // while(ra.getFilePointer() != ra.length())
        //     // {
        //     //     linha = linha + ra.readLine();
        //     // }

        //     Vector <String> dicionario = new Vector <String> ();

        //     for(int i = 0; i < 256; i++)
        //     {
        //         dicionario.add("" + (char) i);
        //     }

        //     String frase  = "WYS*WYGWYS*WYSWYSG";
        //     String prefix = "";
        //     String comp   = "";

        //     for(int i = 0; i < frase.length(); i++)
        //     {
        //         prefix = prefix + frase.charAt(i);

        //         if(!dicionario.contains(prefix))
        //         {
        //             dicionario.add(prefix);
        //             comp   = comp + (dicionario.indexOf(prefix.substring(0, prefix.length()- 1)) + " ");
        //             prefix = "" + frase.charAt(i);
        //         }
        //     }

        //     String [] verify = comp.split(" ");
        //     if(Integer.parseInt(verify[verify.length - 1]) > 255)
        //     {
        //         comp = comp + (int) frase.charAt(frase.length() - 1);
        //     }

        //     System.out.println(comp);

        //     // for(int i = 0; i < dicionario.size(); i++)
        //     // {
        //     //     System.out.println(dicionario.get(i) + " --- " + i);
        //     // }

        //     String [] nums = comp.split(" ");
        //     String decomp  = "";
        //     String aux     = "";

        //     dicionario = new Vector<String>();

        //     for(int i = 0; i < 256; i++)
        //     {
        //         dicionario.add("" + (char) i);
        //     }

        //     for(int i = 0; i < nums.length; i++)
        //     {

        //         aux = aux + dicionario.get(Integer.parseInt(nums[i])).charAt(0);

        //         decomp = decomp + dicionario.get(Integer.parseInt(nums[i]));

        //         if(!dicionario.contains(aux))
        //         {
        //             dicionario.add(aux);
        //             aux = dicionario.get(Integer.parseInt(nums[i]));
        //         }

        //     }

        //     System.out.println(decomp);
  
		// } 
		// catch(Exception e) 
		// {
		//     e.printStackTrace();
		// }

        compress();
        decompress();
		
	}
}
