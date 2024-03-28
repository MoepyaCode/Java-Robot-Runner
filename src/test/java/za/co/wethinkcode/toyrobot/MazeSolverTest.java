package za.co.wethinkcode.toyrobot;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import za.co.wethinkcode.toyrobot.maze.BasicMazeRunner;
import za.co.wethinkcode.toyrobot.world.IWorld.Direction;

public class MazeSolverTest {
    

    @Test
    public void mazeDirectionTest() {

        BasicMazeRunner solver = new BasicMazeRunner("");
        assertEquals(Direction.UP, solver.mazeDirection("top"));
        assertEquals(Direction.RIGHT, solver.mazeDirection("right"));
        assertEquals(Direction.DOWN, solver.mazeDirection("bottom"));
        assertEquals(Direction.LEFT, solver.mazeDirection("left"));

    }


    @Test
    public void endPosition() {

        BasicMazeRunner solver = new BasicMazeRunner("top");
        assertEquals(new Position(0, 200), solver.expectedPosition());
        BasicMazeRunner solver2 = new BasicMazeRunner("left");
        assertEquals(new Position(-100, 0), solver2.expectedPosition());

    }



}
