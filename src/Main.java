/**
 * Created by yoeri on 6/11/2015.
 */

import javax.swing.*;

import puzzle_bitches_logic.BubbleField;
import puzzle_bitches_ui.GameFrame;

import java.util.ArrayList;
import java.util.Queue;

public class Main {

    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            /**
             * TODO: Make a menu panel that creates the gameframe, Main doesn't need to know hiw te game gets started
             */


            @Override
            public void run() {
                BubbleField bubbleField = new BubbleField();
                GameFrame gameFrame = new GameFrame(bubbleField);
                bubbleField.gameStart();
            }
        });
    }
}