import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;

/*
// Classe para gerenciar a tabela de hash utilizada no algoritmo Boyer-Moore
class Hash
{
    public int[] pos;

    // Construtor da classe Hash
    public Hash()
    {
        this.pos = new int[256];
    }

    // Obtém a posição de um caractere na tabela de hash
    public int getPos(char search)
    {
        return (this.pos[search % 256]);
    }

    // Inicializa a tabela de hash para o bad character rule
    public void badChar(String frase)
    {
        for (int i = 0; i < pos.length; i++)
        {
            this.pos[i] = -1;
        }

        for (int i = 0; i < frase.length() - 1; i++)
        {
            pos[frase.charAt(i) % 256] = i;
        }
    }
}
*/

// Classe para representar uma música e realizar pesquisas nela
public class Musica
{

    // Construtor da classe
    public Musica()
    {

    }

    // Método principal para executar a pesquisa de um padrão em um arquivo CSV
    public void run()
    {

        try
        {
            Scanner sc = new Scanner(System.in);
            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/TESTE/SpotifyMusic.csv", "r");

            Vector<String> musicas = new Vector<String>();
            while (ra.getFilePointer() != ra.length())
            {
                musicas.add(ra.readLine());
            }

            System.out.print("\nPalavra para pesquisar: ");
            String palavra = sc.nextLine();

            for (int i = 0; i < musicas.size(); i++)
            {
                boolean resp = pesquisa(musicas.get(i), palavra);

                if (resp)
                {
                    break;
                }

            }

            System.out.println("\n\nFIM DA PESQUISA!\n\n");

            sc.nextLine();

        }
        catch (Exception e)
        {
            System.out.println("ERRO: ARQUIVO NÃO ENCONTRADO");
            e.printStackTrace();
        }

    }

    // Método para imprimir os detalhes de uma música
    public static void printMusica(String[] campos)
    {
        int pos = 0;

        System.out.println("Nome: " + campos[pos]);
        pos++;

        System.out.println("Artistas: " + campos[pos]);
        pos++;

        System.out.println("Album: " + campos[pos]);
        pos++;

        System.out.println("Data: " + campos[pos]);
        pos++;

        System.out.println("Foto do album: " + campos[pos]);
        pos++;

        System.out.println("Duração: " + (Integer.parseInt(campos[pos]) / 60000) + " m " + (Integer.parseInt(campos[pos]) / 1000) % 60 + " s");
        pos++;

        System.out.println("Explícito: " + campos[pos]);
        pos++;

        System.out.println("Genres: " + campos[pos]);
        pos++;

        System.out.println("Tempo: " + campos[pos]);
        pos++;

        System.out.println("Label: " + campos[pos]);
        pos++;

        System.out.println("Key: " + campos[pos]);
        pos++;

        System.out.println("Time Signature: " + campos[pos]);
        pos++;
    }

    // Função auxiliar para retornar o valor máximo entre dois números
    public static int max(int num1, int num2)
    {
        if (num1 > num2)
        {
            return (num1);
        }

        return (num2);
    }

    // Método para realizar a pesquisa de uma palavra nos campos de uma música
    public boolean pesquisa(String frase, String palavra)
    {

        Hash tabela = new Hash();
        Scanner sc = new Scanner(System.in);

        tabela.badChar(palavra);

        int i = 0;

        while (i < (frase.length() - palavra.length()))
        {
            int o = palavra.length() - 1;

            while (o >= 0 && palavra.charAt(o) == frase.charAt(i + o))
            {
                o--;
            }

            if (o < 0)
            {
                System.out.println("\n\nACHOU");
                printMusica(fixMusica(frase));
                System.out.println("\n\nCONTINUAR? \n- SIM\n- NÃO");

                if (!sc.nextLine().equals("SIM"))
                {
                    return (true);
                }
                else
                {
                    return (false);
                }
            }
            else
            {
                i = i + max(1, o - tabela.getPos(frase.charAt(i + o)));
            }

        }

        return (false);

    }

    // Método para remover as aspas de uma string
    public static String fixString(String linha)
    {

        String tmp = "";

        for (int i = 0; i < linha.length(); i++)
        {
            if (linha.charAt(i) != '"')
            {
                tmp = tmp + linha.charAt(i);
            }
        }

        return (tmp);

    }

    // Método para ajustar os campos de uma música
    public static String[] fixMusica(String musica)
    {

        String[] content = musica.split(",");

        Vector<String> campos = new Vector<String>();
        int i = 0;

        if (content[i].charAt(0) == '"')
        {
            String tmp = fixString(content[i++]);
            int contador = 0;

            while (content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp = tmp + ", " + content[i];
                i++;
                contador++;
            }

            tmp = tmp + ", " + content[i];
            i++;

            campos.add(tmp);

        }
        else
        {
            campos.add(fixString(content[i]));
            i++;
        }

        if (content[i].charAt(0) == '"')
        {
            String tmp = fixString(content[i++]);
            int contador = 0;

            while (content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp = tmp + ", " + fixString(content[i]);
                i++;
                contador++;
            }

            tmp = tmp + ", " + fixString(content[i]);
            i++;

            campos.add(tmp.toString());

        }
        else
        {
            campos.add(fixString(content[i]));
            i++;
        }

        if (content[i].charAt(0) == '"')
        {
            String tmp = content[i++];
            int contador = 0;

            while (content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp = tmp + ", " + fixString(content[i]);
                i++;
                contador++;
            }

            tmp = tmp + ", " + fixString(content[i]);
            i++;

            campos.add(tmp);
        }
        else
        {
            campos.add(fixString(content[i]));
            i++;
        }

        if (content[i].length() > 4)
        {
            if (content[i].length() > 7)
            {
                campos.add(content[i]);
                i++;
            }
            else
            {
                campos.add(content[i] + "-01");
                i++;
            }
        }
        else
        {
            campos.add(content[i] + "-01-01");
            i++;
        }

        campos.add(content[i]);
        i++;

        campos.add(content[i]);
        i++;

        campos.add(content[i]);
        i++;

        if (!content[i].equals(""))
        {

            if (content[i].charAt(0) == '"')
            {
                String tmp = fixString(content[i++]);

                while (content[i].charAt(content[i].length() - 1) != '"')
                {
                    tmp = tmp + ", " + fixString(content[i]);
                    i++;
                }

                tmp = tmp + ", " + fixString(content[i]);
                i++;

                campos.add(tmp);

            }
            else
            {
                campos.add(fixString(content[i]));
                i++;
            }
        }
        else
        {
            i++;
        }

        float num = Float.parseFloat(content[i]);
        i++;

        if (num > 300)
        {
            campos.add(String.valueOf(num / 1000));
        }
        else
        {
            campos.add(String.valueOf(num));
        }

        if (content[i].charAt(0) == '"')
        {
            String tmp = content[i++];
            int contador = 0;

            while (content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp = tmp + ", " + fixString(content[i]);
                i++;
                contador++;
            }

            tmp = tmp + ", " + fixString(content[i]);
            i++;

            campos.add(tmp);

        }
        else
        {
            campos.add(fixString(content[i]));
            i++;
        }

        campos.add(content[i]);
        i++;

        campos.add(content[i]);
        i++;

        String[] resp = new String[campos.size()];
        for (int p = 0; p < campos.size(); p++)
        {
            resp[p] = campos.get(p);
        }

        return (resp);

    }

}
