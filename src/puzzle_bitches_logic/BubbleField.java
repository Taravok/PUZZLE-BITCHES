/**
 * Created by Brenainn on 09/11/2015.
 */

package puzzle_bitches_logic;
import puzzle_bitches_interfaces.*;
import java.util.ArrayList;

public class BubbleField {

    public final static int MINFIELDX = 0;
    public final static int MAXFIELDX = 500;
    public final static int MINFIELDY = 0;
    public final static int MAXFIELDY = 600;

    private final int angleStep = 2;
    private ArrayList<RegularBubble> regularBubbles;
    private RegularBubble activeRegularBubble;
    private RegularBubble nextRegularBubble;
    private boolean game;
    private boolean collision;
    private int launcherAngle;
    Thread gameThread;

    public BubbleField(){
        this.regularBubbles = new ArrayList<>();
        this.launcherAngle = 270;
        this.nextRegularBubble = new RegularBubble();
    }

    public ArrayList<RegularBubble> getRegularBubbles(){
        return regularBubbles;
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
            while(activeRegularBubble != null && !collision){
                collision = checkCollisions(activeRegularBubble) || checkCollisionOtherBubbles();
                activeRegularBubble.moveBubble();
                try {
                    Thread.sleep(250 / 120);
                } catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
    }

    private synchronized boolean checkCollisions(RegularBubble regularBubble){
        float collisionBorderMinX = BubbleField.MINFIELDX + RegularBubble.BUBBLERADIUS;
        float collisionBorderMaxX = BubbleField.MAXFIELDX - RegularBubble.BUBBLERADIUS;
        float collisionBorderMinY = BubbleField.MINFIELDY + RegularBubble.BUBBLERADIUS;
        float posBubbleX = regularBubble.getPosX();
        float posBubbleY = regularBubble.getPosY();
        if(posBubbleX < collisionBorderMinX || collisionBorderMaxX < posBubbleX){
            regularBubble.flipAngle();
        }
        if(posBubbleY < collisionBorderMinY){
            regularBubble.setBubbleSpeed(new float[]{0, 0});
            return true;
        }
        return false;
    }

    private boolean checkCollisionOtherBubbles(){
        if(regularBubbles.size() != 1) {
            float currentBubbleX = regularBubbles.get(regularBubbles.size() - 1).getPosX();
            float currentBubbleY = regularBubbles.get(regularBubbles.size() - 1).getPosY();
            for (int i = 0; i < regularBubbles.size() - 1; i++) {
                float prevousBubbleX = regularBubbles.get(i).getPosX();
                float prevousBubbleY = regularBubbles.get(i).getPosY();
                double distanceBetweenBubbles = Math.sqrt(
                        ((currentBubbleX - prevousBubbleX) * (currentBubbleX - prevousBubbleX))
                                + ((currentBubbleY - prevousBubbleY) * (currentBubbleY - prevousBubbleY))
                );
                if (distanceBetweenBubbles < ((RegularBubble.BUBBLERADIUS * 2) + 5)) {
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized void addBubble(){
        collision = false;
        nextRegularBubble.setBubbleTrajectoryAngle(launcherAngle);
        activeRegularBubble = nextRegularBubble;
        regularBubbles.add(nextRegularBubble);
        nextRegularBubble = new RegularBubble();
    }

    public void moveLauncherLeft(){

        if(200 < launcherAngle) {
            launcherAngle -= angleStep;
        }
    }

    public void moveLauncherRight(){
        if(launcherAngle < 340){
            launcherAngle += angleStep;
        }
    }

    public void clearField(){
        regularBubbles = new ArrayList<>();
        activeRegularBubble = null;
        nextRegularBubble = new RegularBubble();
    }

    public RegularBubble getNextRegularBubble() {
        return nextRegularBubble;
    }
}