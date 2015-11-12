package puzzle_bitches_ui;

import javax.swing.*;
import puzzle_bitches_logic.*;

/**
 * Created by Brenainn on 09/11/2015.
 */

public class GameFrame extends JFrame{

    private GameCanvas gameCanvas;

    public GameFrame(BubbleField bubbleField){
        this.gameCanvas = new GameCanvas(BubbleField.MAXFIELDX, BubbleField.MAXFIELDY, bubbleField);
        initialiseFrame();
    }

    private void initialiseFrame(){
        this.setTitle("PUZZLE BITCHES!");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.add(gameCanvas);
        this.pack();
        this.setVisible(true);
        gameCanvas.renderLoop();
    }

}