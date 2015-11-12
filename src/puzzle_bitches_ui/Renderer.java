package puzzle_bitches_ui;

import puzzle_bitches_logic.BubbleField;
import puzzle_bitches_logic.Launcher;
import puzzle_bitches_logic.Bubble;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Brenainn on 12/11/2015.
 */
class Renderer {

    private BubbleField bubbleField;
    private Canvas renderingFor;
    private BufferedImage launcherImage;
    private BufferedImage spriteSheet;

    public Renderer(BubbleField bubbleField, Canvas renderingFor){
        this.bubbleField = bubbleField;
        this.renderingFor = renderingFor;
        loadImages();
    }

    public int getImageWidth(){
        return launcherImage.getWidth(renderingFor);
    }

    private void loadImages(){
        try{
            launcherImage = ImageIO.read(getClass().getResource("/puzzle_bitches_resources/arrow.png"));
            spriteSheet = ImageIO.read(getClass().getResource("/puzzle_bitches_resources/sprite-bubbles.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void paintGrid(Graphics2D g2d){
        for(int i = 25; i < renderingFor.getWidth(); i+= 25){
            g2d.setColor(Color.RED);
            g2d.drawLine(i, 0, i, renderingFor.getHeight());
        }
        for(int i = 25; i < renderingFor.getHeight(); i+= 25){
            g2d.drawLine(0, i, renderingFor.getWidth(), i);
        }
    }

    public void paintNextBubble(Graphics2D g2d){
        Bubble nextRegularBubble = bubbleField.getNextRegularBubble();
        float bubblePosX = nextRegularBubble.getPosX();
        float bubblePosY = nextRegularBubble.getPosY();
        g2d.setColor(nextRegularBubble.getBubbleColor());
        g2d.fillOval(50, BubbleField.MAXFIELDY - 60, (int)(2 * Bubble.BUBBLERADIUS), (int)(2 * Bubble.BUBBLERADIUS));
    }

    public void paintBubbles(Graphics2D g2d){
        ArrayList<Bubble> regularBubbles = (ArrayList<Bubble>)bubbleField.getRegularBubbles().clone();
        for(Bubble regularBubble : regularBubbles){
            int bubblePosX = regularBubble.getBubbleRenderPosX();
            int bubblePosY = regularBubble.getBubbleRenderPosY();
            BufferedImage bubbleSprite = spriteSheet.getSubimage(regularBubble.getBubbleColorValueX() * 32, regularBubble.getBubbleColorValueY() * 32, 32, 32);
            g2d.drawImage(bubbleSprite, bubblePosX, bubblePosY, renderingFor);
        }
    }

    public void paintLauncher(Graphics2D g2d, Launcher launcher){
        int x = (int)launcher.getPosX();
        int y = (int)launcher.getPosY();
        AffineTransform at = launcher.getAffineTransform(x, y, launcherImage.getWidth(renderingFor), launcherImage.getHeight(renderingFor));
        g2d.setTransform(at);
        g2d.drawImage(launcherImage, 0, 0, renderingFor);
    }

}
