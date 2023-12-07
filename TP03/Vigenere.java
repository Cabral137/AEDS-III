import java.util.Scanner;

public class Vigenere
{

    public static String geraChave (String frase, String chave)
    {

        String resp = "";
        int i = 0;
        int cont = 0;

        while(cont != frase.length())
        {
            if(i == chave.length())
            {
                i = 0;
            }


            if(frase.charAt(cont) >= 'A' && frase.charAt(cont) <= 'Z')
            {
                resp = resp + Character.toUpperCase(chave.charAt(i));
            }
            else
            {
                if(frase.charAt(cont) >= 'a' && frase.charAt(cont) <= 'z')
                {
                    resp = resp + Character.toLowerCase(chave.charAt(i));
                }
                else
                {
                    resp = resp + chave.charAt(i);
                }
            }

            cont++;
            i++;
        }

        return(resp);

    }

    public static String cipher (String frase, String chave)
    {

        String tmp = "";

        for(int i = 0; i < frase.length(); i++)
        {
            int aux = 0;
        
            if(frase.charAt(i) > chave.charAt(i))
            {
                aux = ((frase.charAt(i)) - (chave.charAt(i)));
                System.out.println(frase.charAt(i) + "(" + (int) frase.charAt(i) + ")" +" | " + chave.charAt(i) + "(" + (int) chave.charAt(i) + ")" + " = " + aux);
            }
            else
            {
                aux = ((chave.charAt(i)) - (frase.charAt(i)));
                System.out.println(frase.charAt(i) + "(" + (int) frase.charAt(i) + ")" +" | " + chave.charAt(i) + "(" + (int) chave.charAt(i) + ")" + " = " + aux);
            }

            if(Character.isUpperCase(frase.charAt(i)))
            {
                aux = aux + 'A';
                System.out.println("\t" + aux);
            }
            else
            {
                aux = aux + 'a';
                System.out.println("\t" + aux);
            }

            tmp = tmp + ((char) aux );

        }

        return(tmp);

    }

    public static String decipher (String frase, String chave)
    {

        String tmp = "";

        for(int i = 0; i < frase.length(); i++)
        {
            int aux = 0;

            if(frase.charAt(i) > chave.charAt(i))
            {
                aux = ((frase.charAt(i)) - (chave.charAt(i)));
                System.out.println(frase.charAt(i) + "(" + (int) frase.charAt(i) + ")" +" | " + chave.charAt(i) + "(" + (int) chave.charAt(i) + ")" + " = " + aux);
            }
            else
            {
                aux = ((chave.charAt(i)) - (frase.charAt(i)));
                System.out.println(frase.charAt(i) + "(" + (int) frase.charAt(i) + ")" +" | " + chave.charAt(i) + "(" + (int) chave.charAt(i) + ")" + " = " + aux);
            }
        
            if(Character.isUpperCase(frase.charAt(i)))
            {
                aux = (aux) - 'A';
            }
            else
            {
                aux = aux - 'a';
            }



            tmp = tmp + ((char) aux );
            
        }

        return(tmp);

    }

    public static String cipher2 (String frase, String chave)
    {
        String tmp = "";

        for(int i = 0; i < frase.length(); i++)
        {
            int aux = 0;

            if(Character.isUpperCase(frase.charAt(i)))
            {
                aux = ((frase.charAt(i) + chave.charAt(i)) % 26) + 'A';
            }
            else
            {
                aux = ((frase.charAt(i) + chave.charAt(i)) % 26) + 'a';
            }

            tmp = tmp + ((char) aux );

        }

        return(tmp);
    }

    public static String decipher2 (String frase, String chave)
    {
        String tmp = "";

        for(int i = 0; i < frase.length(); i++)
        {
            int aux = 0;

            if(Character.isUpperCase(frase.charAt(i)))
            {
                aux = (((frase.charAt(i) - chave.charAt(i)) + 26) % 26) + 'A';
            }
            else
            {
                aux = (((frase.charAt(i) - chave.charAt(i)) + 26) % 26) + 'a';
            }

            tmp = tmp + ((char) aux );

        }

        return(tmp);
    }

    public static void main(String[] args) 
    {
    
        Scanner sc = new Scanner(System.in);

        String chave = sc.nextLine();
        String frase = sc.nextLine();

        String adgasdg = cipher2(frase, geraChave(frase, chave));

        System.out.println("\n\n\n" + adgasdg + "       " + geraChave(frase, chave) +  "\n\n\n");

        System.out.println(decipher2(adgasdg, geraChave(frase, chave)));


    }
    
}
