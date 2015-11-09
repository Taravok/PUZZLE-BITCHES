/**
 * Created by yoeri on 6/11/2015.
 */


import java.awt.*;
import java.awt.event.KeyEvent;

public class BubbleField {

    private static Color background =  Color.BLACK;
    private static float angleMin = 205;
    private static float angleMax = 335;

    private int minFieldX;
    private int minFieldY;
    private int maxFieldX;
    private int maxFieldY;
    private float angleInDegrees;
    private float angleCenter = 270;
    String test = "test";
    public static float getAngleMin() {
        return angleMin;
    }

    public static float getAngleMax() {
        return angleMax;
    }

    public float getAngleCenter() {
        return angleCenter;
    }

    public float getAngleInDegrees() {
        return angleInDegrees;
    }

    public void setAngleInDegrees(float angleInDegrees) {
        this.angleInDegrees = angleInDegrees;
    }

    public int getMinFieldX() {
        return minFieldX;
    }

    public int getMinFieldY() {
        return minFieldY;
    }

    public int getMaxFieldX() {
        return maxFieldX;
    }

    public int getMaxFieldY() {
        return maxFieldY;
    }


    public BubbleField(int x, int y, int width, int height){
        minFieldX = x;
        minFieldY = y;
        maxFieldX = x + width - 1;
        maxFieldY = y + height - 1;
    }

    public void checkOnCollision(BubbleField bubbleField, Bubble bubble) {

        float collisionBorderMinX = bubbleField.getMinFieldX() + bubble.getBubbleRadius();
        float collisionBorderMinY = bubbleField.getMinFieldY() + bubble.getBubbleRadius();
        float collisionBorderMaxX = bubbleField.getMaxFieldX() - bubble.getBubbleRadius();
        float collisionBorderMaxY = bubbleField.getMaxFieldY() - bubble.getBubbleRadius();

        bubble.setPosBubbleX(bubble.getPosBubbleX() + bubble.getBubbleSpeedX());
        bubble.setPosBubbleY(bubble.getPosBubbleY() + bubble.getBubbleSpeedY());

        if(bubble.getPosBubbleX() < collisionBorderMinX){
            bubble.setBubbleSpeedX(-bubble.getBubbleSpeedX());
            bubble.setPosBubbleX(collisionBorderMinX);
        } else if (bubble.getPosBubbleX() > collisionBorderMaxX) {
            bubble.setBubbleSpeedX(-bubble.getBubbleSpeedX());
            bubble.setPosBubbleX(collisionBorderMaxX);
        }
        if(bubble.getPosBubbleY() < collisionBorderMinY){
            bubble.setBubbleSpeedX(0);
            bubble.setBubbleSpeedY(0);
        }
        else if (bubble.getPosBubbleY() > collisionBorderMaxY) {
            bubble.setBubbleSpeedY(-bubble.getBubbleSpeedY());
            bubble.setPosBubbleY(collisionBorderMaxY);
        }
    }

    public void draw(Graphics g){
        g.setColor(background);
        g.fillRect(minFieldX, minFieldY, maxFieldX - minFieldX, maxFieldY - minFieldY);
    }
}
