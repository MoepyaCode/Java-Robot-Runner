package za.co.wethinkcode.toyrobot.world;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import za.co.wethinkcode.toyrobot.Position;

public class SquareObstacle implements Obstacle {
    private final int x, y;
    private ArrayList<Obstacle> obstList; 

    public SquareObstacle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SquareObstacle() {
        this.x = 1;
        this.y = 1;
    }


    @Override
    public int getBottomLeftX() {
        return this.x;
    }


    @Override
    public int getBottomLeftY() {
        return this.y;
    }


    @Override
    public int getSize() {
        return 5;
    }

    public ArrayList<Obstacle> getObstList() {
        return this.obstList;
    }


    /**
     * Check if the Axis being assessed lies within a specific range.
     * @return boolean value.
     */
    @Override
    public boolean blocksPosition(Position position) {
        List<Integer> obstXList = axisRangeList(this.x, (this.x + 4));
        List<Integer> obstYList = axisRangeList(this.y, (this.y + 4));

        if (obstXList.contains(position.getX()) && obstYList.contains(position.getY())) {
            return true;
        } else{
            return false;
        }
    }


    /**
     * Check if the Axis being assessed lies within a specific range
     * Check if the Axis not being assessed lies within 5 steps of that Axis from the left to the right.
     * If both the above are true then the path is blocked.
     */
    @Override
    public boolean blocksPath(Position a, Position b) {
        String axisChange = xOrYChange(a, b);

        if (axisChange.equals("x-change")) {
            List<Integer> x_axis_change;
            List<Integer> y_axis_boundary;

            if (b.getX() > a.getX())
                x_axis_change = axisRangeList(a.getX(), b.getX());
            else 
                x_axis_change = axisRangeList(b.getX(), a.getX());

            y_axis_boundary = axisRangeList(this.y, (this.y + 4));
            return fullPathCheck(x_axis_change, y_axis_boundary, axisChange, a.getX(), a.getY());

        } else if(axisChange.equals("y-change")) {
            List<Integer> y_axis_change;
            List<Integer> x_axis_boundary;

            if (b.getY() > a.getY())
            y_axis_change = axisRangeList(a.getY(), b.getY());
            else 
            y_axis_change = axisRangeList(b.getY(), a.getY());

            x_axis_boundary = axisRangeList(this.x, (this.x + 4));
            return fullPathCheck(y_axis_change, x_axis_boundary, axisChange, a.getX(), a.getY());

        } else {
            return false;
        }
    }


    public String xOrYChange(Position a, Position b) {

        if(a.getX() != b.getX()) {
            return "x-change";
        } else if(a.getY() != b.getY()) {
            return "y-change";
        }
        return "none";
    }


    public List<Integer> axisRangeList (int start, int end) {
        return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }


    public boolean fullPathCheck(List<Integer> mainList, List<Integer> noneMainList, String axisAssessed, int aX, int aY ) {

        if(axisAssessed.equals("x-change") && mainList.contains(this.x) && noneMainList.contains(aY)) {
            return true;
        } else if (axisAssessed.equals("y-change") && mainList.contains(this.y) && noneMainList.contains(aX)) {
            return true;
        } else {
            return false;
        }
    }


}
