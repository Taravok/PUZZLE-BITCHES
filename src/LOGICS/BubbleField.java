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

    public void setHexGrid(Point[][] hexGrid1){
        if(hexGrid == null){
            System.out.println("setHexGrid called and assigned");
            this.hexGrid = hexGrid1;
        }
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
        float smallestX = 99999;
        float smallestY = 99999;
        Point closestPoint = new Point(0, 0);
        for (int y = 0; hexGrid != null && y < hexGrid.length; y ++) {
            for (int x = 0; hexGrid[y] != null && x < hexGrid[y].length; x++) {
                float gridPosX = hexGrid[y][x].x;
                float gridPosY = hexGrid[y][x].y;
                if(Math.abs(activeX - gridPosX) < smallestX && Math.abs(activeY - gridPosY) <= smallestY){
                    smallestX = activeX - hexGrid[y][x].x;
                    smallestY = activeY - hexGrid[y][x].y;
                    closestPoint = hexGrid[y][x];
                }
            }
        }
        activeRegularBubble.setPosY(closestPoint.y);
        activeRegularBubble.setPosX(closestPoint.x);
    }

    private void drawArray(Point[][] hexGrid){
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
        regularBubbles = new ArrayList<>();
        activeRegularBubble = null;
        nextRegularBubble = new Bubble();
    }

    public Bubble getNextRegularBubble() {
        return nextRegularBubble;
    }
}