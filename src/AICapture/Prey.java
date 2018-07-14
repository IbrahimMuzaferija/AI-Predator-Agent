package AICapture;

import java.awt.Color;
import java.util.List;

public class Prey extends Agent{
    
    public Prey(Cell cell){
        super(cell, Color.RED);
    }
    
    @Override
    public void act(Environment environment) {
        if(environment.random.nextDouble() > 0.4){
            List<Cell> freeSorroundingCells = environment.getFreeSorroundingCells(cell);
            if(!freeSorroundingCells.isEmpty()){
                setCell(freeSorroundingCells.get(environment.random.nextInt(freeSorroundingCells.size())));
            }
        }
    }    
}
