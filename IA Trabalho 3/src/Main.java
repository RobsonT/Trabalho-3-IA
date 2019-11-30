import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//      inicializacao dos parametros utilizados na solucao
        Scanner scanner = new Scanner(System.in);
        String initialState = "";
        int choose, nnurse, chooseState;
        State problem;

//      menu para receber a escolha do usuario
        while (true) {
            do {
                System.out.println("Digite a sua escolha:");
                System.out.println("1 - Tempera simulada");
                System.out.println("2 - Algoritmo genético");
                System.out.println("3 - Sair");
                choose = scanner.nextInt();
            } while (choose < 1 && choose > 3);

            if (choose == 3)
                break;

            System.out.println("Insira o número de enfermeiros:");
            nnurse = scanner.nextInt();

//              recebendo o estado inicial do problema
            do {
                System.out.println("Tipo de entrada:");
                System.out.println("1 - Inserir manualmente");
                System.out.println("2 - Gerar automicamente");
                chooseState = scanner.nextInt();
            } while (chooseState != 1 && chooseState != 2);
            if (chooseState == 1) {
                System.out.println("Inserir o estado inicial");
                do {
//                      Recebe o estado informado pelo usuário
                    initialState = scanner.next();
                } while (initialState.length() != 21 * nnurse);
            } else {
//                  Gera um estado inicial aleatorio
                while (initialState.length() < 21 * nnurse) {
                    double x = Math.random();
                    if (x < 0.5)
                        initialState += "0";
                    else
                        initialState += "1";
                }
            }
//              inicializando o problema com o estado inicial recebido pelo cliente
            problem = new State(initialState, nnurse);

//              resolucao do problema de acordo com a escolha de busca escolhido pelo cliente
            problem.solve(choose, nnurse);
        }
    }
}
