package net.onedsix.ffw4j.txt;

import net.onedsix.ffw4j.services.container.AbstractPlainContainer;

public class TextFileContainer extends AbstractPlainContainer {
    
    //
    private String content;
    //
    private TextComments commentsMode;
    
    //
    public TextFileContainer() {
        this("", new TextComments.NoComments());
    }
    //
    public TextFileContainer(String string) {
        this(string, new TextComments.NoComments());
    }
    //
    public TextFileContainer(String string, TextComments mode) {
        this.content = string;
        this.commentsMode = mode;
    }
    
    //
    @Override
    public String getFileExtension() {
        return "txt";
    }
    //
    @Override
    public String toString() {
        return content;
    }
    
    //
    public void commentsMode(TextComments mode) {
        commentsMode = mode;
    }
    //
    public boolean ignoresComments() {
        return commentsMode instanceof TextComments.NoComments;
    }
    
    //
    public int findCount(String phrase) {
    
    }
    //
    public int findFirst(String phrase) {
    
    }
    
    //
    public String getLine(int number) {
    
    }
}
