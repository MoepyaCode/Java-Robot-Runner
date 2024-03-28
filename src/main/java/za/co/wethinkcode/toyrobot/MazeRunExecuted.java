package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;

import za.co.wethinkcode.toyrobot.maze.BasicMazeRunner;
import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.IWorld.Direction;


public class MazeRunExecuted extends Command {
    private String mazeEdge;

    public MazeRunExecuted(String argument) {
        super("mazerun", argument);
        this.mazeEdge = argument;
    }

    @Override
    public boolean execute(AbstractWorld world) {
        
        Command command;
        String mazeName = world.getMazeType();

        if (mazeName.equalsIgnoreCase("random_maze") || mazeName.equalsIgnoreCase("empty_maze")) {

            BasicMazeRunner basicMazeRunner = new BasicMazeRunner(mazeEdge);
            Direction mazeEdgeDirection = basicMazeRunner.mazeDirection(mazeName);
            basicMazeRunner.mazeRun(world, mazeEdgeDirection);
            ArrayList<String> commandList = basicMazeRunner.getGeneratedCommands();

            for (String action : commandList) {
                command = Command.create(action);
                world.handleCommand(command);

                System.out.println(world);
            }

            world.setStatus("I am at the " + this.mazeEdge + " edge. (Cost: " + basicMazeRunner.getMazeRunCost() + " steps)");

            
            return true;
        }


    
        return true;
    }
    
}
