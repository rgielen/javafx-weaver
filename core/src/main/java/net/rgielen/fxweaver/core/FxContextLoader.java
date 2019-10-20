package net.rgielen.fxweaver.core;

/**
 * FxContextLoader provides a generic factory {@link FxWeaver} template for implementing a concrete context loader for a
 * given DI / bean management framework. May be overridden by a class matching a specific framework bootstrap
 * mechanism.
 * <p/>
 * This is a convenience class, there is no actual need to implement it or to subclass {@link FxWeaver} to use the
 * weaving facility.
 *
 * @author Rene Gielen
 */
public abstract class FxContextLoader<T extends FxWeaver> {

    /**
     * (Optianally) Start the bean management / dependency injection context and provide a suitable {@link FxWeaver}
     * instance.
     *
     * @return A {@link FxWeaver} instance matching a concrete bean management framework
     */
    public abstract T start();

}
