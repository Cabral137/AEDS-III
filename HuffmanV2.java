import java.util.Scanner;
import java.io.RandomAccessFile;
import java.util.Vector;

class Node
{

    private int  quant;
    private Node left;
    private Node rigth;
    private char elemento;
    public  String caminho;

    Node (int quant, char elemento)
    {
        this.quant    = quant;
        this.elemento = elemento;
        this.left     = null;
        this.rigth    = null;
    }

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

    Huffman ()
    {

    }

    public static int [] quantChar (String linha)
    {

        int [] resp = new int [256];

        for(int i = 0; i < linha.length(); i++)
        {
            resp[(int) linha.charAt(i)]++;
        }

        return (resp);

    }

    public static int getMenor (Vector<Node> lizt)
    {
        int menor = lizt.get(0).getQuant();
        int resp  = 0;

        for(int i = 0; i < lizt.size(); i++)
        {
            if(menor > lizt.get(i).getQuant())
            {
                menor = lizt.get(i).getQuant();
                resp  = i;
            }
        }

        return (resp);
    }

    public static void preencheCaminho (Node no, String linha, Node element)
    {
        if(no != null)
        {
            if(element.getElemento() == no.getElemento())
            {
                element.caminho = linha;
            }
            else
            {
                preencheCaminho (no.getLeft(),  linha + 0, element);
                preencheCaminho (no.getRigth(), linha + 1, element);
            }

        }
    }
    
    public static void getElemento (Node no, String linha, String [] resp)
    {
        if(no != null)
        {
            if(no.getLeft() == null && no.getRigth() == null)
            {
                resp[(int) no.getElemento()] = linha;
                return;
            }

            getElemento(no.getLeft(),  linha + "0", resp);
            getElemento(no.getRigth(), linha + "1", resp);
        }

    }

    public Node fazerArvore (int [] quant)
    {
        Vector <Node> lizt = new Vector <Node> ();

        for(int i = 0; i < quant.length; i++)
        {
            if(quant[i] != 0)
            {
                lizt.add(new Node(quant[i], (char) i));
            }
        }

        Node raiz = new Node(0, '-');

        while(lizt.size() > 1)
        {
            Node tmp1 = lizt.remove(getMenor(lizt));
            Node tmp2 = lizt.remove(getMenor(lizt));

            Node notmp = new Node (0, '-');

            notmp.setLeft (tmp1);
            notmp.setRigth(tmp2);
            notmp.setQuant(tmp1.getQuant() + tmp2.getQuant());

            raiz = notmp;

            lizt.add(notmp);
        }

        for(int i = 0; i < lizt.size(); i++)
        {
            preencheCaminho(raiz, "", lizt.get(i));
        }

        return(raiz);
    }

    public String [] fazerTabela (int [] quant)
    {

        String [] tabela = new String [256];

        Node raiz = fazerArvore(quant);
        getElemento(raiz, "", tabela);

        return(tabela);

    }

    public int [] comprimir ()
    {

        try 
        {
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNome do arquivo: ");
            String path = sc.nextLine();

            RandomAccessFile ra = new RandomAccessFile("./" + path, "r");
            String content  = "";
            String compress = "";

            while(ra.getFilePointer() != ra.length())
            {
                content = content + ra.readLine();
            }

            int    [] quant  = quantChar(content);
            String [] tabela = fazerTabela(quant);

            for(int i = 0; i < content.length(); i++)
            {
                compress = compress + tabela[ (int) content.charAt(i) ];
            }

            String write = "";

            for(int i = 0; i < compress.length(); i = i + 8)
            {
                if(i + 8 < compress.length())
                {
                    write = write + (char)Integer.parseInt(compress.substring(i, i+8), 2);
                }
                else
                {
                    String fix = compress.substring(i, compress.length());

                    while(fix.length() != 8)
                    {
                        fix = fix + 0;
                    }

                    write = write + (char)Integer.parseInt(fix);
                }
                
            }

            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/CompHuffman.hex", "rw");

            wa.writeBytes(write);
            
            return(quant);

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

        return(new int [0]);

    }

    public void descomprimir (int [] quant)
    {

        try 
        {

            Scanner sc = new Scanner(System.in);

            //System.out.print("\nNome do arquivo: ");
            String path = "CompHuffman.hex"; //sc.nextLine();

            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/" + path, "r");
            String texto = "";

            while(ra.getFilePointer() != ra.length())
            {
                String tmp = ra.readLine();

                for(int i = 0; i < tmp.length(); i++)
                {
                    String fix = Integer.toBinaryString((int)tmp.charAt(i));

                    while(fix.length() < 8)
                    {
                        fix = '0' + fix;
                    }

                    texto = texto + fix;
                }
                
            }

            Node raiz = fazerArvore(quant);
            Node tmp  = raiz;
            String resp = "";

            for(int i = 0; i < texto.length(); i++)
            {
                if(texto.charAt(i) == '0')
                {
                    tmp = tmp.getLeft();
                }
                else
                {
                    tmp = tmp.getRigth();
                }

                if(tmp.getLeft() == null && tmp.getRigth() == null)
                {
                    resp = resp + tmp.getElemento();
                    tmp = raiz;
                }
            }

            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/DescompHuffman.txt", "rw");

            wa.writeBytes(resp);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void run ()
    {
        Scanner sc = new Scanner(System.in);
        Huffman hf = new Huffman();
        int [] quant = hf.comprimir();
        System.out.println("\nDeseja descomprimir o arquivo?\n- SIM\n- NÃO\n\n");

        if(sc.nextLine().equals("SIM"))
        {
            hf.descomprimir(quant);
        }
    }

}
