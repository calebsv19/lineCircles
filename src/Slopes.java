import java.awt.*;
import java.util.*;

public class Slopes {
    private int x;
    private int y;
    private double degree;

    public Slopes(int x, int y, double degree){
        this.x = x;
        this.y = y;
        this.degree = degree;
    }

    public void tick(Random r){
        this.setDegree(this.getDegree() + .05 + (r.nextDouble() - .5) / 60);

    }

    public void render(Graphics g, int size){
        double degree = this.getDegree();
        int topX = (int) Math.floor(this.getX() + size * (Math.sin(degree) + 1));
        int bottomX = (int) Math.ceil(this.getX() + size * ((Math.sin(degree) - 1) * -1));
        int topY = (int) Math.floor(this.getY() + size * (Math.cos(degree) + 1));
        int bottomY = (int) Math.ceil(this.getY() + size * ((Math.cos(degree) - 1) * -1));
        g.setColor(Color.WHITE);
        g.drawLine(topX, topY, bottomX, bottomY);
        g.setColor(new Color(60, 180, 255));
        g.drawOval(this.getX(), this.getY(), size * 2, size * 2);

    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setDegree(double degree){
        this.degree = degree;

        if (degree >= Math.PI){
            this.degree -= Math.PI;
        }

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public double getDegree(){
        return degree;
    }
}
