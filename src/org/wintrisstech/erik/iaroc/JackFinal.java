/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

import ioio.lib.api.exception.ConnectionLostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 *
 * @author Rob
 */
class JackFinal extends IRobotCreateAdapter implements JackState {
    // Just a FinalState to note completion - methods do nothing

    StateControllerJackBasic controller;

    public JackFinal(IRobotCreateInterface delegate, StateControllerJackBasic controller) {
        super(delegate);
        this.controller = controller;
    }

    public JackState bothBump() {
        return this;
    }

    public JackState leftBump() {
        return this;
    }

    public JackState rightBump() {
        return this;
    }

    public JackState noBump() {
        return this;
    }

    public JackState action() {
        return this;
    }
}
