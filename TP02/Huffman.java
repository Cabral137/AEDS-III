import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;

class Node
{
    // Definição da classe Node para representar os nós da árvore Huffman
    private int   quant;       // Quantidade de ocorrências do caractere na árvore
    private Node  left;        // Nó à esquerda na árvore
    private Node  rigth;       // Nó à direita na árvore
    private char  elemento;    // Caractere associado ao nó
    public String caminho;     // Caminho na árvore (código Huffman) para o caractere

    // Construtor
    Node(int quant, char elemento)
    {
        this.quant = quant;
        this.elemento = elemento;
        this.left = null;
        this.rigth = null;
    }

    //Getters and Setters
    public void setLeft (Node insert)
    {
        this.left = insert;
    }

    public Node getLeft ()
    {
        return(this.left);
    }

    public void setRigth (Node insert)
    {
        this.rigth = insert;
    }

    public Node getRigth ()
    {
        return(this.rigth);
    }

    public void setQuant (int insert)
    {
       this.quant = insert;
    }

    public int getQuant ()
    {
       return(this.quant);
    }    

    public void setElemento (char insert)
    {
       this.elemento = insert;
    }

    public char getElemento ()
    {
       return(this.elemento);
    }    

}

public class Huffman
{
    long pos;

    // Construtor
    Huffman()
    {
        
    }

    // Método para contar a quantidade de ocorrências de cada caractere em uma string
    public static int[] quantChar(String linha)
    {
        int[] resp = new int[256];

        for (int i = 0; i < linha.length(); i++)
        {
            resp[(int) linha.charAt(i)]++;
        }

        return resp;
    }

    // Método para encontrar o índice do nó com a menor quantidade em uma lista de nós
    public static int getMenor(Vector<Node> lizt)
    {
        int menor = lizt.get(0).getQuant();
        int resp = 0;

        for (int i = 0; i < lizt.size(); i++)
        {
            if (menor > lizt.get(i).getQuant())
            {
                menor = lizt.get(i).getQuant();
                resp = i;
            }
        }

        return resp;
    }

    // Método para preencher o caminho (código Huffman) para um determinado caractere na árvore
    public static void preencheCaminho(Node no, String linha, Node element)
    {
        if (no != null)
        {
            if (element.getElemento() == no.getElemento())
            {
                element.caminho = linha;
            }
            else
            {
                preencheCaminho(no.getLeft(), linha + 0, element);
                preencheCaminho(no.getRigth(), linha + 1, element);
            }
        }
    }

    // Método para obter o código Huffman para cada caractere na árvore
    public static void getElemento(Node no, String linha, String[] resp)
    {
        if (no != null)
        {
            if (no.getLeft() == null && no.getRigth() == null)
            {
                resp[(int) no.getElemento()] = linha;
                return;
            }

            getElemento(no.getLeft(), linha + "0", resp);
            getElemento(no.getRigth(), linha + "1", resp);
        }
    }

    // Método para construir a árvore Huffman a partir de uma lista de quantidade de ocorrência de caracteres
    public static Node fazerArvore(int[] quant)
    {
        Vector<Node> lizt = new Vector<Node>();

        for (int i = 0; i < quant.length; i++)
        {
            if (quant[i] != 0)
            {
                lizt.add(new Node(quant[i], (char) i));
            }
        }

        Node raiz = new Node(0, '-');

        while (lizt.size() > 1)
        {
            Node tmp1 = lizt.remove(getMenor(lizt));
            Node tmp2 = lizt.remove(getMenor(lizt));

            Node notmp = new Node(0, '-');

            notmp.setLeft(tmp1);
            notmp.setRigth(tmp2);
            notmp.setQuant(tmp1.getQuant() + tmp2.getQuant());

            raiz = notmp;

            lizt.add(notmp);
        }

        for (int i = 0; i < lizt.size(); i++)
        {
            preencheCaminho(raiz, "", lizt.get(i));
        }

        return raiz;
    }

    // Método para gerar a tabela de códigos Huffman para cada caractere
    public static String[] fazerTabela(int[] quant)
    {
        String[] tabela = new String[256];

        Node raiz = fazerArvore(quant);
        getElemento(raiz, "", tabela);

        return tabela;
    }

    // Método para comprimir um arquivo usando a codificação Huffman
    public static int[] comprimir(String path)
    {
        try
        {
            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/TESTE/" + path, "r");
            String content = "";
            String compress = "";

            while (ra.getFilePointer() != ra.length())
            {
                content = content + ra.readLine() + "~";
            }

            int[] quant = quantChar(content);
            String[] tabela = fazerTabela(quant);

            for (int i = 0; i < content.length(); i++)
            {
                compress = compress + tabela[(int) content.charAt(i)];
            }

            String write = "";

            for (int i = 0; i < compress.length(); i = i + 8)
            {
                if (i + 8 < compress.length())
                {
                    write = write + (char) Integer.parseInt(compress.substring(i, i + 8), 2);
                }
                else
                {
                    String fix = compress.substring(i, compress.length());

                    while (fix.length() != 8)
                    {
                        fix = fix + 0;
                    }

                    write = write + (char) Integer.parseInt(fix);
                }
            }

            path = path.substring(0, path.indexOf(".")) + ".hff";

            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/COMPRESSED/Compressed" + path, "rw");
            wa.writeBytes(write);

            return quant;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new int[0];
    }

    // Método para descomprimir um arquivo usando a árvore Huffman
    public static void descomprimir(int[] quant, String path)
    {
        try
        {
            path = path.substring(0, path.indexOf(".")) + ".hff";

            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/COMPRESSED/Compressed" + path, "r");
            String texto = "";

            while (ra.getFilePointer() != ra.length())
            {
                String tmp = ra.readLine();

                for (int i = 0; i < tmp.length(); i++)
                {
                    String fix = Integer.toBinaryString((int) tmp.charAt(i));

                    while (fix.length() < 8)
                    {
                        fix = '0' + fix;
                    }

                    texto = texto + fix;
                }
            }

            Node raiz = fazerArvore(quant);
            Node tmp = raiz;
            String resp = "";

            for (int i = 0; i < texto.length(); i++)
            {
                if (texto.charAt(i) == '0')
                {
                    tmp = tmp.getLeft();
                }
                else
                {
                    tmp = tmp.getRigth();
                }

                if (tmp.getLeft() == null && tmp.getRigth() == null)
                {
                    resp = resp + tmp.getElemento();
                    tmp = raiz;
                }
            }

            System.out.println(resp);

            while (resp.contains("~"))
            {
                int barraN = resp.indexOf("~");
                resp = resp.substring(0, barraN) + "\n" + resp.substring(barraN + 1, resp.length());
            }

            path = path.substring(0, path.indexOf(".")) + ".hff";

            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/DECOMPRESSED/Decompressed" + path, "rw");
            wa.writeBytes(resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Método principal que solicita o nome do arquivo, comprime e descomprime
    public void run()
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("\nNome do arquivo: ");
        String path = sc.nextLine();

        int[] quant = comprimir(path);

        System.out.println("\n\nDeseja descomprimir o arquivo?\n- SIM\n- NÃO\n\n");
        if (sc.nextLine().equals("SIM"))
        {
            descomprimir(quant, path);
        }
    }
}
