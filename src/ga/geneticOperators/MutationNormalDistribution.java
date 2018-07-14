package ga.geneticOperators;

import ga.GeneticAlgorithm;
import ga.RealVectorIndividual;

public class MutationNormalDistribution<I extends RealVectorIndividual> extends Mutation<I> {

    final private double sd;
    
    public MutationNormalDistribution(double probability, double sd) {
        super(probability);
        this.sd = sd;
    }

    @Override
    public void run(I ind) {
        /*DONE*/

        int individualSize = ind.getNumGenes();

        for (int i = 0; i < individualSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, ind.getGene(i) + sd * GeneticAlgorithm.random.nextDouble());
            }
        }
    }
    
    @Override
    public String toString(){
        return "Normal distribution mutation (" + probability + ")" + " Standard Deviation: " + sd /*DONE*/;
    }
    
}
