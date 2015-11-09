/**
 * Created by yoeri on 6/11/2015.
 */

import java.awt.*;
import java.util.Formatter;
import java.util.Random;

public class Bubble {

    private float posBubbleX;
    private float posBubbleY;
    private static float bubbleRadius = 25;

    private float bubbleSpeedX;
    private float bubbleSpeedY;

    private Color bubbleColor;
    private Color[] colorList = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};

    public float getBubbleSpeedX() {
        return bubbleSpeedX;
    }

    public void setBubbleSpeedX(float bubbleSpeedX) {
        this.bubbleSpeedX = bubbleSpeedX;
    }

    public float getPosBubbleX() {
        return posBubbleX;
    }

    public void setPosBubbleX(float posBubbleX) {
        this.posBubbleX = posBubbleX;
    }

    public float getPosBubbleY() {
        return posBubbleY;
    }

    public void setPosBubbleY(float posBubbleY) {
        this.posBubbleY = posBubbleY;
    }

    public static float getBubbleRadius() {
        return bubbleRadius;
    }

    public float getBubbleSpeedY() {
        return bubbleSpeedY;
    }

    public void setBubbleSpeedY(float bubbleSpeedY) {
        this.bubbleSpeedY = bubbleSpeedY;
    }

    public Color getBubbleColor() {
        return bubbleColor;
    }

    public Bubble(float posBubbleX, float posBubbleY, float speed, float angleInDegrees){

        this.posBubbleX = posBubbleX;
        this.posBubbleY = posBubbleY;
        bubbleSpeedX = (float)(speed * Math.cos(Math.toRadians(angleInDegrees)));
        bubbleSpeedY = (float)(-speed * Math.sin(Math.toRadians(angleInDegrees)));
        bubbleColor = colorList[new Random().nextInt(colorList.length)];
    }

    public void drawBubble(Graphics g){
        g.setColor(bubbleColor);
        g.fillOval((int)(posBubbleX - bubbleRadius), (int)(posBubbleY - bubbleRadius), (int)(2 * bubbleRadius), (int)(2 * bubbleRadius));
    }

    public float getMoveAngle() {
        return (float)Math.toDegrees(Math.atan2(-bubbleSpeedY, bubbleSpeedX));
    }

    private StringBuilder sb = new StringBuilder();
    private Formatter formatter = new Formatter(sb);

    public String toString() {
        sb.delete(0, sb.length());
        formatter.format("@(%3.0f,%3.0f) V=(%2.0f,%2.0f) Angle=%4.0f color=%s", //angle in counterclockwise degrees !
                posBubbleX, posBubbleY, bubbleSpeedX, bubbleSpeedY, getMoveAngle(), getBubbleColor());
        return sb.toString();
    }

}
