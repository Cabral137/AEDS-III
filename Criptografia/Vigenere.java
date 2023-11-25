import java.util.Scanner;

public class Vigenere
{

    public static String geraChave (String frase, String chave)
    {

        String resp = chave;
        int tam = frase.length();
        int i = 0;

        while(resp.length() != frase.length())
        {
            if(i == chave.length())
            {
                i = 0;
            }

            resp = resp + chave.charAt(i);
            i++;
        }

        return(resp);

    }

    public static String cipher (String frase, String chave)
    {

        String tmp = "";

        for(int i = 0; i < frase.length(); i++)
        {
            int aux = ((frase.charAt(i) + chave.charAt(i)) % 26);
        
            aux = aux + 'A';

            tmp = tmp + ((char) aux );
        }

        return(tmp);

    }

    public static void main(String[] args) 
    {
    
        Scanner sc = new Scanner(System.in);

        String chave = sc.nextLine();
        String frase = sc.nextLine();

        System.out.println(cipher(frase, geraChave(frase, chave)));

    }
    
}
