package net.onedsix.ffw4j.services.reader;

import java.io.File;

public interface CastableFile {
	<C> C castRead(File file, Class<C> cast) throws Exception;
	<C> boolean castWrite(File file, C cast) throws Exception;
}
