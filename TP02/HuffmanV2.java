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

public class HuffmanV2
{

    long pos;

    HuffmanV2 ()
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

            printTabela(no.getLeft(),  linha + "0", resp);
            printTabela(no.getRigth(), linha + "1", resp);
        }

    }

    public String [] fazerTabela (int [] quant)
    {

        // Montando a Arvore
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

        String [] tabela = new String [256];

        getElemento(raiz, "", tabela);

        return(tabela);

    }

    public void criptografar ()
    {

        

    }
    
}
