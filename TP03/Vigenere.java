import java.io.RandomAccessFile;
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
                resp = resp + Character.toLowerCase(chave.charAt(i));
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

            if(Character.isLetter(frase.charAt(i)))
            {
                if(Character.isUpperCase(frase.charAt(i)))
                {
                    aux = (frase.charAt(i) + (chave.charAt(i) - 'A'));

                    if(aux > 'Z')
                    {
                        aux = 'A' + (aux - 'Z');
                    }

                }
                else
                {
                    aux = (frase.charAt(i) + (chave.charAt(i) - 'a'));

                    if(aux > 'z')
                    {
                        aux = 'a' + (aux - 'z');
                    }
                }
            }
            else
            {
                if(Character.isDigit(frase.charAt(i)))
                {
                    aux = (frase.charAt(i) + ((chave.charAt(i) - 'a') % 9));

                    if(aux > '9')
                    {
                        aux = '/' + (aux - '9');
                    }

                }
                else
                {
                    aux = frase.charAt(i);
                }
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

            if(Character.isLetter(frase.charAt(i)))
            {
                if(Character.isUpperCase(frase.charAt(i)))
                {
                    aux = (frase.charAt(i) - (chave.charAt(i) - 'A'));

                    if(aux < 'A')
                    {
                        aux = 'Z' + (aux - 'A');
                    }

                }
                else
                {
                    aux = (frase.charAt(i) - (chave.charAt(i) - 'a'));

                    if(aux < 'a')
                    {
                        aux = 'z' + (aux - 'a');
                    }

                }
            }
            else
            {
                if(Character.isDigit(frase.charAt(i)))
                {
                    aux = (frase.charAt(i) - ((chave.charAt(i) - 'a') % 9));

                    if(aux < '0')
                    {
                        aux = ':' - ('0' - aux);
                    }

                }
                else
                {
                    aux = frase.charAt(i);
                }
            }

            tmp = tmp + ((char) aux);

        }

        return(tmp);
    }

    public static void frase ()
    {
        try
        {
            Scanner sc = new Scanner (System.in);

            System.out.println();

            System.out.print("Frase: ");
            String frase = sc.nextLine();

            System.out.print("Chave: ");
            String chave = geraChave(frase, sc.nextLine());

            System.out.println("\n");

            System.out.println("Frase Original: " + frase);
            System.out.println("Chave Adaptada: " + chave);

            System.out.println("\n");

            String tmp = cipher(frase, chave);
            
            System.out.println("Frase Criptografada: " + tmp);
            System.out.println("Frase Descriptogrfada: " + decipher(tmp, chave));
        }
        catch(Exception e)
        {
            System.out.println("ERRO: Erro ao criptografar/descriptografar a frase");
        }
    }

    public static void arquivo ()
    {
        try
        {
            Scanner sc = new Scanner (System.in);

            System.out.println();

            System.out.print("Nome do Arquivo: ");
            String nome = sc.nextLine();

            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/" + nome + ".txt", "r");

            String frase = "";

            while(ra.getFilePointer() < ra.length())
            {
                frase = frase + ra.readLine() + "\n";
            }

            System.out.print("Chave: ");
            String chave = geraChave(frase, sc.nextLine());

            System.out.println("\n");

            String cifra   = cipher(frase, chave);

            ra = new RandomAccessFile("./ARQUIVOS/" + nome + "Cifrado.txt", "rw");
            ra.writeBytes(cifra);

            System.out.println("Deseja decifrar o arquivo?\n" +
                                 "1 - SIM\n" +
                                 "2 - NÃO\n");

            if(Integer.parseInt(sc.nextLine()) == 1)
            {
                String decifra = decipher(cifra, chave);
                ra = new RandomAccessFile("./ARQUIVOS/" + nome + "Decifrado.txt", "rw");
                ra.writeBytes(decifra);
            }

            System.out.println("\nFIM");

        }
        catch(Exception e)
        {
            System.out.println("ERRO: Erro ao criptografar/descriptografar o arquivo");
            e.printStackTrace();
        }
    }

    public void run() 
    {
    
        try
        {

            System.out.println("\n\tTrabalho Prático III - AEDS III (Vigenère) - Victor Cabral\n");

            Scanner sc = new Scanner (System.in);

            System.out.println("O que deseja criptografar? \n" +
                               "1 - Frase\n" +
                               "2 - Arquivo\n" +
                               "0 - Sair\n");

            int acao = Integer.parseInt(sc.nextLine());

            switch (acao) 
            {
                case 1:
                frase();
                break;
            
                case 2:
                arquivo();
                break;
            
            }

        }
        catch(Exception e)
        {
            System.out.println("ERRO: Opção inválida");
        }

    }
    
}
