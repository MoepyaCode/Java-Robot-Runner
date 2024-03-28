package za.co.wethinkcode.toyrobot;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.SquareObstacle;

public class ObstacleTest {
    

    @Test
    void testObstacleDimensions() {
        Obstacle obstacle = new SquareObstacle(5,2);
        assertEquals(5, obstacle.getBottomLeftX());
        assertEquals(2, obstacle.getBottomLeftY());
        assertEquals(5, obstacle.getSize());
    }




}
