import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class Binario
{

    public static String juntarLista (String [] lista)
    {
        String tmp = "";

        for(int i = 0; i < lista.length; i++)
        {
            tmp = tmp + lista[i] + ";;";
        }

        return (tmp);

    }

    public static String [] separarLista (String lista)
    {
        return(lista.split(";;"));
    }

    public byte[] toByteArray() throws IOException
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.writeInt(this.getID());
        
        dos.writeUTF(this.getName());

        dos.writeUTF(juntarLista(this.getArtists()));
        
        dos.writeUTF(this.getAlbumName());

        SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
        dos.writeInt(Integer.parseInt(format.format(this.getReleaseDate())));

        dos.writeUTF(this.getAlbumImage());

        dos.writeInt(this.getDuration());

        dos.writeBoolean(this.getExplicit());

        dos.writeUTF(juntarLista(this.getGenres()));

        dos.writeFloat(this.getTempo());

        dos.writeUTF(juntarLista(this.getLabel()));

        dos.writeByte(this.getKey());

        dos.writeByte(this.getTimeSignature());

        return (baos.toByteArray());

    }

    public void fromByteArray(byte ba[]) throws IOException
    {

        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        this.setID()

    }

    

}
