import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;

// Classe para gerenciar a tabela de hash utilizada no algoritmo Boyer-Moore
class Hash
{
    public int  [] pos; // Variável para armazenar a posição para cada caso de comparação

    // Construtor da classe Hash
    public Hash ()
    {
        this.pos = new int  [256];
    }

    // Obtém a posição de um caractere na tabela de hash
    public int getPos(char search)
    {
        return(this.pos[search % 256]);
    }

    // Inicializa a tabela de hash para o bad character rule
    public void badChar (String frase)
    {
        for(int i = 0; i < pos.length; i++)
        {
            this.pos[i] = -1;
        }

        for(int i = 0; i < frase.length() - 1; i++)
        {
            pos[frase.charAt(i) % 256] = i;
        }
    }
}

// Classe principal que implementa o algoritmo Boyer-Moore
public class BoyerMoore 
{
    // Método para interagir com o usuário e iniciar a pesquisa
	public void pesquisa () 
	{
		try 
        {
            Scanner sc = new Scanner(System.in);

            // Solicitar o nome do arquivo
            System.out.print("\nNome do Arquivo: ");
            String path   = sc.nextLine();
            RandomAccessFile ra = new RandomAccessFile ("./ARQUIVOS/TESTE/" + path, "r");

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

    // Função auxiliar para retornar o valor máximo entre dois números
    public static int max (int num1, int num2)
    {
        if(num1 > num2)
        {
            return(num1);
        }

        return(num2);
    }

    // Método principal para realizar a pesquisa Boyer-Moore
    public static void pesquisa (String frase, String palavra)
    {
        try 
        {
            Hash tabela = new Hash();
            Scanner sc = new Scanner(System.in);

            // Inicializar a tabela de hash
            tabela.badChar(palavra);

            int o = palavra.length() - 1;
            int i = palavra.length() - 1;
            int encontrou   = 0;
            int comparacoes = 0;

            while(i < frase.length())
            {
                comparacoes++;

                if(palavra.charAt(o) == frase.charAt(i))
                {
                    o--;
                    i--;

                    if(o == 1)
                    {
                        System.out.println("\n\nACHOU");
                        System.out.println("Posição: " + (i - 2));
                        System.out.println("Comparações: " + comparacoes);
                        System.out.println("\n\nCONTINUAR? \n- SIM\n- NÃO");
                        encontrou++;

                        if(sc.nextLine().equals("SIM"))
                        {
                            i = i + palavra.length();
                            o = palavra.length() - 1;
                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else
                {
                    if((i+o) <= frase.length())
                    {
                        i = i + max(1, o - tabela.getPos(frase.charAt(i+o)));
                    } 
                }
            }

            // Exibir resultados da pesquisa
            if(encontrou < 1)
			{
				System.out.println("\nNÃO EXISTE ESSE PADRÃO NO ARQUIVO");
			}
			else
			{
				if(encontrou == 1)
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
