package net.onedsix.ffw4j.services.container.composition;

/** Utility class for casting to both {@link ModifiableContainer} and {@link QueryableContainer} at the same time.<br>
 * Can be used in try-with-resource statements. */
public interface CompleteContainer extends ModifiableContainer, QueryableContainer, AutoCloseable {

}
