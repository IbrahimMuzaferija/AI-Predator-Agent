package AICapture;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Environment {

    public Random random;
    private final Cell[][] grid;
    private final Predator predator;
    private final Prey prey;
    private final int maxIterations;

    private int iteration;
    private double distanceMeasure;
    private boolean capture;

    public Environment(
            int size,
            int maxIterations,

            int agentAIsNumInputs,
            int agentAINumHiddenLayers,
            int agentAIsNumOutputs) {

        this.maxIterations = maxIterations;

        grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        prey = new Prey(null);
        predator = new Predator(null, agentAIsNumInputs, agentAINumHiddenLayers, agentAIsNumOutputs);
        this.random = new Random();
    }

    public void setAgentAIsWeights(double[] weights) {
        predator.setWeights(weights);
    }

    public void initializeAgentsPositions(int seed) {
        random.setSeed(seed);

        //reset cells
        prey.setCell(null);
        predator.setCell(null);

        //place agents
        prey.setCell(getCell(random.nextInt(grid.length), random.nextInt(grid.length)));

        do {
            Cell cell = getCell(random.nextInt(grid.length), random.nextInt(grid.length));
            if (!cell.hasAgent()) {
                predator.setCell(cell);
            }
        } while (predator.getCell() == null);

    }


    public void simulate() {
       /*DONE*/
        distanceMeasure = 0;
        capture = false;
        iteration = 0;

        for (int i = 0; i < maxIterations; i++){

            predator.act(this);

            if (random.nextDouble() > 0.6){
                prey.act(this);
            }

            if (predator.cell.getColumn() == prey.cell.getColumn() && predator.cell.getLine() == prey.cell.getLine()){
                capture = true;
                break;
            }

            distanceMeasure += computeAgentPreyPredatorDistanceSum();
            iteration++;

            fireUpdatedEnvironment();

        }


    }


    public int getIteration() {
        return iteration;
    }

    public int computeAgentPreyPredatorDistanceSum() {
        Cell preyCell = prey.getCell();
        int sum = 0;

        Cell predatorCell = predator.getCell();
        int simpleVDistance = Math.abs(predatorCell.getLine() - preyCell.getLine());
        int simpleHDistance = Math.abs(predatorCell.getColumn() - preyCell.getColumn());
        int toroidalVDistance = simpleVDistance < grid.length - simpleVDistance ?
                simpleVDistance : grid.length - simpleVDistance;
        int toroidalHDistance = simpleHDistance < grid.length - simpleHDistance ?
                simpleHDistance : grid.length - simpleHDistance;

        sum = toroidalVDistance + toroidalHDistance;


        return sum;
    }

    public double getDistanceMeasure() {
        return distanceMeasure;
    }

    public boolean isCaptured() {
        return capture;
    }

    public int getSize() {
        return grid.length;
    }

    public final Cell getCell(int line, int column) {
        return grid[line][column];
    }

    public List<Cell> getFreeSorroundingCells(Cell cell) {
        List<Cell> freeCells = new LinkedList<>();
        if (!getNorthCell(cell).hasAgent()) {
            freeCells.add(getNorthCell(cell));
        }
        if (!getSouthCell(cell).hasAgent()) {
            freeCells.add(getSouthCell(cell));
        }
        if (!getEastCell(cell).hasAgent()) {
            freeCells.add(getEastCell(cell));
        }
        if (!getWestCell(cell).hasAgent()) {
            freeCells.add(getWestCell(cell));
        }
        return freeCells;
    }

    public Color getCellColor(int line, int column) {
        return grid[line][column].getColor();
    }

    public Cell getNorthCell(Cell cell) {
        return cell.getLine() > 0 ? grid[cell.getLine() - 1][cell.getColumn()] : grid[grid.length - 1][cell.getColumn()];
    }

    public Cell getSouthCell(Cell cell) {
        return cell.getLine() < grid.length - 1 ? grid[cell.getLine() + 1][cell.getColumn()] : grid[0][cell.getColumn()];
    }

    public Cell getWestCell(Cell cell) {
        return cell.getColumn() > 0 ? grid[cell.getLine()][cell.getColumn() - 1] : grid[cell.getLine()][grid.length - 1];
    }

    public Cell getEastCell(Cell cell) {
        return cell.getColumn() < grid.length - 1 ? grid[cell.getLine()][cell.getColumn() + 1] : grid[cell.getLine()][0];
    }

    public Prey getPrey() {
        return prey;
    }

    public Predator getPredator() {
        return predator;
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

}
