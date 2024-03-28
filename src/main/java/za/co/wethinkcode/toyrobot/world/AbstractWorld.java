package za.co.wethinkcode.toyrobot.world;

import java.util.ArrayList;
import java.util.List;

import za.co.wethinkcode.toyrobot.Command;
import za.co.wethinkcode.toyrobot.Position;

/**
 * First fix the updating of the game, it should update on the Worlds
 * Work on integrating the obstacles into the game.
 * once the integrating is done, work on the isNewPosition
 */
public abstract class AbstractWorld implements IWorld {

    public static final Position CENTRE = new Position(0,0);

    private Position position;
    private Direction currentDirection;
    private String status;
    private String name;
    private ArrayList<String> history;
    private String replayCommand;
    private boolean appendCheck;
    private List<Obstacle> obstacles;
    private String mazeType;
    
    public AbstractWorld() {
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.UP;
        this.history = new ArrayList<>();
    }


    public String getName() {
        return this.name;
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }

    public String getMazeType() {
        return this.mazeType;
    }

    public void setRobotName(String robotName) {
        this.name = robotName;
    }
    
    public void setWorldDetails(String mazeType) {
        this.mazeType = mazeType;
    }
    
    public void globalObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setMazeType(String mazeType) {
        this.mazeType = mazeType;
    }

    

    @Override
    public boolean isNewPositionAllowed(Position position) {

        if (!(position.getX() >= -100 && position.getX() <= 100)) {
            return false;
        } else if (!(position.getY() >= -200 && position.getY() <= 200)) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public boolean isAtEdge() {

        if ((position.getX() == -100 || position.getX() == 100)) {
            return true;
        } else if ((position.getY() == -200 || position.getY() == 200)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * Robot
     */
    public String getStatus() {
        return this.status;
    }
    
    public ArrayList<String> getHistory(){
        return this.history;
    }
    
    public boolean getAppendCheck() {
        return this.appendCheck;
    }

    public void setAppendCheck(boolean appendStatus) {
        this.appendCheck = appendStatus;
    }
    
    public void setCurrentDirection(Direction updatDirection) {
        this.currentDirection = updatDirection;
    }

    public void setNewPosition(Position newPosition) {
        this.position = newPosition;
    }

    public String getReplayCommand() {
        return this.replayCommand;
    }
    
    public void setHistory(String value) {
        this.history.add(value);
    }

    public void setReplayCommand(String replayOutcome) {
        this.replayCommand = replayOutcome;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
    public boolean handleCommand(Command command) {
        return command.execute(this);
    }


    @Override
    public String toString() {
       return "[" + this.position.getX() + "," + this.position.getY() + "] "
               + this.name + "> " + this.status;
    }
    
    @Override
    public void reset() {
        this.status = "Ready";
        this.position = CENTRE;
        this.currentDirection = Direction.UP;
        this.history = new ArrayList<>();
    }

}