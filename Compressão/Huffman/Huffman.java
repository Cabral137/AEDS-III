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

        for(int i = 0; i < resp.size(); i++)
        {
            System.out.println(resp.get(i));
        }

        return (resp);

    }

    public static Vector <Integer> getQuant (String linha, Vector <Character> vetorChar)
    {
        Vector <Integer> resp = new Vector <Integer> (vetorChar.size());

        for(int i = 0; i < linha.length (); i++)
        {
            for(int j = 0; j < vetorChar.size(); j++)
            {
                if(linha.charAt(i) == vetorChar.get(j))
                {
                    resp.set(j, (resp.get(i) + 1));
                    break;
                }
                else
                {
                    break;
                }
                
            }
        }

        return (resp);

    }

    public static void main(String[] args) 
    {

        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
            String linha = br.readLine();

            Vector <Character> vetorChar  = diffChars (linha);
            Vector <Integer>   vetorQuant = getQuant  (linha, vetorChar);

            for(int i = 0; i < vetorChar.size(); i++)
            {
                System.out.println(vetorChar.get(i) + " --- " + vetorQuant.get(i));
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
    
}
