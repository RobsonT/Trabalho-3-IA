package searches;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {

//  gera um estado com o tamanho passado
    private static String generateState(int size) {
        String state = "";
        while (state.length() < size) {
            double x = Math.random();
            if (x < 0.5)
                state += "0";
            else
                state += "1";
        }
        return state;
    }

//  modifica o estado passado como parametro ja posiçao indicada
    private static String mutation(String state, int position){
        String newState = "";
        while (true){
            int n = (int) (Math.random() * state.length());
            int k = (n + position) % state.length();
            char[] stateChar = state.toCharArray();
            int value = Character.getNumericValue(state.charAt(n));
            value = (value + 1) % 2;
            stateChar[n] = Character.forDigit(value, 10);
            newState = String.valueOf(stateChar);
            if(!newState.equals(state))
                break;
        }
        return  newState;
    }

//  gera um conjunto de tamanho de estados iniciais
    private static List generateInitialPopulation(int n, int size){
        List<String> population = new ArrayList<>();
        for(int i = 0; i < n; i++)
            population.add(generateState(size));
        return population;
    }

//  gera dois estados a partir de dois estados passados como parametro
    private static String[] crossover(String genome1, String genome2){
        int cut = (int) (Math.random() * (genome1.length() - 1));
        String[] result = new String[2];

        char[] g1 = genome1.toCharArray();
        char[] g2 = genome2.toCharArray();
        for(int i = cut; i < genome1.length(); i++){
            g1[i] = g2[i];
        }
        for(int i = 0; i < cut; i++){
            g2[i] = g1[i];
        }

        System.out.println("genoma1: "+ String.valueOf(g1));
        System.out.println("genoma2: "+ String.valueOf(g1));

        result[0] = String.valueOf(g1);
        result[1] = String.valueOf(g1);
        return result;
    }

//  avalia cada elemento da populaçao
    private static List<Integer> fitness(List<String> population){
        List<Integer> fits = new ArrayList<>();
        int nnurse = population.get(0).length() / 21;
        for(String s: population)
            fits.add(SearchUtils.evaluate(s, nnurse));
        return fits;
    }

//  pega um estado aleatorio de forma que ele tenha uma boa avaliaçao na maioria das vezes
    private static String roulette(List<String> population, List<Integer> fitness){
        int totalFitness = 0;
        for(int fts: fitness)
            totalFitness += fts;
        List<Double> fractions = new ArrayList<>();
        for(int fts: fitness)
            fractions.add((double) (fts / totalFitness));

        List<Double> intervals = new ArrayList<>();
        double acum = 0;
        for(int i = 0; i < fractions.size(); i++){
            acum += i;
            intervals.add(acum);
        }

        double rouletteResult = (Math.random() * population.size()) / population.size();

        int ind = 0;
        while(ind < (intervals.size() - 1) && rouletteResult > intervals.get(ind)){
            ind++;
        }

        return population.get(ind);
    }

//  seleciona um casal de estados da populaçao utilizando a roulette
    private static List<String> naturalSelection(List<String> population, int numCouple){
        List<String> selection = new ArrayList<>();
        List<Integer> fts = fitness(population);
        for(int i = 0; i < (2 * numCouple); i++){
            selection.add(roulette(population, fts));
        }
        return selection;
    }


//  verifica se na populaçao algum estado é objetivo
    private static boolean testObjective(List<String> population){
        List<Integer> fts = fitness(population);
        for(int i = 0; i < fts.size(); i++){
            if(fts.get(i) == 0){
                System.out.println("Solução encontrada");
                return true;
            }
        }
        return  false;
    }

//  implementacao da busca com algoritmo genético
    public static void search(int nNurse, int populationSize, int generationsSize, double mutationsProb, double elitism) {

//      criaçao da populaçao inicial
        List<String> populalation = generateInitialPopulation(populationSize, nNurse * 21);
        List<String> newPopulation;

//      criançao e verificaçao de cada geraçao
        for(int i = 0; i < generationsSize; i++){

//      verifica se existe estado objetivo na populaçao
            if(testObjective(populalation)){
                return;
            }

            newPopulation = new ArrayList<>();

//          difiniçao do total de casais e quais eles serao
            int numCouple = populationSize/2;
            List<String> breeders = naturalSelection(populalation, numCouple);

//apaga
            System.out.println(numCouple);

//          cria uma nova geraçao de estados a partir dos casais existentes
            for(int j = 0; j < numCouple; i++){
                String[] children = crossover(breeders.get(j), breeders.get(j+numCouple));
                newPopulation.add(children[0]);
                newPopulation.add(children[1]);
            }

//          verifica se existe estado objetivo na nova populaçao
            if(testObjective(newPopulation)){
                return;
            }

//          seleciona de modo aleatorio estados da nova populaçao que irao sofrer mutaçao
            for(int j = 0; j < newPopulation.size(); j++){
                String state = newPopulation.get(i);
                if (Math.random() *100 < mutationsProb) {
                    int value = (int) (Math.random() * (nNurse * 21)-1);
                    state = mutation(state, value);
                    newPopulation.add(i, state);
                }
//              atualiza a populaçao
                populalation = newPopulation;
            }
        }
        System.out.println("Solução não encontrada");
    }
}
