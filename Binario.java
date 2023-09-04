import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

class Binario
{

    public byte[] toByteArray() throws IOException
    {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        
        dos.writeUTF(this.getName());

        for(int i = 0; i < this.getArtists().length; i++)
        {
            dos.writeUTF(this.getArtists()[i]);
        }
        
        dos.writeUTF(this.getAlbumName());

        //dos.writeInt(this.getReleaseDate().toString);

        dos.writeUTF(this.getAlbumImage());

        dos.writeInt(this.getDuration());

        dos.writeBoolean(this.getExplicit());

        for(int i = 0; i < this.getGenres().length; i++)
        {
            dos.writeUTF(this.getGenres()[i]);
        }

        dos.writeFloat(this.getTempo());

        for(int i = 0; i < this.getLabel().length; i++)
        {
            dos.writeUTF(this.getLabel()[i]);
        }

        dos.writeByte(this.getKey());

        dos.writeByte(this.getTimeSignature());

        return (baos.toByteArray());

    }

    public void fromByteArray(byte ba[]) throws IOException
    {

        ByteArrayInputStream bais = new ByteArrayInputStream(ba);
        DataInputStream dis = new DataInputStream(bais);

        

    }

    

}