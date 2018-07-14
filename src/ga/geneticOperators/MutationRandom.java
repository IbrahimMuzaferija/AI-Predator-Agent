package ga.geneticOperators;

import ga.RealVectorIndividual;
import ga.GeneticAlgorithm;

public class MutationRandom<I extends RealVectorIndividual> extends Mutation<I> {
    
    public MutationRandom(double probability) {
        super(probability);
    }

    @Override
    public void run(I ind) {
        int indSize = ind.getNumGenes();
        for (int i = 0; i < indSize; i++) {
            if (GeneticAlgorithm.random.nextDouble() < probability) {
                ind.setGene(i, GeneticAlgorithm.random.nextDouble()*2-1);
            }
        }
    }
    
    @Override
    public String toString(){
        return "Random mutation (" + probability + ")";
    }
}