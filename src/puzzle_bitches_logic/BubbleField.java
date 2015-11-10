/**
 * Created by Brenainn on 09/11/2015.
 */

package puzzle_bitches_logic;

import puzzle_bitches_interfaces.*;

import java.util.ArrayList;
import java.util.Random;

public class BubbleField implements Subject {

    public final static int MINFIELDX = 0;
    public final static int MAXFIELDX = 500;
    public final static int MINFIELDY = 0;
    public final static int MAXFIELDY = 800;

    private final int angleStep = 4;
    private ArrayList<Observer> observers;
    private ArrayList<Bubble> bubbles;
    private Bubble activeBubble;
    private boolean game;
    private int launcherAngle;
    Thread gameThread;

    public BubbleField(){
        this.bubbles = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.launcherAngle = 270;
    }

    public ArrayList<Bubble> getBubbles(){
        return bubbles;
    }

    public int getLauncherAngle(){
        return launcherAngle;
    }

    public void gameStart(){
        game = true;
        gameThread = new Thread(){
            public void run(){
                while(game){
                    updateBubbles();
                }
            }
        };
        gameThread.start();
    }

    private synchronized void updateBubbles(){
            while(activeBubble != null && !checkCollisions(activeBubble)){
                activeBubble.moveBubble();
                updateObservers();
                try {
                    Thread.sleep(250 / 120);
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }

    }

    private boolean checkCollisions(Bubble bubble){
        float collisionBorderMinX = BubbleField.MINFIELDX + Bubble.BUBBLERADIUS;
        float collisionBorderMaxX = BubbleField.MAXFIELDX - Bubble.BUBBLERADIUS;
        float collisionBorderMinY = BubbleField.MINFIELDY + Bubble.BUBBLERADIUS;
        float posBubbleX = bubble.getBubblePosition()[0];
        float posBubbleY = bubble.getBubblePosition()[1];
        if(posBubbleX < collisionBorderMinX || collisionBorderMaxX < posBubbleX){
            bubble.flipAngle();
        }
        if(posBubbleY < collisionBorderMinY){
            bubble.setBubbleSpeed(new float[]{0, 0});
            correctBubble(bubble);
            return true;
        }
        return false;
    }

    private synchronized void correctBubble(Bubble bubble){
        if(!bubble.isCollided()){
            bubble.setCollided(true);
            updateObservers();
        }
    }

    public synchronized void addBubble(){
        Bubble newBubble = new Bubble(launcherAngle);
        bubbles.add(newBubble);
        activeBubble = newBubble;
    }

    public void moveLauncherLeft(){

        if(200 < launcherAngle) {
            launcherAngle -= angleStep;
            System.out.println(launcherAngle % 180);
            updateObservers();
        }
    }

    public void moveLauncherRight(){
        if(launcherAngle < 340){
            launcherAngle += angleStep;
            System.out.println(launcherAngle % 180);
            updateObservers();
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
        o.update();
    }

    @Override
    public void updateObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }
}
