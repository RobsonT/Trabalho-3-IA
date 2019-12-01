import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//      inicializacao dos parametros utilizados na solucao
        Scanner scanner = new Scanner(System.in);
        String initialState = "";
        int choose, nnurse, chooseState, temperature = 360, population = 40, generations = 120;
        double mutation = 5, elitism = 25;
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

//          recebendo o numero total de enfermeiros
            System.out.println("Insira o número de enfermeiros:");
            nnurse = scanner.nextInt();

//          recebendo o estado inicial do problema
            do {
                System.out.println("Tipo de entrada:");
                System.out.println("1 - Inserir manualmente");
                System.out.println("2 - Gerar automicamente");
                chooseState = scanner.nextInt();
            } while (chooseState != 1 && chooseState != 2);
            if(choose == 1) {
                if (chooseState == 1) {
                    System.out.println("Inserir o estado inicial");
                    do {
                        //                  Recebe o estado informado pelo usuário
                        initialState = scanner.next();
                    } while (initialState.length() != 21 * nnurse);
                    System.out.println("Inserir a temperatura");
                    temperature = scanner.nextInt();
                } else {
                    //              Gera um estado inicial aleatorio
                    while (initialState.length() < 21 * nnurse) {
                        double x = Math.random();
                        if (x < 0.5)
                            initialState += "0";
                        else
                            initialState += "1";
                    }
                }
                //          inicializando o problema com o estado inicial recebido pelo cliente
                problem = new State(initialState, nnurse, temperature);

                //          resolucao do problema de acordo com a escolha de busca escolhido pelo cliente
                problem.solveSimulatedAnnealing();
            }else{
                if (chooseState == 1) {
                    System.out.println("Inserir a população inicial");
                    population=scanner.nextInt();
                    System.out.println("Inserir o número de gerações");
                    generations=scanner.nextInt();
                    System.out.println("Inserir a probabilidade de mutação");
                    mutation=scanner.nextDouble();
                    System.out.println("Inserir a porcentagem de elitismo");
                    elitism=scanner.nextDouble();
                }
                //          inicializando o problema com o estado inicial recebido pelo cliente
                problem = new State(nnurse, population, generations, mutation, elitism);

                //          resolucao do problema de acordo com a escolha de busca escolhido pelo cliente
                problem.solveGeneticAlgorithm();
            }
        }
    }
}
