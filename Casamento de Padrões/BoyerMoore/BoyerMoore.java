import java.util.Scanner;

class Hash
{
    public int  [] pos;

    public Hash ()
    {
        this.pos        = new int  [256];
    }

    public int getPos(char search)
    {
        return(this.pos[search % 256]);
    }

    public void badChar (String frase)
    {

        for(int i = 0; i < pos.length; i++)
        {
            this.pos[i] = -1;
        }

        for(int i = 0; i < frase.length() - 1; i++)
        {
            pos[frase.charAt(i) % 256] = i;
        }

    }

}

public class BoyerMoore 
{

    public void goodSufix (String frase)
    {

        

    }

    public static void badCharSearch ()
    {
        Scanner sc  = new Scanner (System.in);
        Hash tabela = new Hash();

        String frase   = "PRUBUGBWG IWFIFWIWFB OWQUFGWUFVOWUF COCACOCACOLARGWWOUBWGOUWGB";
        String palavra = "COCACOLA";
        
        tabela.badChar(palavra);

        int o = palavra.length() - 1;

        int i = palavra.length() - 1;
            
        while(i < frase.length())
        {
            System.out.println(i);

            System.out.println(palavra.charAt(o) + " --- " + frase.charAt(i));
            if(palavra.charAt(o) == frase.charAt(i))
            {
                o--;
                i--;

                if(o == 1)
                {
                    System.out.println("ACHOU");
                    break;
                }

            }
            else
            {
                int tmp = tabela.getPos(frase.charAt(i));

                if(tmp < 0)
                {
                    i = i + palavra.length();
                    o = palavra.length() - 1;
                }
                else
                {
                    i = i + (o - tabela.getPos(frase.charAt(i)));
                    o = palavra.length() - 1;
                }

            }

        }

    }

    public static void main(String[] args) 
    {
        
        badCharSearch();

    }
    
}
