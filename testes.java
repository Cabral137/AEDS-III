import java.util.Vector;


public class Main
{
        public static int [] ordenarMatriz(Vector<int[]> matriz)
        {
    
            int [] tmp  = new int [matriz.size()];
            int [] resp = new int [(matriz.size() * matriz.get(0).length)]; // numero de registros * caminhos = numero total de registros
            int contador = 0;
            int pos = 0;
            
            int [] ponteiros = new int [matriz.size()];
    
            for(int j = 0; j < matriz.get(0).length; j++)
            {
                for(int i = 0; i < matriz.size(); i++)
                {
                    tmp[i] = matriz.get(j)[ponteiros[i]];
                    
                    //System.out.println(ponteiros[i]);
                }
    
                for(int o = 1; o < tmp.length-1; o++)
                {
                    //System.out.println(tmp[o] + "---");
                    
                    if(tmp[o] < tmp[o-1])
                    {
                        resp[contador] = tmp[o];
                        pos = o;
                    }
    
                }
    
                contador++;
                ponteiros[pos] = ponteiros[pos] + 1;
    
            }
    
            return(resp);
    
        }
        
        public static void main (String[] args) 
        {
            
            Vector<int[]> matriz = new Vector<int[]>(3);
            
            int [] array1 = {1,2,2,6,9};
            matriz.add(array1);
            
            int [] array2 = {2,3,5,6,7};
            matriz.add(array2);
            
            int [] print = ordenarMatriz(matriz);
            
            for(int i = 0; i < print.length; i++)
            {
                System.out.println(print[i]);
            }
        }
}
