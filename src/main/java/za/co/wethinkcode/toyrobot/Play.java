package za.co.wethinkcode.toyrobot;

import java.util.Scanner;

import za.co.wethinkcode.toyrobot.maze.RandomMaze;
import za.co.wethinkcode.toyrobot.maze.EmptyMaze;
import za.co.wethinkcode.toyrobot.maze.SimpleMaze;
import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.TextWorld;
import za.co.wethinkcode.toyrobot.world.TurtleWorld;


public class Play {
    static Scanner scanner;
    static String mazeName;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        AbstractWorld world = getAbstractWorld(args);
        world.setMazeType(mazeName);
        String name = getInput("What do you want to name your robot?");
        world.setRobotName(name);
        System.out.println("Hello Kiddo!");
        world.showObstacles();
        System.out.println(world.toString());

        Command command;
        boolean shouldContinue = true;
        do {
            
            String instruction = getInput(world.getName() + "> What must I do next?").strip().toLowerCase();
            String[] firstCommand = instruction.trim().split(" ");
            String stringCommand = String.valueOf(firstCommand[0]);
            boolean validCommand = true;
            boolean appendCheck;

            try {
                command = Command.create(instruction);
                shouldContinue = world.handleCommand(command);
            } catch (IllegalArgumentException e) {
                validCommand = false;
                world.setStatus("Sorry, I did not understand '" + instruction + "'.");
            } catch (NullPointerException e) {
                validCommand = false;
                world.setStatus("Sorry, I did not understand '" + instruction + "'.");
            } 

            //Appending to the list and checking if command is a valid command
            appendCheck = world.getAppendCheck();
            if (validCommand && appendCheck && (!stringCommand.equalsIgnoreCase("help") && !stringCommand.equalsIgnoreCase("replay"))) {
                world.setHistory(instruction);
            }

            //replay prints out and normal print out
            if (stringCommand.equalsIgnoreCase("replay")) {
                System.out.println(world.getReplayCommand());;
            } else{
                System.out.println(world);
            }
            
        } while (shouldContinue);
    }

    private static String getInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();

        while (input.isBlank()) {
            System.out.println(prompt);
            input = scanner.nextLine();
        }
        return input;
    }
    
    private static AbstractWorld getAbstractWorld(String[] args) {

        if(args.length == 0){
            mazeName = "random_maze";
            return new TurtleWorld(new RandomMaze());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("text") && args[1].equalsIgnoreCase("EmptyMaze")){
            mazeName = "empty_maze";
            return new TextWorld(new EmptyMaze());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("text") && args[1].equalsIgnoreCase("SimpleMaze")){
            mazeName = "simple_maze";
            return new TextWorld(new SimpleMaze());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("text") && args[1].equalsIgnoreCase("RandomMaze")){
            mazeName = "random_maze";
            return new TextWorld(new RandomMaze());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("turtle") && args[1].equalsIgnoreCase("EmptyMaze")){
            mazeName = "empty_maze";
            return new TurtleWorld(new EmptyMaze());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("turtle") && args[1].equalsIgnoreCase("SimpleMaze")){
            mazeName = "simple_maze";
            return new TurtleWorld(new SimpleMaze());

        } else if (args.length == 2 && args[0].equalsIgnoreCase("turtle") && args[1].equalsIgnoreCase("RandomMaze")){
            mazeName = "random_maze";
            return new TurtleWorld(new RandomMaze());

        } else if (args.length == 1 && args[0].equalsIgnoreCase("text")){
            mazeName = "empty_maze";
            return new TextWorld(new EmptyMaze());

        } else if (args.length == 1 && args[0].equalsIgnoreCase("turtle")){
            mazeName = "empty_maze";
            return new TurtleWorld(new EmptyMaze());

        } else {
            mazeName = "empty_maze";
            return new TextWorld(new EmptyMaze());
        }

    }

}
