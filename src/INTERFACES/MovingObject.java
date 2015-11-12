package interfaces;

import java.awt.*;

public abstract class MovingObject extends GameObject {

    public final static float BUBBLERADIUS = 37;
    public final static int BASEBUBBLESPEED = 5;
    private float bubbleSpeedX;
    private float bubbleSpeedY;
    private float bubbleTrajectoryAngle;
    private Color bubbleColor;

    public float getBubbleSpeedX() {
        return bubbleSpeedX;
    }

    public void setBubbleSpeedX(float bubbleSpeedX) {
        this.bubbleSpeedX = bubbleSpeedX;
    }

    public float getBubbleSpeedY() {
        return bubbleSpeedY;
    }

    public void setBubbleSpeedY(float bubbleSpeedY) {
        this.bubbleSpeedY = bubbleSpeedY;
    }

    public float getBubbleTrajectoryAngle() {
        return bubbleTrajectoryAngle;
    }

    public void setBubbleTrajectoryAngle(float bubbleTrajectoryAngle) {
        this.bubbleTrajectoryAngle = bubbleTrajectoryAngle;
    }

    public Color getBubbleColor() {
        return bubbleColor;
    }

    public void setBubbleColor(Color bubbleColor) {
        this.bubbleColor = bubbleColor;
    }
}
