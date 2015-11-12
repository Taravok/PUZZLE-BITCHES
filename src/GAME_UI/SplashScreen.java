package game_ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SplashScreen extends JWindow {

    public SplashScreen(int waitTime)
    {
        JLabel l = new JLabel(new ImageIcon("/res/splash2.0.gif"));
        JWindow window = new JWindow();
        window.add(l);
        window.setSize(800,800);
        window.setOpacity(0.5f);
        getContentPane().add(l, BorderLayout.CENTER);
        pack();
        Dimension screenSize =
                Toolkit.getDefaultToolkit().getScreenSize();
        Dimension labelSize = l.getPreferredSize();
        setLocation(screenSize.width/2 - (labelSize.width/2),
                screenSize.height/2 - (labelSize.height/2));
        addMouseListener(new MouseAdapter()
        {
            public void mousePressed(MouseEvent e)
            {
                setVisible(false);
                dispose();
            }
        });
        final int pause = waitTime;
        final Runnable closerRunner = new Runnable()
        {
            public void run()
            {
                setVisible(false);
                dispose();
            }
        };
        Runnable waitRunner = new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(pause);
                    SwingUtilities.invokeAndWait(closerRunner);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        };
        setVisible(true);
        Thread splashThread = new Thread(waitRunner, "SplashThread");
        splashThread.start();
    }
}
