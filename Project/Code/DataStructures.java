import java.awt.Color;
import java.util.ArrayList;

public class DataStructures {
   class Matrix {
      public double[][] matrix = new double[4][4];
   
      public Matrix() {
         
      }
   
      public void set(int x, int y, double var3, String var5, double[][] var6) {
         if (var5.equals("set")) {
            this.matrix[x][y] = var3;
         } else if (var5.equals("replace")) {
            this.matrix = new double[var6.length][var6[0].length];
   
            for(int var7 = 0; var7 < var6.length; ++var7) {
               for(int var8 = 0; var8 < var6[var7].length; ++var8) {
                  this.matrix[var7][var8] = var6[var7][var8];
               }
            }
         }
   
      }
   
      public Vertex mulitplyVector(Vertex in) {
         Vertex out = new Vertex(0.0D, 0.0D, 0.0D);
         out.x = in.x * this.matrix[0][0] + in.y * this.matrix[1][0] + in.z * this.matrix[2][0] + this.matrix[3][0];
         out.y = in.x * this.matrix[0][1] + in.y * this.matrix[1][1] + in.z * this.matrix[2][1] + this.matrix[3][1];
         out.z = in.x * this.matrix[0][2] + in.y * this.matrix[1][2] + in.z * this.matrix[2][2] + this.matrix[3][2];
         double w = in.x * this.matrix[0][3] + in.y * this.matrix[1][3] + in.z * this.matrix[2][3] + this.matrix[3][3];
         if (w != 0.0 && w != 0.0) {
            out.x /= w;
            out.y /= w;
            out.z /= w;
         }
   
         return out;
      }
   
      public void makeIdentity() {
         this.matrix[0][0] = 1.0;
         this.matrix[1][1] = 1.0;
         this.matrix[2][2] = 1.0;
         this.matrix[3][3] = 1.0;
      }
   
      public void makeRotationX(double rot) {
         this.matrix[0][0] = 1.0;
         this.matrix[1][1] = Math.cos(rot);
         this.matrix[1][2] = Math.sin(rot);
         this.matrix[2][1] = -Math.sin(rot);
         this.matrix[2][2] = Math.cos(rot);
         this.matrix[3][3] = 1.0;
      }
   
      public void makeRotationY(double rot) {
         this.matrix[0][0] = Math.cos(rot);
         this.matrix[0][2] = Math.sin(rot);
         this.matrix[2][0] = -Math.sin(rot);
         this.matrix[1][1] = 1.0;
         this.matrix[2][2] = Math.cos(rot);
         this.matrix[3][3] = 1.0;
      }
   
      public void makeRotationZ(double rot) {
         this.matrix[0][0] = Math.cos(rot);
         this.matrix[0][1] = Math.sin(rot);
         this.matrix[1][0] = -Math.sin(rot);
         this.matrix[1][1] = Math.cos(rot);
         this.matrix[2][2] = 1.0;
         this.matrix[3][3] = 1.0;
      }
   
      public void makeProjection() {
         this.matrix[0][0] = Drawing.matrixData.aspectratio * Drawing.matrixData.fovrad;
         this.matrix[1][1] = Drawing.matrixData.fovrad;
         this.matrix[2][2] = Drawing.matrixData.zfar / (Drawing.matrixData.zfar - Drawing.matrixData.znear);
         this.matrix[3][2] = -Drawing.matrixData.zfar * Drawing.matrixData.znear / (Drawing.matrixData.zfar - Drawing.matrixData.znear);
         this.matrix[2][3] = 1.0;
         this.matrix[3][3] = 0.0;
      }
   
      public void makeTranslation(double x, double y, double z) {
         this.matrix[0][0] = 1.0;
         this.matrix[1][1] = 1.0;
         this.matrix[2][2] = 1.0;
         this.matrix[3][3] = 1.0;
         this.matrix[3][0] = x;
         this.matrix[3][1] = y;
         this.matrix[3][2] = z;
      }
   
      public Matrix multiplyMatrix(Matrix mati) {
         Matrix mato = new Matrix();
   
         for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
               mato.matrix[j][i] = this.matrix[j][0] * mati.matrix[0][i] + this.matrix[j][1] * mati.matrix[1][i] + this.matrix[j][2] * mati.matrix[2][i] + this.matrix[j][3] * mati.matrix[3][i];
            }
         }
   
         return mato;
      }
   
      public void pointAt(Vertex vert1, Vertex vert2, Vertex vert3) {
         Vertex vertex1 = vert2.sub(vert1);
         vertex1.normalise();
         Vertex vertex2 = vertex1.mul(vert3.dot(vertex1));
         Vertex vertex3 = vert3.sub(vertex2);
         vertex3.normalise();
         Vertex v = vertex3.cross(vertex1);
         this.matrix[0][0] = v.x;
         this.matrix[0][1] = v.y;
         this.matrix[0][2] = v.z;
         this.matrix[0][3] = 0.0;
         this.matrix[1][0] = v.x;
         this.matrix[1][1] = v.y;
         this.matrix[1][2] = v.z;
         this.matrix[1][3] = 0.0;
         this.matrix[2][0] = v.x;
         this.matrix[2][1] = v.y;
         this.matrix[2][2] = v.z;
         this.matrix[2][3] = 0.0;
         this.matrix[3][0] = v.x;
         this.matrix[3][1] = v.y;
         this.matrix[3][2] = v.z;
         this.matrix[3][3] = 1.0;
      }
   
      public void quickInverse() {
         this.matrix[0][0] = this.matrix[0][0];
         this.matrix[0][1] = this.matrix[1][0];
         this.matrix[0][2] = this.matrix[2][0];
         this.matrix[0][3] = 0.0;
         this.matrix[1][0] = this.matrix[0][1];
         this.matrix[1][1] = this.matrix[1][1];
         this.matrix[1][2] = this.matrix[2][1];
         this.matrix[1][3] = 0.0;
         this.matrix[2][0] = this.matrix[0][2];
         this.matrix[2][1] = this.matrix[1][2];
         this.matrix[2][2] = this.matrix[2][2];
         this.matrix[2][3] = 0.0;
         this.matrix[3][0] = -(this.matrix[3][0] * this.matrix[0][0] + this.matrix[3][1] * this.matrix[1][0] + this.matrix[3][2] * this.matrix[2][0]);
         this.matrix[3][1] = -(this.matrix[3][0] * this.matrix[0][1] + this.matrix[3][1] * this.matrix[1][1] + this.matrix[3][2] * this.matrix[2][1]);
         this.matrix[3][2] = -(this.matrix[3][0] * this.matrix[0][2] + this.matrix[3][1] * this.matrix[1][2] + this.matrix[3][2] * this.matrix[2][2]);
         this.matrix[3][3] = 1.0;
      }
   }
   
   class Vertex {
      public double x;
      public double y;
      public double z;
   
      public Vertex(double x, double y, double z) {
         this.x = x;
         this.y = y;
         this.z = z;
      }
   
      public Vertex add(Vertex in) {
         Vertex out = new Vertex(0.0, 0.0, 0.0);
         out.x = this.x + in.x;
         out.y = this.y + in.y;
         out.z = this.z + in.z;
         return out;
      }
   
      public Vertex sub(Vertex in) {
         Vertex out = new Vertex(0.0, 0.0, 0.0);
         out.x = this.x - in.x;
         out.y = this.y - in.y;
         out.z = this.z - in.z;
         return out;
      }
   
      public Vertex mul(double in) {
         Vertex out = new Vertex(0.0, 0.0, 0.0);
         out.x = this.x * in;
         out.y = this.y * in;
         out.z = this.z * in;
         return out;
      }
   
      public Vertex div(double in) {
         Vertex out = new Vertex(0.0, 0.0, 0.0);
         out.x = this.x / in;
         out.y = this.y / in;
         out.z = this.z / in;
         return out;
      }
   
      public double dot(Vertex in) {
         return this.x * in.x + this.y * in.y + this.z * in.z;
      }
   
      public void normalise() {
         double out = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
         this.x /= out;
         this.y /= out;
         this.z /= out;
      }
   
      public Vertex cross(Vertex var1) {
         Vertex var2 = new Vertex(0.0D, 0.0D, 0.0D);
         var2.x = this.y * var1.z - this.z * var1.y;
         var2.y = this.z * var1.x - this.x * var1.z;
         var2.z = this.x * var1.y - this.y * var1.x;
         return var2;
      }
   }
   
   public static ArrayList<String> shapeObjects() {
      ArrayList<String> objs = new ArrayList<String>();
      objs.add("cube");
      return objs;
   }

   class Triangle {
      public Vertex vertex1;
      public Vertex vertex2;
      public Vertex vertex3;
      public Color color;

      public Triangle(Vertex vert1, Vertex vert2, Vertex vert3) {
         this.vertex1 = vert1;
         this.vertex2 = vert2;
         this.vertex3 = vert3;
      }

      public DataStructures.Triangle get() {
         return this;
      }

      public Color getColor() {
         return this.color;
      }

      public void setColor(Color col) {
         this.color = col;
      }

      public void set(Vertex vert1, Vertex vert2, Vertex vert3) {
         this.vertex1 = vert1;
         this.vertex2 = vert2;
         this.vertex3 = vert3;
      }
   }
}
