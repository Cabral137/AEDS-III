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

	public void pesquisa ()
	{
		
		Scanner sc = new Scanner(System.in);

		System.out.print("\nNome do Arquivo: ");
		String frase   = sc.nextLine();

		System.out.println("\nFrase para pesquisar: ");
		String palavra = sc.nextLine();

		pesquisa(frase, palavra);

	}

	public void pesquisa (String frase, String palavra)
	{
		       
		int [] pos = criarVetor(palavra);
		int o = 0;

		for(int i = 0; i < frase.length(); i++)
		{

			if(palavra.charAt(o) == frase.charAt(i))
			{
				o++;

				if(o == palavra.length())
				{
					System.out.println("ACHOU");
					break;
				}

			}
			else
			{
				i = i + (o - pos[o]);					
				o = pos[o];
			}

		}

	}

}
