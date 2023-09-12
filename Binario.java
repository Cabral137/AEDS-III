import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;
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
                    tmp = tmp + lista[i] + ";;";
                }
                
            }

            return (tmp);
        }


    }

    public static String [] separarLista (String lista)
    {
        return(lista.split(";;"));
    }

    public byte[] toByteArray(Musica music) throws Exception
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(music.getID());
        
        dos.writeInt(music.getName().length());
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

        music.setID(dis.readInt());

        music.setName(dis.readUTF());

        music.setArtists(separarLista(dis.readUTF()));

        music.setAlbumName(dis.readUTF());

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        music.setReleaseDate(format.parse(Integer.toString(dis.readInt())));

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
                rf.writeInt(tmp.length);
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
                    int tamanho = ra.readInt();
                    int IDtmp = ra.readInt();

                    if(ID == IDtmp)
                    {
                        ra.seek(filePointer = filePointer + 2);
                        byte [] musicArray = new byte [tamanho-14];
                        ra.read(musicArray);
                        music = fromByteArray(musicArray);
                        return(music);
                    }

                }
                else
                {
                    int tamanho = ra.readInt();
                    filePointer = filePointer + tamanho;
                    ra.seek(filePointer);
                }

            }   

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

        inserirMusica(music);

    }

}
