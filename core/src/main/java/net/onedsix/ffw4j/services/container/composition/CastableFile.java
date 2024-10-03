package net.onedsix.ffw4j.services.container.composition;

import java.io.File;

public interface CastableFile<C> {
	C cast(Class<C> cast) throws Exception;
	C castRead(File file, Class<C> cast) throws Exception;
	boolean castWrite(File file, C cast) throws Exception;
}
