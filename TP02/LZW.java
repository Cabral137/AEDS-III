import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.Vector;

public class LZW
{
    // Método para comprimir um arquivo usando o algoritmo LZW
    public static void compress()
    {
        try 
		{
            Scanner sc = new Scanner(System.in);

            // Solicitar o nome do arquivo a ser comprimido
            System.out.print("\nNome do arquivo: ");
            String path = sc.nextLine();

            // Ler o conteúdo do arquivo e criar uma string com o arquivo inteiro separando cada linha por um '~'
            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/TESTE/" + path, "r");
            String linha = "";

            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine() + "~";
            }

            // Inicializar o dicionário com caracteres ASCII
            Vector<String> dicionario = new Vector<String>();

            for(int i = 0; i < 256; i++)
            {
                dicionario.add("" + (char) i);
            }

            String prefix = "";
            String comp   = "";

            // Iterar sobre a linha para realizar a compressão
            for(int i = 0; i < linha.length(); i++)
            {
                prefix = prefix + linha.charAt(i);

                if(!dicionario.contains(prefix))
                {
                    dicionario.add(prefix);
                    comp   = comp + (dicionario.indexOf(prefix.substring(0, prefix.length()- 1)) + " ");
                    prefix = "" + linha.charAt(i);
                }
            }

            // Adicionar o índice final ao arquivo comprimido
            if(dicionario.contains(prefix))
            {
                comp = comp + dicionario.indexOf(prefix);
            }

            // Criar o nome do arquivo comprimido com a extensão .lzw
            path = path.substring(0, path.indexOf(".")) + ".lzw";

            // Escrever a sequência comprimida no arquivo
            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/COMPRESSED/Compressed" + path, "rw");
            wa.writeBytes(comp);
        }
        catch (Exception e) 
        {
            System.out.println("ERRO: Falha ao comprimir o arquivo");
        }
    }

    // Método para descomprimir um arquivo comprimido usando o algoritmo LZW
    public static void decompress()
    {
        try 
		{
            Scanner sc = new Scanner(System.in);

            // Solicitar o nome do arquivo a ser descomprimido
            System.out.print("\nNome do arquivo: ");
            String path = sc.nextLine();

            // Substituir a extensão por .lzw ao nome do arquivo
            path = path.substring(0, path.indexOf(".")) + ".lzw";
            System.out.println(path);

            // Ler a sequência comprimida do arquivo
            RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/COMPRESSED/Compressed" + path, "r");
            String linha = ""; 

            while(ra.getFilePointer() != ra.length())
            {
                linha = linha + ra.readLine();
            }

            // Separar os índices da sequência comprimida
            String[] nums = linha.split(" ");
            String decomp  = "";
            String aux     = "";

            // Inicializar o dicionário com caracteres ASCII
            Vector<String> dicionario = new Vector<String>();

            for(int i = 0; i < 256; i++)
            {
                dicionario.add("" + (char) i);
            }

            // Iterar sobre os índices para realizar a descompressão
            for(int i = 0; i < nums.length; i++)
            {
                aux = aux + dicionario.get(Integer.parseInt(nums[i])).charAt(0);
                decomp = decomp + dicionario.get(Integer.parseInt(nums[i]));

                if(!dicionario.contains(aux))
                {
                    dicionario.add(aux);
                    aux = dicionario.get(Integer.parseInt(nums[i]));
                }
            }

            // Criar o nome do arquivo descomprimido com a extensão .lzw
            path = path.substring(0, path.indexOf(".")) + ".lzw";

            // Substituir "~" por quebras de linha no arquivo descomprimido
            while(decomp.contains("~"))
            {
                int barraN = decomp.indexOf("~");
                decomp = decomp.substring(0, barraN) + "\n" + decomp.substring(barraN + 1, decomp.length());
            }

            // Escrever a sequência descomprimida no arquivo
            RandomAccessFile wa = new RandomAccessFile("./ARQUIVOS/DECOMPRESSED/Decompressed" + path, "rw");
            wa.writeBytes(decomp);

            System.out.println("\nCompressão concluída com sucesso\n");

            sc.nextLine();
  
		} 
		catch(Exception e) 
		{
            System.out.println("ERRO: Falha ao descomprimir o arquivo");
            e.printStackTrace();
		}  
    }

    // Método principal que interage com o usuário para escolher entre compressão e descompressão
    public void run()
    {
        try 
        {
            System.out.println("\nO que deseja fazer?\n" +
                            "\n1 - Comprimir" +
                            "\n2 - Descomprimir" +
                            "\n0 - Sair\n");

            Scanner sc = new Scanner(System.in);

            switch (Integer.parseInt(sc.nextLine()))
            {
                case 1:
                    compress();
                    System.out.println("\nDeseja descomprimir o arquivo?\n" +
                                        "- SIM\n" +
                                        "- NÃO");

                    if(sc.nextLine().equals("SIM"))
                    {
                        decompress();
                    }
                    break;
                
                case 2:
                    decompress();
                    break;

                default:
                    break;
            }

        } 
        catch (Exception e) 
        {
            System.out.println("ERRO: OPÇÃO INCORRETA");
        }
    }
}
