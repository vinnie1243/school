package OceansEdge;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DataStructures {
    class Triangle {
        public DataStructures.Vertex vertex1;
        public DataStructures.Vertex vertex2;
        public DataStructures.Vertex vertex3;
        public DataStructures.Texture tex;
        public DataStructures.TexVertex texvert1;
        public DataStructures.TexVertex texvert2;
        public DataStructures.TexVertex texvert3;
        public double[] oldz;
        public Color color;
        public Triangle(DataStructures.Vertex v1, DataStructures.Vertex v2, DataStructures.Vertex v3, DataStructures.Texture tex, DataStructures.TexVertex tv1, DataStructures.TexVertex tv2, DataStructures.TexVertex tv3) {
            this.vertex1 = v1;
            this.vertex2 = v2;
            this.vertex3 = v3;
            this.tex = tex;
            this.texvert1 = tv1;
            this.texvert2 = tv2;
            this.texvert3 = tv3;
        }

        public void popOldZ(){
            this.oldz = new double[] {this.vertex1.z, this.vertex2.z, this.vertex3.z};
        }
        public Triangle get() {
            return this;
        }
        public Color getColor() {
            return this.color;
        }
        public void setColor(Color col) {
            this.color = col;
        }
        public void setVert(DataStructures.Vertex v1, DataStructures.Vertex v2, DataStructures.Vertex v3) {
            this.vertex1 = v1;
            this.vertex2 = v2;
            this.vertex3 = v3;
        }
        public void setTex(DataStructures.Texture tex) {
            this.tex = tex;
        }
        public void clear() {
            this.vertex1 = Client.ds.new Vertex(0, 0, 0);
            this.vertex2 = Client.ds.new Vertex(0, 0, 0);
            this.vertex3 = Client.ds.new Vertex(0, 0, 0);
        }
        public double area() {
            double a = vertex1.distanceTo(vertex2);
            double b = vertex2.distanceTo(vertex3);
            double c = vertex3.distanceTo(vertex1);
            double s = (a + b + c) / 2.0;
            return Math.sqrt(s * (s - a) * (s - b) * (s - c));
        }
    }

    class TexVertex {
        public double u;
        public double v;
        public TexVertex(double u, double v) {
            this.u = u;
            this.v = v;
        }
    }

    class Mouse {
        public int x;
        public int y;
        Mouse(int x, int y) {
            this.y = y;
            this.x = x;
        }
    }
    
    class Chunk {
        public ArrayList<Entities.Obj> objects;
        public int id;
        public int x;
        public int y;

        public Chunk(ArrayList<Entities.Obj> objects, int id, int x, int y) {
            this.objects = objects;
            this.id = id;
            this.x = x;
            this.y = y;
            sort();
        }

        public double distanceFromPlayer(Entities.Obj o1) {
            double dx = o1.position.x - Client.player.position.x;
            double dy = o1.position.y - Client.player.position.y;
            double dz = o1.position.z - Client.player.position.z;
            return Math.sqrt(dx * dx + dy * dy + dz * dz);
        }

        public void sort() {
            Collections.sort(this.objects, new Comparator<Entities.Obj>() {
                @Override   
                public int compare(Entities.Obj o1, Entities.Obj o2) {
                    double distance1 = distanceFromPlayer(o1);
                    double distance2 = distanceFromPlayer(o2);
                    if(distance1 > distance2) {
                        return -1;
                    } else if(distance1 < distance2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        public int[] getxy() {
            switch (this.id) {
                case 1:
                    return new int[] {0, 0};
                case 2: 
                    return new int[] {0, 1};
                case 3:
                    return new int[] {0, 2};
                case 4:
                    return new int[] {0, 3};
                case 5:
                    return new int[] {0, 4};
                case 6:
                    return new int[] {0, 5};
                case 7:
                    return new int[] {0, 6};
                case 8:
                    return new int[] {0, 7};
                case 9:
                    return new int[] {1, 0};
                case 10:
                    return new int[] {1, 1};
                case 11:
                    return new int[] {1, 2};
                case 12:
                    return new int[] {1, 3};
                case 13:
                    return new int[] {1, 4};
                case 14:
                    return new int[] {1, 5};
                case 15:
                    return new int[] {1, 6};
                case 16:
                    return new int[] {1, 7};
                case 17:
                    return new int[] {2, 0};
                case 18:
                    return new int[] {2, 1};
                case 19:
                    return new int[] {2, 2};
                case 20:
                    return new int[] {2, 3};
                case 21:
                    return new int[] {2, 4};
                case 22:
                    return new int[] {2, 5};
                case 23:
                    return new int[] {2, 6};
                case 24:
                    return new int[] {2, 7};
                case 25:
                    return new int[] {3, 0};
                case 26:
                    return new int[] {3, 1};
                case 27:
                    return new int[] {3, 2};
                case 28:
                    return new int[] {3, 3};
                case 29: 
                    return new int[] {3, 4};
                case 30:
                    return new int[] {3, 5};
                case 31:
                    return new int[] {3, 6};
                case 32:
                    return new int[] {3, 7};
                case 33:
                    return new int[] {4, 0};
                case 34:
                    return new int[] {4, 1};
                case 35:
                    return new int[] {4, 2};
                case 36:
                    return new int[] {4, 3};
                case 37:
                    return new int[] {4, 4};
                case 38:
                    return new int[] {4, 5};
                case 39:
                    return new int[] {4, 6};
                case 40:
                    return new int[] {4, 7};
                case 41:
                    return new int[] {5, 0};
                case 42:
                    return new int[] {5, 1};
                case 43:
                    return new int[] {5, 2};
                case 44:
                    return new int[] {5, 3};
                case 45:
                    return new int[] {5, 4};
                case 46:
                    return new int[] {5, 5};
                case 47:
                    return new int[] {5, 6};
                case 48:
                    return new int[] {5, 7};
                case 49:
                    return new int[] {6, 0};
                case 50:
                    return new int[] {6, 1};
                case 51:
                    return new int[] {6, 2};
                case 52:
                    return new int[] {6, 3};
                case 53:
                    return new int[] {6, 4};
                case 54:
                    return new int[] {6, 5};
                case 55:
                    return new int[] {6, 6};
                case 56:
                    return new int[] {6, 7};
                case 57:
                    return new int[] {7, 0};
                case 58:
                    return new int[] {7, 1};
                case 59:
                    return new int[] {7, 2};
                case 60:
                    return new int[] {7, 3};
                case 61:
                    return new int[] {7, 4};
                case 62:
                    return new int[] {7, 5};
                case 63:
                    return new int[] {7, 6};
                case 64:
                    return new int[] {7, 7};
            }
            return null;
        }
    }

    class Texture {
        public BufferedImage image; //will make this preload and just use this to render
        public String url;
        public String name;
        public int width;
        public int height;
        public Img img;
        Texture(String url, int width, int height) {
            this.url = url;
            this.width = width;
            this.height = height;
        }

        public void load() {
            BufferedImage textureImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    textureImage.setRGB(i, j, this.getRGB(i, j).getRGB());
                }
            }
            System.out.println(textureImage.getData());
            this.image = textureImage;
        }

        public void loadImage(Img pix) {
            this.img = pix;
            
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        public Color getRGB(int texX, int texY) {
            return this.img.ret(texX, texY).color;
        }
    }

    class Pixel {
        public int x;
        public int y;
        public Color color;
        public Pixel(int x, int y, int r, int g, int b) {
            this.x = x;
            this.y = y;
            this.color = new Color(r, g, b);
        }
    }

    class Model {
        public int x;
        public int y;
        public int z;
        public String name;
        public String texture;

        public Model(int x, int y, int z, String name) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.name = name;
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

        public double[] coords() {

            return new double[] {this.x, this.y, this.z};
        }
    
        public DataStructures.Vertex add(DataStructures.Vertex v) {
            DataStructures.Vertex res = new Vertex(0, 0, 0);
            res.x = this.x + v.x;
            res.y = this.y + v.y;
            res.z = this.z + v.z;
            return res;
        }
    
        public DataStructures.Vertex sub(DataStructures.Vertex v) {
            DataStructures.Vertex res = new Vertex(0, 0, 0);
            res.x = this.x - v.x;
            res.y = this.y - v.y;
            res.z = this.z - v.z;
            return res;
        }
    
        public DataStructures.Vertex mul(double input) {
            DataStructures.Vertex res = new DataStructures.Vertex(0, 0, 0);
            res.x = this.x * input;
            res.y = this.y * input;
            res.z = this.z * input;

            if(Double.isInfinite(res.x) || Double.isNaN(res.x)) {
                res.x = 0;
            }
            if(Double.isInfinite(res.y) || Double.isNaN(res.y)) {
                res.y = 0;
            }
            if(Double.isInfinite(res.z) || Double.isNaN(res.z)) {
                res.z = 0;
            }

            DecimalFormat df = new DecimalFormat("#.#####");
            res.x = Double.parseDouble(df.format(res.x));
            res.y = Double.parseDouble(df.format(res.y));
            res.z = Double.parseDouble(df.format(res.z));

            return res;
        }
    
        public DataStructures.Vertex div(double input) {
            DataStructures.Vertex res = new DataStructures.Vertex(0, 0, 0);
            res.x = this.x / input;
            res.y = this.y / input;
            res.z = this.z / input;
            return res;
        }
    
        public double dot(DataStructures.Vertex v) {
            return this.x * v.x + this.y * v.y + this.z * v.z;
        }
        
        public void normalise() {
            double len = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
            this.x = this.x / len;
            this.y = this.y / len;
            this.z = this.z / len;
        }
    
        public DataStructures.Vertex cross(DataStructures.Vertex v) {
            DataStructures.Vertex res = new DataStructures.Vertex(0, 0, 0);
            res.x = this.y * v.z - this.z * v.y;
            res.y = this.z * v.x - this.x * v.z;
            res.z = this.x * v.y - this.y * v.x;
            return res;
        }
    
        public DataStructures.Vertex add2(DataStructures.Vertex v1, DataStructures.Vertex v2) {
            DataStructures.Vertex res = new DataStructures.Vertex(0, 0, 0);
            res.x = v1.x + v2.x; 
            res.y = v1.y + v2.y;
            res.z = v1.z + v2.z;
            return res;
        }
    
        public DataStructures.Vertex mul2(DataStructures.Vertex v1, double input) {
            DataStructures.Vertex res = new DataStructures.Vertex(0, 0, 0);
            res.x = v1.x * input;
            res.y = v1.y * input;
            res.z = v1.z * input;
            return res;
        }

        public double distanceTo(DataStructures.Vertex other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2) + Math.pow(this.z - other.z, 2));
        }
    }
    
    class Matrix {
        public double[][] matrix;
    
        public Matrix() {
            this.matrix = new double[4][4];
        }
    
        public void set(int x, int y, double val, String mode, double[][] newMatrix) {
            if (mode.equals("set")) {
                matrix[x][y] = val;
            } else if (mode.equals("replace")) {
                matrix = new double[newMatrix.length][newMatrix[0].length];
                for (int i = 0; i < newMatrix.length; i++) {
                    for (int j = 0; j < newMatrix[i].length; j++) {
                        matrix[i][j] = newMatrix[i][j];
                    }
                }
            }
        }
        //math stuff
        public DataStructures.Vertex mulitplyVector(DataStructures.Vertex v) {
            DataStructures.Vertex result = new DataStructures.Vertex(0, 0, 0);
            result.x = v.x * this.matrix[0][0] + v.y * this.matrix[1][0] + v.z * this.matrix[2][0] + this.matrix[3][0];
            result.y = v.x * this.matrix[0][1] + v.y * this.matrix[1][1] + v.z * this.matrix[2][1] + this.matrix[3][1];
            result.z = v.x * this.matrix[0][2] + v.y * this.matrix[1][2] + v.z * this.matrix[2][2] + this.matrix[3][2];
            double w = v.x * this.matrix[0][3] + v.y * this.matrix[1][3] + v.z * this.matrix[2][3] + this.matrix[3][3];
            if (w != 0.0 && w != 0) {
                result.x /= w;
                result.y /= w;
                result.z /= w;
            }
            return result;
        }
    
        public void makeIdentity() {
            this.matrix[0][0] = 1.0;
            this.matrix[1][1] = 1.0;
            this.matrix[2][2] = 1.0;
            this.matrix[3][3] = 1.0;
        }
    
        public void makeRotationX(double angle) {
            this.matrix[0][0] = 1.0;
            this.matrix[1][1] = Math.cos(angle);
            this.matrix[1][2] = Math.sin(angle);
            this.matrix[2][1] = -Math.sin(angle);
            this.matrix[2][2] = Math.cos(angle);
            this.matrix[3][3] = 1.0;
        }
    
        public void makeRotationY(double angle) {
            this.matrix[0][0] = Math.cos(angle);
            this.matrix[0][2] = Math.sin(angle);
            this.matrix[2][0] = -Math.sin(angle);
            this.matrix[1][1] = 1.0;
            this.matrix[2][2] = Math.cos(angle);
            this.matrix[3][3] = 1.0;
            /*
             * [cos(angle) 0 -sin(angle) 0]
             * [0 1 0 0]
             * [sin(angle) 0 cos(angle) 0]
             * [0 0 0 1]
             */
        }
    
        public void makeRotationZ(double angle) {
            this.matrix[0][0] = Math.cos(angle);
            this.matrix[0][1] = Math.sin(angle);
            this.matrix[1][0] = -Math.sin(angle);
            this.matrix[1][1] = Math.cos(angle);
            this.matrix[2][2] = 1.0;
            this.matrix[3][3] = 1.0;
        }
    
        public void makeProjection() {
            this.matrix[0][0] = Drawing.MatrixData.aspectratio * Drawing.MatrixData.fovrad;
            this.matrix[1][1] = Drawing.MatrixData.fovrad;
            this.matrix[2][2] = Drawing.MatrixData.zfar / (Drawing.MatrixData.zfar - Drawing.MatrixData.znear);
            this.matrix[3][2] = (-Drawing.MatrixData.zfar * Drawing.MatrixData.znear) / (Drawing.MatrixData.zfar - Drawing.MatrixData.znear);
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
    
        public DataStructures.Matrix multiplyMatrix(DataStructures.Matrix mat) {
            DataStructures.Matrix matrix = new DataStructures.Matrix();
            for (int c = 0; c < 4; c++){
                for (int r = 0; r < 4; r++)
                matrix.matrix[r][c] = this.matrix[r][0] * mat.matrix[0][c] + this.matrix[r][1] * mat.matrix[1][c] + this.matrix[r][2] * mat.matrix[2][c] + this.matrix[r][3] * mat.matrix[3][c];
            }
            return matrix;
        }
    
        public void pointAt(DataStructures.Vertex pos, DataStructures.Vertex target, DataStructures.Vertex up) {
            DataStructures.Vertex forward = target.sub(pos);
            forward.normalise();
    
            DataStructures.Vertex a = forward.mul(up.dot(forward));
            DataStructures.Vertex newUp = up.sub(a);
            newUp.normalise();
    
            DataStructures.Vertex right = newUp.cross(forward);
    
            this.matrix[0][0] = right.x; this.matrix[0][1] = right.y; this.matrix[0][2] = right.z; this.matrix[0][3] = 0.0;
            this.matrix[1][0] = newUp.x; this.matrix[1][1] = newUp.y; this.matrix[1][2] = newUp.z; this.matrix[1][3] = 0.0;
            this.matrix[2][0] = forward.x; this.matrix[2][1] = forward.y; this.matrix[2][2] = forward.z; this.matrix[2][3] = 0.0;
            this.matrix[3][0] = pos.x; this.matrix[3][1] = pos.y; this.matrix[3][2] = pos.z; this.matrix[3][3] = 1.0;
        }
    
        public DataStructures.Matrix quickInverse() {
            Matrix result = new Matrix();
            result.matrix[0][0] = this.matrix[0][0]; result.matrix[0][1] = this.matrix[1][0]; result.matrix[0][2] = this.matrix[2][0]; result.matrix[0][3] = 0.0f;
            result.matrix[1][0] = this.matrix[0][1]; result.matrix[1][1] = this.matrix[1][1]; result.matrix[1][2] = this.matrix[2][1]; result.matrix[1][3] = 0.0f;
            result.matrix[2][0] = this.matrix[0][2]; result.matrix[2][1] = this.matrix[1][2]; result.matrix[2][2] = this.matrix[2][2]; result.matrix[2][3] = 0.0f;
            result.matrix[3][0] = -(this.matrix[3][0] * result.matrix[0][0] + this.matrix[3][1] * result.matrix[1][0] + this.matrix[3][2] * result.matrix[2][0]);
            result.matrix[3][1] = -(this.matrix[3][0] * result.matrix[0][1] + this.matrix[3][1] * result.matrix[1][1] + this.matrix[3][2] * result.matrix[2][1]);
            result.matrix[3][2] = -(this.matrix[3][0] * result.matrix[0][2] + this.matrix[3][1] * result.matrix[1][2] + this.matrix[3][2] * result.matrix[2][2]);
            result.matrix[3][3] = 1.0f;

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    //result.matrix[i][j] = this.matrix[i][j];
                }
                result.matrix[i][3] = -this.matrix[i][3];
            }
            return result;
        }
    }
    
    class Vector {
        double yaw;
        double roll;
        double pitch;
        public Vector(double y, double r, double p) {
            this.yaw = y;
            this.roll = r;
            this.pitch = p;
        }
    }
    
    class Velocity {
        public double x;
        public double y;
        public double z;
        public Velocity(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
    
    class ClipRet {
        public int clipnum = 0;
        public DataStructures.Triangle tri1;
        public DataStructures.Triangle tri2;

        public ClipRet(int clip, DataStructures.Triangle tri1, DataStructures.Triangle tri2) {
            this.clipnum = clip;
            this.tri1 = tri1;
            this.tri2 = tri2;

        }
    }
    
    public class HTMLNode {
        public ArrayList<HTMLNode> children;
        public String tag;
        public String content;
        public HashMap<String, String> attributes;
        public ArrayList<String> style;

        //data about element
        public int[] xy = {50, 50};
        public int[] wh = {50, 50};


        public HTMLNode(String tag, String content, HashMap<String, String> attributes) {
            this.tag = tag;
            this.content = content;
            this.attributes = attributes;
            this.children = new ArrayList<HTMLNode>();
            this.style = new ArrayList<String>();
        }

        public void set(String id, String val, HashMap<String, String> map) {

        }

        public String get(String id) {
            if(id == "tag") {
                return this.tag;
            } else if(id == "content") {
                return this.content;
            }

            return null;
        }

        public HashMap<String, String> getHashMap() {

            return null;
        }
        
        public void addChild(HTMLNode node) {
            this.children.add(node);
        }
    }
}


