import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by yoeri on 6/11/2015.
 */

public class GameFrame extends JPanel {

    private static int framesPerSecond = 120;

    private Bubble bubble;
    private ArrayList<Bubble> bubbeList = new ArrayList<Bubble>();
    private BubbleField bubbleField;
    private DrawCanvas canvas;

    private int canvasWidth;
    private int canvasHeight;
    int startPosX;
    int startPosY;
    int speed = 5;
    int angleInDegrees;
    String test = "test";

    private ControlPanel control;

    public GameFrame(int width, int height){

        int controllerHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controllerHeight;
        startPosX = canvasWidth /2;
        startPosY = canvasHeight - 25;

        System.out.println("Chosen angle(random): " + angleInDegrees);

        bubble = new Bubble(startPosX, startPosY, speed, new Random().nextInt(336-204) + 205);
        bubbeList.add(bubble);

        bubbleField = new BubbleField(0, 0, canvasWidth, canvasHeight);

        canvas = new DrawCanvas();
        control = new ControlPanel();
        this.setLayout(new BorderLayout());
        this.add(canvas, BorderLayout.CENTER);
        this.add(control, BorderLayout.SOUTH);

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

            for(Bubble bubble : bubbeList) {

                bubble.drawBubble(g);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.PLAIN, 16));
            g.drawString("Bubble " + bubble.toString(), 10, 20);

        }

        @Override
        public Dimension getPreferredSize(){
            return (new Dimension(canvasWidth, canvasHeight));
        }
    }

    class ControlPanel extends JPanel {

        public ControlPanel(){

            JButton launchBubble = new JButton("Launch a bubble");
            this.add(launchBubble);
            launchBubble.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    Bubble newBubble = new Bubble(startPosX, startPosY, speed, new Random().nextInt(336-204) + 205);
                    bubbeList.add(newBubble);

                    System.out.println("Lastbubble= " + bubbeList.get(bubbeList.size()-1));

                    transferFocusUpCycle();
                }
            });
        }
    }


}
