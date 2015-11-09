package puzzle_bitches_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import puzzle_bitches_interfaces.Observer;
import puzzle_bitches_logic.*;

/**
 * Created by Brenainn on 09/11/2015.
 */

class GamePanel extends JPanel implements Observer {

    public final static int FRAMESPERSECOND = 120;
    private int canvasWidth;
    private int canvasHeight;
    private BubbleField bubbleField;

    public GamePanel(int width, int height, BubbleField bubbleField){
        int controllerHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controllerHeight;
        this.bubbleField = bubbleField;
        this.setBackground(Color.BLACK);
        bubbleField.registerObserver(this);
        update();
    }

    public void update(){
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        ArrayList<Bubble> bubbles = bubbleField.getBubbles();
        for(Bubble bubble : bubbles){
            float[] bubblePosition = bubble.getBubblePosition();
            g.setColor(bubble.getBubbleColor());
            g.fillOval((int)(bubblePosition[0] - Bubble.BUBBLERADIUS), (int)(bubblePosition[1] - Bubble.BUBBLERADIUS), (int)(2 * Bubble.BUBBLERADIUS), (int)(2 * Bubble.BUBBLERADIUS));
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return (new Dimension(canvasWidth, canvasHeight));
    }

}
