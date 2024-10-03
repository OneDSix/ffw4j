package net.onedsix.ffw4j.legacy;

import net.onedsix.ffw4j.services.container.composition.CastableFile;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class YamlFile<C> extends AbstractFileFormat<Yaml> implements CastableFile<Yaml, C> {
	@Override
	public C cast(Yaml data) throws Exception {
		return null;
	}

	@Override
	public C castRead(File file, Class<C> cast) throws Exception {
		return null;
	}

	@Override
	public boolean castWrite(File file, C cast) throws Exception {
		return false;
	}

	@Override
	public boolean write(File file, Yaml data) throws Exception {
		return false;
	}

	@Override
	public Yaml read(File file) throws IOException {
		Yaml yaml = new Yaml();
		try (InputStream is = Files.newInputStream(file.toPath())) {
			yaml.load(is);
		}
		return yaml;
	}
}
