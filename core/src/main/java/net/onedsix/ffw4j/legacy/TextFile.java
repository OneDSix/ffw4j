package net.onedsix.ffw4j.legacy;

import java.io.*;

public class TextFile extends AbstractFileFormat<String> {
	@Override
	public String read(File file) throws IOException {
		return readPlain(file);
	}

	@Override
	public boolean write(File file, String data) throws IOException {
		return writePlain(file, data);
	}
}
