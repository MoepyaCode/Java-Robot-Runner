package za.co.wethinkcode.toyrobot;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class MazeReturnTest {
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
        System.setIn(standardIn);
    }

    private void verifyOutput(String[] actualOutput, String[] expectedOutput) {
        for (int i = actualOutput.length - 1, j = expectedOutput.length - 1; j > 0; i--, j--) {
            assertEquals(expectedOutput[j], actualOutput[i]);
        }
    }

    @Test
    void getMazeRunName() {
        Command test = new MazeRunExecuted("up");
        assertEquals("mazerun", test.getName());
    }


    @Test
    void getMazeRunArgument() {
        Command test = new MazeRunExecuted("up");
        assertEquals("up", test.getArgument());
    }


    @Test
    void testMazerunLeft() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun left\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"[-100,0] HAL> I am at the top edge. (Cost: 2 steps)",
                "HAL> What must I do next?",
                "[-100,0] HAL> Shutting down..."};

        verifyOutput(actualOutput, expectedOutput);
    }

    @Test
    void testMazerunRight() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun right\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"[100,0] HAL> I am at the top edge. (Cost: 2 steps)",
                "HAL> What must I do next?",
                "[100,0] HAL> Shutting down..."};

        verifyOutput(actualOutput, expectedOutput);
    }


    @Test
    void testMazerunBottom() {
        InputStream mockedInput = new ByteArrayInputStream("HAL\nmazerun bottom\noff\n".getBytes());
        System.setIn(mockedInput);
        Play.main(new String[]{"text", "EmptyMaze"});
        String[] actualOutput = outputStreamCaptor.toString().trim().split("\n");
        String[] expectedOutput = {"[0,-200] HAL> I am at the top edge. (Cost: 2 steps)",
                "HAL> What must I do next?",
                "[0,-200] HAL> Shutting down..."};

        verifyOutput(actualOutput, expectedOutput);
    }




}
