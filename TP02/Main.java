import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main 
{
    
    public static void main(String[] args) throws Exception
    {
    
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;

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
                    
                    break;
                
                case 2:

                    break;

                default:
                    
                    break;
            }

        }

    }

}
