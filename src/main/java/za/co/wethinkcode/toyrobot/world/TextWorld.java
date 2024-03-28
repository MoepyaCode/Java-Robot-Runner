package za.co.wethinkcode.toyrobot.world;

import java.util.List;

import za.co.wethinkcode.toyrobot.Command;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;

public class TextWorld extends AbstractWorld{
    private final Maze maze;

    public TextWorld(Maze maze){
        super();
        this.maze = maze;

    }

    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    @Override
    public UpdateResponse updatePosition(int nrSteps){
        
        List<Obstacle> mazeObstacles = maze.getObstacles();

        Position currentPosition = getPosition();
        int newX = getPosition().getX();
        int newY = getPosition().getY();

        switch (getCurrentDirection()) {
            case UP:
                newY += nrSteps;
                break;
            case RIGHT:
                newX += nrSteps;
                break;
            case DOWN:
                newY -= nrSteps;
                break;
            case LEFT:
                newX -= nrSteps;
                break;
        }

        Position newPosition = new Position(newX, newY);
        
        for (Obstacle obstacle : mazeObstacles) {
            
            if (obstacle.blocksPosition(newPosition)) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            } else if (obstacle.blocksPath(currentPosition, newPosition)) {
                return UpdateResponse.FAILED_OBSTRUCTED;
            }

        }

        if (isNewPositionAllowed(newPosition)){
            setNewPosition(newPosition);
            setAppendCheck(true);
            return UpdateResponse.SUCCESS;
        }
        setAppendCheck(false);
        return UpdateResponse.FAILED_OUTSIDE_WORLD;

    }


    @Override
    public void updateDirection(boolean turnRight) {

        Direction oldDirection = getCurrentDirection();
        Direction newDirection;

        if (oldDirection == Direction.UP && turnRight == true) {
            newDirection = Direction.RIGHT;
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.RIGHT && turnRight == true) {
            newDirection = Direction.DOWN;
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.DOWN && turnRight == true) {
            newDirection = Direction.LEFT;
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.LEFT && turnRight == true) {
            newDirection = Direction.UP;
            setCurrentDirection(newDirection);
        }

        if (oldDirection == Direction.UP && turnRight == false) {
            newDirection = Direction.LEFT;
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.RIGHT && turnRight == false) {
            newDirection = Direction.UP;
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.DOWN && turnRight == false) {
            newDirection = Direction.RIGHT;
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.LEFT && turnRight == false) {
            newDirection = Direction.DOWN;
            setCurrentDirection(newDirection);
        }

    }


    @Override
    public void showObstacles() {

        List<Obstacle> obstList = maze.getObstacles();
        globalObstacles(obstList);

        maze.worldMazePrint();

        if (!obstList.isEmpty()) {
            
            System.out.println("There are some obstacles:");

            for (Obstacle array : obstList) {
                System.out.println("- At position " + array.getBottomLeftX() + ", " + array.getBottomLeftY() + " (to " 
                + (array.getBottomLeftX() + 4) + ", " + (array.getBottomLeftY() + 4) + ")");
            }

        }
        
    }


}
