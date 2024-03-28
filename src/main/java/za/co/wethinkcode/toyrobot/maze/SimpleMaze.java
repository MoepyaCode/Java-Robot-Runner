package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class SimpleMaze implements Maze {
    Obstacle squareObstacle = new SquareObstacle();
    ArrayList<Obstacle> obstacles = new ArrayList<>();

    public SimpleMaze() {
        setObstacles();
    }

    public void setObstacles() {
        obstacles.add(squareObstacle);
    }

    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    @Override
    public boolean blocksPath(Position a, Position b) {
        return false;
    }

    @Override
    public void worldMazePrint() {
        System.out.println("Loaded SimpleMaze.");
    }

}
