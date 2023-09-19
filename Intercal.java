import java.io.RandomAccessFile;

public class Intercal 
{

    long filePointer;

    Intercal ()
    {
        this.filePointer = 0;
    }
    
    public Musica [] readArray (int registros)
    {
        Binario bin = new Binario();

        Musica [] array = new Musica [registros];
        Musica music = new Musica ();
        
        try 
        {
            RandomAccessFile ra = new RandomAccessFile("./SpotifyMusic.hex", "r");
            ra.seek(filePointer);
            int contador = 0;

            while(contador < registros)
            {
                ra.readBoolean();
                int tamanho = ra.readShort();
                int id = ra.readShort();

                byte [] musicArray = new byte [tamanho - 2];
                ra.read(musicArray);
                music = bin.fromByteArray(musicArray);
                music.setID(id);
                array[contador] = music;
                contador++;

            }

            filePointer = ra.getFilePointer();
            ra.close();

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        

        return (array);

    }

    public void balanceada (int registros, int caminhos)
    {

        Intercal inc = new Intercal();

        try 
        {
            for(int i = 0; i < caminhos; i++)
            {
                RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/caminho" + i + ".hex", "rw");
                ra.close();
            }   
        } 
        catch (Exception e) 
        {
          e.printStackTrace();
        }

       Musica [] array = readArray(10);

        for(int i = 0; i < array.length; i++)
        {
            if(array[i] != null)
            {
                array[i].imprimir();
            }
            
        }

        System.out.println("-------------------------------------------------------------------------------------------");

        array = readArray(10);

        for(int i = 0; i < array.length; i++)
        {
            if(array[i] != null)
            {
                array[i].imprimir();
            }
            
        }

    }
    
}
