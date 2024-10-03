package net.onedsix.ffw4j.legacy;

import java.io.*;

public abstract class AbstractFileFormat<T> {
	public T value;
	public AbstractFileFormat(File file) throws Exception {
		value = this.read(file);
	}
	public AbstractFileFormat() {}
	public abstract boolean write(File file, T data) throws Exception;
	public abstract T read(File file) throws Exception;

	protected String readPlain(File file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder content = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) content.append(line).append("\n");
		br.close();
		return content.toString();
	}

	protected boolean writePlain(File file, String data) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.write(data);
		writer.close();
		return false;
	}
}
