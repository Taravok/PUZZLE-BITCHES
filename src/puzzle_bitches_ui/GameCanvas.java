package puzzle_bitches_ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;

import puzzle_bitches_logic.*;

/**
 * Created by Brenainn on 09/11/2015.
 */

class GameCanvas extends Canvas {

    private int canvasWidth;
    private int canvasHeight;
    private BubbleField bubbleField;
    private Renderer renderer;
    private Launcher launcher;
    private BufferStrategy bs;
    private Thread renderThread;

    public GameCanvas(int width, int height, BubbleField bubbleField){
        int controllerHeight = 30;
        canvasWidth = width;
        canvasHeight = height - controllerHeight;
        this.bubbleField = bubbleField;
        this.renderer = new Renderer(bubbleField, this);
        this.launcher = new Launcher(canvasWidth, canvasHeight, renderer.getImageWidth(), bubbleField);
        setFocusable(true);
        bindKeyEvent();
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
                } else if (e.getKeyChar() == 'r'){
                    bubbleField.clearField();
                }
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
        renderer.paintGrid(g2d);
        renderer.paintNextBubble(g2d);
        renderer.paintBubbles(g2d);
        renderer.paintLauncher(g2d, launcher);
        bs.show();
    }

    @Override
    public Dimension getPreferredSize(){
        return (new Dimension(canvasWidth, canvasHeight));
    }

}