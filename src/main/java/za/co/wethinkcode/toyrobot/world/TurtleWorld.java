package za.co.wethinkcode.toyrobot.world;

import java.util.List;

import za.co.wethinkcode.toyrobot.Command;
import za.co.wethinkcode.toyrobot.Position;
import za.co.wethinkcode.toyrobot.maze.Maze;


public class TurtleWorld extends AbstractWorld{
    private final Maze maze;
    private Turtle turtle;
    private Turtle player;


    private static final double boarderLeftX = 0.3;
    private static final double boarderLeftY = 0.1;
    private static final double playerCentreX = 0.5;
    private static final double playerCentreY = 0.5;
    private static final double angle = 0.0;
    private static final double degrees = 90;
    

    public TurtleWorld(Maze maze){

        super();
        this.maze = maze;
        this.turtle = new Turtle(boarderLeftX, boarderLeftY, angle);
        turtle.show();

    }

    public void defaultPlayer() {

        Turtle player = new Turtle(playerCentreX, playerCentreY, angle);
        player.turnLeft(degrees);
        player.setPenColor(0, 255, 0);
        player.setPenRadius(0.003);
        player.show();

        this.player = player;

    }

    public void setTurtleNewPosition(double steps) {

        double decimalSteps = (steps / 1000) * 2;

        this.player.goForward(decimalSteps);

    }

    public void defaultBorder() {

        Turtle borderTurtle = new Turtle(boarderLeftX, boarderLeftY, angle); 

        borderTurtle.setPenColor(200, 0, 0);
        borderTurtle.setPenRadius(0.005);


        for (int i = 0; i < 2; i++) {
            
            borderTurtle.goForward(0.40);
            borderTurtle.turnLeft(90);
            borderTurtle.goForward(0.80);
            borderTurtle.turnLeft(90);

        }

        borderTurtle.show();

    }

    public void drawObstacles(double obstX, double obstY) {

        double obstStartX = ((obstX/1000) * 2) + playerCentreX;
        double obstStartY = ((obstY/1000) * 2) + playerCentreY;

        Turtle obstacle = new Turtle(obstStartX, obstStartY, 0);
        obstacle.setPenColor(255, 0, 0);


        for (int i = 0; i < 4; i++) {

            obstacle.goForward(0.004);
            obstacle.turnLeft(90);
            
        }

        obstacle.show();

    }

    public void removeTrace() {};

    public boolean handleCommand(Command command) {
        return command.execute(this);
    }


    @Override
    public void showObstacles() {

        List<Obstacle> obstList = maze.getObstacles();
        globalObstacles(obstList);

        defaultBorder();

        maze.worldMazePrint();

        if (!obstList.isEmpty()) {
            
            System.out.println("There are some obstacles:");

            for (Obstacle array : obstList) {

                drawObstacles((double) array.getBottomLeftX(), (double) array.getBottomLeftY());

                System.out.println("- At position " + array.getBottomLeftX() + ", " + array.getBottomLeftY() + " (to " 
                + (array.getBottomLeftX() + 4) + ", " + (array.getBottomLeftY() + 4) + ")");
            }

        }

        defaultPlayer();
        
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
            setTurtleNewPosition( (double) nrSteps);
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
            this.player.turnLeft(-degrees);
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.RIGHT && turnRight == true) {
            newDirection = Direction.DOWN;
            this.player.turnLeft(-degrees);
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.DOWN && turnRight == true) {
            newDirection = Direction.LEFT;
            this.player.turnLeft(-degrees);
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.LEFT && turnRight == true) {
            newDirection = Direction.UP;
            this.player.turnLeft(-degrees);
            setCurrentDirection(newDirection);
        }

        if (oldDirection == Direction.UP && turnRight == false) {
            newDirection = Direction.LEFT;
            this.player.turnLeft(degrees);
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.RIGHT && turnRight == false) {
            newDirection = Direction.UP;
            this.player.turnLeft(degrees);
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.DOWN && turnRight == false) {
            newDirection = Direction.RIGHT;
            this.player.turnLeft(degrees);
            setCurrentDirection(newDirection);
        } else if (oldDirection == Direction.LEFT && turnRight == false) {
            newDirection = Direction.DOWN;
            this.player.turnLeft(degrees);
            setCurrentDirection(newDirection);
        }

    }

}