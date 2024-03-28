package za.co.wethinkcode.toyrobot.maze;

import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

import java.util.ArrayList;
import java.util.List;

public class RandomMaze implements Maze {
    private final List<Obstacle> obstacles;

    public RandomMaze() {
        this.obstacles = new ArrayList<>();
        setObstacles();
    
    }

    public void setObstacles() {

        int min  = 0;
        int max  = 10;
        int obstNumber = (int) (Math.random() * ((max - min + 1) + min));
    
        for (int i = 0; i < obstNumber; i++) {
            
            int minX  = -100;
            int maxX  = 100;        
            int minY  = -200;
            int maxY  = 200;
            int randNumberX = (int) (Math.random() * (maxX - minX + 1) + minX);
            int randNumberY = (int) (Math.random() * (maxY - minY + 1) + minY);

            if (this.obstacles.contains(new SquareObstacle(randNumberX, randNumberY))) {

                i--;
                continue;

            }

            this.obstacles.add(new SquareObstacle(randNumberX, randNumberY));
        }

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
        System.out.println("Loaded RandomMaze.");
    }

}
