package game_ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import logics.*;

class GameCanvas extends Canvas {

    private int canvasWidth;
    private int canvasHeight;
    private BubbleField bubbleField;
    private Renderer renderer;
    private Shooter shooter;
    private BufferStrategy bs;
    private Thread renderThread;

    public GameCanvas(int width, int height, BubbleField bubbleField){
        //int controllerHeight = 30;
        canvasWidth = width;
        canvasHeight = height;
        this.bubbleField = bubbleField;
        this.renderer = new Renderer(bubbleField, this);
        this.shooter = new Shooter(canvasWidth, canvasHeight, renderer.getImageWidth(), bubbleField);
        setFocusable(true);
        bindKeyEvent();
    }

    private void bindKeyEvent(){
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();
                if (key == 37 || key == KeyEvent.VK_A) {
                    bubbleField.moveLauncherLeft();
                } else if (key == 39 || key == KeyEvent.VK_D) {
                    bubbleField.moveLauncherRight();
                } else if (key == 38 || key == KeyEvent.VK_W) {
                    bubbleField.addBubble();
                } else if (e.getKeyChar() == 'r'){
                    bubbleField.clearField();
                } else if(key == 27) System.exit(1);
            }
        });
    }

    public void renderLoop(){
        renderThread = new Thread(){
            public void run(){
                while(true){
                    paintComponents();
                }
            }
        };
        renderThread.start();
    }

    public void paintComponents(){
        if(bs == null){
            this.createBufferStrategy(5);
            bs = getBufferStrategy();
        }
        Graphics2D g2d = (Graphics2D)bs.getDrawGraphics();
        g2d.setColor(new Color(173, 144, 13));
        g2d.fillRect(0, 0, canvasWidth, canvasHeight);
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHints(rh);
        //renderer.paintGrid(g2d);
        renderer.paintNextBubble(g2d);
        renderer.paintBubbles(g2d);
        renderer.paintHexGrid(g2d);
        renderer.paintShooter(g2d, shooter);
        bs.show();
    }

    @Override
    public Dimension getPreferredSize(){
        return (new Dimension(canvasWidth, canvasHeight));
    }

}