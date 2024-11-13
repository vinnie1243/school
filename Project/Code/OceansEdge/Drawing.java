package OceansEdge;

//builtin classes
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import java.awt.Image;

//Custom Classes
import OceansEdge.Parsers.MenuParser;

public class Drawing {
    public static double elapsedTime = 0;
    public static int debug = 1;
    public static DataStructures.Vertex camera = Client.ds.new Vertex(Client.player.position.x, Client.player.position.y, Client.player.position.z);
    public static DataStructures.Vertex lookDir = Client.ds.new Vertex(0, 0, 1);
    public static ArrayList<DataStructures.Triangle> tris = new ArrayList<DataStructures.Triangle>();
    public static ArrayList<DataStructures.Chunk> chunks = new ArrayList<DataStructures.Chunk>();
    private static long lastFrameTime = System.nanoTime();
    private static double fps = 0;
    public static boolean DEBUG = false;
    public static Map<String, Img> textures = new HashMap<String, Img>();
    public static Map<String, DataStructures.Texture> textureList = new HashMap<String, DataStructures.Texture>();
    public static HTMLRender render = new HTMLRender();
    public static MenuParser parser = new MenuParser();
    public static int[][] chunkArr = {
        {1,2,3,4,5,6,7,8},
        {9,10,11,12,13,14,15,16},
        {17,18,19,20,21,22,23,24},
        {25,26,27,28,29,30,31,32},
        {33,34,35,36,37,38,39,40},
        {41,42,43,44,45,46,47,48},
        {49,50,51,52,53,54,55,56},
        {57,58,59,60,61,62,63,64}
    };

    //mode 1 is game
    //mode 2 is main menu
    public static void draw(Graphics g, int mode) {
        
        long currentFrameTime = System.nanoTime();
        long frameTime = currentFrameTime - lastFrameTime;
        lastFrameTime = currentFrameTime;
        fps = 1_000_000_000.0 / frameTime;               

        if(mode == 1) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, Client.screenWidth, Client.screenHeight);
            //System.out.println("Drawing chunks: " + Drawing.chunks.size());
            for(int i = 0; i < Drawing.chunks.size(); i++) {      
                //System.out.println("Drawing objects: " + Drawing.chunks.get(i).objects.size());
                if (Drawing.chunks.get(i).objects != null && !Drawing.chunks.get(i).objects.isEmpty()) {
                    for(int j = 0; j < Drawing.chunks.get(i).objects.size(); j++) {
                        if(Drawing.chunks.get(i).objects.get(j).tris != null && !Drawing.chunks.get(i).objects.get(j).tris.isEmpty()) {
                            for(int k = 0; k < Drawing.chunks.get(i).objects.get(j).tris.size(); k++) {
                                if(Drawing.DEBUG == false) {
                                    fillTriangle(g, Drawing.chunks.get(i).objects.get(j).tris.get(k), Drawing.chunks.get(i).objects.get(j).tris.get(k).color);
                                    drawTriangle(g, Drawing.chunks.get(i).objects.get(j).tris.get(k), Color.BLUE);
                                } else {
                                    DataStructures.Triangle tri = Drawing.chunks.get(i).objects.get(j).tris.get(k);
                                    Drawing.drawTex((int) Math.round(tri.vertex1.x), (int) Math.round(tri.vertex1.y), tri.texvert1.u, tri.texvert1.v, (int) Math.round(tri.vertex2.x), (int) Math.round(tri.vertex2.y), tri.texvert2.u, tri.texvert2.v, (int) Math.round(tri.vertex3.x), (int) Math.round(tri.vertex3.y), tri.texvert3.u, tri.texvert3.v, tri.tex, g);  
                                }
                            }
                        }
                    }
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString(String.format("FPS: %.2f", fps), Client.screenWidth - 100, 30);    
        } else if(mode == 2) {
            switch (Client.menu) {
                case "main":
                    //parse menu
                    DataStructures.HTMLNode root = parser.parseMenu("main");
                    //render menu
                    render.render(root, g);
                break;
                case "settings": 
                    Document dataSettings;
                    dataSettings = Xml.get("../Data/menuData.xml"); 
                    Element rootSettings = dataSettings.getDocumentElement();
                    rootSettings = Xml.childEle(rootSettings, "settings");
                    ArrayList<Element> arrSettings = new ArrayList<Element>();
                    for(int i = 0; i < rootSettings.getChildNodes().getLength(); i++) {
                        if(rootSettings.getChildNodes().item(i).getNodeType() == 1) {
                            arrSettings.add((Element)rootSettings.getChildNodes().item(i));
                        }
                    }
                    for(int i = 0 ; i < arrSettings.size(); i++) {
                        if(arrSettings.get(i).getTagName().equals("button")) {
                            
                        } else if(arrSettings.get(i).getTagName().equals("bar")) {

                        } else if(arrSettings.get(i).getTagName().equals("label")) {

                        } else if(arrSettings.get(i).getTagName().equals("header")) {

                        }
                    }
                break;
                case "credits":

                break;
            }
        }
    }

    public static void loadTexture(String path, String name) {
        ArrayList<DataStructures.Pixel> pixels = Xml.getPixels(path);
        Img img = new Img();
        img.store(pixels, name);
        img.load();
        System.out.println(img.pixels.size() + " " + name);
    }

    public static void makeTexture(Img img, int width, int height, String name) {
        DataStructures.Texture tex = Client.ds.new Texture(name, width, height);
        tex.loadImage(img);
        tex.load();
        textureList.put(name, tex);
    }

    // Method to remove all buttons from drawPanel
    public static void removeAllButtons() {   
        Component[] components = Client.drawPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                Client.drawPanel.remove(component);
            }
        }
        Client.drawPanel.revalidate();
    }

    public static void drawTriangle(Graphics g, DataStructures.Triangle tri, Color col) {
        //System.out.println(tri.vertex1.x + " " + tri.vertex1.y + " " + tri.vertex1.z + " " + tri.vertex2.x + " " + tri.vertex2.y + " " + tri.vertex2.z + " " + tri.vertex3.x + " " + tri.vertex3.y + " " + tri.vertex3.z);
        g.setColor(col);
        g.drawLine((int)tri.vertex1.x, (int)tri.vertex1.y, (int)tri.vertex2.x, (int)tri.vertex2.y);
        g.drawLine((int)tri.vertex2.x, (int)tri.vertex2.y, (int)tri.vertex3.x, (int)tri.vertex3.y);
        g.drawLine((int)tri.vertex3.x, (int)tri.vertex3.y, (int)tri.vertex1.x, (int)tri.vertex1.y);
    }
    
    public static void fillTriangle(Graphics g, DataStructures.Triangle tri, Color col) {
        g.setColor(col);
        g.fillPolygon(new int[] {(int)tri.vertex1.x, (int)tri.vertex2.x, (int)tri.vertex3.x}, new int[] {(int)tri.vertex1.y, (int)tri.vertex2.y, (int)tri.vertex3.y}, 3);
    }

    public static void drawTex(int x1, int y1, double u1, double v1, int x2, int y2, double u2, double v2, int x3, int y3, double u3, double v3, DataStructures.Texture tex, Graphics g) {
        // Sort vertices by y-coordinate
        if (y1 > y2) {
            int tempX = x1, tempY = y1;
            double tempU = u1, tempV = v1;
            x1 = x2; y1 = y2; u1 = u2; v1 = v2;
            x2 = tempX; y2 = tempY; u2 = tempU; v2 = tempV;
        }
        if (y1 > y3) {
            int tempX = x1, tempY = y1;
            double tempU = u1, tempV = v1;
            x1 = x3; y1 = y3; u1 = u3; v1 = v3;
            x3 = tempX; y3 = tempY; u3 = tempU; v3 = tempV;
        }
        if (y2 > y3) {
            int tempX = x2, tempY = y2;
            double tempU = u2, tempV = v2;
            x2 = x3; y2 = y3; u2 = u3; v2 = v3;
            x3 = tempX; y3 = tempY; u3 = tempU; v3 = tempV;
        }

        // Create a BufferedImage from the texture
        BufferedImage textureImage = textureList.get(tex.name).image;;

        // Calculate the bounding box of the triangle
        int minX = Math.min(x1, Math.min(x2, x3));
        int minY = Math.min(y1, Math.min(y2, y3));
        int maxX = Math.max(x1, Math.max(x2, x3));
        int maxY = Math.max(y1, Math.max(y2, y3));

        // Create an AffineTransform to scale the texture image to fit the triangle
        AffineTransform transform = new AffineTransform();
        transform.translate(minX, minY);
        transform.scale((double)(maxX - minX) / tex.getWidth(), (double)(maxY - minY) / tex.getHeight());

        // Draw the transformed texture image
        Graphics2D g2d = (Graphics2D) g;
        g2d.setClip(new Polygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3));
        g2d.drawImage(textureImage, transform, null);
        return;
    }
   
    public static int[] getChunkIds(int chunk) {
        int[] chunks = new int[9];
        chunks[0] = chunk;
        int[] xy = Drawing.figXY(chunk);
        if(xy[1] - 1 >= 0) {
            chunks[1] = chunkArr[xy[1] - 1][xy[0]]; //up
        }
        if(xy[0] - 1 >= 0 && xy[1] - 1 >= 0) {
            chunks[2] = chunkArr[xy[1] - 1][xy[0] + 1]; //up-right
        }
        if(xy[0] + 1 <= 7) {
            chunks[3] = chunkArr[xy[1]][xy[0] + 1]; //right
        }
        if(xy[1] + 1 <= 7 && xy[0] + 1 <= 7) {
            chunks[4] = chunkArr[xy[1] + 1][xy[0] + 1]; //down-right
        }
        if(xy[1] + 1 <= 7) {
            chunks[5] = chunkArr[xy[1] + 1][xy[0]]; //down
        }
        if(xy[1] + 1 <= 7 && xy[0] - 1 >= 0) {
            chunks[6] = chunkArr[xy[1] + 1][xy[0] - 1]; //down-left
        }
        if(xy[0] - 1 >= 0) {
            chunks[7] = chunkArr[xy[1]][xy[0] - 1]; //left
        }
        if(xy[1] - 1 >=0 && xy[0] - 1 >= 0) {
            chunks[8] = chunkArr[xy[1] - 1][xy[0] - 1]; //up-left
        }
        return chunks;
    }

    public static int[] figXY(int chunk) {
        int[] xy = new int[2];
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(chunkArr[i][j] == chunk) {
                    xy[0] = i;
                    xy[1] = j;
                }
            }
        }
        return xy;
    }
    
    class MatrixData {
        public static double znear = 0.1;
        public static double zfar = 1000.0; 
        public static double fov = 90.0;
        public static double aspectratio = (double) Client.screenHeight / (double) Client.screenWidth;
        public static double fovrad = 1.0 / Math.tan(fov * 0.5 / 180.0 * Math.PI);
    }

    class Utilities {
        public int otri1 = 0;
        public int otri2 = 0;
        public DataStructures.Vertex intersectPlane(DataStructures.Vertex planeP, DataStructures.Vertex planeN, DataStructures.Vertex lineStart, DataStructures.Vertex lineEnd) {
            planeN.normalise();
            double plane_d = -planeN.dot(planeP);
            double ad = lineStart.dot(planeN);
            double bd = lineEnd.dot(planeN);
            double t = (-plane_d - ad) / (bd - ad);
            DataStructures.Vertex lineStartToEnd = lineEnd.sub(lineStart);
            DataStructures.Vertex lineToIntersect = lineStartToEnd.mul(t);
            return lineStart.add(lineToIntersect);
        }

        public Double dist(DataStructures.Vertex p, DataStructures.Vertex planeP, DataStructures.Vertex planeN) {
            DataStructures.Vertex p2 = Client.ds.new Vertex(p.x, p.y, p.z);
            DataStructures.Vertex planeN2 = Client.ds.new Vertex(planeN.x, planeN.y, planeN.z);
            DataStructures.Vertex planeP2 = Client.ds.new Vertex(planeP.x, planeP.y, planeP.z);
            p2.normalise();
        
            return (planeN2.x * p2.x + planeN2.y * p2.y + planeN2.z * p2.z - planeN2.dot(planeP2));
        }

        public DataStructures.ClipRet clip(DataStructures.Vertex planeP, DataStructures.Vertex planeN, DataStructures.Triangle tri) {
            DataStructures.Triangle outTri1 = Client.ds.new Triangle(Client.ds.new Vertex(0,0,0), Client.ds.new Vertex(0,0,0), Client.ds.new Vertex(0,0,0), Client.ds.new Texture(null, 0, 0), Client.ds.new TexVertex(0, 0), Client.ds.new TexVertex(0,0), Client.ds.new TexVertex(0,0));
            DataStructures.Triangle outTri2 = Client.ds.new Triangle(Client.ds.new Vertex(0,0,0), Client.ds.new Vertex(0,0,0), Client.ds.new Vertex(0,0,0), Client.ds.new Texture(null, 0, 0), Client.ds.new TexVertex(0, 0), Client.ds.new TexVertex(0,0), Client.ds.new TexVertex(0,0));
    
            // Make sure plane normal is indeed normal
            planeN.normalise();
            // Create two temporary storage arrays to classify points either side of plane
            // If distance sign is positive, point lies on "inside" of plane
            ArrayList<DataStructures.Vertex> insidePoints = new ArrayList<DataStructures.Vertex>();  
            ArrayList<DataStructures.Vertex> outsidePoints = new ArrayList<DataStructures.Vertex>(); 
            int insidePointCount = 0;
            int outsidePointCount = 0;
    
            // Get signed distance of each point in triangle to plane
            DataStructures.Vertex[] points = {Client.ds.new Vertex(tri.vertex1.x, tri.vertex1.y, tri.vertex1.z), Client.ds.new Vertex(tri.vertex2.x, tri.vertex2.y, tri.vertex2.z), Client.ds.new Vertex(tri.vertex3.x, tri.vertex3.y, tri.vertex3.z)};
            double d0 = dist(points[0], planeP, planeN);
            double d1 = dist(points[1], planeP, planeN);
            double d2 = dist(points[2], planeP, planeN);

            if (d0 >= 0) {
                insidePoints.add(tri.vertex1);
                insidePointCount++;
            } else {
                outsidePoints.add(tri.vertex1);
                outsidePointCount++; 
            }

            if (d1 >= 0) { 
                insidePoints.add(tri.vertex2);
                insidePointCount++; 
            } else { 
                outsidePoints.add(tri.vertex2); 
                outsidePointCount++;
            }

            if (d2 >= 0) { 
                insidePoints.add(tri.vertex3);
                insidePointCount++; 
            } else { 
                outsidePoints.add(tri.vertex3);
                outsidePointCount++; 
            }
            // Now classify triangle points, and break the input triangle into 
            // smaller output triangles if required. There are four possible
            // outcomes...
    
            if (insidePointCount == 0) {
                // All points lie on the outside of plane, so clip whole triangle
                // It ceases to exist
                DataStructures.ClipRet ret = Client.ds.new ClipRet(0, null, null);
                return ret; // No returned triangles are valid
            }
    
            if (insidePointCount == 3) {
                // All points lie on the inside of plane, so do nothing
                // and allow the triangle to simply pass through
                DataStructures.ClipRet ret = Client.ds.new ClipRet(1, tri, null);
                return ret; // Just the one returned original triangle is valid
            }
    
            if (insidePointCount == 1 && outsidePointCount == 2) {
               // The inside point is valid, so keep that...
               outTri1.vertex1 = insidePoints.get(0);
               outTri1.color = tri.color;
               // but the two new points are at the locations where the 
               // original sides of the triangle (lines) intersect with the plane
               outTri1.vertex2 = intersectPlane(planeP, planeN, insidePoints.get(0), outsidePoints.get(0));
               outTri1.vertex3 = intersectPlane(planeP, planeN, insidePoints.get(0), outsidePoints.get(1));
               DataStructures.ClipRet ret = Client.ds.new ClipRet(1, outTri1, null);
               return ret; // Return the newly formed single triangle
            }
    
            if (insidePointCount == 2 && outsidePointCount == 1) {
                // Triangle should be clipped. As two points lie inside the plane,
                // the clipped triangle becomes a "quad". Fortunately, we can
                // represent a quad with two new triangles
    
                // Copy appearance info to new triangles
    
                // The first triangle consists of the two inside points and a new
                // point determined by the location where one side of the triangle
                // intersects with the plane
                outTri1.vertex1 = insidePoints.get(0);
                outTri1.vertex2 = insidePoints.get(1);
                outTri1.vertex3 = intersectPlane(planeP, planeN, insidePoints.get(0), outsidePoints.get(0));
                outTri1.color = tri.color;
                // The second triangle is composed of one of he inside points, a
                // new point determined by the intersection of the other side of the 
                // triangle and the plane, and the newly created point above
                outTri2.vertex1 = insidePoints.get(1);
                outTri2.vertex2 = outTri1.vertex3;
                outTri2.vertex3 = intersectPlane(planeP, planeN, insidePoints.get(1), outsidePoints.get(0));
                outTri2.color = tri.color;
                DataStructures.ClipRet ret = Client.ds.new ClipRet(2, outTri1, outTri2);
                return ret; // Return two newly formed triangles which form a quad
            }
            return Client.ds.new ClipRet(0, null, null);
        }
    }
}

