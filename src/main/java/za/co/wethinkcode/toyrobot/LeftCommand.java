package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.AbstractWorld;

public class LeftCommand extends Command {
    @Override
    public boolean execute(AbstractWorld target) {


        target.updateDirection(false);

        target.setStatus("Turned left.");

        return true;
    }

    public LeftCommand() {
        super("left");
    }
}
