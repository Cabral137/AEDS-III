import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Vector;

class Node
{

    private int  quant;
    private Node left;
    private Node rigth;
    private char elemento;

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

    public static Vector <Character> diffChars (String linha)
    {

        Vector <Character> resp = new Vector<Character> ();

        for(int i = 0; i < linha.length (); i++)
        {
            if(!resp.contains(linha.charAt(i)))
            {
                resp.add(linha.charAt(i));
            }
        }

        return (resp);

    }

    public static Vector <Integer> getQuant (String linha, Vector <Character> vetorChar)
    {
        Vector <Integer> resp = new Vector <Integer> ();

        for(int i = 0; i < vetorChar.size(); i++)
        {
            resp.add(0);
        }

        for(int i = 0; i < linha.length (); i++)
        {
            for(int j = 0; j < vetorChar.size(); j++)
            {
                if(linha.charAt(i) == vetorChar.get(j))
                {
                    resp.set(j, (resp.get(j) + 1));
                }
            }
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

    public static void printTabela (Node no, String linha)
    {
        if(no != null)
        {
            if(no.getLeft() == null && no.getRigth() == null)
            {
                System.out.println(no.getElemento() + ": " + linha);
                return;
            }

            printTabela(no.getLeft(),  linha + "0");
            printTabela(no.getRigth(), linha + "1");
        }

    }

    public static void printCompress (Node no, String linha, char element, String [] resp)
    {

        if(no != null)
        {
            if(element == no.getElemento())
            {
                System.out.println("ACHOU"); resp[0] = linha;
            }
            else
            {
                printCompress(no.getLeft(),  linha + 0, element, resp);
                printCompress(no.getRigth(), linha + 1, element, resp);
            }

        }

    }

    public void printDecompressed (Node no)
    {

        if(no != null)
        {

            if(no.getLeft() == null && no.getRigth() == null)
            {
                System.out.print(no.getElemento());
            }
            else
            {
                try
                {
                    RandomAccessFile ra = new RandomAccessFile("./SpotifyMusic.txt", "r");
                    ra.seek(pos);

                    if(ra.readByte() == '0')
                    {
                        this.pos = this.pos + 1;
                        this.printDecompressed (no.getLeft());
                    }
                    else
                    {
                        this.pos = this.pos + 1;
                        this.printDecompressed (no.getRigth());
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

        }

    }

    public static void readbits ()
    {

        try
        {
            RandomAccessFile ra = new RandomAccessFile("./SpotifyMusic.csv", "r");

            String bits = "1110000";

            String sequencia = "0010101011100101011111001010010111110100101010000001010101010010101";

            for(int i = 7; i < sequencia.length(); i = i + 8)
            {
                String porta = sequencia.substring(i);

                System.out.println("\n\n");
                System.out.println(sequencia);
                System.out.println(porta);
            }


            System.out.println((char)Integer.parseInt(bits, 2));
            System.out.println(Integer.toBinaryString((int) 'p'));


        }
        catch(Exception e)
        {

        }

    }

    public static void main(String[] args) 
    {

        Huffman hf = new Huffman ();

        try
        {

            RandomAccessFile ra = new RandomAccessFile("./SpotifyMusic.csv", "r");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
            String linha = ra.readLine();

            for(int i = 0; i < 10; i++)
            {
                linha = linha + ra.readLine();
            }

            Vector <Character> vetorChar  = diffChars (linha);
            Vector <Integer>   vetorQuant = getQuant  (linha, vetorChar);

            Vector <Node> lizt = new Vector <Node> ();

            for(int i = 0; i < vetorChar.size(); i++)
            {
                lizt.add(new Node (vetorQuant.get(i), vetorChar.get(i)));
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
            
            String comp = "";

            for(int i = 0; i < 10; i++)
            {
                String [] resp = {""};
                printCompress(raiz, "", linha.charAt(i), resp);
                comp = comp + resp[0];
            }

            System.out.println(comp);

            for(int i = 0; i < comp.length(); i = i + 8)
            {
                if(i + 8 < comp.length())
                {
                    System.out.println(comp.substring(i, i+8));
                    System.out.println((char)Integer.parseInt(comp.substring(i, i+8), 2) );
                }
                else
                {
                    
                    String fix = comp.substring(i, comp.length());

                    while(fix.length() != 8)
                    {
                        fix = fix + 0;
                    }

                    System.out.println(fix);
                    System.out.println((char)Integer.parseInt(fix));
                }
                
            }

            /*
            printTabela(raiz, "");

            while(true)
            {
                hf.printDecompressed(raiz);
            }


            */


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
}
