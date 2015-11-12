package puzzle_bitches_logic;

import puzzle_bitches_interfaces.GameObject;
import java.awt.geom.AffineTransform;

/**
 * Created by Brenainn on 12/11/2015.
 */
public class Launcher extends GameObject {

    private BubbleField bubbleField;

    public Launcher(int parentWidth, int parentHeight, int imageWidth, BubbleField bubbleField){
        calculatePosition(parentWidth, parentHeight, imageWidth);
        this.bubbleField = bubbleField;
    }

    private void calculatePosition(int parentWidth, int parentHeight, int imageWidth){
        posX = (parentWidth - imageWidth) / 2;
        posY = parentHeight - 45;
    }

    public AffineTransform getAffineTransform(int x, int y, int imageWidth, int imageHeight){
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.toRadians(bubbleField.getLauncherAngle()), x + (imageWidth / 2), y + (imageHeight / 2));
        at.translate(x, y);
        return at;
    }

}
