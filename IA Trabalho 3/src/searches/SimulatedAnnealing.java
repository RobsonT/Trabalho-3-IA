package searches;

public class SimulatedAnnealing {



//  gera um filho do estado passado como parametro
    private static String generateState(String state, int nNurse) {
        int n = (int) (Math.random() * ((nNurse * 21)-1));
        int value = Character.getNumericValue(state.charAt(n));
        value = (value + 1) % 2;
        char[] stateChar = state.toCharArray();
        stateChar[n] = Character.forDigit(value, 10);
        state = String.valueOf(stateChar);
        return state;
    }

//  implementacao da busca têmpera simulada
    public static void search(String initialState, int nNurse, int temper) {

//      inicializacao dos parametros temperature que representa a temperatura inicial
//      e temp que representa a temperatura atualizada
        int temperature = temper, temp = 0;

//      currentState representa o estado verificado no momento
//      value representa o quanto o estado satisfaz os requisitos
        String currentState = initialState;
        int value = 0;

//      for que inicializa com i = 350 e para quando ele chegar a 0 ou
//      se nao houver um estado para ser verificado
        for (int i = temperature; i > 0; i--) {
//          value recebe o valor de satisfacao do estado atual
            value = SearchUtils.evaluate(currentState, nNurse);

//          printa na tela o estado que esta sendo visitado, assim como a sua temperatura e avaliacao
            System.out.println("Estado visitado:");
            SearchUtils.printState(currentState, nNurse);
            System.out.println("Temperatura: " + i);
            System.out.println("Avaliação: " + value);

//          verica se o estado atual é o de aceitacao, ou seja ele satisfaz todos os requisitos
//          se o estado for de aceitacao ele é printado na tela com as suas informaçoes
            if (value == 0) {
                System.out.println("Solução encontrada!");
                SearchUtils.printState(currentState, nNurse);
                System.out.println("Temperatura: " + i);
                System.out.println("Avaliação: " + value);
            }

//          sucessor recebe um estado gerado a partir do estado atual
            String successor = generateState(currentState, nNurse);

//          deltaE recebe a diferença entre os valores de satisfaçao do estado atual e
//          do estado que ele gerou
            int deltaE = value - SearchUtils.evaluate(successor, nNurse);

//          caso o valor de deltaE seja maior ou igual a zero, o estado atual é atualizado
            if(deltaE >= 0) {
                currentState = successor;
            }
            else{
//          caso o valor de deltaE ser menor que zero sera feito um teste de continuaçao

//              n recebe um valor aleatorio
                int n = (int) (Math.random() * 100);

//              o estado atual é atualizado caso o valor de n seja menor que e^(deltaE / i)
//              euler elevado a diferença entre os valores de satisfaçao do estado atual e
//              do estado gerado, dividido pela temperatura atual
                if (n < Math.exp(deltaE/i))
                    currentState = successor;
            }
//          atualiza a temperatura
            temp = i;
        }

//      caso a temperatura nunca chegue a zero sera printado na tela o estado com a melhor
//      avaliaçao encontrado, assim como a temperatura e o seu valor de satisfaçao
        System.out.println("Estado objetivo não encontrado");
        System.out.println("Melhor Solução encontrada:");
        SearchUtils.printState(currentState, nNurse);
        System.out.println("Temperatura: " + temp);
        System.out.println("Avaliação: " + value);
    }
}
