/**
 * Created by yoeri on 6/11/2015.
 */

import javax.swing.*;

public class Main {

    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Puzzle Bobble");
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.setContentPane(new GameFrame(800, 800));
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}