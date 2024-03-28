package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.IWorld;

/**
 * getArgument() returns the number movement
 * nrSteps is derived from get Argument
 */
public class ForwardCommand extends Command {
    @Override
    public boolean execute(AbstractWorld target) {
        int nrSteps = Integer.parseInt(getArgument());
        IWorld.UpdateResponse response = target.updatePosition(nrSteps);

        if (response == IWorld.UpdateResponse.SUCCESS){
            target.setStatus("Moved forward by "+nrSteps+" steps.");
        } else if (response == IWorld.UpdateResponse.FAILED_OBSTRUCTED) {
            target.setStatus("Sorry, there is an obstacle in the way.");
        } else {
            target.setStatus("Sorry, I cannot go outside my safe zone.");
        }
        return true;
    }

    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}