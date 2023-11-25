import java.util.Scanner;

public class Caesar
{

    public static String cipher (String frase, int chave)
    {

        String tmp = "";

        for(int i = 0; i < frase.length(); i++)
        {
            if((frase.charAt(i) >= 'a' && frase.charAt(i) <= 'z') | (frase.charAt(i) >= 'A' && frase.charAt(i) <= 'Z'))
            {
                tmp = tmp + ((char) (frase.charAt(i) + chave));
            }
            else
            {
                tmp = tmp + frase.charAt(i);
            }
        }

        return(tmp);

    }

    public static void main(String[] args) 
    {
    
        Scanner sc = new Scanner(System.in);

        int    chave = Integer.parseInt(sc.nextLine());
        String frase = sc.nextLine();

        System.out.println(cipher(frase, chave));


    }
    
}
