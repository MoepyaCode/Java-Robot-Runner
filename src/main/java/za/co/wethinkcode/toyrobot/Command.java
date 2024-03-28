package za.co.wethinkcode.toyrobot;

import java.util.ArrayList;

import za.co.wethinkcode.toyrobot.world.AbstractWorld;

public abstract class Command {
    private final String name;
    private String playOrder = "";
    private String argument;
    private ArrayList<String> history;

    /**
     * Change the Robot to Abstract.
     * @param target
     * @return
     */
    public abstract boolean execute(AbstractWorld target);


    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public Command(String name, String playingOrder, String argument) {
        this(name);
        this.argument = argument.trim();
        this.playOrder = playingOrder;
    }

    public String getName() {                                                                           //<2>
        return name;
    }

    public String getPlayOrder() {
        return playOrder;
    }

    public String getArgument() {
        return this.argument;
    }

    public ArrayList<String> getHistory(){
        return history;
    }

    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "shutdown":
            case "off":
                return new ShutdownCommand();
            case "help":
                return new HelpCommand();
            case "forward":
                return new ForwardCommand(args[1]);
            case "back":
                return new BackCommand(args[1]);
            case "left":
                return new LeftCommand();
            case "right":
                return new RightCommand();
            case "sprint":
                return new SprintCommand((args[1]));
            case "replay":
                if (args.length == 1) {
                    return new ReplayCommand();
                } else if (args.length == 2) {
                    return new ReplayCommand(args[1]);
                } else if (args.length == 3) {
                    return new ReplayCommand(args[1], args[2]);
                }
            case "mazerun" :
                return new MazeRunExecuted(args[1]);
            default:
                throw new IllegalArgumentException("Unsupported command: " + instruction);
        }
    }
}

