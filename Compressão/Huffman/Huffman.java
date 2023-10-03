import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        if(no.getLeft() == null && no.getRigth() == null && Character.isLetter(no.getElemento()))
        {
            System.out.println(no.getElemento() + ": " + linha);
            return;
        }

        printTabela(no.getLeft(),  linha + "1");
        printTabela(no.getRigth(), linha + "0");

    }

    public static void printCompress (Node no, String linha, char element)
    {

        if(no != null)
        {
            if(element == no.getElemento())
            {
                System.out.print(linha);
            }
            else
            {
                printCompress(no.getLeft(),  linha + 0, element);
                printCompress(no.getRigth(), linha + 1, element);
            }

        }

    }

    public static void main(String[] args) 
    {

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
            String linha = br.readLine();

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

            for(int i = 0; i < linha.length(); i++)
            {
                printCompress(raiz, "", linha.charAt(i));
            }
            

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
}
