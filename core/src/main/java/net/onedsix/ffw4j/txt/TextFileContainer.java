package net.onedsix.ffw4j.txt;

import net.onedsix.ffw4j.services.container.AbstractContainer;

public class TextFileContainer extends AbstractContainer {
    
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
    public void setCommentsMode(TextComments mode) {
        commentsMode = mode;
    }
    //
    public boolean ignoresComments() {
        return commentsMode instanceof TextComments.NoComments;
    }
    
    /** Note that this method returns the line in off-by-one count.
     * Line 0 is the first line, line 1 is the second, etc.
     * @see #setLine(int, String) */
    public String getLine(int lineNum) throws ArrayIndexOutOfBoundsException {
        String[] split = content.split("\n");
        return split[lineNum];
    }
    /** Note that this method returns the line in off-by-one count.
     * Line 0 is the first line, line 1 is the second, etc.
     * @see #getLine(int)  */
    public String setLine(int lineNum, String str) throws ArrayIndexOutOfBoundsException {
        String[] split = content.split("\n");
        split[lineNum] = str;
        StringBuilder sb = new StringBuilder(split.length);
        for (String line : split) {
            sb.append(line).append("\n");
        }
        this.content = sb.toString();
        return this.content;
    }
    
    @Override
    public void close() {
        // Nothing!
    }
}
