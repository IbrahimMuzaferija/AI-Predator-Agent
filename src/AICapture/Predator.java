package AICapture;

import java.awt.Color;
import java.util.Random;

public class Predator extends Agent {
   
    final private int inputLayerSize;
    final private int hiddenLayerSize;
    final private int outputLayerSize;


    /**
     * Network inputs array.
     */
    final private int[] inputs;
    /**
     * Hidden layer weights.
     */
    final private double[][] w1;
    /**
     * Output layer weights.
     */
    final private double[][] w2;
    /**
     * Hidden layer activation values.
     */
    final private double[] hiddenLayerOutput;
    /**
     * Output layer activation values.
     */
    final private int[] output;

    public Predator(
            Cell cell,
            int inputLayerSize,
            int hiddenLayerSize,
            int outputLayerSize) {
        super(cell, Color.BLUE);
        this.inputLayerSize = inputLayerSize;
        this.hiddenLayerSize = hiddenLayerSize;
        this.outputLayerSize = outputLayerSize;
        inputs = new int[inputLayerSize];
        inputs[inputs.length - 1] = -1; //bias entry
        w1 = new double[inputLayerSize][hiddenLayerSize]; // the bias entry for the hidden layer neurons is already counted in inputLayerSize variable
        w2 = new double[hiddenLayerSize + 1][outputLayerSize]; // + 1 due to the bias entry for the output neurons
        hiddenLayerOutput = new double[hiddenLayerSize + 1];
        hiddenLayerOutput[hiddenLayerSize] = -1; // the bias entry for the output neurons
        output = new int[outputLayerSize];
    }



    @Override
    public void act(Environment environment) {
        buildPerception(environment);
        execute(decide(), environment);
    }

    //AgentAI coordinates relative to the Prey
    private void buildPerception(Environment environment) {
        /*DONE*/
        Cell preyCell = environment.getPrey().cell;

        inputs[0] = relativeDistanceToOponent( preyCell.getColumn(), cell.getColumn(), environment.getSize());
        inputs[1] = relativeDistanceToOponent( preyCell.getLine(), cell.getLine(), environment.getSize());
        inputs[2] = preyCell.getLine() > cell.getLine() ? 1 : 0;
        inputs[3] = preyCell.getColumn() > cell.getColumn() ? 1 : 0;

    }

    private Action decide() {
        forwardPropagation();
               
        //Here we are assuming that the output has two elements
        if (output[0] == 0 && output[1] == 0) {
            return Action.NORTH;
        } else if (output[0] == 0 && output[1] == 1) {
            return Action.SOUTH;
        } else if (output[0] == 1 && output[1] == 0) {
            return Action.WEST;
        }
        return Action.EAST;
    }

    private void execute(Action action, Environment environment) {
        Cell nextCell;
        if (action == Action.NORTH) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST) {
            nextCell = environment.getWestCell(cell);
        } else {
            nextCell = environment.getEastCell(cell);
        }


        setCell(nextCell);

    }

    /**
     * Initializes the network's weights
     *
     * @param weights vector of weights.
     */
    public void setWeights(double[] weights) {
        /*DONE*/

        int index = 0;

        for (int i = 0; i < inputLayerSize; i++) {
            for (int j = 0; j < hiddenLayerSize; j++) {
                w1[i][j] = weights[index];
                index++;
            }
        }
        for (int i = 0; i < hiddenLayerSize+1; i++) {
            for (int j = 0; j < outputLayerSize; j++) {
                w2[i][j] = weights[index];
                index++;
            }
        }
    }

    /**
     * Computes the relative (least) "toroidal" distance of a agentAI to the opponent given
     * allong a given coordinate (line or column).
     * @param preyCoord prey coordinate (line or column).
     * @param predatorCoord predator coordinate (line or column).
     * @param envSize environment size;
     * @return the relative toroidal distance to the oponent along the given coordinates.
     */
    private int relativeDistanceToOponent(int preyCoord, int predatorCoord, int envSize) {
        int simpleDistance = preyCoord - predatorCoord;
        int toroidalDistance = envSize - Math.abs(simpleDistance);
        int relativeDistance = Math.abs(simpleDistance) < toroidalDistance
                ? simpleDistance
                : simpleDistance > 0 ? -toroidalDistance : toroidalDistance;
        return relativeDistance;
    }
    
    /**
     * Computes the output of the network for the inputs saved in the class
     * vector "inputs".
     *
     */
    private void forwardPropagation() {
        double sum;

        //First layer outputs computation
        for (int i = 0; i < hiddenLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < inputLayerSize; j++) {
                sum += inputs[j] * w1[j][i];
            }
            // sigmoid function
            hiddenLayerOutput[i] = 1 / (1 + Math.pow(Math.E, -sum)); 
        }
        //the bias entry is already set to -1 in the constructor

        //output layer outputs computation
        for (int i = 0; i < outputLayerSize; i++) {
            sum = 0;
            for (int j = 0; j < hiddenLayerSize + 1; j++) {
                sum += hiddenLayerOutput[j] * w2[j][i];
            }
            output[i] = sum > 0 ? 1 : 0; //step function
        }
    }
        

}
