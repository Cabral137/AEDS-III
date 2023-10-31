import java.io.BufferedReader;
import java.io.EOFException;
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

    public static Vector <Character> diffChars (String linha, Vector <Integer> vetorQuant)
    {

        Vector <Character> resp = new Vector<Character> ();

        for(int i = 0; i < linha.length (); i++)
        {
            if(!resp.contains(linha.charAt(i)))
            {
                resp.add(linha.charAt(i));
                vetorQuant.add(1);
            }
            else
            {
                vetorQuant.setElementAt(vetorQuant.get(resp.indexOf(linha.charAt(i))), resp.indexOf(linha.charAt(i)));
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
                resp[0] = linha;
            }
            else
            {
                printCompress(no.getLeft(),  linha + 0, element, resp);
                printCompress(no.getRigth(), linha + 1, element, resp);
            }

        }

    }

    public void printDecompressed (Node no, String [] resp)
    {

        if(no != null)
        {

            if(no.getLeft() == null && no.getRigth() == null)
            {
                resp[0] = resp[0] + no.getElemento();
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
                        this.printDecompressed (no.getLeft(), resp);
                    }
                    else
                    {
                        this.pos = this.pos + 1;
                        this.printDecompressed (no.getRigth(), resp);
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

    public void descomprimir (String nome)
    {


        
    }

    public void comprimir (String nome)
    {
        System.out.println(0);
        
        Huffman hf = new Huffman ();

        try
        {

            RandomAccessFile ra = new RandomAccessFile("./" + nome, "r");

            String linha = ""; 

            // for(int p = 0; p < 1000; p++)
            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine();
            }

            System.out.println(1);

            Vector <Integer>   vetorQuant = new Vector<Integer>();
            Vector <Character> vetorChar  = diffChars (linha, vetorQuant);

            Vector <Node> lizt = new Vector <Node> ();

            for(int i = 0; i < vetorChar.size(); i++)
            {
                lizt.add(new Node (vetorQuant.get(i), vetorChar.get(i)));
            }

            System.out.println(2);
            
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

            System.out.println(3);
            
            String comp = "";

            for(int i = 0; i < linha.length(); i++)
            {
                String [] resp = {""};
                printCompress(raiz, "", linha.charAt(i), resp);
                comp = comp + resp[0];
            }

            System.out.println(4);

            String write = "";

            for(int i = 0; i < comp.length(); i = i + 8)
            {
                if(i + 8 < comp.length())
                {
                    write = write + (char)Integer.parseInt(comp.substring(i, i+8), 2);
                }
                else
                {
                    
                    String fix = comp.substring(i, comp.length());

                    while(fix.length() != 8)
                    {
                        fix = fix + 0;
                    }

                    write = write + (char)Integer.parseInt(fix);
                }
                
            }

            System.out.println(5);

            RandomAccessFile writeComp = new RandomAccessFile("./" + nome + "Huffman.hex", "rw");
            writeComp.writeBytes(write);

            System.out.println(6);

        }
        catch(Exception e)
        {
            System.out.println("ERRO: Erro na compressão");
        }


    }
    
}
