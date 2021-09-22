import java.awt.*;
import java.io.*;
import java.util.*;

public class Handler {
    private Slopes[][] slopes;
    private Random r;
    private double t;

    public static final int BALL_RAD = 14, SEPERATION = 2, X_START = 70, Y_START = 70;
    public static final int X_NUM = (int) (1.0 * (Game.X_DIMENSION - (X_START * 2)) / (BALL_RAD * 2 + SEPERATION));
    public static final int Y_NUM = (int) (1.0 * (Game.Y_DIMENSION - (Y_START * 2)) / (BALL_RAD * 2 + SEPERATION));

    public static final double CHANGE = .35;


    public Handler(){
        r = new Random();
        t = 0;
        slopes = new Slopes[Y_NUM][X_NUM];

        slopes[0][0] = new Slopes( X_START, Y_START, r.nextDouble() * Math.PI);

        for (int i = 1; i < X_NUM; i++){
            slopes[0][i] = new Slopes( X_START + i * (BALL_RAD * 2 + SEPERATION), Y_START,
                    slopes[0][i - 1].getDegree() + (r.nextDouble() - .5) * CHANGE);
        }


        for (int j = 1; j < Y_NUM; j++){

            slopes[j][0] = new Slopes( X_START, Y_START  + (j * (BALL_RAD * 2 + SEPERATION)),
                    slopes[j - 1][0].getDegree() + (r.nextDouble() - .5) * CHANGE);


            for (int i = 1; i < X_NUM; i++){

                double deg = (slopes[j - 1][i].getDegree() + slopes[j][i - 1].getDegree()) / 2 + ((r.nextDouble() - .5) * CHANGE);

                slopes[j][i] = new Slopes(i * (BALL_RAD * 2 + SEPERATION)  + X_START,
                        j * (BALL_RAD * 2 + SEPERATION) + Y_START, deg);
            }
        }
    }

    public void tick(int width, int height){
        for (int i = 0; i < X_NUM; i++){
            for (int j = 0; j < Y_NUM; j++){
                slopes[j][i].tick(r);
            }
        }
    }

    public void render(Graphics g, int width, int height){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(X_START,Y_START, X_NUM * (2 * BALL_RAD + SEPERATION) - SEPERATION, Y_NUM * ( 2 * BALL_RAD + SEPERATION) - SEPERATION);

        for (int i = 0; i < X_NUM; i++){
            for (int j = 0; j < Y_NUM; j++){
               slopes[j][i].render(g, BALL_RAD);
            }
        }
    }
}