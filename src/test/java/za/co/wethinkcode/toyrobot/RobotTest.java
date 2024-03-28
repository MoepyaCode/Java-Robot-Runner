package za.co.wethinkcode.toyrobot;


import org.junit.jupiter.api.Test;

import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;

import static org.junit.jupiter.api.Assertions.*;

class RobotTest {

   @Test
   void initialPosition() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       assertEquals(TextWorld.CENTRE, robot.getPosition());
       assertEquals(IWorld.Direction.UP, robot.getCurrentDirection());
   }

   @Test
   void dump() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       assertEquals("[0,0] CrashTestDummy> Ready", robot.toString());
   }

   @Test
   void shutdown() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       ShutdownCommand command = new ShutdownCommand();
       assertFalse(robot.handleCommand(command));
   }

   @Test
   void forward() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       ForwardCommand command = new ForwardCommand("10");
       assertTrue(robot.handleCommand(command));
       Position expectedPosition = new Position(TextWorld.CENTRE.getX(), TextWorld.CENTRE.getY() + 10);
       assertEquals(expectedPosition, robot.getPosition());
       assertEquals("Moved forward by 10 steps.", robot.getStatus());
   }

   @Test
   void forwardforward() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       assertTrue(robot.handleCommand(new ForwardCommand("10")));
       assertTrue(robot.handleCommand(new ForwardCommand("5")));
       assertEquals("Moved forward by 5 steps.", robot.getStatus());
   }

   @Test
   void tooFarForward() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       assertTrue(robot.handleCommand(new ForwardCommand("1000")));
       assertEquals(TextWorld.CENTRE, robot.getPosition());
       assertEquals("Sorry, I cannot go outside my safe zone.", robot.getStatus());
   }

   @Test
   void help() {
       AbstractWorld robot = new TextWorld(new EmptyMaze());
       robot.setRobotName("CrashTestDummy");
       Command command = new HelpCommand();
       assertTrue(robot.handleCommand(command));
       assertEquals("I can understand these commands:\n" +
       "OFF  - Shut down robot\n" +
       "HELP - provide information about commands\n" +
       "FORWARD - move forward by specified number of steps, e.g. 'FORWARD 10'\n" +
       "BACK  - move back by specified number of steps, e.g. 'FORWARD 10'\n" +
       "RIGHT - turns right\n" +
       "LEFT - turns left'", robot.getStatus());
   }
}