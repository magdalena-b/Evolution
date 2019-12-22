
package Game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {



    public static final int WIDTH = 640;
    public static final int HEIGHT = WIDTH / 12 * 9;
    private Thread thread;
    private boolean running = false;
    private Map map;

    public Game() {
        new Window(WIDTH, HEIGHT, "Evolution IGF 2020", this);
        double jungleRatio = 0.5;
        map = new Map(WIDTH, HEIGHT, jungleRatio);
        map.addAnimal(new Animal(new Vector2d(320, 150)));
        //map.addPlant(new Plant(new Vector2d(100, 100)));
        map.createJungleAndSahannah();
    }

    public synchronized void start(){

        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    double interpolation = 0;
    final int TICKS_PER_SECOND = 25;
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    final int MAX_FRAMESKIP = 5;

    long desiredFPS = 60;
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;

    private double x = 0;

    protected void update(int deltaTime) {
        x += deltaTime * 0.2;
        while (x > 500) {
            x -= 500;
        }
    }


        public void run(){

        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;

        while(running){
            beginLoopTime = System.nanoTime();

            render();
            tick(WIDTH, HEIGHT);

            lastUpdateTime = currentUpdateTime;
            currentUpdateTime = System.nanoTime();
            update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;

            if(deltaLoop > desiredDeltaLoop){
                //Do nothing. We are already late.
            }else{
                try{
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));
                }catch(InterruptedException e){
                    //Do nothing
                }
            }
        }
    }


    private void tick(int width, int height) {
        map.tick(width, height);
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        //bs.getDrawGraphics().

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        map.render(g);


        g.dispose();
        bs.show();

    }

    public static void main(String args[]){
        new Game();
    }

}
