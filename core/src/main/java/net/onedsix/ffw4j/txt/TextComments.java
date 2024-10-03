package net.onedsix.ffw4j.txt;

public interface TextComments {
    //
    String beginComment();
    //
    String endComment();
    //
    String wholeLineComment();
    
    class NoComments implements TextComments {
        @Override
        public String beginComment() {
            return null;
        }
    
        @Override
        public String endComment() {
            return null;
        }
    
        @Override
        public String wholeLineComment() {
            return null;
        }
    }
    
    class SaneComments implements TextComments {
        @Override
        public String beginComment() {
            return "/*";
        }
    
        @Override
        public String endComment() {
            return "*/";
        }
    
        @Override
        public String wholeLineComment() {
            return "//";
        }
    }
    
    class PythonComments implements TextComments {
        @Override
        public String beginComment() {
            return null;
        }
    
        @Override
        public String endComment() {
            return null;
        }
    
        @Override
        public String wholeLineComment() {
            return "#";
        }
    }
    
    class MarkupComments implements TextComments {
        @Override
        public String beginComment() {
            return "<!--";
        }
    
        @Override
        public String endComment() {
            return "-->";
        }
    
        @Override
        public String wholeLineComment() {
            return null;
        }
    }
}
