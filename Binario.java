import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
            return("q");
        }
        else
        {
            String tmp = "";

            for(int i = 0; i < lista.length; i++)
            {
                tmp = tmp + lista[i] + ";;";
            }

            return (tmp);
        }


    }

    public static String [] separarLista (String lista)
    {
        return(lista.split(";;"));
    }

    public byte[] toByteArray(Musica music) throws IOException
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(music.getID());
        
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

    public Musica fromByteArray(byte ba[]) throws IOException
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

        BufferedReader scF  = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\789765\\Documents\\Faculdade-main\\SpotifyMusic.csv"),"UTF-8"));
        RandomAccessFile rf = new RandomAccessFile("SpotifyMusic.hex", "rw");
        int contador = 0;

        while(scF.ready())
        {

            String smp [] = scF.readLine().split(",");  
                    
            Musica music = new Musica();
            music = music.preencherObjeto(smp);
            music.setID(contador); contador++;

            byte [] tmp = toByteArray(music);

            long tamanho = tmp.length;

            rf.writeLong(tamanho);
            rf.writeBoolean(false);
            rf.write(tmp);

        }



    }

}
