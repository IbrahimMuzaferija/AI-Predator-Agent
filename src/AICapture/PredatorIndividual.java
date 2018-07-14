package AICapture;

import ga.RealVectorIndividual;

public class PredatorIndividual extends RealVectorIndividual<AICaptureProblem, PredatorIndividual> {

    final private int numEnvironmentSimulations;
    private int captures;

    public PredatorIndividual(AICaptureProblem problem, int size, int numEnvironmentSimulations) {
        super(problem, size);
        this.numEnvironmentSimulations = numEnvironmentSimulations;
    }

    public PredatorIndividual(PredatorIndividual original) {
        super(original);
        this.numEnvironmentSimulations = original.numEnvironmentSimulations;
        this.captures = original.captures;
    }

    @Override
    public double computeFitness() {
        /*TODO*/

        Environment e = problem.getEnvironment();
        captures = 0;
        int iterations = 0;

        double distanceWeigth = -0.001;
        double iterationWeigth = -0.01;

        for (int i = 0; i < numEnvironmentSimulations; i++) {
            e.initializeAgentsPositions(i);
            e.setAgentAIsWeights(genome);
            e.simulate();
            if (e.isCaptured()){
                captures++;
            }
            iterations += e.getIteration();
        }

        fitness = e.getDistanceMeasure() * distanceWeigth +  captures + iterations * iterationWeigth;
        return fitness;
    }    

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        sb.append("\ncaptures: ");
        sb.append(captures);
        sb.append("\nweights:\n");
        for (int i = 0; i < genome.length; i++) {
            sb.append(genome[i]);
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(PredatorIndividual i) {
        /*DONE*/
        return this.fitness > i.fitness ? 1 : this.fitness < i.fitness ? -1 : 0;
    }

    @Override
    public PredatorIndividual clone() {
        return new PredatorIndividual(this);
    }
}
