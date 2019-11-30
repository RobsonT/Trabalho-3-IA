import searches.Search;

import java.util.Map;

public class State {
    String initialState;
    int nnurse;

    //  recebe o estado inicial e o número de enfermeiros
    public State(String initialState, int nnurse) {
        this.initialState = initialState;
        this.nnurse = nnurse;
    }

    //  chamada da busca escolhida pelo usuario
    public void solve(int searchType, int nnurse) {
        switch (searchType) {
            case 1:
                Search.simulatedAnnealing(initialState, nnurse);
            case 2:
                Search.geneticAlgorithm(initialState, nnurse);
            default:
                System.out.println("Tipo de busca não encontrada");
        }
    }
}
