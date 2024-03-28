package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class EmptyMaze implements Maze {
    private ArrayList<Obstacle> obstList;


    public EmptyMaze (){
        this.obstList = new ArrayList<>();
    };

    public void worldMazePrint() {
        System.out.println("Loaded EmptyMaze.");
    }
    
    
    @Override
    public List<Obstacle> getObstacles() {
        return this.obstList;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        return false;
    }
}
