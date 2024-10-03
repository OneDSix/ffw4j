package net.onedsix.ffw4j.legacy.converters;

/** Utility interface for checking if an object is able to convert.<br/>
 * The other interfaces in this package may extend this interface. */
public interface ConvertibleFile<T> {
	T getValue();
}
