package puzzle_bitches_ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import puzzle_bitches_interfaces.Observer;
import puzzle_bitches_logic.*;

/**
 * Created by Brenainn on 09/11/2015.
 */

class GamePanel extends JPanel implements Observer {

    private int canvasWidth;
    private int canvasHeight;
    private BufferedImage image;
    private BubbleField bubbleField;

    public GamePanel(int width, int height, BubbleField bubbleField){
        int controllerHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controllerHeight;
        this.bubbleField = bubbleField;
        loadImage();
        this.setBackground(Color.BLACK);
        setFocusable(true);
        bindKeyEvent();
        bubbleField.registerObserver(this);
    }

    private void loadImage(){
        //try{
        try{
            image = ImageIO.read(getClass().getResource("arrow.png"));
        } catch(Exception e){
            e.printStackTrace();
        }

            //image = ImageIO.read(new File("file:puzzle_bitches_resources/arrow.png"));
        //} catch(IOException e){
        //    e.printStackTrace();
        //}
    }

    private void bindKeyEvent(){
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyChar() == 'a') {
                    bubbleField.moveLauncherLeft();
                } else if (e.getKeyChar() == 'd') {
                    bubbleField.moveLauncherRight();
                } else if (e.getKeyChar() == 'w') {
                    bubbleField.addBubble();
                }
            }
        });
    }

    public void update(){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        paintBubbles(g);
        paintLauncher(g);
    }

    private void paintBubbles(Graphics g){
        ArrayList<Bubble> bubbles = bubbleField.getBubbles();
        for(Bubble bubble : bubbles){
            float[] bubblePosition = bubble.getBubblePosition();
            g.setColor(bubble.getBubbleColor());
            g.fillOval((int)(bubblePosition[0] - Bubble.BUBBLERADIUS), (int)(bubblePosition[1] - Bubble.BUBBLERADIUS), (int)(2 * Bubble.BUBBLERADIUS), (int)(2 * Bubble.BUBBLERADIUS));
        }
    }

    private void paintLauncher(Graphics g){
        Graphics2D g2d = (Graphics2D)g.create();
        int x = (getWidth() - image.getWidth()) / 2;
        int y = getHeight() - 45;
        AffineTransform at = new AffineTransform();
        at.setToRotation(Math.toRadians(bubbleField.getLauncherAngle()), x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, this);
    }

    @Override
    public Dimension getPreferredSize(){
        return (new Dimension(canvasWidth, canvasHeight));
    }

}