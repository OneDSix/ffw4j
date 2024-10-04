package net.onedsix.ffw4j.jackson.pojo;

import net.onedsix.ffw4j.services.reader.CastableFile;
import net.onedsix.ffw4j.services.reader.FileIOService;

public abstract class JacksonPojoReader<P> implements FileIOService<P>, CastableFile<P> {
}
