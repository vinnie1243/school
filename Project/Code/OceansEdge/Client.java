package OceansEdge;

import javax.swing.*;   
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.*;
import java.awt.image.MemoryImageSource;

public class Client {
    public static int screenWidth;
    public static int screenHeight;
    public static JPanel drawPanel;
    public static Entities.Player player;
    public static DataStructures.Mouse m;
    public static int mode = 2;
    public static String menu = "main";
    public static boolean connected = false;
    public static InetAddress inetAddress;
    public static boolean connecting = false;
    public static boolean gameLoopRunning = false;
    public static DataStructures ds = new DataStructures();
    public static Data dataStore  = new Data();
    public static boolean focus = false;
    public static boolean mouseControl = true;
    public static int mouseX = 0;
    public static int mouseY = 0;
    public static ArrayList<Object> entities = new ArrayList<Object>();
    private static Robot robot;
    public static boolean[] keys = {false,false,false,false,false,false};//wasd control space 
    public static boolean processing = false;
    public static boolean noServer = true;
    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            // Handle the exception here
            e.printStackTrace();
        }
    }
    private static boolean recentering = false;

    static {
        try {
            inetAddress = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            // Handle the exception here
            e.printStackTrace();
        }
    }
    public static String ipAddress = inetAddress.getHostAddress();
    public static String[] connectData = {ipAddress, "5000"};
    
    public static void initialise(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Ocean's Edge"); // Create a JFrame with a title

        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        screenWidth = width;
        screenHeight = height;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        frame.setUndecorated(true); // Remove window decorations
        frame.setResizable(false); // Make the window non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Change to dispose on close

        // Set the frame to full-screen mode
        gd.setFullScreenWindow(frame);
        Entities en = new Entities();
        player = en.new Player();
        player.create("player", 100, 100, ds.new Vertex(3, 1, -1), Client.ds.new Vector(0, 0, 0), Client.ds.new Velocity(0,0,0), "../Data/sphere.xml", true, "idle", null, null, null, 0, 1);
        // Create a custom JPanel for Drawing1
        JPanel DrawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { 
                super.paintComponent(g);

                //need to make this a while loop later
                Drawing.draw(g, Client.mode); // Call the draw method from the Drawing class
            }
        };
        //success
        drawPanel = DrawingPanel;
        frame.add(drawPanel); // Add the custom Drawing panel to the frame

        // Make the Drawing panel focusable and request focus for key listener
        drawPanel.setFocusable(true);
        drawPanel.requestFocusInWindow();

        // Add a KeyListener to the Drawing panel
        drawPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //exit if backslash is pressed
                if(e.getKeyCode() == 192) { 
                    if(Drawing.DEBUG == true) {
                        Drawing.DEBUG = false;
                    } else {
                        Drawing.DEBUG = true;
                    }
                } 
                if(e.getKeyCode() == 87 && Client.mouseControl) { // move forward if w is pressed 
                    Client.keys[0] = true; //sets the w key to pressed
                }
                if(e.getKeyCode() == 83 && Client.mouseControl) { // move backward if s is pressed
                    Client.keys[1] = true;
                }
                if(e.getKeyCode() == 65 && Client.mouseControl) { // move left if a is pressed
                    Client.keys[2] = true;
                    Client.connected = true;
                }
                if(e.getKeyCode() == 68 && Client.mouseControl) { // move right if d is pressed
                    Client.keys[3] = true;
                    Client.connected = false;
                }
                if(e.getKeyCode() == 32 && Client.mouseControl) { // move up if space is pressed
                    Client.keys[4] = true;
                }
                if(e.getKeyCode() == 17 && Client.mouseControl) { // move down if ctrl is pressed
                    Client.keys[5] = true;
                }
                if(e.getKeyCode() == 82 && !Client.mouseControl) { //reset if r is pressed
                    Client.player.position = Client.ds.new Vertex(0, 0, 0);
                    Client.player.rotation = Client.ds.new Vector(0, 0, 0);
                    Client.player.motion = Client.ds.new Velocity(0, 0, 0);
                }
                if(e.getKeyCode() == 27 && Client.mode == 1) {
                    Client.menu = "pause";
                }
                //capslock
                if(e.getKeyCode() == 20) {
                    Client.mouseControl = !Client.mouseControl;
                }
            }

            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == 87) {
                    Client.keys[0] = false;
                }
                if(e.getKeyCode() == 83) { // move backward if s is pressed
                    Client.keys[1] = false;
                }
                if(e.getKeyCode() == 65) { // move left if a is pressed
                    Client.keys[2] = false;
                }
                if(e.getKeyCode() == 68) { // move right if d is pressed
                    Client.keys[3] = false;
                }
                if(e.getKeyCode() == 32) { // move up if space is pressed
                    Client.keys[4] = false;
                }
                if(e.getKeyCode() == 17) { // move down if ctrl is pressed
                    Client.keys[5] = false;
                }
            }
        });
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //System.out.println("Mouse Pressed");
            }
            public void mouseReleased(MouseEvent e) {
                //System.out.println("Mouse Released");
            }
            public void mouseClicked(MouseEvent e) {
                if(Client.mode == 2) {
                    
                }
            }
        }); 
        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                this.handleMouseMovement(e);
            }
            public void mouseDragged(MouseEvent e) {
                //System.out.println("Mouse Dragged");
            }
            private void handleMouseMovement(MouseEvent e) {
                if (Client.recentering || Client.focus == false || !Client.mouseControl) {
                    return;
                }
                //change to last mouse position system that also just loops the mouse to the other edge when it hits one
                int deltaX = e.getX() - Client.mouseX;
                int deltaY = e.getY() - Client.mouseY;

                Client.mouseX = e.getX();
                Client.mouseY = e.getY();

                if(recentering == false) {
                    player.updatePitchAndYaw(deltaX, deltaY);
                }

                recentering = true;
                if(Client.focus && e.getX() > Client.screenWidth - 300 && Client.mouseControl == true) {
                    robot.mouseMove(500,e.getY());
                    Client.mouseX = 500;
                    Client.mouseY = e.getY();
                } else if(Client.focus && e.getX() < 300 && Client.mouseControl == true) {
                    robot.mouseMove(Client.screenWidth - 500, e.getY());
                    Client.mouseX = Client.screenWidth - 500;
                    Client.mouseY = e.getY();
                }
                
                recentering = false;
            }
        });
        frame.setVisible(true); // Make the window visible
        
        //make loop threads 
        Thread dataThread = new Thread(() -> {
            try {
                dataStore.start();
            } catch (Exception e) {
                // TODO: handle exception
            }

        });

        //start threads
        dataThread.start();
        // Print the screen width and height to the console
        System.out.println("Screen Width: " + width + ", Screen Height: " + height);
    }

    public static void preProcess() {
        processing = true;
        //load Textures
        Drawing.loadTexture("Data/Textures/test.texture", "test");
        Drawing.loadTexture("Data/Textures/dog.texture", "dog");

        //load into images
        Drawing.makeTexture(Drawing.textures.get("test"), 3, 3, "test");

        processing = false;
    }

    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        // Will eventually replace this line with data file validation to prevent data corruption
        initialise(args); // Call initialise from main to open the window
        long startTime = System.currentTimeMillis();
        preProcess();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("preProcess took " + duration + " milliseconds to run.");
        while (true) {
            while (!Client.connected && !Client.menu.equals("connecting") && false) {
                Drawing.removeAllButtons();
                Client.drawPanel.repaint();
                Thread.sleep(1000 / 60);
            }
    
            while (!Client.connected && Client.menu.equals("connecting") && false) {
                Drawing.removeAllButtons();
                Client.drawPanel.repaint();
                Thread.sleep(1000 / 60);
    
                // Check if a connection attempt is already in progress
                if (!Client.connecting) {
                    Client.connecting = true; // Set a flag to indicate a connection attempt is in progress
                    if(Client.noServer == false) {
                        // Start a new thread to handle the connection
                        Thread connectThread = new Thread(() -> {
                            try {
                                if(Client.noServer == false) {
                                    connect(Client.connectData); 
                                }
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            Client.connecting = false; // Reset the flag after the connection attempt
                        });
                        connectThread.start();
                    } else {
                        Client.connected = true;
                        Client.connecting = false;
                    }
                }
            }
            Client.drawPanel.requestFocusInWindow();

            int[] pixels = new int[16 * 16];
            java.awt.Image image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, pixels, 0, 16));
            Cursor transparentCursor = Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "invisibleCursor");

            while (Client.connected || true) {
                Client.mode = 1;
                if(Client.mouseControl) {
                    Client.drawPanel.setCursor(transparentCursor);
                } else {
                    Client.drawPanel.setCursor(Cursor.getDefaultCursor());
                }

                gameLoop(); // Call the game loop
                Thread.sleep(1000 / 60);
            }
        }
    }

    //update the game state 

    public static void gameLoop() {  
        if(Client.drawPanel.hasFocus()) {
           Client.focus = true;
        }
        Client.player.move();
        Physics.update();
        int[] chunks = Drawing.getChunkIds(player.chunk);
        Xml.getChunkData(chunks);
        Client.drawPanel.repaint();
    }
    
    //connect to the server
    public static void connect(String[] args) throws IOException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
        String publicIp = in.readLine();
        System.out.println("Public IP Address: " + publicIp);
        in.close();
        try (Socket socket = new Socket(publicIp, 5500);
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            Client.connected = true;

            // Start a thread to listen for heartbeat signals
            Thread heartbeatThread = new Thread(() -> {
                while (Client.connected) {
                    try {
                        String message = dataInputStream.readUTF();
                        if ("HEARTBEAT".equals(message)) {
                            dataOutputStream.writeUTF("HEARTBEAT_RESPONSE");
                            dataOutputStream.flush();
                        }
                    } catch (IOException e) {
                        System.out.println("Heartbeat exception: " + e.getMessage());
                        Client.connected = false;
                    }
                }
            });
            heartbeatThread.start();

            if (args.length < 2) return;
            Client.connected = true;
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            Console console = System.console();
            String user;
            //String pass;
 
            do {
                user = console.readLine("Enter Username: ");
                //pass = console.readLine("Enter Password: ");
                
                writer.println(user);
                
                //nputStream input = socket.getInputStream();
                //BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
                //String time = reader.readLine();
    
                //System.out.println(time);
                
            } while (!user.equals("bye"));
            try {
                socket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}