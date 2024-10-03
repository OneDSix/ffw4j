package net.onedsix.ffw4j.txt;

import net.onedsix.ffw4j.services.reader.FileIOService;

import java.io.*;

public final class TextFileIO implements FileIOService<TextFileContainer> {
    @Override
    public String getFileExtension() {
        return "txt";
    }
    
    @Override
    public TextFileContainer read(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) content.append(line).append("\n");
        br.close();
        return new TextFileContainer(content.toString());
    }
    
    @Override
    public boolean write(File file, TextFileContainer data) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(file));
        writer.write(data.toString());
        writer.close();
        return false;
    }
}
