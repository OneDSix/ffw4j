package net.onedsix.ffw4j.jackson.pojo;

import net.onedsix.ffw4j.services.container.composition.CastableFile;
import net.onedsix.ffw4j.services.reader.FileIOService;

public abstract class JacksonPojoReader<P> implements FileIOService<P>, CastableFile<P> {
}
