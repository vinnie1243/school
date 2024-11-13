package OceansEdge;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;

public class Xml {
    public static ArrayList<DataStructures.Pixel> getPixels(String path) { // Correct class name
        File file = getFile(path);
        ArrayList<DataStructures.Pixel> data = new ArrayList<DataStructures.Pixel>(); // Correct class name
        data.addAll(readFile(file));
        return data;
    }

    private static ArrayList<DataStructures.Pixel> readFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            ArrayList<DataStructures.Pixel> data = new ArrayList<DataStructures.Pixel>();
            ArrayList<int[]> colors = new ArrayList<int[]>();
            while ((line = br.readLine()) != null) {
                if (line.startsWith("c")) {
                    String[] color = line.split(" ");
                    int red = Integer.parseInt(color[1]);
                    int blue = Integer.parseInt(color[2]);
                    int green = Integer.parseInt(color[3]);
                    int[] col = {red, green, blue};
                    colors.add(col);
                } else if(line.startsWith("p")) {
                    String[] pixel = line.split(" ");
                    int x = Integer.parseInt(pixel[1]);
                    int y = Integer.parseInt(pixel[2]);
                    int colorIndex = Integer.parseInt(pixel[3]);
                    DataStructures.Pixel pix = Client.ds.new Pixel(x, y, colors.get(colorIndex)[0], colors.get(colorIndex)[1], colors.get(colorIndex)[2]);
                    data.add(pix);
                }
            }  

            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static File getFile(String filename) {
        File file = new File(filename);
        return file;
    }

    public static Document get(String path) {
        try {
            File file = new File(path);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static Element childEle(Element parent, String name) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element ele = (Element) node;
                if (ele.getTagName().equals(name)) {
                    return ele;
                }
            }
        }
        return null;
    }
    
    public static ArrayList<DataStructures.Triangle> convert(Document doc) {
        ArrayList<DataStructures.Triangle> tris = new ArrayList<DataStructures.Triangle>();
        Element root = doc.getDocumentElement();
        NodeList Triangles = root.getElementsByTagName("triangle");
        for (int i = 0; i < Triangles.getLength(); i++) {
            Element triEle = (Element) Triangles.item(i);
            Element v1Ele = childEle(triEle, "vertex1");
            Element v2Ele = childEle(triEle, "vertex2");
            Element v3Ele = childEle(triEle, "vertex3");
            Element t1Ele = childEle(triEle, "tex1");
            Element t2Ele = childEle(triEle, "tex2");
            Element t3Ele = childEle(triEle, "tex3");
            DataStructures ds = new DataStructures();
            DataStructures.Vertex vertex1 = ds.new Vertex(Double.parseDouble(childEle(v1Ele, "x").getTextContent()), Double.parseDouble(childEle(v1Ele, "y").getTextContent()), Double.parseDouble(childEle(v1Ele, "z").getTextContent()));
            DataStructures.Vertex vertex2 = ds.new Vertex(Double.parseDouble(childEle(v2Ele, "x").getTextContent()), Double.parseDouble(childEle(v2Ele, "y").getTextContent()), Double.parseDouble(childEle(v2Ele, "z").getTextContent()));
            DataStructures.Vertex vertex3 = ds.new Vertex(Double.parseDouble(childEle(v3Ele, "x").getTextContent()), Double.parseDouble(childEle(v3Ele, "y").getTextContent()), Double.parseDouble(childEle(v3Ele, "z").getTextContent()));
            DataStructures.TexVertex tex1 = ds.new TexVertex(Double.parseDouble(childEle(t1Ele, "u").getTextContent()), Double.parseDouble(childEle(t1Ele, "v").getTextContent()));
            DataStructures.TexVertex tex2 = ds.new TexVertex(Double.parseDouble(childEle(t2Ele, "u").getTextContent()), Double.parseDouble(childEle(t2Ele, "v").getTextContent()));
            DataStructures.TexVertex tex3 = ds.new TexVertex(Double.parseDouble(childEle(t3Ele, "u").getTextContent()), Double.parseDouble(childEle(t3Ele, "v").getTextContent()));
            DataStructures.Triangle tri = ds.new Triangle(vertex1, vertex2, vertex3, null, tex1, tex2, tex3);
            tris.add(tri.get());
        }
        
        return tris;
    }
   
    public static void getChunkData(int[] chunks) {
        Document doc = get("Data/chunks.xml");
        Element root = doc.getDocumentElement();        
        Drawing.chunks.clear();
        for (int i = 0; i < chunks.length; i++) {  
            DataStructures.Chunk c;
            ArrayList<Entities.Obj> objects = new ArrayList<Entities.Obj>();
            for(int j = 0; j < root.getElementsByTagName("chunk").getLength(); j++) {
                if(chunks[i] != 0) {
                    /*if(!root.getElementsByTagName("chunk").item(j).getFirstChild().getTextContent().equals("")) {
                        Node node = root.getElementsByTagName("chunk").item(j);
                        Element e = (Element) node;
                        System.out.println(childEle(e, "id").getTextContent());
                    }*/

                    if(childEle((Element)root.getElementsByTagName("chunk").item(j), "id").getTextContent().equals(Integer.toString(chunks[i]))) {
                        Element ele = (Element) root.getElementsByTagName("chunk").item(j);
                        Element object = childEle(ele, "objects");
                        NodeList objList = object.getChildNodes();
                        for(int k = 0; k < objList.getLength(); k++) {
                            Node objNode = objList.item(k);
                            if(objNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element objEle = (Element) objNode;
                                
                                boolean collision = Boolean.parseBoolean(childEle(objEle, "collision").getTextContent());
                                int x = Integer.parseInt(childEle(childEle(objEle, "pos"), "x").getTextContent());
                                int y = Integer.parseInt(childEle(childEle(objEle, "pos"), "y").getTextContent());
                                int z = Integer.parseInt(childEle(childEle(objEle, "pos"), "z").getTextContent());
                                DataStructures.Vertex pos = Client.ds.new Vertex(x, y, z);
                                int pitch = Integer.parseInt(childEle(childEle(objEle, "rot"), "pitch").getTextContent());
                                int yaw = Integer.parseInt(childEle(childEle(objEle, "rot"), "yaw").getTextContent());
                                int roll = Integer.parseInt(childEle(childEle(objEle, "rot"), "roll").getTextContent());
                                DataStructures.Vector rot = Client.ds.new Vector(yaw, pitch, roll);
                                int vx = Integer.parseInt(childEle(childEle(objEle, "vel"), "x").getTextContent());
                                int vy = Integer.parseInt(childEle(childEle(objEle, "vel"), "y").getTextContent());
                                int vz = Integer.parseInt(childEle(childEle(objEle, "vel"), "z").getTextContent());
                                DataStructures.Velocity vel = Client.ds.new Velocity(vx, vy, vz);
                                String model = childEle(objEle, "model").getTextContent();
                                DataStructures.Texture texture = Client.ds.new Texture(childEle(childEle(objEle, "texture"), "name").getTextContent(), Integer.parseInt(childEle(childEle(objEle, "texture"), "width").getTextContent()), Integer.parseInt(childEle(childEle(objEle, "texture"), "height").getTextContent()));
                                texture.name = childEle(childEle(objEle, "texture"), "name").getTextContent();
                                for(int l = 0; l < Drawing.textures.size(); l++) {
                                    if(Drawing.textures.containsKey(texture.name)) {
                                        texture.loadImage(Drawing.textures.get(texture.name));
                                    }
                                }
                                Color color = new Color(Integer.parseInt(childEle(childEle(objEle, "color"), "r").getTextContent()), Integer.parseInt(childEle(childEle(objEle, "color"), "g").getTextContent()), Integer.parseInt(childEle(childEle(objEle, "color"), "b").getTextContent()));
                                Entities.Obj o = new Entities().new Obj(collision, pos, rot, vel, model, texture, color);
                                objects.add(o);
                            }
                        }
                        int[] chunkxy = new int[2];
                        chunkxy = Drawing.figXY(chunks[i]);
                        c = Client.ds.new Chunk(objects, chunks[i], chunkxy[0], chunkxy[1]);
                        Drawing.chunks.add(c);
                    }
                }
            }
        }
    }
}

