import java.util.Scanner;

class Main 
{
    
    public static void main(String[] args) throws Exception
    {

        Scanner sc  = new Scanner(System.in);
        Binario bin = new Binario(); 
        int acao = 0;

        System.out.println("\n\tTrabalho Prático - AEDS III - Spotify Top 10000 - Victor Cabral\n");

        while(acao != 6)
        {

            System.out.println("\nOpções:\n" +
                               "1 - Carregar CSV\n" +
                               "2 - Ler musica\n" +
                               "3 - Adicionar musica\n" +
                               "4 - Excluir musica\n" +
                               "5 - Atualizar musica\n" +
                               "6 - Sair\n");

            acao = Integer.parseInt(sc.nextLine());

            switch (acao)
            {
                case 1:
                bin.CarregarCSV();
                break;

                case 2:
                bin.PesquisarMusicaID();
                break;

                case 3:
                break;

                case 4:
                break;

                case 5:
                break;

            }

        }

    }

}
