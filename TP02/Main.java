import java.io.BufferedReader;
import java.io.InputStreamReader;

// Classe principal que contém o menu principal e as operações de compressão e casamento de padrões
public class Main 
{
    // Método para limpar o console
    public static void clearScreen() 
    {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    // Método para realizar operações de compressão (Huffman ou LZW)
    public static void compressao()
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
                    hf.run();
                    break;
                    
                case 2:
                    LZW lzw = new LZW();
                    lzw.run();
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

    // Método para realizar operações de casamento de padrões (KMP, Boyer-Moore ou Musica - Boyer-Moore)
    public static void casamento()
    {
        clearScreen();

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.println("\n\tTrabalho Prático II (Casamento de Padrões) - AEDS III - Victor Cabral\n");
    
            System.out.println("\nOpções:\n" +
                                "1 - KMP\n" +
                                "2 - Boyer-Moore\n" +
                                "3 - Musica (Boyer-Moore)\n" +
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

                case 3:
                Musica mu = new Musica();
                mu.run();
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
    
    // Método principal que exibe o menu principal
    public static void main(String[] args)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int action = -1;

            while(action != 0)
            {
                clearScreen();
                System.out.println("\n\tTrabalho Prático II - AEDS III - Victor Cabral\n");
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
