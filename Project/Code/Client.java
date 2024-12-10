import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Client {
   public static int screenWidth;
   public static int screenHeight;
   public static JPanel drawPanel;
   public static Entity player = new Entity();

   public static void initialise(String[] var0) {
      JFrame frame = new JFrame("Ocean's Edge");
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
      int width = (int)dim.getWidth();
      int height = (int)dim.getHeight();
      screenWidth = width;
      screenHeight = height;
      frame.setSize(width, height);
      frame.setDefaultCloseOperation(3);
      JPanel draw = new JPanel() {
         protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Drawing.draw(g);
         }
      };
      drawPanel = draw;
      frame.add(drawPanel);
      drawPanel.setFocusable(true);
      drawPanel.requestFocusInWindow();
      drawPanel.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent key) {
            if (key.getKeyCode() == 112) {
               System.out.println("Exiting");
               System.exit(0);
            }

            System.out.println(key.getKeyCode());
         }
      });
      frame.setVisible(true);
      System.out.println("Screen Width: " + width + ", Screen Height: " + height);
   }

   public static void main(String[] args) {
      initialise(args);

      while(true) {
         Drawing.elapsedTime += 0.01;
         gameLoop();
         try {
            Thread.sleep(16);
         } catch (InterruptedException error) {
            error.printStackTrace();
         }
      }
   }

   public static void gameLoop() {
      drawPanel.repaint();
   }
}
