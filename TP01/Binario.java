import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.FileInputStream;

class Binario
{

    public static String juntarLista (String [] lista)
    {
        if(lista == null)
        {
            return("---");
        }
        else
        {
            String tmp = "";

            for(int i = 0; i < lista.length; i++)
            {
                if(lista[i] != null)
                {

                    if((i + 1) < lista.length)
                    {
                        tmp = tmp + lista[i] + ";";
                    }
                    else
                    {
                        tmp = tmp + lista[i];
                    }
                   
                }

                
            }

            return (tmp);
        }


    }

    public static int tamanho (String [] array)
    {

        int tamanho = 0;

        if(array != null)
        {

            for (int i = 0; i < array.length; i++)
            {
                if(array[i] != null)
                {
                    tamanho = tamanho + array[i].length();
                }
                
            }

        }

        return (tamanho);

    }

    public static String [] separarLista (String lista)
    {
        return(lista.split(";"));
    }

    public byte[] toByteArray(Musica music) throws Exception
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeShort(music.getID());

        dos.writeUTF(music.getName());

        dos.writeUTF(juntarLista(music.getArtists()));
        
        dos.writeUTF(music.getAlbumName());

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        dos.writeInt(Integer.parseInt(format.format(music.getReleaseDate())));

        dos.writeUTF(music.getAlbumImage());

        dos.writeInt(music.getDuration());

        dos.writeBoolean(music.getExplicit());

        dos.writeUTF(juntarLista(music.getGenres()));

        dos.writeFloat(music.getTempo());

        dos.writeUTF(juntarLista(music.getLabel()));

        dos.writeByte(music.getKey());

        dos.writeByte(music.getTimeSignature());

        return (baos.toByteArray());

    }

    public Musica fromByteArray(byte ba[]) throws Exception
    {

        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);
        Musica music = new Musica();

        music.setName(dis.readUTF());

        music.setArtists(separarLista(dis.readUTF()));

        music.setAlbumName(dis.readUTF());

        SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat format2 = new SimpleDateFormat("dMMyyyy");
        int tmp = dis.readInt();

        if(tmp > 9999999)
        {
            music.setReleaseDate(format1.parse(Integer.toString(tmp)));
        }
        else
        {
            music.setReleaseDate(format2.parse(Integer.toString(tmp)));
        }

        music.setAlbumImage(dis.readUTF());

        music.setDuration(dis.readInt());

        music.setExplicit(dis.readBoolean());

        music.setGenres(separarLista(dis.readUTF()));

        music.setTempo(dis.readFloat());

        music.setLabel(separarLista(dis.readUTF()));

        music.setKey(dis.readByte());

        music.setTimeSignature(dis.readByte());

        return(music);

    }

    public void CarregarCSV () throws Exception
    {

        try
        {
            File excluir = new File ("SpotifyMusic.hex");
            excluir.delete();
        }
        catch(Exception e)
        {
            System.out.println("\n\n\tERRO: Não foi possível carregar o CSV\n\n");
        }

        BufferedReader scF  = new BufferedReader(new InputStreamReader(new FileInputStream("SpotifyMusic.csv"),"UTF-8"));
        RandomAccessFile rf = new RandomAccessFile("SpotifyMusic.hex", "rw");
        int contador = 0;
        rf.writeInt(0);

        while(scF.ready())
        {

            String linha = scF.readLine();

            if(linha.charAt(0) != ',')
            {
                String smp [] = linha.split(",");  
                        
                Musica music = new Musica();
                music = music.preencherObjeto(smp);
                music.setID(contador);

                byte [] tmp = toByteArray(music);
                rf.writeBoolean(false);
                rf.writeShort(tmp.length);
                rf.write(tmp);

                long ponteiro = rf.getFilePointer();          // Atualiza o primeiro byte do arquivo com o ultimo ID inserido
                rf.seek(0);
                rf.writeInt(contador);
                rf.seek(ponteiro);

                contador++;
            }

        }

        System.out.println("\n\n\tARQUIVOS CARREGADOS\n\n");

    }

    public Musica LerMusicaID (int ID)
    {

        Musica music = new Musica();

        try 
        {
            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "r");
            ra.readInt();

            long filePointer = ra.getFilePointer();

            while(filePointer < ra.length())
            {

                if(!ra.readBoolean())
                {
                    int tamanho = ra.readShort();
                    int IDtmp = ra.readShort();

                    if(ID == IDtmp)
                    {
                        byte [] musicArray = new byte [tamanho - 2];
                        ra.read(musicArray);
                        music = fromByteArray(musicArray);
                        return(music);
                    }
                    else
                    {
                        filePointer = filePointer + (tamanho + 3);
                        ra.seek(filePointer); 
                    }

                }
                else
                {
                    int tamanho = ra.readShort();
                    ra.readShort();
                    filePointer = filePointer + (tamanho + 3);
                    ra.seek(filePointer);
                }

            }   

            ra.close();

        } 
        catch (Exception e) 
        {
            System.out.println("\n\n\tERRO: Não foi possível ler a música\n\n");
        }


        return(null);

    }

    public void PesquisarMusicaID () throws Exception
    {

        Scanner sc = new Scanner(System.in);

        System.out.print(" - ID da música: ");
        int ID = Integer.parseInt(sc.nextLine());


        RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "rw");

        if(ra.readInt() < ID)
        {
            System.out.println("\n\n\tNÃO EXISTE UMA MÚSICA COM ESSE ID\n\n");
        }
        else
        {

            Musica music = LerMusicaID(ID);

            if(music == null)
            {
                System.out.println("\n\n\tNÃO EXISTE UMA MÚSICA COM ESSE ID\n\n");
            }
            else
            {
                music.setID(ID);
                music.imprimir();    
            }

        }


    }

    public void inserirMusica (Musica music, boolean read)
    {

        try
        {
            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "rw");

            if(read)
            {
                int id = ra.readInt();
                ra.writeInt(id + 1);
                music.setID(id);
            }

            ra.seek(ra.length());

            byte [] tmp = toByteArray(music);

            ra.writeBoolean(false);
            ra.writeShort(tmp.length);
            ra.write(tmp);

                        
        }
        catch(Exception e)
        {
            System.out.println("\n\nERRO: Não foi possível inserir a música");
        }
        

    }

    public void AddMusica () throws Exception   
    {

        Scanner sc = new Scanner(System.in);
        Musica music = new Musica();
        String tmp = "";

        try
        {
            System.out.print("\n - Nome: ");
            music.setName(sc.nextLine());

            System.out.print("\n - Artistas: ");
            music.setArtists(sc.nextLine().split(","));

            System.out.print("\n - Album: ");
            music.setAlbumName(sc.nextLine());

            System.out.print("\n - Data de Lançamento: ");
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            music.setReleaseDate(format.parse(sc.nextLine()));

            System.out.print("\n - Link Imagem do Album: ");
            music.setAlbumImage(sc.nextLine());

            System.out.print("\n - Duração: ");
            music.setDuration(Integer.parseInt(sc.nextLine()));

            System.out.print("\n - Explícito: ");
            System.out.println("\n - SIM\n - NÃO\n");
            tmp = sc.nextLine();

            if(tmp.equals("SIM"))
            {
                music.setExplicit(true);
            }
            else
            {
                music.setExplicit(false);
            }

            System.out.print("\n - Generos: ");
            music.setGenres(sc.nextLine().split(","));

            System.out.print("\n - Tempo: ");
            music.setTempo(Float.parseFloat(sc.nextLine()));

            System.out.print("\n - Label: ");
            music.setLabel(sc.nextLine().split(","));

            System.out.print("\n - Key: ");
            music.setKey(Byte.parseByte(sc.nextLine()));

            System.out.print("\n - Time Signature: ");
            music.setTimeSignature(Byte.parseByte(sc.nextLine()));

            sc.close();
            inserirMusica(music, true);
        }
        catch(Exception e)
        {
            System.out.println("\n\nERRO: Parâmetro incorreto\n\n");
        }


    }

    public void ExcluirMusica()
    {

        try 
        {

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "rw");

            System.out.print("\n\n - ID da música: ");
            int ID = Integer.parseInt(br.readLine());

            ra.readInt();
            long filePointer = ra.getFilePointer();

            while(filePointer < ra.length())
            {
                long tmp = ra.getFilePointer();

                boolean verify = ra.readBoolean();
                int tamanho = ra.readShort();

                if(ra.readShort() == ID)
                {

                    if(verify)
                    {
                        System.out.println("\n\n\tNÃO EXISTE UMA MÚSICA COM ESSE ID\n\n");
                        break;
                    }

                    String name = ra.readUTF();
                    System.out.println("\n - Quer excluir " + name + "? ");
                    System.out.println(" - SIM\n - NÃO\n\n");

                    if(br.readLine().equals("SIM"))
                    {
                        ra.seek(tmp);
                        ra.writeBoolean(true);
                        System.out.println("\n\n\tMúsica Excluída\n\n");
                    }
                    
                    break;

                }
                else
                {
                    filePointer = ra.getFilePointer() + tamanho - 2;
                    ra.seek(filePointer);
                }

            }  

            ra.close();

        } 
        catch (Exception e) 
        {
            System.out.println("ERRO: Não foi possível excluir a música");
        }
        
    }

    public void ExcluirMusica(int ID)
    {

        try 
        {
            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "rw");

            ra.readInt();
            long filePointer = ra.getFilePointer();

            while(filePointer < ra.length())
            {
                long tmp = ra.getFilePointer();

                ra.readBoolean();
                int tamanho = ra.readShort();

                if(ra.readShort() == ID)
                {
                    ra.seek(tmp);
                    ra.writeBoolean(true);                    
                    break;
                }
                else
                {
                    filePointer = ra.getFilePointer() + tamanho - 2;
                    ra.seek(filePointer);
                }

            }  

            ra.close();

        } 
        catch (Exception e) 
        {
            System.out.println("\n\n\tERRO: Não foi possível excluir o registro\n\n");
        }
        
    }

    public void ChangeMusic (Musica music, int campo, boolean tam)
    {

        if(!tam)
        {
            ExcluirMusica(music.getID());
            inserirMusica(music, false);
        }
        else
        {
            try 
            {

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

                RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "rw");

                ra.readInt();
                long filePointer = ra.getFilePointer();

                while(filePointer < ra.length())
                {

                    ra.readBoolean();
                    long tmp = ra.getFilePointer();
                    int tamanho = ra.readShort();

                    if(ra.readShort() == music.getID())
                    {
                        ra.seek(tmp);  
                        byte [] array = toByteArray(music);
                        ra.writeShort(array.length);
                        ra.write(array);
                    }
                    else
                    {
                        filePointer = ra.getFilePointer() + tamanho - 2;
                        ra.seek(filePointer);
                    }

                }  

                ra.close();
            }
            catch(Exception e)
            {
                System.out.println("\n\n\tERRO: Não foi possível atualizar o registro\n\n");  
            }

        }


        

    }

    public void AtualizarMusica ()
    {

        try 
        {

            Scanner sc = new Scanner(System.in);

            System.out.print(" - ID da música: ");
            int ID = Integer.parseInt(sc.nextLine());

            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "rw");

            if(ra.readInt() < ID)
            {
                System.out.println("\n\n\tNÃO EXISTE UMA MÚSICA COM ESSE ID\n\n");
            }
            else
            {

                Musica music = LerMusicaID(ID);

                if(music == null)
                {
                    System.out.println("\n\n\tNÃO EXISTE UMA MÚSICA COM ESSE ID\n\n");
                }
                else
                {
                    music.setID(ID);
                    music.imprimir();    



                    System.out.println("\n - Qual campo deseja mudar? (1 - 12) \n");
                        
                    int campo = Integer.parseInt(sc.nextLine());
                    boolean tamanho = false;

                    switch (campo) 
                    {
                        case 1:
                        System.out.print("Nome: ");
                        String tmp = sc.nextLine();
                        if(music.getName().length() == tmp.length())
                        {
                            tamanho = true;
                        }
                        music.setName(tmp);
                        break;
                        
                        case 2:
                        System.out.print("Artistas: ");
                        tmp = sc.nextLine();
                        music.setArtists(tmp.split(","));
                        break;

                        case 3:
                        System.out.print("Album: ");
                        tmp = sc.nextLine();
                        if(music.getAlbumName().length() == tmp.length())
                        {
                            tamanho = true;
                        }
                        music.setAlbumName(tmp);
                        break;

                        case 4:
                        System.out.print("Data: ");
                        tmp = sc.nextLine(); tamanho = true;
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        music.setReleaseDate(format.parse(tmp));
                        break;

                        case 5:
                        System.out.print("Foto do Album: ");
                        tmp = sc.nextLine();
                        if(music.getAlbumImage().length() == tmp.length())
                        {
                            tamanho = true;
                        }
                        music.setAlbumImage(tmp);
                        break;

                        case 6:
                        System.out.print("Duração: ");
                        tmp = sc.nextLine(); tamanho = true;
                        music.setDuration(Integer.parseInt(tmp));
                        break;

                        case 7:
                        System.out.print("Explícito ");
                        tmp = sc.nextLine(); tamanho = true;
                        if(tmp.equals("SIM"))
                        {
                            music.setExplicit(true);
                        }
                        else
                        {
                            music.setExplicit(false);
                        }
                        break;

                        case 8:
                        System.out.print("Gêneros: ");
                        tmp = sc.nextLine(); 
                        music.setGenres(tmp.split(","));
                        break;

                        case 9:
                        System.out.print("Tempo: ");
                        tmp = sc.nextLine(); tamanho = true;
                        music.setTempo(Float.parseFloat(tmp));
                        break;

                        case 10:
                        System.out.print("Label: ");
                        tmp = sc.nextLine();
                        music.setLabel(tmp.split(","));
                        break;

                        case 11:
                        System.out.print("Key: ");
                        tmp = sc.nextLine(); tamanho = true;
                        music.setKey(Byte.parseByte(tmp));
                        break;

                        case 12:
                        System.out.print("Time Signature: ");
                        tmp = sc.nextLine(); tamanho = true;
                        music.setTimeSignature(Byte.parseByte(tmp));
                        break;

                        default:
                        System.out.println("\n\n\tEssa não é uma opção\n\n");
                        break;

                    }

                    ChangeMusic(music, campo, tamanho);
                }
            }

        } 
        catch (Exception e) 
        {
            System.out.println("\n\n\tERRO: Não foi possível atualizar o registro\n\n");
        }

    }

}
