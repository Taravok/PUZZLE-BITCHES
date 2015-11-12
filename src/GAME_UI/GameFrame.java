package GAME_UI;

import javax.swing.*;
import LOGICS.*;

public class GameFrame extends JFrame{

    private GameCanvas gameCanvas;

    public GameFrame(BubbleField bubbleField){
        this.gameCanvas = new GameCanvas(BubbleField.MAXFIELDX, BubbleField.MAXFIELDY, bubbleField);
        initialiseFrame();
    }

    private void initialiseFrame(){
        this.setTitle("BUBBLE BITCHES!");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(gameCanvas);
        this.pack();
        this.setVisible(true);
        gameCanvas.renderLoop();
    }

}