package OceansEdge;

import java.awt.Graphics;
import java.util.Map.Entry;   
import java.util.HashMap;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;

public class HTMLRender {
    public HashMap<String, String> documentData = new HashMap<String, String>();
    //css stuff
    public HashMap<String, HashMap<String, String>> classData = new HashMap<String, HashMap<String, String>>();
    public HashMap<String, HashMap<String, String>> idData = new HashMap<String, HashMap<String, String>>();
    public HashMap<String, HashMap<String, String>> tagData = new HashMap<String, HashMap<String, String>>();

    public void render(DataStructures.HTMLNode ele, Graphics g) {
        for(int i = 0; i < ele.children.size(); i++) {
            renderElement(ele.children.get(i), g, ele);
            render(ele.children.get(i), g);
        }
    
    }

    public DataStructures.HTMLNode applyTagCSS(DataStructures.HTMLNode node) {
        if(tagData.containsKey(node.tag)) {
            for(Entry<String, String> entry : tagData.get(node.tag).entrySet()) {
                //attrParse(entry.getKey(), entry.getValue(), node.tag);
            }
        }
        return node;
    }

    public DataStructures.HTMLNode applyInlineCSS(DataStructures.HTMLNode node) {
        for(Entry<String, String> entry : node.attributes.entrySet()) {
            //attrParse(entry.getKey(), entry.getValue(), node.tag);
        }
        return node;
    }

    public DataStructures.HTMLNode applyIDCSS(DataStructures.HTMLNode node) {
        if(node.attributes.containsKey("id")) {
            String id = node.attributes.get("id");
            id = id.substring(1, id.length() - 1);
            if(Drawing.parser.finalCssRules.get(1).containsKey(id)) {
                for(Entry entry : Drawing.parser.finalCssRules.get(1).get(id).entrySet()) {
                    node = applyCSS(node, entry.getKey().toString(), entry.getValue().toString());
                }
            }
        }

        return node;
    }

    public DataStructures.HTMLNode applyClassCSS(DataStructures.HTMLNode node) {
        ArrayList<String> classes = new ArrayList<String>();
        if(node.attributes.containsKey("class")) {
            classes.addAll(Arrays.asList(node.attributes.get("class").split(" ")));
            for(int i = 0; i < classes.size(); i++) {
                classes.set(i, classes.get(i).substring(1, classes.get(i).length() - 1));
                if(Drawing.parser.finalCssRules.get(0).containsKey(classes.get(i))) {
                    for(Entry entry : Drawing.parser.finalCssRules.get(0).get(classes.get(i)).entrySet()) {
                        node = applyCSS(node, entry.getKey().toString(), entry.getValue().toString());
                    }
                }
            }
        }

        return node;
    }

    public DataStructures.HTMLNode applyWildCSS(DataStructures.HTMLNode node) {
        if(tagData.containsKey(node.tag)) {
            for(Entry<String, String> entry : tagData.get(node.tag).entrySet()) {
                attrParse(entry.getKey(), entry.getValue(), node.tag);
            }
        }
        return node;
    }
 
    public DataStructures.HTMLNode applyBaseCSS(DataStructures.HTMLNode node) {
        switch(node.tag) {
            //root element
            case "html": // root element of a document

            break;
            //head stuff
            case "base": //specifies base url for relative urls

            break;
            case "head": //container for meta data

            break;
            case "link": //defines relationship between document and external resource

            break;
            case "meta": //contians metadata that is not represeted by other head tags

            break;
            case "style": //intenal css

            break;
            case "title": //title of the document

            break;
            //body stuff
            case "body": //container for content

            break;
            case "address": //contact information for the author of the document

            break;
            case "article": //independent self contained content

            break;
            case "aside": //content aside from the content it is placed in

            break;
            case "footer": //footer for the document or section

            break;
            case "header": //header for the document or section

            break;
            case "h1": //heading
                
            break;
            case "h2": //heading

            break;
            case "h3": //heading

            break;
            case "h4": //heading

            break;
            case "h5": //heading

            break;
            case "h6": //heading

            break;
            case "hgroup": //group of headings

            break;
            case "main": //main content of the document

            break;
            case "nav": //navigation links

            break;
            case "section": //section of the document

            break;
            case "search":

            break;
            case "blockquote": //long quote

            break;
            case "dd": //description of the term in a description list

            break;
            case "div": //division or section

            break;
            case "dl": //description list

            break;
            case "dt": //term in a description list

            break;
            case "figcaption": //caption for a figure element

            break;
            case "figure": //group of media content

            break;
            case "hr": //horizontal rule

            break;
            case "li": //list item

            break;
            case "menu": //menu list

            break;
            case "ol": //ordered list

            break;
            case "p": //paragraph
                if(!node.attributes.containsKey("display")) {
                    node.attributes.put("display", "block"); 
                }
                if(!node.attributes.containsKey("margin-top")) {
                    node.attributes.put("margin-top", "1em");
                }
                if(!node.attributes.containsKey("margin-bottom")) {
                    node.attributes.put("margin-bottom", "1em");
                }
                if(!node.attributes.containsKey("margin-left")) {
                    node.attributes.put("margin-left", "0em");
                }
                if(!node.attributes.containsKey("margin-right")) {
                    node.attributes.put("margin-right", "0em");
                }
                if(node.attributes.get("color") == null && !node.style.contains("color")) {
                    node.attributes.put("color", "#0000ff");
                }
                if(node.attributes.get("font") == null) {
                    node.attributes.put("font", "Times New Roman&PLAIN&16");
                }
            break;
            case "pre": //preformatted text

            break;
            case "ul": //unordered list

            break;
            case "a": //anchor

            break;
            case "abbr": //abbreviation

            break;
            case "b": //bold text

            break;
            case "bdi": //isolates a part of text that might be formatted in a different direction from other text outside of it

            break;
            case "bdo": //overrides the current text direction

            break;
            case "br": //line break

            break;
            case "cite": //title of a work

            break;
            case "code": //code

            break;
            case "data": //value of the element

            break;
            case "dfn": //defining instance of a term

            break;
            case "em": //emphasized text

            break;
            case "i": //italic text

            break;
            case "kbd": //keyboard input

            break;
            case "mark": //highlighted text

            break;
            case "q": //short inline quote

            break;
            case "rp": //what to show in browsers that do not support ruby annotations

            break;
            case "rt": //ruby annotation text

            break;
            case "ruby": //ruby annotation

            break;
            case "s": //strikethrough text

            break;
            case "samp": //sample output from a computer program

            break;
            case "small": //small text

            break;
            case "span": //generic inline container

            break;
            case "strong": //strong text

            break;
            case "sub": //subscript text

            break;
            case "sup": //superscript text

            break;
            case "time": //time or date

            break;
            case "u":

            break;
            case "var": //variable

            break;
            case "wbr": //word break opportunity

            break;
            case "area": //hyperlink area

            break;
            case "audio": //audio content

            break;
            case "img": //image

            break;
            case "map": //image map

            break;
            case "track": //text track for media element

            break;
            case "video": //video content

            break;
            case "embed": //external content

            break;
            case "fencedframe":
                
            break;
            case "iframe": //inline frame

            break;
            case "object": //external resource

            break;
            case "picture": //container for multiple image sources

            break;
            case "portal": //container for external content

            break;
            case "source": //media source

            break;
            case "svg": //scalable vector graphics

            break;
            case "math":

            break;
            case "canvas":

            break;
            case "noscript":

            break;
            case "script":

            break;
            case "del":

            break;
            case "ins":

            break;
            case "caption":

            break;
            case "col":

            break;
            case "colgroup":

            break;
            case "table":

            break;
            case "tbody":

            break;
            case "td":

            break;
            case "tfoot":

            break;
            case "th":
            
            break;
            case "thead":

            break;
            case "tr":

            break;
            case "button":

            break;
            case "datalist":

            break;
            case "fieldset":
            
            break;
            case "form":

            break;
            case "input":

            break;
            case "label":

            break;
            case "legend":

            break;
            case "meter":

            break;
            case "optgroup":

            break;
            case "option":

            break;
            case "output":

            break;
            case "progress":

            break;
            case "select":

            break;
            case "textarea":

            break;
            case "details":

            break;
            case "dialog":

            break;
            case "summary":

            break;
            case "slot":

            break;
            case "template":
            
            break;
            case "acronym":

            break;
            case "big":

            break;
            case "center":

            break;
            case "content":

            break;
            case "dir":

            break;
            case "font":

            break;
            case "frame":

            break;
            case "frameset":

            break;
            case "image":

            break;
            case "marquee":

            break;
            case "menuitem":

            break;
            case "nobr":

            break;
            case "noembed":

            break;
            case "noframes":

            break;
            case "param":

            break;
            case "plaintext":

            break;
            case "rb":

            break;
            case "rtc":

            break;
            case "shadow":

            break;
            case "strike":

            break;
            case "tt":

            break;
            case "xmp":

            break;

        }
        return node;
    }

    public void renderElement(DataStructures.HTMLNode node, Graphics g, DataStructures.HTMLNode parent) {
        switch (node.tag) {
            //root element
            case "html": // root element of a document

            break;
            //head stuff
            case "base": //specifies base url for relative urls

            break;
            case "head": //container for meta data

            break;
            case "link": //defines relationship between document and external resource

            break;
            case "meta": //contians metadata that is not represeted by other head tags

            break;
            case "style": //intenal css

            break;
            case "title": //title of the document

            break;
            //body stuff
            case "body": //container for content

            break;
            case "address": //contact information for the author of the document

            break;
            case "article": //independent self contained content

            break;
            case "aside": //content aside from the content it is placed in

            break;
            case "footer": //footer for the document or section

            break;
            case "header": //header for the document or section

            break;
            case "h1": //heading
                
            break;
            case "h2": //heading

            break;
            case "h3": //heading

            break;
            case "h4": //heading

            break;
            case "h5": //heading

            break;
            case "h6": //heading

            break;
            case "hgroup": //group of headings

            break;
            case "main": //main content of the document

            break;
            case "nav": //navigation links

            break;
            case "section": //section of the document

            break;
            case "search":

            break;
            case "blockquote": //long quote

            break;
            case "dd": //description of the term in a description list

            break;
            case "div": //division or section

            break;
            case "dl": //description list

            break;
            case "dt": //term in a description list

            break;
            case "figcaption": //caption for a figure element

            break;
            case "figure": //group of media content

            break;
            case "hr": //horizontal rule

            break;
            case "li": //list item

            break;
            case "menu": //menu list

            break;
            case "ol": //ordered list

            break;
            case "p": //paragraph
                node = applyInlineCSS(node);
                node = applyIDCSS(node);
                node = applyClassCSS(node);
                node = applyTagCSS(node);
                node = applyWildCSS(node);
                node = applyBaseCSS(node);   
                g.setColor(colorDecode(node.attributes.get("color")));
                g.setFont(fontDecode(node.attributes.get("font")));
                g.drawString(node.content, node.xy[0], node.xy[1]);
            break;
            case "pre": //preformatted text

            break;
            case "ul": //unordered list

            break;
            case "a": //anchor

            break;
            case "abbr": //abbreviation

            break;
            case "b": //bold text

            break;
            case "bdi": //isolates a part of text that might be formatted in a different direction from other text outside of it

            break;
            case "bdo": //overrides the current text direction

            break;
            case "br": //line break

            break;
            case "cite": //title of a work

            break;
            case "code": //code

            break;
            case "data": //value of the element

            break;
            case "dfn": //defining instance of a term

            break;
            case "em": //emphasized text

            break;
            case "i": //italic text

            break;
            case "kbd": //keyboard input

            break;
            case "mark": //highlighted text

            break;
            case "q": //short inline quote

            break;
            case "rp": //what to show in browsers that do not support ruby annotations

            break;
            case "rt": //ruby annotation text

            break;
            case "ruby": //ruby annotation

            break;
            case "s": //strikethrough text

            break;
            case "samp": //sample output from a computer program

            break;
            case "small": //small text

            break;
            case "span": //generic inline container

            break;
            case "strong": //strong text

            break;
            case "sub": //subscript text

            break;
            case "sup": //superscript text

            break;
            case "time": //time or date

            break;
            case "u":

            break;
            case "var": //variable

            break;
            case "wbr": //word break opportunity

            break;
            case "area": //hyperlink area

            break;
            case "audio": //audio content

            break;
            case "img": //image

            break;
            case "map": //image map

            break;
            case "track": //text track for media element

            break;
            case "video": //video content

            break;
            case "embed": //external content

            break;
            case "fencedframe":
                
            break;
            case "iframe": //inline frame

            break;
            case "object": //external resource

            break;
            case "picture": //container for multiple image sources

            break;
            case "portal": //container for external content

            break;
            case "source": //media source

            break;
            case "svg": //scalable vector graphics

            break;
            case "math":

            break;
            case "canvas":

            break;
            case "noscript":

            break;
            case "script":

            break;
            case "del":

            break;
            case "ins":

            break;
            case "caption":

            break;
            case "col":

            break;
            case "colgroup":

            break;
            case "table":

            break;
            case "tbody":

            break;
            case "td":

            break;
            case "tfoot":

            break;
            case "th":
            
            break;
            case "thead":

            break;
            case "tr":

            break;
            case "button":

            break;
            case "datalist":

            break;
            case "fieldset":
            
            break;
            case "form":

            break;
            case "input":

            break;
            case "label":

            break;
            case "legend":

            break;
            case "meter":

            break;
            case "optgroup":

            break;
            case "option":

            break;
            case "output":

            break;
            case "progress":

            break;
            case "select":

            break;
            case "textarea":

            break;
            case "details":

            break;
            case "dialog":

            break;
            case "summary":

            break;
            case "slot":

            break;
            case "template":
            
            break;
            case "acronym":

            break;
            case "big":

            break;
            case "center":

            break;
            case "content":

            break;
            case "dir":

            break;
            case "font":

            break;
            case "frame":

            break;
            case "frameset":

            break;
            case "image":

            break;
            case "marquee":

            break;
            case "menuitem":

            break;
            case "nobr":

            break;
            case "noembed":

            break;
            case "noframes":

            break;
            case "param":

            break;
            case "plaintext":

            break;
            case "rb":

            break;
            case "rtc":

            break;
            case "shadow":

            break;
            case "strike":

            break;
            case "tt":

            break;
            case "xmp":

            break;

        }
    }

    private void attrParse(String key, String value, String tag) {
        if(key.contains("data=")) {

        }
        switch (key) {
            case "accesskey": // if alt is pressed then the key that is the value of this attribue then the element will get focus
            
            break;
            case "anchor": //anchors one element to another based on id so that it stays next to the element when scrolling

            break;
            case "autocapitalize": //capitalizes the first letter of the element

            break;
            case "autofocus": //focuses on the element when the page is loaded

            break;
            case "class": //apply class styels to element

            break;
            case "contenteditable": //lets you change the text in a element 

            break;
            case "dir": //direction of text

            break;  
            case "draggable": //lets you drag the element
            
            break;
            case "hidden": //hides element from view

            break;
            case "id": // identifier for element 

            break;
            case "inert": //makes it so the element can not be interacted with

            break;
            case "is": //defines a custom element from js

            break;
            case "lang": //language of the element
                if(tag == "html") {
                    documentData.put("lang", value);
                }
            break;
            case "style": //css styles for the element

            break;
            case "tabindex": //tab order of the element

            break;
            case "title": //title of the element

            break;
            case "href": //hyperlink reference

            break;
            case "target": //where to open the link

            break;
        }
    }

    private Color colorDecode(String color) {
        return new Color(Integer.parseInt(color.substring(1, 3), 16), Integer.parseInt(color.substring(3, 5), 16), Integer.parseInt(color.substring(5, 7), 16));
    }

    private Font fontDecode(String font) {
        if(font.split("&")[1] == "PLAIN") {
            return new Font(font.split("&")[0], Font.PLAIN, Integer.parseInt(font.split("&")[2]));
        }
        return new Font(font.split("&")[0], Font.PLAIN, Integer.parseInt(font.split("&")[2]));
    }
    
    private DataStructures.HTMLNode applyCSS(DataStructures.HTMLNode node, String property, String value) {
        switch(property) {
            //a
            case "accent-color":

            break;
            case "align-content":

            break;
            case "align-items":

            break;
            case "align-self":

            break;
            case "all":

            break;
            case "animation":

            break;
            case "animation-delay":

            break;
            case "animation-direction":

            break;
            case "animation-duration":

            break;
            case "animation-fill-mode":

            break;
            case "animation-iteration-count":

            break;
            case "animation-name":

            break;
            case "animation-play-state":

            break;
            case "animation-timing-function":

            break;
            case "aspect-ratio":

            break;
            //b
            case "backdrop-filter":

            break;
            case "backface-visibility":

            break;
            case "background":

            break;
            case "background-attachment":

            break;
            case "background-blend-mode":

            break;
            case "background-clip":

            break;
            case "background-color":

            break;
            case "background-image":

            break;
            case "background-origin":

            break;
            case "background-position":

            break;
            case "background-position-x":

            break;
            case "background-position-y":

            break;
            case "background-repeat":

            break;
            case "background-size":

            break;
            case "block-size":

            break;
            case "border":

            break;
            case "border-block":

            break;
            case "border-block-color":

            break;
            case "border-block-end":

            break;
            case "border-block-end-color":

            break;
            case "border-block-end-style":

            break;
            case "border-block-end-width":

            break;
            case "border-block-start":

            break;
            case "border-block-start-color":

            break;
            case "border-block-start-style":

            break;
            case "border-block-start-width":

            break;
            case "border-block-style":

            break;
            case "border-block-width":

            break;
            case "border-bottom":

            break;
            case "border-bottom-color":

            break;
            case "border-bottom-left-radius":

            break;
            case "border-bottom-right-radius":

            break;
            case "border-bottom-style":

            break;
            case "border-bottom-width":

            break;
            case "border-collapse":

            break;
            case "border-color":

            break;
            case "border-end-end-radius":

            break;
            case "border-end-start-radius":

            break;
            case "border-image":

            break;
            case "border-image-outset":

            break;
            case "border-image-repeat":

            break;
            case "border-image-slice":

            break;
            case "border-image-source":

            break;
            case "border-image-width":

            break;
            case "border-inline":

            break;
            case "border-inline-color":

            break;
            case "border-inline-end":

            break;
            case "border-inline-end-color":

            break;
            case "border-inline-end-style":

            break;
            case "border-inline-end-width":

            break;
            case "border-inline-start":

            break;
            case "border-inline-start-color":

            break;
            case "border-inline-start-style":

            break;
            case "border-inline-start-width":

            break;
            case "border-inline-style":

            break;
            case "border-inline-width":

            break;
            case "border-left":

            break;
            case "border-left-color":

            break;
            case "border-left-style":

            break;
            case "border-left-width":

            break;
            case "border-radius":

            break;
            case "border-right-color":

            break;
            case "border-right-style":

            break;
            case "border-right-width":

            break;
            case "border-spacing":

            break;
            case "border-start-end-radius":

            break;
            case "border-start-start-radius":

            break;
            case "border-style":

            break;
            case "border-top":

            break;
            case "border-top-color":

            break;
            case "border-top-left-radius":

            break;
            case "border-top-right-radius":

            break;
            case "border-top-style":

            break;
            case "border-top-width":

            break;
            case "border-width":

            break;
            case "bottom":

            break;
            case "box-decoration-break":

            break;
            case "box-reflect":

            break;
            case "box-shadow":

            break;
            case "box-sizing":

            break;
            case "break-after":

            break;
            case "break-before":

            break;
            case "break-inside":

            break;
            //c
            case "caption-side":

            break;
            case "caret-color":

            break;
            case "@charset":

            break;
            case "clear":

            break;
            case "clip":

            break;
            case "clip-path":

            break;
            case "color":
                if (value.startsWith("rgb")) {
                    String[] rgbValues = value.substring(value.indexOf('(') + 1, value.indexOf(')')).split(",");
                    int r = Integer.parseInt(rgbValues[0].trim());
                    int g = Integer.parseInt(rgbValues[1].trim());
                    int b = Integer.parseInt(rgbValues[2].trim());
                    value = String.format("#%02x%02x%02x", r, g, b);
                    node.attributes.put("color", value);
                    node.style.add("color");
                    return node;
                } else if(value.startsWith("#")) {
                    node.attributes.put("color", value);
                    node.style.add("color");
                    return node;
                } else if(value.equals("black") || value.equals("BLACK")) {
                    node.attributes.put("color", "#000000");
                    node.style.add("color");
                    return node;
                } else if(value.equals("red") || value.equals("RED")) {
                    node.attributes.put("color", "#FF0000");
                    node.style.add("color");
                }
            break;
            case "color-scheme":

            break;
            case "column-count":

            break;
            case "column-fill":

            break;
            case "column-gap":

            break;
            case "column-rule":

            break;
            case "column-rule-color":

            break;
            case "column-rule-style":

            break;
            case "column-span":

            break;
            case "column-width":

            break;
            case "columns":

            break;
            case "content":

            break;
            case "counter-increment":

            break;
            case "counter-reset":

            break;
            case "counter-set":

            break;
            case "cursor":

            break;
            //d
            case "direction":

            break;
            case "display":

            break;
            //e
            case "empty-cells":

            break;
            //f
            case "filter":

            break;
            case "flex":

            break;
            case "flex-basis":

            break;
            case "flex-direction":

            break;
            case "flex-flow":

            break;
            case "flex-grow":

            break;
            case "flex-shrink":

            break;
            case "flex-wrap":

            break;
            case "float":

            break;
            case "font":

            break;
            case "@font-face":

            break;
            case "font-family":

            break;
            case "font-feature-settings":

            break;
            case "@font-feature-values":

            break;
            case "font-kerning":

            break;
            case "font-language-override":

            break;
            case "font-size":
                if(value.contains("px")) {
                    value = value.substring(0, value.length() - 2);
                }
                if(node.attributes.containsKey("font") && !node.style.contains("font-size")) {
                    node.attributes.put("font", node.attributes.get("font").split("&")[0] + node.attributes.get("font").split("&")[1] + "&" + value);
                    node.style.add("font-size");
                } else if(!node.attributes.containsKey("font") && !node.style.contains("font-size")) {
                    node.attributes.put("font", "Times New Roman&PLAIN&" + value);
                    node.style.add("font-size");
                }
            break;
            case "font-size-adjust":

            break;
            case "font-stretch":

            break;
            case "font-style":

            break;
            case "font-synthesis":

            break;
            case "font-variant":

            break;
            case "font-variant-alternates":

            break;
            case "font-variant-caps":

            break;
            case "font-variant-east-asian":

            break;
            case "font-variant-ligatures":

            break;
            case "font-variant-numeric":

            break;
            case "font-variant-position":

            break;
            case "font-variant-weight":

            break;
            //g
            case "gap":

            break;
            case "grid":

            break;
            case "grid-area":

            break;
            case "grid-auto-columns":

            break;
            case "grid-auto-flow":

            break;
            case "grid-auto-rows":

            break;
            case "grid-column":

            break;
            case "grid-column-end":

            break;
            case "grid-column-start":

            break;
            case "grid-row":

            break;
            case "grid-row-end":

            break;
            case "grid-row-start":

            break;
            case "grid-template":

            break;
            case "grid-template-areas":

            break;
            case "grid-template-columns":

            break;
            case "grid-template-rows":

            break;
            //h
            case "hanging-punctuation":

            break;
            case "height":

            break;
            case "hyphens":

            break;
            case "hyphenate-character":

            break;
            //i
            case "image-rendering":

            break;
            case "@import":

            break;
            case "initial-letter":

            break;
            case "inline-size":

            break;
            case "inset":

            break;
            case "inset-block":

            break;
            case "inset-block-end":

            break;
            case "inset-block-start":

            break;
            case "inset-inline":

            break;
            case "inset-inline-end":

            break;
            case "inset-inline-start":

            break;
            case "isolation":

            break;
            //j
            case "justify-content":

            break;
            case "justify-items":

            break;
            case "justify-self":

            break;
            //k
            case "@keyframes":

            break;
            //l
            case "left":

            break;
            case "letter-spacing":

            break;
            case "line-break":

            break;
            case "line-height":

            break;
            case "list-style":

            break;
            case "list-style-image":

            break;
            case "list-style-position":

            break;
            case "list-style-type":

            break;
            //m
            case "margin":

            break;
            case "margin-block":

            break;
            case "margin-block-end":

            break;
            case "margin-block-start":

            break;
            case "margin-bottom":

            break;
            case "margin-inline":

            break;
            case "margin-inline-end":

            break;
            case "margin-inline-start":

            break;
            case "margin-left":

            break;
            case "margin-right":

            break;
            case "margin-top":

            break;
            case "marker":

            break;
            case "marker-end":

            break;
            case "marker-mid":

            break;
            case "marker-start":

            break;
            case "mask":

            break;
            case "mask-clip":

            break;
            case "mask-composite":

            break;
            case "mask-image":

            break;
            case "mask-mode":

            break;
            case "mask-origin":

            break;
            case "mask-position":

            break;
            case "mask-repeat":

            break;
            case "mask-size":

            break;
            case "mask-type":

            break;
            case "max-height":

            break;
            case "max-width":

            break;
            case "@media":

            break;
            case "max-block-size":

            break;
            case "max-inline-size":

            break;
            case "min-block-size":

            break;
            case "min-inline-size":

            break;
            case "min-height":

            break;
            case "min-width":

            break;
            case "mix-blend-mode":

            break;
            //o
            case "object-fit":

            break;
            case "object-position":

            break;
            case "offset":

            break;
            case "offset-anchor":

            break;
            case "offset-distance":

            break;
            case "offset-path":

            break;
            case "offset-position":

            break;
            case "offset-rotate":

            break;
            case "opacity":

            break;
            case "order":

            break;
            case "orphans":

            break;
            case "outline":

            break;
            case "outline-color":

            break;
            case "outline-offset":

            break;
            case "outline-style":

            break;
            case "outline-width":

            break;
            case "overflow":

            break;
            case "overflow-anchor":

            break;
            case "overflow-wrap":

            break;
            case "overflow-x":

            break;
            case "overflow-y":

            break;
            case "overscroll-behavior":

            break;
            case "overscroll-behavior-block":

            break;
            case "overscroll-behavior-inline":

            break;
            case "overscroll-behavior-x":

            break;
            case "overscroll-behavior-y":

            break;
            //p
            case "padding":

            break;
            case "padding-block":

            break;
            case "padding-block-end":

            break;
            case "padding-block-start":

            break;
            case "padding-bottom":

            break;
            case "padding-inline":

            break;
            case "padding-inline-end":

            break;
            case "padding-inline-start":

            break;
            case "padding-left":

            break;
            case "padding-right":

            break;
            case "padding-top":

            break;
            case "page-break-after":

            break;
            case "page-break-before":

            break;
            case "page-break-inside":

            break;
            case "paint-order":

            break;
            case "perspective":

            break;
            case "perspective-origin":

            break;
            case "place-content":

            break;
            case "place-items":

            break;
            case "place-self":

            break;
            case "pointer-events":

            break;
            case "position":

            break;
            case "@property":

            break;
            //q
            case "quotes":

            break;
            //r
            case "resize":

            break;
            case "right":

            break;
            case "rotate":

            break;
            case "row-gap":

            break;
            //s
            case "scale":

            break;
            case "scroll-behavior":

            break;
            case "scroll-margin":

            break;
            case "scroll-margin-block":

            break;
            case "scroll-margin-block-end":

            break;
            case "scroll-margin-block-start":

            break;
            case "scroll-margin-bottom":

            break;
            case "scroll-margin-inline":

            break;
            case "scroll-margin-inline-end":

            break;
            case "scroll-margin-inline-start":

            break;
            case "scroll-margin-left":

            break;
            case "scroll-margin-right":

            break;
            case "scroll-margin-top":

            break;
            case "scroll-padding":

            break;
            case "scroll-padding-block":

            break;
            case "scroll-padding-block-end":

            break;
            case "scroll-padding-block-start":

            break;
            case "scroll-padding-bottom":

            break;
            case "scroll-padding-inline":

            break;
            case "scroll-padding-inline-end":

            break;
            case "scroll-padding-inline-start":

            break;
            case "scroll-padding-left":

            break;
            case "scroll-padding-right":

            break;
            case "scroll-padding-top":

            break;
            case "scroll-snap-align":

            break;
            case "scroll-snap-stop":

            break;
            case "scroll-snap-type":

            break;
            case "scrollbar-color":

            break;
            //t
            case "tab-size":

            break;
            case "table-layout":

            break;
            case "text-align":

            break;
            case "text-align-last":

            break;
            case "text-combine-upright":

            break;
            case "text-decoration":

            break;
            case "text-decoration-color":

            break;
            case "text-decoration-line":

            break;
            case "text-decoration-style":

            break;
            case "text-decoration-thickness":

            break;
            case "text-emphasis":

            break;
            case "text-emphasis-color":

            break;
            case "text-emphasis-position":

            break;
            case "text-emphasis-style":

            break;
            case "text-indent":

            break;
            case "text-justify":

            break;
            case "text-orentation":

            break;
            case "text-overflow":

            break;
            case "text-shadow":

            break;
            case "text-transform":

            break;
            case "text-underline-offset":

            break;
            case "text-underline-position":

            break;
            case "top":

            break;
            case "transform":

            break;
            case "transform-origin":

            break;
            case "transform-style":

            break;
            case "transition":

            break;
            case "transition-delay":

            break;
            case "transition-duration":

            break;
            case "transition-property":

            break;
            case "transition-timing-function":

            break;
            case "translate":

            break;
            //u
            case "unicode-bidi":

            break;
            case "user-select":

            break;
            //v
            case "vertical-align":

            break;
            case "visibility":

            break;
            //w
            case "white-space":

            break;
            case "widows":

            break;
            case "width":

            break;
            case "word-break":

            break;
            case "word-spacing":

            break;
            case "word-wrap":

            break;
            case "writing-mode":

            break;
            //z
            case "z-index":

            break;
            case "zoom":

            break;

            default:
                System.out.println("unrecognised css property: " + property);
            break;
        }
        return node;
    }
}
