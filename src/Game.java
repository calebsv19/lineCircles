import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{
    private Handler handler;
    private Thread thread;
    private boolean running = false;
    /**
     *
     */
    private static final long serialVersionUID = -2599171697168126036L;


    public static final int X_DIMENSION = 1400, Y_DIMENSION = 800, TICKS = 30;

    public static void main(String[] args){
        new Game();
    }

    public Game(){
        handler = new Handler();
        new Window(X_DIMENSION, Y_DIMENSION, "SINE WAVES", this);
    }

    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
        run();
    }

    public synchronized void stop(){
        try {
            thread.join();
            running = false;
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        long lastTime = System.nanoTime();
        double tickCount = TICKS;
        double ns = 1000000000 / tickCount;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1){
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("Fps: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick(Y_DIMENSION, X_DIMENSION);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(5);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0,0, X_DIMENSION, Y_DIMENSION);

        handler.render(g, X_DIMENSION, Y_DIMENSION);

        g.dispose();
        bs.show();
    }

}
