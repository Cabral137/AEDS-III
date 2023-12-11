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

                if(pos == frase.length())
                {
                    break;
                }

                tabela[i][j] = frase.charAt(pos);
                pos++;

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

        
        for(int i = 0; i < colunas; i++)
        {
            for(int p = 0; p < 5; p++)
            {
                System.out.print(tabela[i][p] + "\t");
            }

            System.out.println();
        }

        
        return(tmp);

    }

    public static String decipher (String frase, String chave)
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

        String aux [] = new String [colunas];
        int cont = 0;

        for(int i = 0; cont < colunas; i = i + frase.length()/colunas)
        {
            if(frase.length() > (i + frase.length()/colunas))
            {
                aux[cont] = frase.substring(i, i + frase.length()/colunas);
                cont++;
            }
            else
            {
                aux[cont] = frase.substring(i, frase.length());
                cont++;
            }
        }

        for(int i = 0; i < aux.length; i++)
        {
            System.out.println(aux[i]);
        }

        // for(int i = 0; i < colunas; i++)
        // {
        //     for(int p = 0; p < 5; p++)
        //     {
        //         System.out.print(tabela[i][p] + "\t");
        //     }

        //     System.out.println();
        // }

        // for(int i = 0; i < colunas; i++)
        // {
        //     int menor = getLowest(tabela, linhas, colunas);

        //     for(int j = 1; j < linhas; j++)
        //     {
        //         tmp = tmp + tabela[j][menor];
        //         tabela[0][menor] = '~';
        //     }

        // }

        
        return(tmp);

    }

    public static void main(String[] args) 
    {
    
        Scanner sc = new Scanner(System.in);

        String chave = sc.nextLine();
        String frase = sc.nextLine();

        String tmp = cipher(frase, chave);

        System.out.println(tmp);

        System.out.println(decipher(tmp, chave));

    }
    
}
