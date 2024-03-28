package za.co.wethinkcode.toyrobot;

import za.co.wethinkcode.toyrobot.world.AbstractWorld;

public class SprintCommand extends Command{


    public SprintCommand(String argument) {
        super("sprint", argument);
    }

    @Override
    public boolean execute(AbstractWorld target) {
        int n = Integer.parseInt(this.getArgument());

        while(n > 0){

            ForwardCommand forwardCommand = new ForwardCommand(String.valueOf(n));

            if (forwardCommand.execute(target)) {
                if (n > 1){
                    System.out.println(target);
                }
            } else{
                return false;
            }
            n--;

        }
        return true;
    }

}
