import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 * Created by yoeri on 6/11/2015.
 */

public class GameFrame extends JPanel {

    private static int framesPerSecond = 120;

    private Bubble bubble;
    private BubbleField bubbleField;
    private DrawCanvas canvas;

    private int canvasWidth;
    private int canvasHeight;

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public GameFrame(int width, int height){

        canvasWidth = width;
        canvasHeight = height;

        int speed = 5;
        int angleInDegrees = new Random().nextInt(336-204) + 205;
        System.out.println(angleInDegrees);

        //bubble = new Bubble(speed, bubbleField.getAngleInDegrees());
        bubble = new Bubble(speed, angleInDegrees);
        bubbleField = new BubbleField(0, 0, canvasWidth, canvasHeight);

        canvas = new DrawCanvas();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);

        gameStart();
    }

    public void gameStart(){

        Thread gameThread = new Thread(){
            public void run(){
                while(true){
                    gameUpdate();
                    repaint();
                    try {
                        Thread.sleep(1000 / framesPerSecond);
                    } catch (InterruptedException ex){}
                }
            }
        };
        gameThread.start();
    }

    public void gameUpdate(){
        bubbleField.checkOnCollision(bubbleField, bubble);
    }

    class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            bubbleField.draw(g);
            bubble.draw(g);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 18));
            g.drawString("Bubble " + bubble.toString(), 20, 30);
        }

        @Override
        public Dimension getPreferredSize(){
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }


}
