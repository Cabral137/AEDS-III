import java.io.RandomAccessFile;
import java.util.Vector;

public class Intercal 
{

    long filePointer;

    Intercal ()
    {
        this.filePointer = 0;
    }

    public static void swap (Musica [] array, int i, int j)
    {
        Musica temp = array [i];
        array[i]    = array [j];
        array[j]    = temp;
    }

    public static int partition (Musica [] array, int high, int low)
    {

        Musica pivot = array [high];

        int i = (low - 1);

        for(int j = low; j <= high - 1; j++)
        {
            if(array[j].getID() < pivot.getID())
            {
                i++;
                swap(array, i, j);
            }
        }

        swap (array, i + 1, high);

        return(i + 1);

    }

    public static void quickSort (Musica [] array, int high, int low)
    {
        if(low < high)
        {
            int p1 = partition(array, high, low);

            quickSort(array, high, p1 - 1);
            quickSort(array, p1 + 1, low);
        }
    }

    public static void quickSort (Musica [] array)
    {
        quickSort(array, 0, array.length - 1);
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
                boolean lapide = ra.readBoolean();
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

    public void writeCaminho (String path, Musica [] array)
    {

        try 
        {
            RandomAccessFile rf = new RandomAccessFile (path, "rw");
            Binario bin = new Binario ();
            rf.seek(rf.length());
            
            for(int i = 0; i < array.length - 1; i++)
            {
                byte [] tmp = bin.toByteArray(array[i]);

                rf.writeBoolean(false);
                rf.writeShort(tmp.length);
                rf.write(tmp);
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }

    public void primeiraParte (int registros, int caminhos)
    {

        try 
        {
            for(int i = 0; i < caminhos; i++)
            {
                RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/caminho" + i + ".hex", "rw");
                ra.close();
            }   

            for(int j = 0; j < 5; j++)
            {
                for(int i = 0; i < caminhos; i++)
                {
                    Musica [] array = readArray(registros);

                    quickSort (array);
                    String path = "./ARQUIVOS/caminho" + i + ".hex";
                    writeCaminho(path, array);
                }
            }

        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }

    }

    public Musica [] ordenarMatriz(Vector<Musica[]> matriz)
    {

        Musica [] tmp  = new Musica [matriz.size()];
        Musica [] resp = new Musica [(matriz.size() * matriz.get(0).length)]; // numero de registros * caminhos = numero total de registros
        int contador = 0;
        int pos = 0;
        
        int [] ponteiros = new int [matriz.size];

        for(int j = 0; j < matriz.get(0).length; j++)
        {
            for(int i = 0; i < matriz.size(); i++)
            {
                tmp[i] = matriz.get(i)[ponteiros[j]];
            }

            for(int o = 1; o < tmp.length; o++)
            {
                if(tmp[o].getID() < tmp[o-1].getID())
                {
                    resp[contador] = tmp[o];
                    pos = o;
                }

            }

            contador++;
            ponteiros[pos]++;

        }

        return(resp);

    }

    public void balanceada (int registros, int caminhos)
    {

        primeiraParte (registros, caminhos);
        
        Vector<Musica[]> array = new Vector<Musica[]>(caminhos);

        try 
        {
          
            for(int i = 0; i < caminhos; i++)
            {
                RandomAccessFile ra = new RandomAccessFile("./ARQUIVOS/caminho" + i + ".hex", "rw");
                Musica [] tmp = readArray(registros/caminhos);

                array.add(tmp);

            }

            Musica [] tmp = ordenarMatriz (array);

        } 
        catch (Exception e) 
        {
            // TODO: handle exception
        }



    }
    
}
