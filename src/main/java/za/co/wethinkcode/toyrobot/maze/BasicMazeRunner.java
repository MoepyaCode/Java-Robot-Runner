package za.co.wethinkcode.toyrobot.maze;

import java.util.ArrayList;
import java.util.List;

import za.co.wethinkcode.toyrobot.world.AbstractWorld;
import za.co.wethinkcode.toyrobot.world.IWorld;
import za.co.wethinkcode.toyrobot.world.Obstacle;
import za.co.wethinkcode.toyrobot.world.IWorld.Direction;
import za.co.wethinkcode.toyrobot.Position;

public class BasicMazeRunner implements MazeRunner{
    private AbstractWorld world;
    private String mazeEdgeEnd;

    private ArrayList<String> commandGenerator;

    private Position currentPosition;
    private Position expectedPosition;    

    private Direction currentDirection;
    private List<Obstacle> obstacles;

    /**
     * 1) Which edge am I going to ???
     * 2) What position am I currently at ???
     * 3) Create a function that looks at the surroundings and then makes a path to the edge (list of commands)
     * 4) run the commands from the mazerunner class by calling 
     */

    public BasicMazeRunner(String mazeEdge) {
        this.mazeEdgeEnd = mazeEdge;
        this.commandGenerator = new ArrayList<>();
    }

    /**
     * A new algorithm which I call centred Algorithm. 
     */
    @Override
    public boolean mazeRun(AbstractWorld world, Direction edgeDirection) {

        this.world = world;
        this.currentPosition = world.getPosition();
        this.expectedPosition = expectedPosition();
        this.currentDirection = world.getCurrentDirection();
        this.obstacles = world.getObstacles();

        mazeLogic();

        return true;
    }

    @Override
    public int getMazeRunCost() {
        return commandGenerator.size();
    }

    public ArrayList<String> getGeneratedCommands() {
        return this.commandGenerator;
    }
 
    public IWorld.Direction mazeDirection(String direction) {

        switch(direction) {
            case "top":
                return IWorld.Direction.UP;
            case "right":
                return IWorld.Direction.RIGHT;
            case "bottom":
                return IWorld.Direction.DOWN;
            case "left":
                return IWorld.Direction.LEFT;
            default:
                return IWorld.Direction.UP;
        }

    }

    public Position expectedPosition() {

        if (mazeEdgeEnd.equalsIgnoreCase("top")) {
            return new Position(0, 200);
        } else if (mazeEdgeEnd.equalsIgnoreCase("right")) {
            return new Position(100, 0);
        } else if (mazeEdgeEnd.equalsIgnoreCase("bottom")) {
            return new Position(0, -200);
        } else {
            return new Position(-100, 0);
        }
    }

    public void mazeLogic() {

        while (currentDirection != mazeDirection(mazeEdgeEnd)) {

            updateDirection(true);

        }

        positionFix();
        directionAction();
        positionFix();

        while (currentDirection != mazeDirection(mazeEdgeEnd)) {

            updateDirection(true);

        }

        int i = 0;
        int listSize = this.commandGenerator.size();

        while (i < listSize) {
            
            if (this.commandGenerator.get(i).equalsIgnoreCase("forward 0") || this.commandGenerator.get(i).equalsIgnoreCase("back 0")) {
                this.commandGenerator.remove(i);
                i--;
            }
            
            listSize = this.commandGenerator.size();
            i++;
        }

    }

    public void unblockMovement() {

        updateDirection(true);
        updatePosition(5);
        commandGenerator.add("forward 5");
        updateDirection(false);

    }
                
    /**
     * Action needed to be taken to get to the final direction of the maze.
     */
    private boolean directionAction() {

        boolean blockResult;
        
        for (Obstacle obstacle : obstacles) {
            
            blockResult = obstacle.blocksPath(currentPosition, expectedPosition);

            if (blockResult) {
                
                updateDirection(true);
                updatePosition(5);
                commandGenerator.add("forward 5");
                updateDirection(false);

                return directionAction();

            }
        }

        String mazeMainDestination = calculateDistance();
        commandGenerator.add(mazeMainDestination);

        String mazePointFixing = calculateDistance();
        commandGenerator.add(mazePointFixing);

        return true;

    }

    public void positionFix() {

        boolean blockCheck;

        if (currentDirection == Direction.UP && currentPosition.getX() != 0) {

            updateDirection(true);

            if (currentPosition.getX() < 0) {
                commandGenerator.add("forward " + -currentPosition.getX());
                blockCheck = updatePosition(-currentPosition.getX());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            } else {
                commandGenerator.add("back " + currentPosition.getX());
                blockCheck = updatePosition(-currentPosition.getX());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            }

            updateDirection(false);

        } else if (currentDirection == Direction.RIGHT && currentPosition.getY() != 0) {
            
            updateDirection(false);

            if (currentPosition.getY() < 0) {
                commandGenerator.add("forward " + -currentPosition.getY());
                blockCheck = updatePosition(-currentPosition.getY());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            } else {
                commandGenerator.add("back " + currentPosition.getY());
                blockCheck = updatePosition(-currentPosition.getY());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            }

            updateDirection(true);

        } else if (currentDirection == Direction.DOWN && currentPosition.getX() != 0) {
            
            updateDirection(false);

            if (currentPosition.getX() < 0) {
                commandGenerator.add("forward " + -currentPosition.getX());
                blockCheck = updatePosition(-currentPosition.getX());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            } else {
                commandGenerator.add("back " + currentPosition.getX());
                blockCheck = updatePosition(-currentPosition.getX());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            }

            updateDirection(true);

        } else if (currentDirection == Direction.LEFT && currentPosition.getX() != 0) {
            
            updateDirection(true);

            if (currentPosition.getY() < 0) {
                commandGenerator.add("forward " + -currentPosition.getY());
                blockCheck = updatePosition(-currentPosition.getY());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            } else {
                commandGenerator.add("back " + currentPosition.getY());
                blockCheck = updatePosition(-currentPosition.getY());
               
               if (blockCheck == false) {
                    unblockMovement();
                    positionFix();
               }

            }

            updateDirection(false);
            
        }

    }


    /**
     * calculated the distance to the maze point
     * @return
     */
    private String calculateDistance() {

        if (currentDirection == Direction.UP) {
            
            int distance = expectedPosition.getY() - currentPosition.getY();
            updatePosition(distance);
            return "forward " + distance;

        } else if (currentDirection == Direction.RIGHT) {
            
            int distance = expectedPosition.getX() - currentPosition.getX();
            updatePosition(distance);
            return "forward " + distance;

        } else if (currentDirection == Direction.DOWN) {
            
            int distance =  currentPosition.getY() - expectedPosition.getY();
            updatePosition(distance);
            return "forward " + distance;

        } else {
            
            int distance =  currentPosition.getX() - expectedPosition.getX();
            updatePosition(distance);
            return "forward " + distance;

        }

    }

    public boolean updatePosition(int nrSteps){
        
        int newX = currentPosition.getX();
        int newY = currentPosition.getY();

        switch (currentDirection) {
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
        
        for (Obstacle obstacle : obstacles) {
            
            if (obstacle.blocksPosition(newPosition)) {
                return false;
            } else if (obstacle.blocksPath(currentPosition, newPosition)) {
                return false;
            }

        }

        if (world.isNewPositionAllowed(newPosition)){
            this.currentPosition = newPosition;
            return true;
        }
        return false;

    }


    public void updateDirection(boolean turnRight) {

        if (this.currentDirection == Direction.UP && turnRight == true) {
            this.currentDirection = Direction.RIGHT;
            commandGenerator.add("right");
        } else if (this.currentDirection == Direction.RIGHT && turnRight == true) {
            this.currentDirection = Direction.DOWN;
            commandGenerator.add("right");
        } else if (this.currentDirection == Direction.DOWN && turnRight == true) {
            this.currentDirection = Direction.LEFT;
            commandGenerator.add("right");
        } else if (this.currentDirection == Direction.LEFT && turnRight == true) {
            this.currentDirection = Direction.UP;
            commandGenerator.add("right");
        } else if (this.currentDirection == Direction.UP && turnRight == false) {
            this.currentDirection = Direction.LEFT;
            commandGenerator.add("left");
        } else if (this.currentDirection == Direction.RIGHT && turnRight == false) {
            this.currentDirection = Direction.UP;
            commandGenerator.add("left");
        } else if (this.currentDirection == Direction.DOWN && turnRight == false) {
            this.currentDirection = Direction.RIGHT;
            commandGenerator.add("left");
        } else if (this.currentDirection == Direction.LEFT && turnRight == false) {
            this.currentDirection = Direction.DOWN;
            commandGenerator.add("left");
        }


    }
    
}
