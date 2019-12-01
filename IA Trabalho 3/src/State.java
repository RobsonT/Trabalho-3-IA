import searches.GeneticAlgorithm;
import searches.SimulatedAnnealing;

public class State {
    String initialState;
    int nnurse, temperature;

    int population, generations;
    double mutations, elitism;

//  recebe o estado inicial e o número de enfermeiros
    public State(String initialState, int nnurse, int temperature) {
        this.initialState = initialState;
        this.nnurse = nnurse;
        this.temperature = temperature;
    }

    public State(int nnurse, int population, int generations, double mutations, double elitism) {
        this.nnurse = nnurse;
        this.population = population;
        this.generations = generations;
        this.mutations = mutations;
        this.elitism = elitism;
    }

    //  chamada da busca escolhida pelo usuario
    public void solveSimulatedAnnealing() {
        SimulatedAnnealing.search(initialState, nnurse, temperature);
    }

    public void solveGeneticAlgorithm() {
        GeneticAlgorithm.search(nnurse, population, generations, mutations, elitism);
    }
}
