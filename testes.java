import java.util.Vector;


public class Main
{
    
        public static String getMenor (int [] array)
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
            
            String resp = menor + ";" + pos;
            
            return(resp);
            
        }
    
        public static int [] ordenarMatriz(Vector<int[]> matriz)
        {
    
            int [] tmp  = new int [matriz.size()];
            int [] resp = new int [(matriz.size() * matriz.get(0).length)]; // numero de registros * caminhos = numero total de registros
            int contador = 0;
            int pos = 0;
            
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
    
    
                for(int i = 0; i < tmp.length; i++)
                {
                    System.out.println(tmp[i]);
                }
                
                System.out.println();
    
                for(int o = 0; o < tmp.length - 1; o++)
                {
                    String [] tmp2 = getMenor(tmp).split(";");
                    
                    resp[contador] = Integer.parseInt(tmp2[0]);
                    pos = Integer.parseInt(tmp2[1]);
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
            
            int [] array3 = {2,3,5,6,7};
            matriz.add(array3);
            
            int [] print = ordenarMatriz(matriz);
            
            for(int i = 0; i < print.length; i++)
            {
                System.out.print(print[i]);
            }
        }
}
