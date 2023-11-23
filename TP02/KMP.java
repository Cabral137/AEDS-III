import java.io.RandomAccessFile;
import java.util.Scanner;

public class KMP
{

	public static int [] criarVetor (String palavra)
	{

	    int [] pos = new int [palavra.length()];
		    
	    for(int i = 1; i < palavra.length(); i++)
		{
		           
		    for(int j = 0; j < i; j++)
	        {
		               
	            if(palavra.charAt(i) == palavra.charAt(j))
		        {
		                  
		            int contador = 0;
					int tmp = j;
    		               
    		        while(tmp > -1)
		            {
		               
    	                if(palavra.charAt(i - contador) == palavra.charAt(tmp))
        	            {
    		                contador++;
        		        }
        		        else
        		        {
        		            contador = 0;
    		                break;
    		            }
        	               
        	            tmp--;
    		        }
    		               
					if(contador > pos[i])
					{
						pos[i] = contador;
					}
		               
	            }
		           
		    }
		           
		}

		return(pos);
	}

	public static void pesquisa () 
	{
		try 
        {
            Scanner sc = new Scanner(System.in);

            System.out.print("\nNome do Arquivo: ");
            String path   = sc.nextLine();
            RandomAccessFile ra = new RandomAccessFile ("./" + path, "r");

            String frase = ra.readLine();

            System.out.print("\nFrase para pesquisar: ");
            String palavra = sc.nextLine();

            pesquisa(frase, palavra);
        } 
        catch (Exception e) 
        
        {
            System.out.println("ERRO: ARQUIVO NÃO ENCONTRADO");
        }


	}

	public static void pesquisa (String frase, String palavra)
	{

		try 
		{
			Scanner sc = new Scanner(System.in);
			boolean encontrou = false;   

			int [] pos = criarVetor(palavra);
			int o = 0;

			for(int i = 0; i < frase.length(); i++)
			{

				if(palavra.charAt(o) == frase.charAt(i))
				{
					o++;

					if(o == palavra.length())
					{
						System.out.println("\nACHOU");
						System.out.println("Posição: " + i);
						System.out.println("\n\nCONTINUAR? \n- SIM\n- NÃO");
						encontrou = true;

						if(!sc.nextLine().equals("SIM"))
						{
							break;
						}

						o = 0;
						
					}

				}
				else
				{
					i = i + (o - pos[o]);					
					o = pos[o];
				}

			}

			if(!encontrou)
			{
				System.out.println("\nNÃO EXISTE ESSE PADRÃO NO ARQUIVO");
			}
		} 
		catch (Exception e) 
		{
			System.out.println("ERRO: ERRO NA PESQUISA");
			e.printStackTrace();
		}


	}

	public static void main(String[] args) 
	{
		pesquisa();
	}

}
