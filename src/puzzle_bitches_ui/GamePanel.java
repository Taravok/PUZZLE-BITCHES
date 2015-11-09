package puzzle_bitches_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import puzzle_bitches_interfaces.Observer;
import puzzle_bitches_logic.*;

/**
 * Created by Brenainn on 09/11/2015.
 */

class GamePanel extends JPanel implements Observer {

    private int canvasWidth;
    private int canvasHeight;
    private BubbleField bubbleField;

    public GamePanel(int width, int height, BubbleField bubbleField){
        int controllerHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controllerHeight;
        this.bubbleField = bubbleField;
        this.setBackground(Color.BLACK);
        bindClickEvent();
        bubbleField.registerObserver(this);
        update();
    }

    private void bindClickEvent(){
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bubbleField.addBubble();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
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