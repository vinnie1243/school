package OceansEdge;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.w3c.dom.Document;

public class Entities {
    class Player {
        public int health;
        public int maxHealth;
        public String name;
        public DataStructures.Vertex position;
        public DataStructures.Vector rotation;
        public DataStructures.Velocity motion;
        public String model;
        public boolean collision;
        public String animation;
        public Entities.Weapon firstSlot;
        public Entities.Weapon secondSlot;
        public Entities.Ship ship;
        public int money;
        public int chunk;
        public int width = 1;
        public int height = 2;

        public Player() {
            this.health = 100;
            this.maxHealth = 100;
            this.name = "";
            this.position = Client.ds.new Vertex(0, 0, 0);
            this.rotation = Client.ds.new Vector(0, 0, 0);
            this.motion = Client.ds.new Velocity(0, 0, 0);
            this.model = "";
            this.collision = true;
            this.animation = "";
            this.firstSlot = new Entities.Weapon("", 0, false, 0, "", Client.ds.new Vertex(0, 0, 0), Client.ds.new Vector(0, 0, 0));
            this.secondSlot = new Entities.Weapon("", 0, false, 0, "", Client.ds.new Vertex(0, 0, 0), Client.ds.new Vector(0, 0, 0));
            this.ship = new Entities.Ship();
            this.money = 0;
            this.chunk = 1;
        }
    
        public void create(String name, int health, int maxHealth, DataStructures.Vertex position, DataStructures.Vector rotation, DataStructures.Velocity motion, String model, boolean collision, String animation, Entities.Weapon firstSlot, Entities.Weapon secondSlot, Entities.Ship ship, int money, int chunk) {
            this.name = name;
            this.health = health;
            this.maxHealth = maxHealth;
            this.name = name;
            this.position = position;
            this.rotation = rotation;
            this.motion = motion;
            this.model = model;
            this.collision = collision;
            this.animation = animation;
            this.firstSlot = firstSlot;
            this.secondSlot = secondSlot;
            this.ship = ship;
            this.money = money;
            this.chunk = chunk;
        }
        public void updatePitchAndYaw(int deltaX, int deltaY) {
            double sensitivity = 0.007; // Adjust sensitivity as needed
            this.rotation.yaw -= (deltaX) * sensitivity;
            this.rotation.pitch -= -(deltaY) * sensitivity;
            if(this.rotation.pitch > 1.1) {
                this.rotation.pitch = 1.1;
            } else if(this.rotation.pitch < -1.1) {
                this.rotation.pitch = -1.1;
            }
        }
        public void move() {
            if(Client.keys[0] == true) {
                Client.player.motion.x += 0.005 * -Math.sin(Client.player.rotation.yaw);
                Client.player.motion.z += 0.005 * Math.cos(Client.player.rotation.yaw);
            } else if(Client.keys[1] == true) {
                Client.player.motion.x -= 0.005 * -Math.sin(Client.player.rotation.yaw);
                Client.player.motion.z -= 0.005 * Math.cos(Client.player.rotation.yaw);
            } else if(Client.keys[4] == true) {
                Client.player.position.y += 0.05;
            }
        }
    } 

    class Weapon {
        public String name;
        public int id;
        public boolean held;
        public int damage; // how much damage the weapon does
        public String owner; // who the original person that equipped the weapon
        public int life; // how long before it despawns\
        public DataStructures.Vertex position;
        public DataStructures.Vector rotation;
        
        public Weapon(String name, int id, boolean held, int damage, String owner, DataStructures.Vertex position, DataStructures.Vector rotation) {
            this.name = name;
            this.id = id;
            this.held = held;
            this.damage = damage;
            this.owner = owner;
            this.position = position;
        }
        
    }

    class Npc  {
        public int health;
        public String type;
        public DataStructures.Vertex position;
        public DataStructures.Vector rotation;
        public String state;
        public String animation;

        public Npc(int health, String type, DataStructures.Vertex position, DataStructures.Vector rotation, String state, String animation) {
            this.health = health;
            this.type = type;
            this.position = position;
            this.rotation = rotation;
            this.state = state;
            this.animation = animation;
        }
    }

    class Pickup {
        public String type;
        public int value;
        
        public Pickup(String type, int value) {
            this.type = type;
            this.value = value;
        }
    }

    class Ship {
        public String type;
        public String name;
    }

    class Pirate {
        public DataStructures.Vertex position;
        public DataStructures.Vector rotation;
        public DataStructures.Velocity motion;
        public String weapon;
        public int id;
        public int health;
        public double damage;
        public double damageMul = 0.25;

        public Pirate() {
            startBrain();
            if(this.weapon != null) {
                this.damage = this.damage * this.damageMul;
            }
        }

        public void startBrain() {
            AI aiInstance = new AI();
            AI.PirateAI ai = aiInstance.new PirateAI(new Entities());
        }

        public void pickWeapon() {
            //pick random number max length is total weapons 
            this.weapon = "";//replace with picked weapon
        }
        
        public void die() {

        }
    }

    class Ally {
        
    }

    class Obj {
        public boolean collision;
        public DataStructures.Vertex position;
        public DataStructures.Vector rotation;
        public DataStructures.Velocity motion;
        public String model;
        public DataStructures.Texture texture;
        public ArrayList<DataStructures.Triangle> tris = new ArrayList<DataStructures.Triangle>();
        public Color col;
        public Color finalColor;
        public double width;
        public double height;

        public Obj(boolean collision, DataStructures.Vertex position, DataStructures.Vector rotation, DataStructures.Velocity motion, String model, DataStructures.Texture texture, Color col) {
            this.collision = collision;
            this.position = position;
            this.rotation = rotation;
            this.model = model;
            this.texture = texture;
            this.col = col;
            this.tris.clear();
            parse();
            fixTris();
        }

        public void fixTris() {
            for(int i = 0; i < this.tris.size(); i++) {
                this.tris.get(i).setTex(this.texture);
            }
        }

        public void parse() {
            Document doc = Xml.get(this.model);
            //camera stuff
            Drawing.camera = Client.ds.new Vertex(Client.player.position.x, Client.player.position.y, Client.player.position.z);
            
            //matrix stuff
            DataStructures.Matrix mat = Client.ds.new Matrix(); 
            mat.makeProjection();

            DataStructures.Matrix rotx = Client.ds.new Matrix();
            rotx.makeRotationX(this.rotation.pitch);

            DataStructures.Matrix roty = Client.ds.new Matrix();
            roty.makeRotationY(this.rotation.yaw);
             
            DataStructures.Matrix rotz = Client.ds.new Matrix();
            rotz.makeRotationZ(this.rotation.roll);
            
            DataStructures.Matrix trans = Client.ds.new Matrix();
            trans.makeTranslation(this.position.x, this.position.y, this.position.z);
    
            DataStructures.Matrix world = Client.ds.new Matrix();
            world.makeIdentity();
            world = rotz.multiplyMatrix(rotx);  
            world = world.multiplyMatrix(roty);
            world = world.multiplyMatrix(trans);

            DataStructures.Vertex up = Client.ds.new Vertex(0, 1, 0);
            DataStructures.Vertex target = Client.ds.new Vertex(0, 0, 1);
    
            DataStructures.Matrix camRotX = Client.ds.new Matrix();
            DataStructures.Matrix camRotY = Client.ds.new Matrix();
            DataStructures.Matrix camRotZ = Client.ds.new Matrix();
    
            camRotX.makeRotationX(Client.player.rotation.pitch);
            camRotY.makeRotationY(Client.player.rotation.yaw);
            camRotZ.makeRotationZ(Client.player.rotation.roll);
    
            Drawing.lookDir = camRotX.mulitplyVector(target);
            Drawing.lookDir = camRotY.mulitplyVector(Drawing.lookDir);
            Drawing.lookDir = camRotZ.mulitplyVector(Drawing.lookDir);
    
            target = Drawing.camera.add(Drawing.lookDir);   
    
            DataStructures.Matrix cameraMatrix = Client.ds.new Matrix();
            cameraMatrix.pointAt(Drawing.camera, target, up);
            cameraMatrix = cameraMatrix.quickInverse();    

            //triangles
            ArrayList<DataStructures.Triangle> sortTris = new ArrayList<DataStructures.Triangle>();
            ArrayList<DataStructures.Triangle> tris = new ArrayList<DataStructures.Triangle>();
            tris.addAll(Xml.convert(doc));
            for(int i = 0; i < tris.size(); i++) {
                tris.get(i).vertex1 = world.mulitplyVector(tris.get(i).vertex1);
                tris.get(i).vertex2 = world.mulitplyVector(tris.get(i).vertex2);
                tris.get(i).vertex3 = world.mulitplyVector(tris.get(i).vertex3);
               
                //calculate normals
                DataStructures.Vertex normal = Client.ds.new Vertex(0,0,0); 
                DataStructures.Vertex line1 = Client.ds.new Vertex(0,0,0); 
                DataStructures.Vertex line2 = Client.ds.new Vertex(0,0,0);
    
                line1 = tris.get(i).vertex2.sub(tris.get(i).vertex1);
                line2 = tris.get(i).vertex3.sub(tris.get(i).vertex1);
    
                normal = line1.cross(line2);
    
                normal.normalise();
    
                DataStructures.Vertex camRay = tris.get(i).vertex1.sub(Drawing.camera);
    
                if(camRay.dot(normal) < 0.0) {
                    //lighting 
                    DataStructures.Vertex light = Client.ds.new Vertex(
                        Math.sin(Client.player.rotation.yaw) * Math.cos(Client.player.rotation.pitch), // X component
                        Math.sin(Client.player.rotation.pitch),                          // Y component
                        -Math.cos(Client.player.rotation.yaw) * Math.cos(Client.player.rotation.pitch) // Z component
                    );
                    //normalize light

                    double le = Math.sqrt(light.x * light.x + light.y * light.y + light.z * light.z);
                    light.x /= le; light.y /= le; light.z /= le;
    
                    // How similar is normal to light direction
                    double dp = normal.x * light.x + normal.y * light.y + normal.z * light.z;
                    // Calculate brightness based on dp
                    double brightness = Math.max(0, Math.min(1, dp)); // Clamp dp to range [0, 1]
                    brightness = 0.2 + brightness * 0.8;
                    brightness = Math.max(0, Math.min(1, brightness)); // Clamp to range [0, 1]
                    // Scale color intensity
                    int cR = (int) (this.col.getRed() * brightness);
                    int cG = (int) (this.col.getGreen() * brightness);
                    int cB = (int) (this.col.getBlue() * brightness);
                    Color shadedColor = new Color(cR, cG, cB);
                    tris.get(i).color = shadedColor;
                    //tris.get(i).color = this.col; //disables brightness
    
                    //convert from world to camera space
                    tris.get(i).vertex1 = cameraMatrix.mulitplyVector(tris.get(i).vertex1);
                    tris.get(i).vertex2 = cameraMatrix.mulitplyVector(tris.get(i).vertex2);
                    tris.get(i).vertex3 = cameraMatrix.mulitplyVector(tris.get(i).vertex3);

                    
                    ArrayList<DataStructures.Triangle> clipped = new ArrayList<DataStructures.Triangle>();
                    Drawing.Utilities util = new Drawing().new Utilities();
                    DataStructures.ClipRet ret = util.clip(Client.ds.new Vertex(0,0, 0.1), Client.ds.new Vertex(0,0,1), tris.get(i));
                    if(ret.tri1 != null) {
                        clipped.add(ret.tri1);
                    }
                    if(ret.tri2 != null) {
                        clipped.add(ret.tri2);
                    }
                    int clip = ret.clipnum;
                    
                    //System.out.println(clipped.size());
                    //clipped.add(tris.get(i));
                    for(int n = 0; n < clip; n++) {
                        //clipped.get(n).color = tris.get(i).color;
                        //convert from 3d to 2d
                        clipped.get(n).vertex1 = mat.mulitplyVector(clipped.get(n).vertex1);
                        clipped.get(n).vertex2 = mat.mulitplyVector(clipped.get(n).vertex2);
                        clipped.get(n).vertex3 = mat.mulitplyVector(clipped.get(n).vertex3);

                        clipped.get(n).texvert1.u /= 1;
                        clipped.get(n).texvert1.v /= 1;
                        clipped.get(n).texvert2.u /= 1;
                        clipped.get(n).texvert2.v /= 1;
                        clipped.get(n).texvert3.u /= 1;
                        clipped.get(n).texvert3.v /= 1;

                        //scale into view
                        clipped.get(n).vertex1.x += 1.0;
                        clipped.get(n).vertex1.y += 1.0;
                        clipped.get(n).vertex2.x += 1.0;
                        clipped.get(n).vertex2.y += 1.0;
                        clipped.get(n).vertex3.x += 1.0;
                        clipped.get(n).vertex3.y += 1.0;
                        clipped.get(n).vertex1.x *= (0.5 * Client.screenWidth);
                        clipped.get(n).vertex1.y *= (0.5 * Client.screenHeight);
                        clipped.get(n).vertex2.x *= (0.5 * Client.screenWidth);
                        clipped.get(n).vertex2.y *= (0.5 * Client.screenHeight);
                        clipped.get(n).vertex3.x *= (0.5 * Client.screenWidth);
                        clipped.get(n).vertex3.y *= (0.5 * Client.screenHeight);
                        
                        // Invert y-coordinates to correct the upside-down issue
                        clipped.get(n).vertex1.y = Client.screenHeight - clipped.get(n).vertex1.y;
                        clipped.get(n).vertex2.y = Client.screenHeight - clipped.get(n).vertex2.y;
                        clipped.get(n).vertex3.y = Client.screenHeight - clipped.get(n).vertex3.y;
                        if(Double.isNaN(clipped.get(n).vertex1.z) || Double.isInfinite(clipped.get(n).vertex1.z)) {
                            clipped.get(n).vertex1.z = 0.1;
                        }
                        if(Double.isNaN(clipped.get(n).vertex2.z) || Double.isInfinite(clipped.get(n).vertex1.z)) {
                            clipped.get(n).vertex2.z = 0.1;
                        }
                        if(Double.isNaN(clipped.get(n).vertex3.z) || Double.isInfinite(clipped.get(n).vertex1.z)) {
                            clipped.get(n).vertex3.z = 0.1;
                        }
                        clipped.get(n).popOldZ();
                        sortTris.add(clipped.get(n));
                    }
                }
            }
            //sort triangles
            Collections.sort(sortTris, new Comparator<DataStructures.Triangle>() {
                @Override
                public int compare(DataStructures.Triangle t1, DataStructures.Triangle t2) {

                    double z1 = (t1.vertex1.z + t1.vertex2.z + t1.vertex3.z) / 3.0;
                    double z2 = (t2.vertex1.z + t2.vertex2.z + t2.vertex3.z) / 3.0;
                    if(z1 > z2) {
                        return -1;
                    } else if(z1 < z2) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            }); 

            for(int p = 0; p < sortTris.size(); p++) {
			    // Clip triangles against all four screen edges, this could yield
			    // a bunch of triangles, so create a queue that we traverse to 
			    //  ensure we only test new triangles generated against planes
			    ArrayList<DataStructures.Triangle> listTriangles = new ArrayList<DataStructures.Triangle>();

			    // Add initial triangle
			    listTriangles.addAll(sortTris);
			    int nNewTriangles = 1;

			    for (int n = 0; n < 4; n++) {
			    	while (nNewTriangles > 0) {
                        
			    		// Take triangle from front of queue
			    		DataStructures.Triangle test = listTriangles.get(0);
			    		listTriangles.removeFirst();
                        ArrayList<DataStructures.Triangle> clipped = new ArrayList<DataStructures.Triangle>();
                        Drawing.Utilities util = new Drawing().new Utilities();
                        DataStructures.ClipRet ret = util.clip(Client.ds.new Vertex(0,0, 0), Client.ds.new Vertex(0,1,0), test);
                        if(ret.tri1 != null) {
                            clipped.add(ret.tri1);
                        }
                        if(ret.tri2 != null) {
                            clipped.add(ret.tri2);
                        }
                        DataStructures.ClipRet ret2 = util.clip(Client.ds.new Vertex(0,(double) Client.screenHeight - 1, 0), Client.ds.new Vertex(0, -1, 0), test);
                        if(ret2.tri1 != null) {
                            clipped.add(ret2.tri1);
                        }
                        if(ret2.tri2 != null) {
                            clipped.add(ret2.tri2);
                        }
                        DataStructures.ClipRet ret3 = util.clip(Client.ds.new Vertex(0, 0, 0), Client.ds.new Vertex(1, 0, 0), test);
                        if(ret3.tri1 != null) {
                            clipped.add(ret3.tri1);
                        }
                        if(ret3.tri2 != null) {
                            clipped.add(ret3.tri2);
                        }
                        DataStructures.ClipRet ret4 = util.clip(Client.ds.new Vertex((double)Client.screenWidth - 1, 0, 0), Client.ds.new Vertex(-1, 0, 0), test);
                        if(ret4.tri1 != null) {
                            clipped.add(ret4.tri1);
                        }
                        if(ret4.tri2 != null) {
                            clipped.add(ret4.tri2);
                        }
			    		// Clip it against a plane. We only need to test each 
			    		// subsequent plane, against subsequent new triangles
			    		// as all triangles after a plane clip are guaranteed
			    		// to lie on the inside of the plane. I like how this
			    		// comment is almost completely and utterly justified
			    		// Clipping may yield a variable number of triangles, so
			    		// add these new ones to the back of the queue for subsequent
			    		// clipping against next planes
			    		listTriangles.addAll(clipped);
                        nNewTriangles--;
			    	}
			    }


			    // Draw the transformed, viewed, clipped, projected, sorted, clipped triangles
                this.tris.addAll(listTriangles);
		    }
            //this.tris.addAll(sortTris);
        }

    }
}