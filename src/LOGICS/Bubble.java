package logics;

import java.util.Random;
import interfaces.MovingObject;

public class Bubble extends MovingObject {

    int bubbleColorValueX;
    int bubbleColorValueY;

    public Bubble(){
        super();
        setPosX(BubbleField.MAXFIELDX / 2);
        setPosY(BubbleField.MAXFIELDY - RADIUS);
        bubbleColorValueX = new Random().nextInt(4);
        bubbleColorValueY = new Random().nextInt(4);
    }

    public void setBubbleTrajectoryAngle(float bubbleTrajectoryAngle){
        super.setBubbleTrajectoryAngle(bubbleTrajectoryAngle);
        this.setBubbleSpeedX((float)(BASEBUBBLESPEED * Math.cos(Math.toRadians(bubbleTrajectoryAngle))));
        this.setBubbleSpeedY((float)(BASEBUBBLESPEED * Math.sin(Math.toRadians(bubbleTrajectoryAngle))));
    }

    public void setBubbleSpeed(float[] speeds){
        setBubbleSpeedX(speeds[0]);
        setBubbleSpeedY(speeds[1]);
    }

    public void moveBubble(){
        setPosX(getPosX() + getBubbleSpeedX());
        setPosY(getPosY() + getBubbleSpeedY());
    }

    public int getBubbleColorValueX(){
        return bubbleColorValueX;
    }

    public int getBubbleColorValueY(){
        return bubbleColorValueY;
    }

    public int getBubbleRenderPosX(){
        return (int)(posX - RADIUS);
    }

    public int getBubbleRenderPosY(){
        return (int)(posY - RADIUS);
    }

    public void flipAngle(){
        setBubbleSpeedX(-getBubbleSpeedX());
    }

}
