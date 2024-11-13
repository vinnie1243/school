package OceansEdge.Parsers;

//builtin classes
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.Stack;

//Custom Classes
//import org.jsoup.parser;

public class HTMLParser {
    public Document doc;
    public Element parent;
    public boolean skip = false;
    private Element root;
    private OceansEdge.DataStructures.HTMLNode rootNode;
    private Stack<OceansEdge.DataStructures.HTMLNode> htmlnodes = new Stack<OceansEdge.DataStructures.HTMLNode>();

    public Element parseHTML(ArrayList<String> html) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.newDocument();
        } catch (Exception e) {
            // TODO: handle exception
        }
        Element root = doc.createElement("html");
        for(int i = 0; i < html.size()- 1; i++) {
            String line = html.get(i);
            line = line.strip();
            //System.out.println(line);
            //look for tag info
            String tagRegex = "<(\\/?)\\b([a-zA-Z][a-zA-Z0-9]*)\\b([^>]*?)(\\/?)>([^<]*)";
            Pattern pattern = Pattern.compile(tagRegex);
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                // Group 1: Captures the optional '/' in the beginning for closing tags
                String closingSlash = matcher.group(1);
                // Group 2: The tag name (e.g., "div", "img")
                String tagName = matcher.group(2);
                // Group 3: The attributes (if any)
                String attributes = matcher.group(3).trim();
                ArrayList<String> attrList = new ArrayList<String>();
                attrList.addAll(Arrays.asList(attributes.split(" ")));
                // Group 4: Captures the optional '/' for self-closing tags
                String selfClosingSlash = matcher.group(4);
                // Group 5: Content between the opening and closing tags
                String content = matcher.group(5).trim();
    
                if (!closingSlash.isEmpty()) {
                    // This is a closing tag
                    parent = (Element) parent.getParentNode();

                } else if (!selfClosingSlash.isEmpty()) {
                    // This is a self-closing tag
                    HashMap<String, String> attrMap = new HashMap<String, String>();
                    for (String attr : attrList) {
                        String[] keyValue = attr.split("=");
                        if (keyValue.length == 2) {
                            attrMap.put(keyValue[0], keyValue[1]);
                        }
                    }
                    Element ele = doc.createElement(tagName);
                    for (String key : attrMap.keySet()) {
                        ele.setAttribute(key, attrMap.get(key));
                    }
                    if(tagName.equals("link")) {
                        if(OceansEdge.Drawing.parser.documentInfo.get("links") == null) {
                            ArrayList<String> data = new ArrayList<String>();
                            data.add(ele.getAttribute("href"));
                            OceansEdge.Drawing.parser.documentInfo.put("links", data);
                        } else {
                            OceansEdge.Drawing.parser.documentInfo.get("links").add(ele.getAttribute("href"));
                        }
                    } else if(ele.getTagName() == "script") {
                        //OceansEdge.Drawing.parser.documentInfo.put("script", ele.getAttribute("src"));
                    } else if(ele.getTagName() == "style") {
                        int iter = 0;
                        ArrayList<String> css = new ArrayList<String>();
                        while(!html.get(i + iter).contains("</style>")) {
                            css.add(html.get(i + iter));
                            iter++;
                        }
                        i += iter;
                        OceansEdge.Drawing.parser.documentInfo.put("internalCss", css);
                    } else {
                        if(parent != null) {
                            parent.appendChild(ele);
                        }
                    }
                    
                } else {
                    // This is an opening tag
                    HashMap<String, String> attrMap = new HashMap<String, String>();
                    for (String attr : attrList) {
                        String[] keyValue = attr.split("=");
                        if (keyValue.length == 2) {
                            attrMap.put(keyValue[0], keyValue[1]);
                        }
                    }  
                    Element ele = doc.createElement(tagName);
                    for (String key : attrMap.keySet()) {
                        ele.setAttribute(key, attrMap.get(key));
                    }
                    if(parent != null) {
                        parent.appendChild(ele);
                    }
                    if(!content.isEmpty()) {
                        ele.setTextContent(content);
                    }
                    parent = ele;
                    
                }
            }
            if(parent != null) {
                root = parent;
            }
        }
        return root;
    }

    public OceansEdge.DataStructures.HTMLNode convertHTML(Element root) {
        return traverse(root, true);
    }

    private OceansEdge.DataStructures.HTMLNode traverse(Node node, boolean base) {
        // Process the current node (for example, print the node name)
        //System.out.println("Node: " + node.getNodeName());

        // Iterate through the children of the current node
        if(base) {
            OceansEdge.DataStructures.HTMLNode parentNode = new OceansEdge.DataStructures().new HTMLNode(null, null, null);
            parentNode.tag = node.getNodeName();
            HashMap<String, String> attrMap = new HashMap<String, String>();
            if(node.getAttributes() != null) {
                for(int j = 0; j < node.getAttributes().getLength(); j++) {
                    attrMap.put(node.getAttributes().item(j).getNodeName(), node.getAttributes().item(j).getNodeValue());
                }
            }
            parentNode.content = node.getTextContent();
            htmlnodes.push(parentNode);
        }
        for (int i = 0; i < node.getChildNodes().getLength(); i++) {
            Node child = node.getChildNodes().item(i);
            OceansEdge.DataStructures.HTMLNode newnode = new OceansEdge.DataStructures().new HTMLNode(null, null, null);
            newnode.tag = child.getNodeName();
            HashMap<String, String> attrMap = new HashMap<String, String>();
            if(child.getAttributes() != null) {
                for(int j = 0; j < child.getAttributes().getLength(); j++) {
                    attrMap.put(child.getAttributes().item(j).getNodeName(), child.getAttributes().item(j).getNodeValue());
                }
            }
            
            newnode.content = child.getTextContent();
            newnode.attributes = attrMap;
            htmlnodes.peek().children.add(newnode);
            htmlnodes.push(newnode);

            // Recursive call to process the child node
            traverse(child, false);
        }
        if(htmlnodes.size() > 1) {
            htmlnodes.pop();
        } else {
            if(base) {
                rootNode = htmlnodes.pop();
                return rootNode;
            }
            return htmlnodes.pop();
        }
        return null;    
    }
}
