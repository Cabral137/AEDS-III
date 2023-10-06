import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main 
{
    
    public static void main(String[] args) throws Exception
    {

        Binario  bin = new Binario(); 
        Intercal inc = new Intercal();
        int acao = 0;

        System.out.println("\n\tTrabalho Prático - AEDS III - Spotify Top 10000 - Victor Cabral\n");

        while(acao != 7)
        {

            System.out.println("\nOpções:\n" +
                               "1 - Carregar CSV\n" +
                               "2 - Pesquisar música\n" +
                               "3 - Adicionar música\n" +
                               "4 - Excluir música\n" +
                               "5 - Atualizar música\n" +
                               "6 - Intercalação\n" +
                               "7 - Sair\n");            

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try
            {
                acao = Integer.parseInt(br.readLine());
            }
            catch(Exception e)
            {
                acao = 7;
            }

            switch (acao)
            {
                case 1:
                bin.CarregarCSV();
                break;

                case 2:
                bin.PesquisarMusicaID();
                break;

                case 3:
                bin.AddMusica();
                break;

                case 4:
                bin.ExcluirMusica();
                break;

                case 5:
                bin.AtualizarMusica();
                break;

                case 6:
                inc.balanceada(100, 5);
                break;

            }        

        }

    }

}
