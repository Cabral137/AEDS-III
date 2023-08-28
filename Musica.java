import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Musica 
{

    // Variaveis

    private String  name;
    private String  [] artists;
    private String  albumName;
    private Date    releaseDate;
    private String  albumImage;
    private int     duration;
    private boolean explicit;
    private String  [] genres;
    private float   tempo;
    private String  [] label;
    private byte    key;
    private byte    timeSignature;
    
    // Construtores
    
    public Musica() 
    {
        
    }
    
    public Musica(String name, String [] artists, String albumName, Date releaseDate, String albumImage, int duration, boolean explicit, String [] genres, float tempo, String [] label, byte key, byte timeSignature) 
    {
        this.key           = key;
        this.name          = name;
        this.tempo         = tempo;
        this.label         = label;
        this.genres        = genres;
        this.artists       = artists;
        this.duration      = duration;
        this.explicit      = explicit;
        this.albumName     = albumName;
        this.albumImage    = albumImage;
        this.releaseDate   = releaseDate;
        this.timeSignature = timeSignature;
    }

    // Getters and Setters

    public String getName () 
    {
        return (this.name);
    }

    public void setName (String insert) 
    {
        this.name = insert;
    }

    public String [] getArtists () 
    {
        return (this.artists);
    }

    public void setArtists (String [] insert) 
    {
        this.artists = insert;
    }

    public String getAlbumName () 
    {
        return (this.albumName);
    }

    public void setAlbumName (String insert) 
    {
        this.albumName = insert;
    }

    public Date getReleaseDate () 
    {
        return (this.releaseDate);
    }

    public void setReleaseDate (Date insert) 
    {
        this.releaseDate = insert;
    }

    public String getAlbumImage () 
    {
        return (this.albumImage);
    }

    public void setAlbumImage (String insert) 
    {
        this.albumImage = insert;
    }

    public int getDuration ()
    {
        return (this.duration);
    }

    public void setDuration (int insert) 
    {
        this.duration = insert;
    }

    public boolean isExplicit () 
    {
        return (this.explicit);
    }

    public void setExplicit (boolean insert) 
    {
        this.explicit = insert;
    }

    public String [] getGenres () 
    {
        return (this.genres);
    }

    public void setGenres (String [] insert) 
    {
        this.genres = insert;
    }

    public float getTempo () 
    {
        return (this.tempo);
    }

    public void setTempo (float insert) 
    {
        this.tempo = insert;
    }

    public String [] getLabel () 
    {
        return (this.label);
    }

    public void setLabel (String [] insert) 
    {
        this.label = insert;
    }

    public byte getKey () 
    {
        return (this.key);
    }

    public void setKey (byte insert) 
    {
        this.key = insert;
    }

    public byte getTimeSignature () 
    {
        return (this.timeSignature);
    }

    public void setTimeSignature(byte insert) 
    {
        this.timeSignature = insert;
    }

    // Funcoes


    public static Musica preencherObjeto (String [] content) throws ParseException
    {
        Musica music = new Musica();

        music.setName(content[0]);

        int i = 1;

        if(content[i].charAt(0) == '"')
        {
            String [] tmp = new String [5];

            while(content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp[i-1] = content[i]; i++;
            }

            tmp[i-1] = content[i]; i++;

            music.setArtists(tmp);

        }
        else
        {
            String [] tmp = {content[i], null};
            music.setArtists(tmp); i++;
        }

        if(content[i].charAt(0) == '"')
        {
            music.setAlbumName(content[i] + "," + content[i+1]); i++; i++;
        }
        else
        {
            music.setAlbumName(content[i]); i++;
        }

        if(content[i].length() > 4)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            music.setReleaseDate(format.parse(content[i])); i++;
        }
        else
        {
            content[i] = "01/01/"+ content[i];
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            music.setReleaseDate(format.parse(content[i])); i++;
        }

        music.setAlbumImage(content[i]); i++;

        music.setDuration(Integer.parseInt(content[i])); i++;

        if(content[i].equals("FALSE"))
        {
            music.setExplicit(false); i++;
        }
        else
        {
            music.setExplicit(true); i++;
        }

        if(content[i].charAt(0) == '"')
        {
            String [] tmp = new String [32];
            int contador = 0;

            while(content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp[contador] = content[i]; i++; contador++;
            }

            tmp[contador] = content[i]; i++;

            music.setGenres(tmp);

        }
        else
        {
            String [] tmp = {content[i], null}; i++;
            music.setGenres(tmp);
        }


        music.setTempo(Float.parseFloat(content[i])); i++;


        if(content[i].charAt(0) == '"')
        {
            String [] tmp = new String [4];
            int contador = 0;

            while(content[i].charAt(content[i].length() - 1) != '"')
            {
                tmp[contador] = content[i]; i++; contador++;
            }

            tmp[contador] = content[i]; i++;

            music.setLabel(tmp);

        }
        else
        {
            String [] tmp = {content[i], null}; i++;
            music.setLabel(tmp);
        }


        music.setKey(Byte.parseByte(content[i])); i++;

        music.setTimeSignature(Byte.parseByte(content[i])); i++;

        return(music);

    }

    public void imprimir ()
    {
        System.out.println("Nome: "    + this.getName());
        
        System.out.print("Artistas: ");
        for(int i = 0; i < this.getArtists().length; i++)
        {
            if(this.getArtists()[i] == null)
            {
                break;
            }

            System.out.print(this.getArtists()[i]);

            if(this.getArtists()[i+1] != null)
            {
                System.out.print(", ");
            }
            else
            {
                System.out.println();
            }

        }

        System.out.println("Album: " + this.getAlbumName());

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("Data: " + format.format(this.getReleaseDate()));

        System.out.println("Foto do album: " + this.getAlbumImage());

        System.out.println("Duracao: " + (this.getDuration()) + " ms");

        System.out.println("Explicito: " + this.explicit);

        System.out.print("Genres: ");
        for(int i = 0; i < this.getGenres().length; i++)
        {
            if(this.getGenres()[i] == null)
            {
                break;
            }

            System.out.print(this.getGenres()[i]);

            if(this.getGenres()[i+1] != null)
            {
                System.out.print(", ");
            }
            else
            {
                System.out.println();
            }

        }

        System.out.println("Tempo: " + this.getTempo());

        System.out.print("Label: ");
        for(int i = 0; i < this.getLabel().length; i++)
        {
            if(this.getLabel()[i] == null)
            {
                break;
            }

            System.out.print(this.getLabel()[i]);

            if(this.getLabel()[i+1] != null)
            {
                System.out.print(", ");
            }
            else
            {
                System.out.println();
            }

        }

        System.out.println("Key: " + this.getKey());

        System.out.println("Time Signature: " + this.getTimeSignature());


    }



    public static void main(String[] args) throws IOException, ParseException
    {

        BufferedReader scF  = new BufferedReader(new InputStreamReader(new FileInputStream("SpotifyMusic.csv"),"UTF-8"));

        String tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();
               tmp    = scF.readLine();

        String smp [] = tmp.split(",");


        Musica music = preencherObjeto(smp);

        music.imprimir();

    }

    

}
