package puzzle_bitches_interfaces;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Brenainn on 12/11/2015.
 */
public abstract class GameObject {

    public final static float RADIUS = 18F;
    public final static int SPEED = 18;
    public final static int ANGLESTEP = 7;

    protected float posX;
    protected float posY;
    //protected ID id;
    protected float trajectoryAngle;
    //protected BufferedImage spriteSheet = null;
    //protected BufferedImage image;
    protected boolean shooting;


    //public abstract void tick();
    //public abstract void render(Graphics g);

    public boolean isShooting(){
        return shooting;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) { this.posY = posY; }

    //public ID getId() { return id; }

    //public void setId(ID id) { this.id = id; }

    public float getTrajectoryAngle() {
        return trajectoryAngle;
    }

    public void setTrajectoryAngle(float trajectoryAngle) {
        this.trajectoryAngle = trajectoryAngle;
    }

}
