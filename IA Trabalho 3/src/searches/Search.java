package searches;

public class Search {

    private static String generateState(String state) {
        int n = (int) (Math.random() * 210);
        int value = Character.getNumericValue(state.charAt(n));
        value = (value + 1) % 2;
        char[] stateChar = state.toCharArray();
        stateChar[n] = Character.forDigit(value, 10);
        state = String.valueOf(stateChar);
        return state;
    }

    public static void simulatedAnnealing(String initialState, int nNurse) {
        int temperature = 350, temp = 0;
        String currentState = initialState;
        int value = 0;
        for (int i = temperature; i > 0; i--) {
            value = SearchUtils.evaluate(currentState, nNurse);
            System.out.println("Estado visitado:");
            SearchUtils.printState(currentState, nNurse);
            System.out.println("Temperatura: " + i);
            System.out.println("Avaliação: " + value);
            if (value == 0) {
                System.out.println("Solução encontrada!");
                SearchUtils.printState(currentState, nNurse);
                System.out.println("Temperatura: " + i);
                System.out.println("Avaliação: " + value);
            }
            String successor = generateState(currentState);
            int deltaE = value - SearchUtils.evaluate(successor, nNurse);
            if(deltaE >= 0)
                currentState = successor;
            else{
                int n = (int) (Math.random() * 100);
                if (n < Math.exp(deltaE/i))
                    currentState = successor;
            }
            temp = i;
        }
        System.out.println("Estado objetivo não encontrado");
        System.out.println("Melhor Solução encontrada:");
        SearchUtils.printState(currentState, nNurse);
        System.out.println("Temperatura: " + temp);
        System.out.println("Avaliação: " + value);
    }

    public static void geneticAlgorithm(String initialState, int nnurse) {
    }
}
