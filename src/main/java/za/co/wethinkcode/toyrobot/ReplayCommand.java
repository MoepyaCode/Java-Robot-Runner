package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;

import za.co.wethinkcode.toyrobot.world.AbstractWorld;

public class ReplayCommand extends Command{
    private Command command;
    private String outcomeString;

    public ReplayCommand(){
        super("replay");
    }


    public ReplayCommand(String argument) {
        super("replay", argument);
    }


    public ReplayCommand(String playingOrder, String range) {
        super("replay", playingOrder, range);
    }


    public String toString(String commandsPlayed, AbstractWorld robot) {
        return "[" + robot.getPosition().getX() + "," + robot.getPosition().getY() + "] "
        + robot.getName() + "> " + commandsPlayed;
    }

    @Override
    public boolean execute(AbstractWorld target){

        String fullCommand = (this.getName() + " " + this.getPlayOrder() + " " + this.getArgument()).trim();
        String[] commandList = fullCommand.trim().split(" ");
        ArrayList<String> adjustedList = new ArrayList<>();

        for (int i = 0; i < commandList.length; i++) {

            if (!commandList[i].equalsIgnoreCase("")) {
                adjustedList.add(commandList[i]);
            }

        }

        fullCommand = String.join(" ", adjustedList);
        commandList = adjustedList.toArray(new String[0]);

        if (commandList.length == 1) {
            return replayEverything(target, fullCommand);
        } else if(commandList.length == 2 && commandList[1].equals("reversed")) {
            return replayReversedEverything(target, fullCommand);
        } else if (commandList.length == 3 && commandList[1].equals("reversed") && commandList[2].length() == 1) {
            return replayReversedSpecificNumber(target, commandList);
        } else if (commandList.length == 3 && commandList[1].equals("reversed") && commandList[2].length() >= 3) {
            return replayReversedInterval(target, commandList);
        } else if (commandList.length == 2 && commandList[1].length() == 1) {
            return replaySpecificNumber(target, commandList);
        } else if (commandList.length == 2 && commandList[1].length() >= 3) {
            return replayInterval(target, commandList);
        }
        return true;
    }



    public boolean replayEverything(AbstractWorld robot, String fullCommand) {

        int historySize = robot.getHistory().size();
        String stringPrint;

        stringPrint = "replayed " + historySize + " commands.";

        for (int i = 0; i < historySize; i++) {

            String instruction = robot.getHistory().get(i);
            command = Command.create(instruction);
            robot.handleCommand(command);

            System.out.println(robot);

            if (i == historySize - 1) {
                this.outcomeString = toString(stringPrint, robot);
                robot.setReplayCommand(this.outcomeString);
                return true;
            } 
        }

        if(robot.getHistory().isEmpty()) {
            this.outcomeString = toString(stringPrint, robot);
            robot.setReplayCommand(this.outcomeString);
        }
        return true;
    }


    public boolean replaySpecificNumber(AbstractWorld robot, String[] commandList) {

        String joinedCommand = String.join(" ", commandList);
        int historySize = robot.getHistory().size();
        
        try {
            int numberReplays = Integer.parseInt(commandList[1]);
            
            for (int i = (historySize - numberReplays); i < historySize; i++) {
    
                String instruction = robot.getHistory().get(i);
                command = Command.create(instruction);
                robot.handleCommand(command);
                System.out.println(robot);
    
    
                if (i == (robot.getHistory().size() - 1)) {
                    String stringPrint = "replayed " + numberReplays + " commands.";
                    this.outcomeString = toString(stringPrint, robot);
                    robot.setReplayCommand(this.outcomeString);
                    return true;
                }
    
            }

        } catch (IndexOutOfBoundsException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        } catch (IllegalArgumentException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        }

        return true;
    }



    public boolean replayInterval(AbstractWorld robot, String[] commandList) {

        String joinedCommand = String.join(" ", commandList);
        int historySize = robot.getHistory().size();
        boolean validInterval = IntervalFormatting(commandList[1]);

        if (!validInterval) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
            return true;
        }
        
        try {

            String[] numbers = commandList[1].split("-");
            int n = Integer.parseInt(numbers[0]);
            int m = Integer.parseInt(numbers[1]);
            int commandsToReplay = n - m;
            int count = 0;
            
            for (int i = (historySize - n); i < (historySize - m); i++) {
    
                String instruction = robot.getHistory().get(i);
                command = Command.create(instruction);
                robot.handleCommand(command);
                System.out.println(robot);
                count++;
    
                if (commandsToReplay == count) {
                    String stringPrint = "replayed " + commandsToReplay + " commands.";
                    this.outcomeString = toString(stringPrint, robot);
                    robot.setReplayCommand(this.outcomeString);
                    return true;
                }
    
            }

        } catch (IndexOutOfBoundsException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        } catch (IllegalArgumentException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        }

        return true;
    }
    

    private boolean replayReversedEverything(AbstractWorld robot, String fullCommand) {
        
        int historySize = robot.getHistory().size();
        String stringPrint;

        stringPrint = "replayed " + historySize + " commands.";

        for (int i = (historySize - 1); i >= 0; i--) {

            String instruction = robot.getHistory().get(i);
            command = Command.create(instruction);
            robot.handleCommand(command);
            System.out.println(robot);


            if (i == 0) {
                this.outcomeString = toString(stringPrint, robot);
                robot.setReplayCommand(this.outcomeString);
                return true;
            }
        } 
        if(robot.getHistory().isEmpty()) {
            this.outcomeString = toString(stringPrint, robot);
            robot.setReplayCommand(this.outcomeString);
        }
            return true;
    }
    

    public boolean replayReversedSpecificNumber(AbstractWorld robot, String[] commandList) {

        String joinedCommand = String.join(" ", commandList);
        int historySize = robot.getHistory().size();
        
        try {
            int numberReplays = Integer.parseInt(commandList[2]);
            int count = 0;
            
            for (int i = (historySize - 1); i >= (historySize - numberReplays); i--) {
    
                String instruction = robot.getHistory().get(i);
                command = Command.create(instruction);
                robot.handleCommand(command);
                System.out.println(robot);
                count++;
    
    
                if (numberReplays == count) {
                    String stringPrint = "replayed " + numberReplays + " commands.";
                    this.outcomeString = toString(stringPrint, robot);
                    robot.setReplayCommand(this.outcomeString);
                    return true;
                }
    
            }

        } catch (IndexOutOfBoundsException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        } catch (IllegalArgumentException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        }

        return true;
    }


    private boolean replayReversedInterval(AbstractWorld robot, String[] commandList) {

        String joinedCommand = String.join(" ", commandList);
        int historySize = robot.getHistory().size();
        boolean validInterval = IntervalFormatting(commandList[2]);

        if (!validInterval) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
            return true;
        }
        
        try {

            String[] numbers = commandList[2].split("-");
            int n = Integer.parseInt(numbers[0]);
            int m = Integer.parseInt(numbers[1]);
            int commandsToReplay = n - m;
            int count = 0;
            
            for (int i = (historySize - m - 1); i >= (historySize - n); i--) {
    
                String instruction = robot.getHistory().get(i);
                command = Command.create(instruction);
                robot.handleCommand(command);
                System.out.println(robot);
                count++;
    
    
                if (commandsToReplay == count) {
                    String stringPrint = "replayed " + commandsToReplay + " commands.";
                    this.outcomeString = toString(stringPrint, robot);
                    robot.setReplayCommand(this.outcomeString);
                    return true;
                }
            }

        } catch (IndexOutOfBoundsException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        } catch (IllegalArgumentException e) {
            robot.setReplayCommand("Sorry, I did not understand '" + joinedCommand + "'.");
        }

        return true;
    }




    public boolean IntervalFormatting(String interval) {

        try {

            String[] numberList = interval.split("-");
            int n = 0;
            int m = 0;
            
            if(!(numberList.length == 2)) {
                return false;
            }

            if(true) {
                n = Integer.parseInt(numberList[0]);
                m = Integer.parseInt(numberList[1]);
            }

            if (n < m) {
                return false;
            }

            return true;


        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}