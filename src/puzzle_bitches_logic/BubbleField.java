/**
 * Created by Brenainn on 09/11/2015.
 */

package puzzle_bitches_logic;

import puzzle_bitches_interfaces.*;

import java.util.ArrayList;

public class BubbleField implements Subject {

    public final static int MINFIELDX = 0;
    public final static int MAXFIELDX = 500;
    public final static int MINFIELDY = 0;
    public final static int MAXFIELDY = 800;


    private ArrayList<Observer> observers;
    private ArrayList<Bubble> bubbles;
    private boolean game;

    public BubbleField(){
        this.bubbles = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public ArrayList<Bubble> getBubbles(){
        return bubbles;
    }

    public void gameStart(){
        game = true;
        Thread gameThread = new Thread(){
            public void run(){
                while(game){
                    updateBubbles();

                }
            }
        };
        gameThread.start();
    }

    private void updateBubbles(){
        for(Bubble bubble : bubbles){
            while(!checkCollisions(bubble)){
                bubble.moveBubble();
                updateObservers();
                try {
                    Thread.sleep(1000 / 120);
                } catch (InterruptedException ex){}
            }
        }
    }

    private boolean checkCollisions(Bubble bubble){
        float collisionBorderMinX = BubbleField.MINFIELDX + Bubble.BUBBLERADIUS;
        float collisionBorderMaxX = BubbleField.MAXFIELDX - Bubble.BUBBLERADIUS;
        float collisionBorderMinY = BubbleField.MINFIELDY + Bubble.BUBBLERADIUS;
        //float collisionBorderMaxY = BubbleField.MAXFIELDY - Bubble.BUBBLERADIUS;
        float posBubbleX = bubble.getBubblePosition()[0];
        float posBubbleY = bubble.getBubblePosition()[1];
        if(posBubbleX < collisionBorderMinX || posBubbleX < collisionBorderMaxX){
            bubble.flipAngle();
        }
        if(posBubbleY < collisionBorderMinY){
            bubble.setBubbleSpeed(new float[]{0, 0});
            return true;
        }
        return false;
    }

    public void addBubble(){
        Bubble newBubble = new Bubble(270);
        bubbles.add(newBubble);
        newBubble.updateBubble();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void updateObservers() {
        for(Observer observer : observers){
            observer.update();
        }
    }
}
