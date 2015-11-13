package logics;

import java.awt.*;
import java.util.ArrayList;

public class BubbleField {

    public final static int MINFIELDX = 0;
    public final static int MAXFIELDX = 608;
    public final static int MINFIELDY = 0;
    public final static int MAXFIELDY = 900;

    private final int angleStep = 2;
    private ArrayList<Bubble> regularBubbles;
    private Bubble activeRegularBubble;
    private Bubble nextRegularBubble;
    private boolean game;
    private boolean collision;
    private int launcherAngle;
    private Point[][] hexGrid;
    private boolean[][] filledHexes;
    private boolean logged;

    Thread gameThread;

    public BubbleField(){
        this.regularBubbles = new ArrayList<>();
        this.launcherAngle = 270;
        this.nextRegularBubble = new Bubble() {};
        this.logged = false;
    }

    public ArrayList<Bubble> getRegularBubbles(){
        return regularBubbles;
    }

    public int getLauncherAngle(){
        return launcherAngle;
    }

    public void setHexGrid(Point[][] hexGrid){
        if(this.hexGrid == null) {
            System.out.println("setHexGrid called and assigned");
            this.hexGrid = hexGrid;
            initialiseFilledHexes();
        }
    }

    private void initialiseFilledHexes(){
        this.filledHexes = new boolean[hexGrid.length][];
        for(int y = 0; y < hexGrid.length; y++){
            filledHexes[y] = new boolean[hexGrid[y].length];
        }
        drawArray(filledHexes);
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
            if(collision){
                snapToGrid();
            }
            try {
                Thread.sleep(250 /120);
            } catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }

    private void snapToGrid(){
        float activeX = activeRegularBubble.getPosX();
        float activeY = activeRegularBubble.getPosY();
        float smallestXDifference = 99999;
        float smallestYDifference = 99999;
        int definitiveX = 0;
        int definitiveY = 0;
        Point closestPoint = new Point(0, 0);
        for (int y = 0; hexGrid != null && y < hexGrid.length; y ++) {
            for (int x = 0; x < hexGrid[y].length; x++) {
                float compareX = Math.abs(activeX - hexGrid[y][x].x);
                float compareY = Math.abs(activeY - hexGrid[y][x].y);
                float tempX = smallestXDifference;
                float tempY = smallestYDifference;
                smallestXDifference = (compareX < smallestXDifference) ? compareX : smallestXDifference;
                smallestYDifference = (compareY < smallestYDifference) ? compareY : smallestYDifference;
                System.out.println("HexGrid cell x: " + x + " y: " + y);
                System.out.println("Difference between hexGrid point and bubbleX: " + compareX);
                System.out.println("Difference between hexGrid point and bubbleY: " + compareY);
                System.out.println("Smallest X according to current detection: " + smallestXDifference);
                System.out.println("Smallest Y according to current detection: " + smallestYDifference);
                if(smallestXDifference == compareX && smallestYDifference == compareY){
                    if(!filledHexes[y][x]){
                        closestPoint = hexGrid[y][x];
                        definitiveX = x;
                        definitiveY = y;
                        System.out.println(closestPoint);
                    } else{
                        smallestXDifference = tempX;
                        smallestYDifference = tempY;
                    }

                }
                System.out.println();
            }
        }
        filledHexes[definitiveY][definitiveX] = true;
        activeRegularBubble.setPosX(closestPoint.x);
        activeRegularBubble.setPosY(closestPoint.y);
        drawArray(filledHexes);
    }

    private void drawArray(boolean[][] hexGrid){
        if(!logged){
            for(int y = 0; y < hexGrid.length; y++){
                for(int x = 0; x < hexGrid[y].length; x++){
                    System.out.print(hexGrid[y][x]);
                }
                System.out.println();
            }
            logged = true;
        }
    }

    private synchronized boolean checkCollisions(Bubble regularBubble){
        float collisionLeftWall = MINFIELDX + Bubble.BUBBLERADIUS;
        float collisionRightWall = MAXFIELDX - Bubble.BUBBLERADIUS;
        float collisionCeiling = MINFIELDY + Bubble.BUBBLERADIUS + 5;
        float posBubbleX = regularBubble.getPosX();
        float posBubbleY = regularBubble.getPosY();
        if(posBubbleX < collisionLeftWall || collisionRightWall < posBubbleX){
            regularBubble.flipAngle();
        }
        if(posBubbleY < collisionCeiling){
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
                if (distanceBetweenBubbles < ((Bubble.BUBBLERADIUS * 2) + 4)) {
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
        nextRegularBubble = new Bubble();
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
        initialiseFilledHexes();
        regularBubbles = new ArrayList<>();
        activeRegularBubble = null;
        nextRegularBubble = new Bubble();
    }

    public Bubble getNextRegularBubble() {
        return nextRegularBubble;
    }
}