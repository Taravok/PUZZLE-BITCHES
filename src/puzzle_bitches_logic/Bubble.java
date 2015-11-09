package puzzle_bitches_logic;

import java.awt.*;
import java.util.Random;

/**
 * Created by Brenainn on 09/11/2015.
 */
public class Bubble {

    public final static float BUBBLERADIUS = 25;
    public final static int BASEBUBBLESPEED = 5;

    private float posBubbleX;
    private float posBubbleY;
    private float bubbleSpeedX;
    private float bubbleSpeedY;
    private Color bubbleColor;

    public Bubble(float bubbleTrajectoryAngle){
        this.posBubbleX = BubbleField.MAXFIELDX / 2;
        this.posBubbleY = BubbleField.MAXFIELDY - 60;
        this.bubbleSpeedX = (float)(BASEBUBBLESPEED * Math.cos(Math.toRadians(bubbleTrajectoryAngle)));
        this.bubbleSpeedY = (float)(BASEBUBBLESPEED * Math.sin(Math.toRadians(bubbleTrajectoryAngle)));
        Color[] colorList = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};
        this.bubbleColor = colorList[new Random().nextInt(colorList.length)];
    }

    public Color getBubbleColor(){
        return bubbleColor;
    }

    public float[] getBubblePosition(){
        return new float[] { posBubbleX, posBubbleY };
    }

    public void setBubbleSpeed(float[] speeds){
        bubbleSpeedX = speeds[0];
        bubbleSpeedY = speeds[1];
    }

    public void updateBubble(){
        moveBubble();
    }

    public void moveBubble(){
        posBubbleX += bubbleSpeedX;
        posBubbleY += bubbleSpeedY;
        System.out.println("Current X : " + posBubbleX + " Current Y: " + posBubbleY);
    }

    public void flipAngle(){
        bubbleSpeedX = -bubbleSpeedX;
    }

}