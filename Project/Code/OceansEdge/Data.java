package OceansEdge;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;


//purpose is to store data that may need to be used again soon but if not used in 5 mins it will be dropped from the thing
//will be used to store things temporarily so data dosent need to be constantly sent by the server and will check to see if every thing that is required is already stored
//will also drop things no longer on the list of things needed
public class Data {
    public HashMap<String, DataEntry> Data = new HashMap<String, DataEntry>();
    public class DataEntry {
        public Object data;
        public int time;

        public int getTime() {
            return time;
        }
    }
    public String list;

    public void start() throws InterruptedException {
        while(true) {
            updateTime();
            Thread.sleep(60000); // sleep for a minute
        }
    }

    public Object get(String key) {
        if(Data.containsKey(key)) {
            Data.get(key).time = 5;
            return Data.get(key).data;
        } else {
            return null;
        }
    }

    public void store(String key, String type, Object newData, boolean replace) {
        
        if(replace == true) {
            if(Data.containsKey(key)) {
                Data.remove(key);
                DataEntry ent = new DataEntry();
                ent.time = 5;
                ent.data = newData;
                Data.put(key, ent);
            } else {
                DataEntry ent = new DataEntry();
                ent.time = 5;
                ent.data = newData;
                Data.put(key, ent);
            }
        } else {
            if(Data.containsKey(key)) {
                if(Data.get(key).data.getClass().getName().equals(newData.getClass().getName())) {
                    String c = newData.getClass().getName();
                    switch(c) {
                        case "java.lang.String":

                        break;
                        case "java.lang.Integer":

                        break;
                        case "java.lang.Double":

                        break;
                        case "java.lang.Float":

                        break;
                        case "DataStructures.TexVertex":

                        break;
                        case "DataStructures.Vertex":

                        break;
                        case "DataStructures.Vector":

                        break;
                        case "DataStructures.Velocity":

                        break;
                        case "DataStructures.HTMLNode":

                        break;
                    }
                } else {
                    if(checkCompatability(Data.get(key), newData)) {
                        String c = newData.getClass().getName();
                        switch(c) {
                            case "java.lang.String":
    
                            break;
                            case "java.lang.Integer":
    
                            break;
                            case "java.lang.Double":
    
                            break;
                            case "java.lang.Float":
    
                            break;
                            case "DataStructures.TexVertex":
    
                            break;
                            case "DataStructures.Vertex":
    
                            break;
                            case "DataStructures.Vector":
    
                            break;
                            case "DataStructures.Velocity":
    
                            break;
                            case "DataStructures.HTMLNode":

                            break;
                        }
                    } else {
                        Data.remove(key);
                        DataEntry ent = new DataEntry();
                        ent.time = 5;
                        ent.data = newData;
                        Data.put(key, ent);
                    }
                }
            } else {
                DataEntry ent = new DataEntry();
                ent.time = 5;
                ent.data = newData;
                Data.put(key, ent);
            }
        }
    }
    // will check if the data is able to be combined based on preset combinations like string and int and then combine them
    public boolean checkCompatability(Object ndata, Object newData) { 

        return false;
    }

    public void updateTime() {
        for(Entry e : Data.entrySet()) {
            System.out.println(e.getKey() + " " + e.getValue().getClass());
            //int time = e.getValue();
            //String key = e.getKey();
            //if(time <= 0) {
            //    Data.remove(key);
            //}
        }
    }
}
