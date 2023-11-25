import java.io.RandomAccessFile;
import java.util.Scanner;

public class KMP
{

    // Método para criar o vetor de posições (prefixo-sufixo) para a palavra no algoritmo KMP
    public static int[] criarVetor(String palavra)
    {
        int[] pos = new int[palavra.length()];

        for (int i = 1; i < palavra.length(); i++)
        {
            for (int j = 0; j < i; j++)
            {
                if (palavra.charAt(i) == palavra.charAt(j))
                {
                    int contador = 0;
                    int tmp = j;

                    while (tmp > -1)
                    {
                        if (palavra.charAt(i - contador) == palavra.charAt(tmp))
                        {
                            contador++;
                        }
                        else
                        {
                            contador = 0;
                            break;
                        }

                        tmp--;
                    }

                    if (contador > pos[i])
                    {
                        pos[i] = contador;
                    }
                }
            }
        }

        return pos;
    }

    // Método principal para iniciar a pesquisa KMP
    public void pesquisa() 
    {
        try 
        {
            Scanner sc = new Scanner(System.in);

            // Solicitar o nome do arquivo
            System.out.print("\nNome do Arquivo: ");
            String path = sc.nextLine();
            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/TESTE/" + path, "r");

            String frase = "";
            while (ra.getFilePointer() != ra.length())
            {
                frase = frase + ra.readLine();
            }

            // Solicitar a palavra a ser pesquisada
            System.out.print("\nPalavra para pesquisar: ");
            String palavra = sc.nextLine();

            // Iniciar a pesquisa
            pesquisa(frase, palavra);
        } 
        catch (Exception e) 
        {
            System.out.println("ERRO: ARQUIVO NÃO ENCONTRADO");
        }
    }

    // Método para realizar a pesquisa KMP
    public static void pesquisa(String frase, String palavra)
    {
        try 
        {
            Scanner sc = new Scanner(System.in);

            int encontrou = 0;
            int comparacoes = 0;
            int[] pos = criarVetor(palavra);
            int o = 0;

            for (int i = 0; i < frase.length(); i++)
            {
                comparacoes++;
                if (palavra.charAt(o) == frase.charAt(i))
                {
                    o++;

                    if (o == palavra.length())
                    {
                        System.out.println("\n\nACHOU");
                        System.out.println("Posição: " + (i - palavra.length()));
                        System.out.println("Comparações: " + comparacoes);
                        System.out.println("\n\nCONTINUAR? \n- SIM\n- NÃO");
                        encontrou++;

                        if (sc.nextLine().equals("SIM"))
                        {
                            o = 0;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else
                {
                    i = i + (o - pos[o]);
                    o = pos[o];
                }
            }

            // Exibir resultados da pesquisa
            if (encontrou < 1)
            {
                System.out.println("\nNÃO EXISTE ESSE PADRÃO NO ARQUIVO");
            }
            else
            {
                if (encontrou == 1)
                {
                    System.out.println("\nO PADRÃO FOI ENCONTRADO " + encontrou + " VEZ NO ARQUIVO");
                }
                else
                {
                    System.out.println("\nO PADRÃO FOI ENCONTRADO " + encontrou + " VEZES NO ARQUIVO");
                }
            }

            sc.nextLine();
        } 
        catch (Exception e) 
        {
            System.out.println("\nERRO: ERRO NA PESQUISA");
            e.printStackTrace();
        }
    }
}
