import java.util.Scanner;

public class Colunas
{

    public static int getLowest (char[][] tabela, int linhas, int colunas)
    {
        int pos = 0;
        int aux = tabela[0][0];

        for(int i = 1; i < colunas; i++)
        {
            if(tabela[0][i] < aux && tabela[0][i] != '~')
            {
                pos = i;
            }
        }

        return(pos);

    }

    public static String cipher (String frase, String chave)
    {

        int linhas  = (frase.length()/chave.length()) + 2;
        int colunas = chave.length();

        char[][] tabela = new char[linhas][colunas];
        String tmp = "";
        int pos = 0;

        for(int i = 0; i < colunas; i++)
        {
            tabela[0][i] = chave.charAt(i);
        }

        for(int i = 1; i < linhas; i++)
        {
            for(int j = 0; j < colunas; j++)
            {
                tabela[i][j] = frase.charAt(pos);
                pos++;

                if(pos == frase.length())
                {
                    break;
                }
            }
        }

        for(int i = 0; i < colunas; i++)
        {
            int menor = getLowest(tabela, linhas, colunas);

            for(int j = 1; j < linhas; j++)
            {
                tmp = tmp + tabela[j][menor];
                tabela[0][menor] = '~';
            }

        }

        
        return(tmp);

    }

    public static void main(String[] args) 
    {
    
        Scanner sc = new Scanner(System.in);

        String chave = sc.nextLine();
        String frase = sc.nextLine();

        System.out.println(cipher(frase, chave));

    }
    
}
