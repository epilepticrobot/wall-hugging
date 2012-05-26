package org.wintrisstech.erik.iaroc;

import ioio.lib.api.exception.ConnectionLostException;

/**
 *
 * @author Rob
 */
public interface StateControllerInterface {
    public void startStateController() throws ConnectionLostException;
}
