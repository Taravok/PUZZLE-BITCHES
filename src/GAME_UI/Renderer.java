package GAME_UI;

import LOGICS.BubbleField;
import LOGICS.Shooter;
import LOGICS.Bubble;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class Renderer {

    private BubbleField bubbleField;
    private Canvas renderingFor;
    private BufferedImage shooterImage;
    private BufferedImage spriteSheet;

    public Renderer(BubbleField bubbleField, Canvas renderingFor){
        this.bubbleField = bubbleField;
        this.renderingFor = renderingFor;
        loadImages();
    }

    public int getImageWidth(){
        return shooterImage.getWidth(renderingFor);
    }

    private void loadImages(){
        try{
            shooterImage = ImageIO.read(getClass().getResource("/RES/arrow.png"));
            spriteSheet = ImageIO.read(getClass().getResource("/RES/bubbles.png"));
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
            BufferedImage bubbleSprite = spriteSheet.getSubimage(regularBubble.getBubbleColorValueX() * 75, regularBubble.getBubbleColorValueY() * 75, 75, 75);
            g2d.drawImage(bubbleSprite, bubblePosX, bubblePosY, renderingFor);
        }
    }

    public void paintShooter(Graphics2D g2d, Shooter shooter){
        int x = (int) shooter.getPosX();
        int y = (int) shooter.getPosY();
        AffineTransform at = shooter.getAffineTransform(x, y, shooterImage.getWidth(renderingFor), shooterImage.getHeight(renderingFor));
        g2d.setTransform(at);
        g2d.drawImage(shooterImage, 0, 0, renderingFor);
    }

}
