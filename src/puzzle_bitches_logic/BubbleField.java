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

    private final int angleStep = 4;
    private ArrayList<Observer> observers;
    private ArrayList<Bubble> bubbles;
    private Bubble activeBubble;
    private Bubble nextBubble;
    private boolean game;
    private boolean collision;
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
            while(activeBubble != null && !collision){
                collision = checkCollisions(activeBubble) || checkCollisionOtherBubbles();
                activeBubble.moveBubble();
                updateObservers();
                try {
                    Thread.sleep(250 / 120);
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }

    }

    private synchronized boolean checkCollisions(Bubble bubble){
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
            System.out.println("Collision with ceiling");
            return true;
        }
        return false;
    }

    private boolean checkCollisionOtherBubbles(){
        if(bubbles.size() != 1) {
            float currentBubbleX = bubbles.get(bubbles.size() - 1).getBubblePosition()[0];
            float currentBubbleY = bubbles.get(bubbles.size() - 1).getBubblePosition()[1];
            for (int i = 0; i < bubbles.size() - 1; i++) {
                float prevousBubbleX = bubbles.get(i).getBubblePosition()[0];
                float prevousBubbleY = bubbles.get(i).getBubblePosition()[1];
                double distanceBetweenBubbles = Math.sqrt(
                        ((currentBubbleX - prevousBubbleX) * (currentBubbleX - prevousBubbleX))
                                + ((currentBubbleY - prevousBubbleY) * (currentBubbleY - prevousBubbleY))
                );
                if (distanceBetweenBubbles < ((Bubble.BUBBLERADIUS * 2) + 5)) {
                    System.out.println("Collision with bubble");
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void addBubble(){
        collision = false;
        Bubble newBubble = new Bubble(launcherAngle);
        activeBubble = newBubble;
        bubbles.add(newBubble);
    }

    public void moveLauncherLeft(){

        if(200 < launcherAngle) {
            launcherAngle -= angleStep;
            updateObservers();
        }
    }

    public void moveLauncherRight(){
        if(launcherAngle < 340){
            launcherAngle += angleStep;
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