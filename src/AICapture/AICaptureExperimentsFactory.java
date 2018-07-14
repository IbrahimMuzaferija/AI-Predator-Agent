package AICapture;

import experiments.*;
import ga.GAListener;
import ga.GeneticAlgorithm;
import ga.geneticOperators.*;
import ga.selectionMethods.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import statistics.StatisticBestAverage;
import statistics.StatisticBestInRun;

public class AICaptureExperimentsFactory extends ExperimentsFactory {

    private int populationSize;
    private int maxGenerations;
    private SelectionMethod<PredatorIndividual, AICaptureProblem> selection;
    private Recombination<PredatorIndividual> recombination;
    private Mutation<PredatorIndividual> mutation;
    private AICaptureProblem problem;
    private Experiment<AICaptureExperimentsFactory, AICaptureProblem> experiment;

    public AICaptureExperimentsFactory(File configFile) throws IOException {
        super(configFile);
    }

    @Override
    public Experiment buildExperiment() throws IOException {
        numRuns = Integer.parseInt(getParameterValue("Runs"));
        populationSize = Integer.parseInt(getParameterValue("Population size"));
        maxGenerations = Integer.parseInt(getParameterValue("Max generations"));

        //SELECTION
        switch(getParameterValue("Selection")){
            case "tournament":
                int tournamentSize = Integer.parseInt(getParameterValue("Tournament size"));
                selection = new Tournament<>(populationSize, tournamentSize);
                break;
            case "roulette":
                selection = new RouletteWheel<>(populationSize);
        }

        //RECOMBINATION
        double recombinationProbability = Double.parseDouble(getParameterValue("Recombination probability"));
        switch(getParameterValue("Recombination")){
            case "one_cut":
                recombination = new RecombinationOneCut<>(recombinationProbability);
                break;
            case "uniform":
                recombination = new RecombinationUniform<>(recombinationProbability);
        }

        //MUTATION
        double mutationProbability = Double.parseDouble(getParameterValue("Mutation probability"));
        switch(getParameterValue("Mutation")){
            case "random":
                mutation = new MutationRandom<>(mutationProbability);
                break;
            case "normal_distribution":
                double sd = Double.parseDouble(getParameterValue("Standard deviation"));
                mutation = new MutationNormalDistribution<>(mutationProbability, sd);
        }

        //PROBLEM 
        problem = AICaptureProblem.buildProblemFromFile(new File(getParameterValue("Problem file")));

        String textualRepresentation = buildTextualExperiment();

        experiment = new Experiment<>(this, numRuns, problem, textualRepresentation);

        statistics = new ArrayList<>();
        for (String statisticName : statisticsNames) {
            ExperimentListener statistic = buildStatistic(statisticName);
            statistics.add(statistic);
            experiment.addExperimentListener(statistic);
        }

        return experiment;
    }

    @Override
    public GeneticAlgorithm generateGAInstance(int seed) {
        GeneticAlgorithm<PredatorIndividual, AICaptureProblem> ga =
                new GeneticAlgorithm<>(
                    populationSize,
                    maxGenerations,
                    selection,
                    recombination,
                    mutation,
                    new Random(seed));

        for (ExperimentListener statistic : statistics) {
            ga.addGAListener((GAListener) statistic);
        }

        return ga;
    }

    private ExperimentListener buildStatistic(String statisticName) {
        switch(statisticName){
            case "BestIndividual":
                return new StatisticBestInRun();
            case "BestAverage":
                return new StatisticBestAverage(numRuns);
        }        
        return null;
    }

    private String buildTextualExperiment() {
        StringBuilder sb = new StringBuilder();
        sb.append("Population size:" + populationSize + "\t");
        sb.append("Max generations:" + maxGenerations + "\t");
        sb.append("Selection:" + selection + "\t");
        sb.append("Recombination:" + recombination + "\t");
        sb.append("Mutation:" + mutation + "\t");
        return sb.toString();
    }
}
