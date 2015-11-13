package game_ui;

import logics.BubbleField;
import logics.Shooter;
import logics.Bubble;
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
    private Font font = new Font("Arial", Font.BOLD, 14);
    FontMetrics metrics;

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
            shooterImage = ImageIO.read(getClass().getResource("/res/arrow.png"));
            spriteSheet = ImageIO.read(getClass().getResource("/res/bubbles.png"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void paintHexGrid(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Point origin = new Point (300, 445);

        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
        g2d.setFont(font);
        metrics = g.getFontMetrics();

        drawHexGridLoop(g2d, origin, 15, 39);
    }

    private void drawHexGridLoop(Graphics g, Point origin, int size, float radius) {
        double ang30 = Math.toRadians(30);
        double xOff = Math.cos(ang30) * radius;
        double yOff = Math.sin(ang30) * radius;
        int half = size / 2;

        for (int row = 0; row < size; row++) {
            int cols = (row % 2 == 0) ? 8 : 9;

            for (int col = 0; col < cols; col++) {
                int x = (int) (origin.x + xOff * (col * 2 + 1 - cols));
                int y = (int) (origin.y + yOff * (row - half) * 3);

                drawHex(g, row, col, x, y, radius);
            }
        }
    }

    private void drawHex(Graphics g, int posX, int posY, int x, int y, float r) {
        Graphics2D g2d = (Graphics2D) g;
        String text = String.format("%s : %s", coord(posX), coord(posY));
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();

        Hexagon hex = new Hexagon(x, y, r);
        hex.draw(g2d, x, y, 1, 0xFFDD88, false);

        g.setColor(new Color(0xFFFFFF));
        g.drawString(text, x - w/2, y + h/2);
    }

    private String coord(int value) {
        return (value > 0 ? "+" : "") + Integer.toString(value);
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
