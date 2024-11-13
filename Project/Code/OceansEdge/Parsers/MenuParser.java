package OceansEdge.Parsers;

import java.util.Scanner;
import org.w3c.dom.Element;
import java.util.ArrayList;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class MenuParser {
    public OceansEdge.DataStructures.HTMLNode root;
    public HashMap<String, ArrayList<String>> documentInfo = new HashMap<String, ArrayList<String>>();

    private HashMap<String, HashMap<String, String>> externalCssClassRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> externalCssIDRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> externalCssTagRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> externalCssPseudoRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> externalCssUniversalRules = new HashMap<String, HashMap<String, String>>();

    public ArrayList<HashMap<String, HashMap<String, String>>> externalCssRules = new ArrayList<HashMap<String, HashMap<String, String>>>();
    
    private HashMap<String, HashMap<String, String>> internalCssClassRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> internalCssIDRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> internalCssTagRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> internalCssPseudoRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> internalCssUniversalRules = new HashMap<String, HashMap<String, String>>();

    public ArrayList<HashMap<String, HashMap<String, String>>> internalCssRules = new ArrayList<HashMap<String, HashMap<String, String>>>();

    private HashMap<String, HashMap<String, String>> finalCssClassRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> finalCssIDRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> finalCssTagRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> finalCssPseudoRules = new HashMap<String, HashMap<String, String>>();
    private HashMap<String, HashMap<String, String>> finalCssUniversalRules = new HashMap<String, HashMap<String, String>>();

    public ArrayList<HashMap<String, HashMap<String, String>>> finalCssRules = new ArrayList<HashMap<String, HashMap<String, String>>>();

    public MenuParser() {
        externalCssRules.add(externalCssClassRules);
        externalCssRules.add(externalCssIDRules);
        externalCssRules.add(externalCssTagRules);
        externalCssRules.add(externalCssPseudoRules);
        externalCssRules.add(externalCssUniversalRules);

        internalCssRules.add(internalCssClassRules);
        internalCssRules.add(internalCssIDRules);
        internalCssRules.add(internalCssTagRules);
        internalCssRules.add(internalCssPseudoRules);
        internalCssRules.add(internalCssUniversalRules);

        finalCssRules.add(finalCssClassRules);
        finalCssRules.add(finalCssIDRules);
        finalCssRules.add(finalCssTagRules);
        finalCssRules.add(finalCssPseudoRules);
        finalCssRules.add(finalCssUniversalRules);
    }

    public OceansEdge.DataStructures.HTMLNode parseMenu(String menu) {
        //check if page is already parsed in storage
        if(!checkStorage(menu)) {
            //read html
            ArrayList<String> html = new ArrayList<String>();
            try {
                html = readHTML(menu);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //parse html
            HTMLParser htmlParser = new HTMLParser(); 
            Element root = htmlParser.parseHTML(html);
            //parser.reset();
            //convert html to custom structure
            OceansEdge.DataStructures.HTMLNode document = htmlParser.convertHTML(root);
            //read external css
            ArrayList<ArrayList<String>> cssRules1 = new ArrayList<ArrayList<String>>();
            ArrayList<String> links = documentInfo.get("links");
            if(links != null) {
                for(int i = 0; i < links.size(); i++) {
                    String link = links.get(i);
                    ArrayList<String> css = readCSS(link, menu);
                    cssRules1.add(css);

                }
            }
            //parse external css
            CSSParser cssParser = new CSSParser();
            for(int i = 0; i < cssRules1.size(); i++) {
                cssRules1.set(i, cssParser.parseCSS(cssRules1.get(i), "external"));
            }

            //validate external css
            for(int i = 0; i < externalCssRules.size(); i++) {
                externalCssRules.set(i, cssParser.validate(externalCssRules.get(i)));
            }

            //read internal css
            ArrayList<String> cssRules2 = new ArrayList<String>();
            ArrayList<String> internalCss = documentInfo.get("internalCss");
            if(internalCss != null && !internalCss.isEmpty()) {
                cssRules2.addAll(internalCss);
            }
            //parse internal css
            cssRules2 = cssParser.parseCSS(cssRules2, "internal");
            //validate internal css
            for(int i = 0; i < internalCssRules.size(); i++) {
                internalCssRules.set(i, cssParser.validate(internalCssRules.get(i)));
            }

            //combine css rules by priority
            cssParser.combine();
            System.out.println(finalCssRules.toString());
            //read javascript

            //parse javascript

            //append javascript to html nodes

            //store parsed page
            //OceansEdge.Client.dataStore.store(menu, "DataStructures.HTMLNode", document, true);
            //return root node for rendering
            return document;
        } else {
            //return root node from storage
            return null;
        }
    }

    public ArrayList<String> readHTML(String menu) throws Exception {
        if(menu == "main") {
            ArrayList<String> lines = new ArrayList<String>();
            String filePath = "Data/Menus/Main/Main.html";
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String line = sc.nextLine();
                lines.add(line);
            }
            return lines;
        }
        return null;
    }

    public ArrayList<String> readCSS(String link, String menu) {
        ArrayList<String> lines = new ArrayList<String>();
        link = "Data/Menus/" + Character.toString(menu.charAt(0)).toUpperCase() + menu.substring(1) + "/" + link.substring(1, link.length() - 1);
        try {
            File file = new File(link);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lines.add(line);
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    private boolean checkStorage(String menu) {
        return false;
        //if(OceansEdge.Client.dataStore.get(menu) != null) {
        //    return true;
        //} else {
        //    return false;
        //}
    }
}
