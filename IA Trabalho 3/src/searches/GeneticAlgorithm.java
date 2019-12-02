package searches;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
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
//  Printa os estados de uma geração na tela
    private static void printResult(List<String> population){
        int index = 0;
        for (String state: population){
            System.out.println("índice: " + index +
                    " Individuo: " + state + " fitness: " +
                    SearchUtils.evaluate(state, state.length() / 21));
            index++;
        }
    }

    //  modifica o estado passado como parametro na posiçao indicada
    private static String mutation(String state, int position) {
        char[] stateChar = state.toCharArray();
        int value = Character.getNumericValue(state.charAt(position));
        value = (value + 1) % 2;
        stateChar[position] = Character.forDigit(value, 10);
        state = String.valueOf(stateChar);
        return state;
    }

    //  gera uma população de tamanho n com estados de tamanho size
    private static List generateInitialPopulation(int n, int size) {
        List<String> population = new ArrayList<>();
        for (int i = 0; i < n; i++)
            population.add(generateState(size));
        return population;
    }

    //  gera dois estados a partir da combinação dos  dois estados passados como parametros
    private static String[] crossover(String genome1, String genome2) {
        int cut = (int) (Math.random() * (genome1.length() - 1));
        String[] result = new String[2];

        char[] g1 = genome1.toCharArray();
        char[] g2 = genome2.toCharArray();
        char aux;
        for (int i = cut; i < genome1.length(); i++) {
            aux = g1[i];
            g1[i] = g2[i];
            g2[i] = aux;
        }

        result[0] = String.valueOf(g1);
        result[1] = String.valueOf(g1);
        return result;
    }

    //  avalia cada elemento da populaçao
    private static List<Integer> fitness(List<String> population) {
        List<Integer> fits = new ArrayList<>();
        int nnurse = population.get(0).length() / 21;
        for (String s : population)
            fits.add(SearchUtils.evaluate(s, nnurse));
        return fits;
    }

    //  seleciona os reprodutores para criação dos individuos da procima geraçao
    private static List<String> naturalSelection(List<String> population, int populationSize) {
        List<String> selection = new ArrayList<>();
        List<Integer> fts = fitness(population);
        for (int i = 0; i < populationSize; i++) {
            int selected = (int) (Math.random() * (populationSize - 1));
            selection.add(population.get(selected));
        }
        return selection;
    }


    //  verifica se na populaçao algum estado é objetivo
    private static boolean testObjective(List<String> population) {
        List<Integer> fts = fitness(population);
        for (int i = 0; i < fts.size(); i++) {
            if (fts.get(i) == 0) {
                System.out.println("Encontrado estado objetivo");
                return true;
            }
        }
        return false;
    }

    //  implementacao da busca com algoritmo genético
    public static void search(int nNurse, int populationSize, int generationsSize, double mutationsProb, double elitism) {

//      criaçao da populaçao inicial
        List<String> populalation = generateInitialPopulation(populationSize, nNurse * 21);
        List<String> newPopulation;

//      criaçao e verificaçao de cada geraçao
        for (int i = 0; i < generationsSize; i++) {

//      verifica se existe estado objetivo na populaçao
            if (testObjective(populalation)) {
                return;
            }

            newPopulation = new ArrayList<>();

            //Ordena os individuos pelo fitness
            String[] pop = new String[populalation.size()];
            pop = populalation.toArray(pop);
            SearchUtils.quickSort(pop, 0, populationSize - 1, nNurse);
            populalation = Arrays.asList(pop);

            System.out.println("Geração atual: " + i);
            printResult(populalation);
            //Define quanto individuos serao pegos pelo elitismo
            int eliteSize = (int) ((elitism / 100) * populationSize);
            //pega os individuos que permanecerao para  a proxima geraçao
            for (int k = 0; k < eliteSize; k++) {
                newPopulation.add(populalation.get(k));
            }

//          difiniçao do total de casais e quais eles serao
            List<String> breeders = naturalSelection(populalation, (populationSize - eliteSize));

//          cria uma nova geraçao de estados a partir dos casais existentes
            for (int j = 0; j < (populationSize - eliteSize); j+=2) {
                int n = (j > (populationSize - eliteSize) - 2) ? j+1 : j-1;
                if(n < 0)
                    n = 0;
                while(n < (populationSize - eliteSize)-1 && breeders.get(j).equals(breeders.get(n)))
                    n++;
                String[] children = crossover(breeders.get(j), breeders.get(n));
                newPopulation.add(children[0]);
                newPopulation.add(children[1]);
            }

//          verifica se existe estado objetivo na nova populaçao
            if (testObjective(newPopulation)) {
                return;
            }

//          seleciona de modo aleatorio estados da nova populaçao que irao sofrer mutaçao
            for (int j = 0; j < newPopulation.size(); j++) {
                String state = newPopulation.get(j);
                if (Math.random() * 100 < mutationsProb) {
                    int value = (int) (Math.random() * (nNurse * 21) - 1);
                    state = mutation(state, value);
                    newPopulation.set(j, state);
                }
//              atualiza a populaçao
                populalation = newPopulation;
            }
        }
        System.out.println("Estado objetivo não encontrado");
    }
}
