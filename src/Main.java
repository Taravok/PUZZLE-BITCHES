
import LOGICS.BubbleField;
import GAME_UI.*;

public class Main {

    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            /**
             * TODO: Make a menu panel that creates the gameframe, Main doesn't need to know how te game gets started
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