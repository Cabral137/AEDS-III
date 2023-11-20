import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main 
{

    public static void clearScreen() 
    {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void compressao ()
    {

        clearScreen();

        try 
        {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\tTrabalho Prático II (Compressão) - AEDS III - Victor Cabral\n");

            System.out.println("\nOpções:\n" +
                                "1 - Huffman\n" +
                                "2 - LZW\n" +
                                "0 - Sair\n"); 

            int action = Integer.parseInt(br.readLine());

            switch (action) 
            {
                case 1:
                Huffman hf = new Huffman();
                break;
                    
                case 2:
                LZW lzw = new LZW();
                break;

                default:
                break;
            }

            
        } 
        catch (Exception e) 
        {
            System.out.println("ERRO: Entrada Inválida");
        }


    } 

    public static void casamento ()
    {

        clearScreen();

        try
        {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\tTrabalho Prático II (Casamento de Padrões) - AEDS III - Victor Cabral\n");
    
            System.out.println("\nOpções:\n" +
                                "1 - KMP\n" +
                                "2 - Boyer-Moore\n" +
                                "0 - Sair\n"); 

            int action = Integer.parseInt(br.readLine());

            switch (action) 
            {
                case 1:
                KMP kmp = new KMP();
                kmp.pesquisa();
                break;
                    
                case 2:
                BoyerMoore bm = new BoyerMoore();
                bm.pesquisa();
                break;

                default:
                break;
            }

        } 
        catch (Exception e) 
        {
            System.out.println("ERRO: Entrada Inválida");
        }

    } 
    
    public static void main(String[] args)
    {

        try
        {
    
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int action = -1;

            clearScreen();

            System.out.println("\n\tTrabalho Prático II - AEDS III - Victor Cabral\n");

            while(action != 0)
            {

                System.out.println("\nOpções:\n" +
                                    "1 - Compressão\n" +
                                    "2 - Casamento de Padrão\n" +
                                    "0 - Sair\n");    
                                
                action = Integer.parseInt(br.readLine());

                switch (action) 
                {
                    case 1:
                    compressao();    
                    break;
                    
                    case 2:
                    casamento();
                    break;

                    default:
                    break;
                }

            }

        }
        catch (Exception e)
        {
            System.out.println("ERRO: Entrada Inválida");
        }

    }

}
