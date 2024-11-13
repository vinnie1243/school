//can work like a compiler for java by making functions a class and having them be an array list of instruction codes

package OceansEdge.Parsers;

import java.util.ArrayList;

public class JavaScriptParser {
    

    class function {
        public String name;
        public String[] args;
        public String ret;
        public ArrayList<String> code;
        function(String name, String[] args, ArrayList<String> code, String ret) {
            this.name = name;
            this.args = args;
            this.code = code;
            this.ret = ret;
        }

        public void start() {
            
        }
        
    }
}
