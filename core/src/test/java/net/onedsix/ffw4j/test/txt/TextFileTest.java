package net.onedsix.ffw4j.test.txt;

import net.onedsix.ffw4j.core.FFWServiceLoader;
import net.onedsix.ffw4j.core.ServiceOptions;
import net.onedsix.ffw4j.txt.TextFileContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static net.onedsix.ffw4j.core.FileUtils.getResource;
import static net.onedsix.ffw4j.core.FileWrapper.read;
import static net.onedsix.ffw4j.core.FileWrapper.write;
import static org.junit.jupiter.api.Assertions.*;

public class TextFileTest {
    
    @BeforeAll
    public static void beforeTesting() {
        new FFWServiceLoader(ServiceOptions.ERROR_ON_COLLISION, ServiceOptions.DEBUG_LOGGING);
    }
    
    @Test
    public void gettingAndSetting() {
        // reading from a file (internal file at that)
        try (TextFileContainer textFile = (TextFileContainer) read(getResource("eula.txt"))) {
            
            // getLine()
            assertEquals(textFile.getLine(2), "eula=false");
            
            // setLine()
            textFile.setLine(2, "eula=true");
            assertEquals(textFile.getLine(2), "eula=true");
            
            // writing to a file
            File temp = new File("./temp.txt");
            temp.createNewFile();
            temp.deleteOnExit();
            write(temp, textFile);
            
            // make sure changes persist
            textFile.setLine(0, "# Some legal stuff that comes from Minecraft's eula.txt file");
            TextFileContainer newTextFile = (TextFileContainer) read(new File("./temp.txt"));
            assertNotEquals(textFile.toString(), newTextFile.toString());
            
        } catch (Exception e) {
            // very bad, no good.
            fail(e.getCause().getMessage());
        }
    }
}
