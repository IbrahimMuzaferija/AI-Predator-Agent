package AICapture;

import ga.Problem;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class AICaptureProblem implements Problem<PredatorIndividual> {

    public static int NUM_OUTPUTS = 2;
    
    final private int environmentSize;
    final private int maxIterations;
    final private int numInputs;
    final private int numHiddenUnits;
    final public int numOutputs;
    final private int numEnvironmentSimulations;


    final private Environment environment;

    public AICaptureProblem(
            int environmentSize,
            int maxIterations,
            int numHiddenUnits,
            int numEnvironmentSimulations) {
        this.environmentSize = environmentSize;
        this.maxIterations = maxIterations;

        this.numInputs = 5; /*DONE*/
        this.numHiddenUnits = numHiddenUnits;
        this.numOutputs = NUM_OUTPUTS;
        this.numEnvironmentSimulations = numEnvironmentSimulations;

        environment = new Environment(
                environmentSize,
                maxIterations,
                numInputs,
                numHiddenUnits,
                numOutputs);
    }

    @Override
    public PredatorIndividual getNewIndividual() {
        int genomeSize = (numInputs) * numHiddenUnits + (numHiddenUnits + 1) * numOutputs;
        return new PredatorIndividual(this, genomeSize, numEnvironmentSimulations);
    }

    public Environment getEnvironment() {
        return environment;
    }
    
    public int getNumEvironmentSimulations(){
        return numEnvironmentSimulations;
    }

    public static AICaptureProblem buildProblemFromFile(File file) throws IOException {
        java.util.Scanner f = new java.util.Scanner(file);

        List<String> lines = new LinkedList<>();

        while (f.hasNextLine()) {
            String s = f.nextLine();
            if (!s.equals("") && !s.startsWith("//")) {
                lines.add(s);
            }
        }        
        
        List<String> parametersValues = new LinkedList<>();
        for (String line : lines) {
            String[] tokens = line.split(":");
            parametersValues.add(tokens[1].trim());
        }
                
        int environmentSize = Integer.parseInt(parametersValues.get(0));
        int maxIterations = Integer.parseInt(parametersValues.get(1));
        int numHiddenUnits = Integer.parseInt(parametersValues.get(2));
        int numEnvironmentSimulations = Integer.parseInt(parametersValues.get(3));

        return new AICaptureProblem(
                environmentSize,
                maxIterations,
                numHiddenUnits,
                numEnvironmentSimulations);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Environment size: ");
        sb.append(environmentSize);
        sb.append("\n");
        sb.append("Maximum number of iterations: ");
        sb.append(maxIterations);
        sb.append("\n");
        sb.append("Number of inputs: ");
        sb.append(numInputs);
        sb.append("\n");
        sb.append("Number of hidden units: ");
        sb.append(numHiddenUnits);
        sb.append("\n");
        sb.append("Number of outputs: ");
        sb.append(numOutputs);
        sb.append("\n");
        sb.append("Number of environment simulations: ");
        sb.append(numEnvironmentSimulations);
        return sb.toString();
    }

}
