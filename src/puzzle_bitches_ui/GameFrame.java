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

public class GameFrame extends JFrame{

    private GamePanel gamePanel;

    public GameFrame(BubbleField bubbleField){
        this.gamePanel = new GamePanel(BubbleField.MAXFIELDX, BubbleField.MAXFIELDY, bubbleField);
        initialiseFrame();
    }

    private void initialiseFrame(){
        this.setTitle("PUZZLE BITCHES!");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(gamePanel);
        this.pack();
        this.setVisible(true);
    }

}