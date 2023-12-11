import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main 
{
    // Método para limpar o console
    public static void clearScreen() 
    {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static void main(String[] args)
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            int action = -1;

            while(action != 0)
            {
                clearScreen();
                System.out.println("\n\tTrabalho Prático III - AEDS III - Victor Cabral\n");
                System.out.println("\nOpções:\n" +
                                    "1 - Vigenère\n" +
                                    "2 - ---\n" +
                                    "0 - Sair\n");    
                                
                action = Integer.parseInt(br.readLine());

                switch (action) 
                {
                    case 1:
                    clearScreen();
                    Vigenere vg = new Vigenere();
                    vg.run();
                    break;
                    
                    case 2:

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
