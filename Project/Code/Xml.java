import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Xml {
   public static Document get(String url) {
      try {
         File file = new File(url);
         DocumentBuilderFactory docfac = DocumentBuilderFactory.newInstance();
         DocumentBuilder docbuild = docfac.newDocumentBuilder();
         Document doc = docbuild.parse(file);
         doc.getDocumentElement().normalize();
         return doc;
      } catch (Exception error) {
         error.printStackTrace();
         return null;
      }
   }

   public static Element childEle(Element parent, String target) {
      NodeList nlist = parent.getChildNodes();

      for(int i = 0; i < nlist.getLength(); i++) {
         Node node = nlist.item(i);
         if (node.getNodeType() == 1) {
            Element ele = (Element) node;
            if (ele.getTagName().equals(target)) {
               return ele;
            }
         }
      }

      return null;
   }

   public static ArrayList<DataStructures.Triangle> convert(Document doc) {
      ArrayList<DataStructures.Triangle> arr = new ArrayList<DataStructures.Triangle>();
      Element var2 = doc.getDocumentElement();
      NodeList var3 = var2.getElementsByTagName("triangle");

      for(int var4 = 0; var4 < var3.getLength(); ++var4) {
         Element var5 = (Element)var3.item(var4);
         Element var6 = childEle(var5, "vertex1");
         Element var7 = childEle(var5, "vertex2");
         Element var8 = childEle(var5, "vertex3");
         DataStructures.Vertex vert1 = new DataStructures().new Vertex(Double.parseDouble(childEle(var6, "x").getTextContent()), Double.parseDouble(childEle(var6, "y").getTextContent()), Double.parseDouble(childEle(var6, "z").getTextContent()));
         DataStructures.Vertex vert2 = new DataStructures().new Vertex(Double.parseDouble(childEle(var7, "x").getTextContent()), Double.parseDouble(childEle(var7, "y").getTextContent()), Double.parseDouble(childEle(var7, "z").getTextContent()));
         DataStructures.Vertex vert3 = new DataStructures().new Vertex(Double.parseDouble(childEle(var8, "x").getTextContent()), Double.parseDouble(childEle(var8, "y").getTextContent()), Double.parseDouble(childEle(var8, "z").getTextContent()));
         DataStructures.Triangle tri = new DataStructures().new Triangle(vert1, vert2, vert3);
         arr.add(tri.get());
      }

      return arr;
   }
}
