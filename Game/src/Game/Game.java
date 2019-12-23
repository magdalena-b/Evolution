
package Game;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

public class Game extends Canvas implements Runnable {



    //public static final int WIDTH = 640;
    //public static final int HEIGHT = WIDTH / 12 * 9;
    public static int WIDTH;
    public static int HEIGHT;
    private Thread thread;
    private boolean running = false;
    private Map map;

    public Game(int locationX, int locationY) {

        JSONObject obj = null;
        // parsing file "JSONExample.json"
        try {
            URL path = Game.class.getResource("parameters.json");
            File f = new File(path.getFile());
            obj = (JSONObject) new JSONParser().parse(new FileReader(f));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (obj == null) {
            throw new RuntimeException("plik json nie znaleziony");
        }
        WIDTH = (int) (long) obj.get("WIDTH");
        HEIGHT = (int) (long) obj.get("HEIGHT");

        double jungleRation = (double) (long) obj.get("WIDTH");
        int numberOfFirstGeneration = (int) (long) obj.get("numberOfFirstGeneration");

        new Window(WIDTH, HEIGHT, "Evolution IGF 2020", this, locationX, locationY);
        double jungleRatio = 0.5;
        map = new Map(WIDTH, HEIGHT, jungleRatio);
        for (int i = 0; i < numberOfFirstGeneration; i++) {
            map.addAnimal(new Animal(map.getRandomLocation()));
        }
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

    long desiredFPS = 10;
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
        new Game(0, 0);
        new Game(700, 700);
        //new Game();
    }

}
