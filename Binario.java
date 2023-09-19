import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;
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

        BufferedReader scF  = new BufferedReader(new InputStreamReader(new FileInputStream("SpotifyMusic.csv"),"UTF-8"));
        RandomAccessFile rf = new RandomAccessFile("SpotifyMusic.hex", "rw");
        int contador = 0;

        while(scF.ready())
        {

            String linha = scF.readLine();

            if(linha.charAt(0) != ',')
            {
                String smp [] = linha.split(",");  
                        
                Musica music = new Musica();
                music = music.preencherObjeto(smp);
                music.setID(contador); contador++;

                byte [] tmp = toByteArray(music);

                rf.writeBoolean(false);
                rf.writeShort(tmp.length);
                rf.write(tmp);
            }

        }

    }

    public Musica LerMusicaID (int ID)
    {

        Musica music = new Musica();

        try 
        {
            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "r");

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
                    filePointer = filePointer + (tamanho + 5);
                    ra.seek(filePointer);
                }

            }   

            ra.close();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }


        return(music);

    }

    public void PesquisarMusicaID () throws Exception
    {

        Scanner sc = new Scanner(System.in);

        System.out.print("ID da música: ");
        int ID = Integer.parseInt(sc.nextLine());

        Musica music = LerMusicaID(ID);
        music.setID(ID);

        music.imprimir();


    }

    public void inserirMusica (Musica music)
    {
        
    }

    public void AddMusica () throws Exception   
    {

        Scanner sc = new Scanner(System.in);
        Musica music = new Musica();
        String tmp = "";
        
        System.out.print("\nNome: ");
        music.setName(sc.nextLine());

        System.out.print("\nArtistas:");
        music.setArtists(sc.nextLine().split(","));

        System.out.print("\nAlbum: ");
        music.setAlbumName(sc.nextLine());

        System.out.print("\nData de Lançamento: ");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        music.setReleaseDate(format.parse(sc.nextLine()));

        System.out.print("\nLink Imagem do Album: ");
        music.setAlbumImage(sc.nextLine());

        System.out.print("\nDuração: ");
        music.setDuration(Integer.parseInt(sc.nextLine()));

        System.out.print("\nExplícito: ");
        System.out.println("- SIM\n- NÃO");
        tmp = sc.nextLine();

        if(tmp.equals("SIM"))
        {
            music.setExplicit(true);
        }
        else
        {
            music.setExplicit(false);
        }

        System.out.print("\nGeneros:");
        music.setGenres(sc.nextLine().split(","));

        System.out.print("\nTempo: ");
        music.setTempo(Float.parseFloat(sc.nextLine()));

        System.out.print("\nLabel:");
        music.setLabel(sc.nextLine().split(","));

        System.out.print("\nKey: ");
        music.setKey(Byte.parseByte(sc.nextLine()));

        System.out.print("\nTime Signature: ");
        music.setTimeSignature(Byte.parseByte(sc.nextLine()));

        sc.close();
        inserirMusica(music);

    }

    public void teste ()
    {

        try 
        {

            RandomAccessFile ra = new RandomAccessFile("SpotifyMusic.hex", "r");
            long filePointer = ra.getFilePointer();

            boolean array = ra.readBoolean();

            for(int i = 0; i < 1; i++)
            {
                System.out.println(array);
            }

            ra.close();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        

    }

}
