package OceansEdge;

import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class Img {
    public ArrayList<DataStructures.Pixel> pixels = new ArrayList<DataStructures.Pixel>();
    public String name;
    public BufferedImage img;

    public void store(ArrayList<DataStructures.Pixel> pix, String name) {
        this.pixels = pix;
        this.name = name;
    }

    public DataStructures.Pixel ret(int x, int y) {
        for(int i = 0; i < pixels.size(); i++) {
            if(pixels.get(i).x == x && pixels.get(i).y == y) {
                return pixels.get(i);
            }
        }   
        return null;
    }

    public void load() {
        Drawing.textures.put(name, this);
    }
}       
