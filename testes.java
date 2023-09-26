import java.util.Vector;


public class Main
{
    
        public static int getMenor (int [] array)
        {
            int menor = array[0];
            int pos   = 0;
            
            for(int i = 0; i < array.length; i++)
            {
                
                if(menor > array[i])
                {
                    menor = array[i];
                    pos   = i;
                }
            }
            
            return(menor); 
            
        }
    
    public static int [] ordenarMatriz(Vector<int[]> matriz)
    {
    
        int [] tmp  = new int [matriz.size()];
        int [] resp = new int [(matriz.size() * matriz.get(0).length)]; // caminhos * numero de registros = numero total de registros
        int contador = 0;
          
        int [] ponteiros = new int [matriz.size()];
    
        for(int j = 0; j < matriz.get(0).length * matriz.size(); j++)
        {                
                
            for(int i = 0; i < matriz.size(); i++)
            {
                if(ponteiros[i] < matriz.get(0).length)
                {
                    tmp[i] = matriz.get(i)[ponteiros[i]];
                }
                else
                {
                    tmp[i] = 0xFFFFFFF;
                }          
            }

            int tmp2 = getMenor(tmp);
            resp[contador] = tmp2;
            
            for(int p = 0; p < tmp.length; p++)
            {
                if(tmp2 == tmp[p])
                {
                    ponteiros[p] = ponteiros[p] + 1;
                    //System.out.println(p);
                }
            }
    
            contador++;
    
        }
    
        return(resp);
    
    }
        
        public static void main (String[] args) 
        {
            
            Vector<int[]> matriz = new Vector<int[]>(3);
            
            int [] array1 = {1,10,3,17,2};
            matriz.add(array1);
            
            int [] array2 = {5,7,8,11,9};
            matriz.add(array2);
            
            int [] array3 = {6,13,19,4,15};
            matriz.add(array3);
            
            int [] print = ordenarMatriz(matriz);
            
            for(int i = 0; i < print.length; i++)
            {
                System.out.print(print[i] + "-");
            }
        }
}
