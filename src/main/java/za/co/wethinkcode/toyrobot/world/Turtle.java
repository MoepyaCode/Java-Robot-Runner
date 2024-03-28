package za.co.wethinkcode.toyrobot.world;


import org.turtle.StdDraw;

public class Turtle {
    private double x, y;     // turtle is at (x, y)
    private double angle;    // facing this many degrees counterclockwise from the x-axis

    // start at (x0, y0), facing a0 degrees counterclockwise from the x-axis
    public Turtle(double x, double y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    // rotate orientation delta degrees counterclockwise
    public void turnLeft(double delta) {
        angle += delta;
    }

    // move forward the given amount, with the pen down
    public void goForward(double step) {
        double oldx = x;
        double oldy = y;
        x += step * Math.cos(Math.toRadians(angle));
        y += step * Math.sin(Math.toRadians(angle));
        StdDraw.line(oldx, oldy, x, y);
    }

    // copy to onscreen
    public void show() {
        StdDraw.show();
    }

    // pause t milliseconds
    public void pause(int t) {
        StdDraw.pause(t);
    }


    public void setPenColor(int red, int green, int blue) {
        StdDraw.setPenColor(red, green, blue);
    }

    public void setPenRadius(double radius) {
        StdDraw.setPenRadius(radius);
    }

    public void setCanvasSize(int width, int height) {
        StdDraw.setCanvasSize(width, height);
    }

    public void setXscale(double min, double max) {
        StdDraw.setXscale(min, max);
    }

    public void setYscale(double min, double max) {
        StdDraw.setYscale(min, max);
    }

    public void drawObstacles(double x, double y, double obstX, double obstY) {

        double obstX2 = (obstX/1000) + x;
        double obstY2 = (obstY/1000) + y;

        Turtle obstacle = new Turtle(obstX2, obstY2, 0);


        for (int i = 0; i < 4; i++) {

            obstacle.goForward(0.005);
            obstacle.turnLeft(90);
            
        }

        obstacle.show();

    }

    public void setBoxBoundary() {

        double bottomLeftX = 0.3;
        double bottomLeftY = 0.1;
        double angle = 0.0;

        Turtle turtle = new Turtle(bottomLeftX, bottomLeftY, angle);
        turtle.setPenColor(200, 500, 15);

        for (int i = 0; i < 2; i++) {
            
            turtle.goForward(0.45);
            turtle.turnLeft(90);
            turtle.goForward(0.90);
            turtle.turnLeft(90);
        }

        turtle.show();
    }

    // public static void main(String[] args) {
    //     // StdDraw.enableDoubleBuffering();
    //     double bottomLeftX = 0.3;
    //     double bottomLeftY = 0.1;
    //     double angle = 0.0;
    //     Turtle border = new Turtle(0.3, 0.1, 0);
    //     Turtle player = new Turtle(0.5, 0.5, 0);

    //     border.setPenRadius(0.005);
    //     border.setPenColor(0, 0, 0);

    //     for (int i = 0; i < 2; i++) {
            
    //         border.goForward(0.4);
    //         border.turnLeft(90);
    //         border.goForward(0.8);
    //         border.turnLeft(90);
    //     }

    //     border.show();
    //     border.drawObstacles(bottomLeftX, bottomLeftY, 100, 150);

  

    // }


}











// Scanner scanner = new Scanner(System.in);

// // New Turtle
// player.setPenColor(255, 0, 0);
// player.setPenRadius(0.002);
// player.turnLeft(90);



// for (int i = 0; i < 5; i++) {

//     System.out.print("Move forward " + i + ": ");

//     String stringMovement = scanner.nextLine().trim().toLowerCase();


//     if (stringMovement.equalsIgnoreCase("left")) {

//         player.turnLeft(90);

//     } else if (stringMovement.equalsIgnoreCase("right")) {
        
//         player.turnLeft(-90);

//     } else {

//         double movement = Double.parseDouble(stringMovement);
        
//         double divide =  (movement / 1000) * 2;

//         player.goForward(divide);
//     }



//     player.show();

//     scanner.close();

// }