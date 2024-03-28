package za.co.wethinkcode.toyrobot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GeneralTest {
    

    @Test
    void MazeRunExecutedTest() {
        Command test = new MazeRunExecuted("");
        assertEquals("mazerun", test.getName());
    }
 


}
