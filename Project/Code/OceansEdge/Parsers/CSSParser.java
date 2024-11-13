package OceansEdge.Parsers;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.HashMap;

public class CSSParser {
    

    public ArrayList<String> parseCSS(ArrayList<String> css, String type) {
        for(int i = 0; i < css.size(); i++) {
            String line = css.get(i);
            if(line.contains(".")) {
                String className = line.substring(line.indexOf(".") + 1, line.indexOf("{") - 1);
                //System.out.println(className);
                int iter = 0;
                while(!css.get(i + iter).contains("}") ) {
                    String line2 = css.get(i + iter);
                    String property = ""; 
                    String value = "";
                    if(line2.contains(":")) {
                        String[] split = line2.split(":");
                        property = split[0];
                        value = split[1];
                    }
                    property = property.trim();
                    value = value.trim();
                    value = value.replace(";", "");
                    value = value.replace("\"", "");
                    if(!property.isEmpty()) { 
                        if(type.equals("external")) { //external css
                            if(OceansEdge.Drawing.parser.externalCssRules.get(0).containsKey(className)) {
                                if(OceansEdge.Drawing.parser.externalCssRules.get(0).get(className).containsKey(property)) {
                                    if(!OceansEdge.Drawing.parser.externalCssRules.get(0).get(className).get(property).equals(value)) {
                                        OceansEdge.Drawing.parser.externalCssRules.get(0).get(className).put(property, value);
                                    }
                                } else {
                                    OceansEdge.Drawing.parser.externalCssRules.get(0).get(className).put(property, value);
                                }
                            } else {
                                HashMap<String, String> propertyMap = new HashMap<String, String>();
                                propertyMap.put(property, value);
                                OceansEdge.Drawing.parser.externalCssRules.get(0).put(className, propertyMap);
                            }
                        } else { //internal css
                            if(OceansEdge.Drawing.parser.internalCssRules.get(0).containsKey(className)) {
                                if(OceansEdge.Drawing.parser.internalCssRules.get(0).get(className).containsKey(property)) {
                                    if(!OceansEdge.Drawing.parser.internalCssRules.get(0).get(className).get(property).equals(value)) {
                                        OceansEdge.Drawing.parser.internalCssRules.get(0).get(className).put(property, value);
                                    }
                                } else {
                                    OceansEdge.Drawing.parser.internalCssRules.get(0).get(className).put(property, value);
                                }
                            } else {
                                HashMap<String, String> propertyMap = new HashMap<String, String>();
                                propertyMap.put(property, value);
                                OceansEdge.Drawing.parser.internalCssRules.get(0).put(className, propertyMap);
                            }
                        }
                    }
                    iter++;
                }
                //System.out.println("clas+
            } else if(line.contains("#")) {
                String idName = line.substring(line.indexOf("#") + 1, line.indexOf("{") - 1);
                int iter = 0;
                while(!css.get(i + iter).contains("}") ) {
                    String line2 = css.get(i + iter);
                    String property = ""; 
                    String value = "";
                    if(line2.contains(":")) {
                        String[] split = line2.split(":");
                        property = split[0];
                        value = split[1];
                    }
                    property = property.trim();
                    value = value.trim();
                    value = value.replace(";", "");
                    value = value.replace("\"", "");
                    if(!property.isEmpty()) { 
                        if(type.equals("external")) { //external css
                            if(OceansEdge.Drawing.parser.externalCssRules.get(1).containsKey(idName)) {
                                if(OceansEdge.Drawing.parser.externalCssRules.get(1).get(idName).containsKey(property)) {
                                    if(!OceansEdge.Drawing.parser.externalCssRules.get(1).get(idName).get(property).equals(value)) {
                                        OceansEdge.Drawing.parser.externalCssRules.get(1).get(idName).put(property, value);
                                    }
                                } else {
                                    OceansEdge.Drawing.parser.externalCssRules.get(1).get(idName).put(property, value);
                                }
                            } else {
                                HashMap<String, String> propertyMap = new HashMap<String, String>();
                                propertyMap.put(property, value);
                                OceansEdge.Drawing.parser.externalCssRules.get(1).put(idName, propertyMap);
                            }
                        } else { //internal css
                            if(OceansEdge.Drawing.parser.internalCssRules.get(1).containsKey(idName)) {
                                if(OceansEdge.Drawing.parser.internalCssRules.get(1).get(idName).containsKey(property)) {
                                    if(!OceansEdge.Drawing.parser.internalCssRules.get(1).get(idName).get(property).equals(value)) {
                                        OceansEdge.Drawing.parser.internalCssRules.get(1).get(idName).put(property, value);
                                    }
                                } else {
                                    OceansEdge.Drawing.parser.internalCssRules.get(1).get(idName).put(property, value);
                                }
                            } else {
                                HashMap<String, String> propertyMap = new HashMap<String, String>();
                                propertyMap.put(property, value);
                                OceansEdge.Drawing.parser.internalCssRules.get(1).put(idName, propertyMap);
                            }
                        }
                    }
                    iter++;
                }
            } else if(line.contains("@")) {
                //System.out.println("at test");
            } else if(line.contains("*")) {
                //System.out.println("wild test");
            } else if(Boolean.parseBoolean(checkForTag(line)[1])) {
                //System.out.println("tag Test");
            }
        }
        return css;
    }

    private String[] checkForTag(String line) {
        if(line.contains("p")) {
            String[] tag = {"p", "true"};
            return tag;
        }
        String[] tag = {"", "false"};
        return tag;
    }

    public HashMap<String, HashMap<String, String>> validate(HashMap<String, HashMap<String, String>> css) {
        for(Entry ent1 : css.entrySet()) {
            String name = (String) ent1.getKey();
            for(Entry ent2 : css.get(name).entrySet()) {
                String property = (String) ent2.getKey();
                String value = css.get(name).get(property);
                if(!isValidValue(property, value)) {
                    css.get(name).remove(property);
                }
                if(css.get(name).isEmpty()) {
                    css.remove(name);
                }
            }
        }
        return css;
    }
    //checks to see if the value is valid for the property it is set too
    private boolean isValidValue(String prop, String value) {
        return true;
    }

    public void combine() {
        OceansEdge.Drawing.parser.finalCssRules = OceansEdge.Drawing.parser.internalCssRules;
        for(int i = 0; i < OceansEdge.Drawing.parser.finalCssRules.size(); i++) {
            for(Entry ent1 : OceansEdge.Drawing.parser.externalCssRules.get(i).entrySet()) {
                String name = (String) ent1.getKey();
                for(Entry ent2 : OceansEdge.Drawing.parser.externalCssRules.get(i).get(name).entrySet()) {
                    String property = (String) ent2.getKey();
                    String value = OceansEdge.Drawing.parser.externalCssRules.get(i).get(name).get(property);
                    if(OceansEdge.Drawing.parser.finalCssRules.get(i).containsKey(name)) {
                        if(!OceansEdge.Drawing.parser.finalCssRules.get(i).get(name).containsKey(property)) {
                            OceansEdge.Drawing.parser.finalCssRules.get(i).get(name).put(property, value);
                        }
                    } else {
                        HashMap<String, String> propertyMap = new HashMap<String, String>();
                        propertyMap.put(property, value);
                        OceansEdge.Drawing.parser.finalCssRules.get(i).put(name, propertyMap);
                    }
                }
            }
        }
    }
}
