import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.w3c.dom.Document;

public class Drawing {
   public static double elapsedTime = 0.0;

   public static void draw(Graphics g) {
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, Client.screenWidth, Client.screenHeight);
      Document doc = Xml.get("Data/videoShip.xml");
      DataStructures.Vertex pos = new DataStructures().new Vertex(0.0, 0.0, 0.0);
      DataStructures.Matrix projectionMatrix = new DataStructures().new Matrix();
      projectionMatrix.makeProjection();
      double theta = 1.0 * elapsedTime;
      DataStructures.Matrix rotz = new DataStructures().new Matrix();
      rotz.makeRotationZ(theta);
      DataStructures.Matrix rotx = new DataStructures().new Matrix();
      rotx.makeRotationX(theta);
      DataStructures.Matrix trans = new DataStructures().new Matrix();
      trans.makeTranslation(0.0, 0.0, 8.0);
      DataStructures.Matrix world = new DataStructures().new Matrix();
      world.makeIdentity();
      world = rotz.multiplyMatrix(rotx);
      world = world.multiplyMatrix(trans);
      ArrayList<DataStructures.Triangle> sort = new ArrayList<DataStructures.Triangle>();
      ArrayList<DataStructures.Triangle> tris = new ArrayList<DataStructures.Triangle>();
      tris.addAll(Xml.convert(doc));
      for(int i = 0; i < tris.size(); i++) {
         //world projection
         tris.get(i).vertex1 = world.mulitplyVector(((DataStructures.Triangle) tris.get(i)).vertex1);
         tris.get(i).vertex2 = world.mulitplyVector(((DataStructures.Triangle) tris.get(i)).vertex2);
         tris.get(i).vertex3 = world.mulitplyVector(((DataStructures.Triangle) tris.get(i)).vertex3);
         //z projection
         DataStructures.Vertex line1 = tris.get(i).vertex2.sub(tris.get(i).vertex1);
         DataStructures.Vertex line2 = tris.get(i).vertex3.sub(tris.get(i).vertex1);
         DataStructures.Vertex normal = line1.cross(line2);
         normal.normalise();
         DataStructures.Vertex var17 = tris.get(i).vertex1.sub(pos);
         if (var17.dot(normal) < 0.0) {
            DataStructures.Vertex light = new DataStructures().new Vertex(0.0, 0.0, -1.0);
            double l = Math.sqrt(light.x * light.x + light.y * light.y + light.z * light.z);
            light.x /= l;
            light.y /= l;
            light.z /= l;
            double dot = normal.x * light.x + normal.y * light.y + normal.z * light.z;
            //color
            double brightness = Math.max(0.0, Math.min(1.0, dot));
            int colr = (int) ((double) Color.WHITE.getRed() * brightness);
            int colg = (int) ((double) Color.WHITE.getGreen() * brightness);
            int colb = (int) ((double) Color.WHITE.getBlue() * brightness);
            Color fincol = new Color(colr, colg, colb);
            tris.get(i).setColor(fincol);
            //projection
            tris.get(i).vertex1 = projectionMatrix.mulitplyVector(tris.get(i).vertex1);
            tris.get(i).vertex2 = projectionMatrix.mulitplyVector(tris.get(i).vertex2);
            tris.get(i).vertex3 = projectionMatrix.mulitplyVector(tris.get(i).vertex3);
            tris.get(i).vertex1.x+=1;
            tris.get(i).vertex1.y+=1;
            tris.get(i).vertex2.x+=1;
            tris.get(i).vertex2.y+=1;
            tris.get(i).vertex3.x+=1;
            tris.get(i).vertex3.y+=1;
            DataStructures.Vertex vert1 = tris.get(i).vertex1;
            DataStructures.Vertex vert2 = tris.get(i).vertex2;
            DataStructures.Vertex vert3 = tris.get(i).vertex3;
            vert1.x *= 0.5 * Client.screenWidth;
            vert1.y *= 0.5 * Client.screenHeight;
            vert2.x *= 0.5 * Client.screenWidth;
            vert2.y *= 0.5 * Client.screenHeight;
            vert3.x *= 0.5 * Client.screenWidth;
            vert3.y *= 0.5 * Client.screenHeight;
            sort.add(tris.get(i));
         }
      }

      Collections.sort(sort, new Comparator<DataStructures.Triangle>() {
         public int compare(DataStructures.Triangle tri1, DataStructures.Triangle tri2) {
            double z1 = (tri1.vertex1.z + tri1.vertex2.z + tri1.vertex3.z) / 3.0;
            double z2 = (tri2.vertex1.z + tri2.vertex2.z + tri2.vertex3.z) / 3.0;
            if (z1 > z2) {
               return -1;
            } else {
               return z1 < z2 ? 1 : 0;
            }
         }
      });

      for(int i = 0; i < sort.size(); i++) {
         fillTriangle(g, sort.get(i), sort.get(i).getColor());
         //drawTriangle(g, sort.get(i), Color.BLUE);
      }
   }

   

   public static void drawTriangle(Graphics g, DataStructures.Triangle tri, Color var2) {
      g.setColor(var2);
      g.drawLine((int) tri.vertex1.x, (int) tri.vertex1.y, (int) tri.vertex2.x, (int) tri.vertex2.y);
      g.drawLine((int) tri.vertex2.x, (int) tri.vertex2.y, (int) tri.vertex3.x, (int) tri.vertex3.y);
      g.drawLine((int) tri.vertex3.x, (int) tri.vertex3.y, (int) tri.vertex1.x, (int) tri.vertex1.y);
   }

   public static void fillTriangle(Graphics g, DataStructures.Triangle tri, Color col) {
      g.setColor(col);
      g.fillPolygon(new int[]{(int) tri.vertex1.x, (int) tri.vertex2.x, (int) tri.vertex3.x}, new int[]{(int) tri.vertex1.y, (int) tri.vertex2.y, (int) tri.vertex3.y}, 3);
   }

   class matrixData {
      public static double znear = 0.1;
      public static double zfar = 1000.0;
      public static double fov = 90.0;
      public static double aspectratio;
      public static double fovrad;
   
      static {
         aspectratio = (double) Client.screenHeight / (double) Client.screenWidth;
         fovrad = 1.0 / Math.tan(fov * 0.5 / 180.0 * Math.PI);
      }
   }
   
}
