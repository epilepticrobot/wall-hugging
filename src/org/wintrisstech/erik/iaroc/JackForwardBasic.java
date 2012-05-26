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
public class JackForwardBasic extends IRobotCreateAdapter implements JackState {

    StateControllerJackBasic controller;

    public JackForwardBasic(IRobotCreateInterface delegate, StateControllerJackBasic controller) {
        super(delegate);
        this.controller = controller;
    }

    public JackState bothBump() {
        if (Math.random() < .5) {
            return leftBump();
        } else {
            return rightBump();
        }
    }

    public JackState leftBump() {
        return controller.BACKFACERIGHT;
    }

    public JackState rightBump() {
        return controller.BACKFACELEFT;
    }

    public JackState noBump() {
        return this;
    }

    public JackState action() {
        try {
            driveDirect(300, 300);
        } catch (ConnectionLostException ex) {
            Logger.getLogger(JackForwardBasic.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (isBumpLeft() && isBumpRight()) {
            return bothBump();
        } else if (isBumpLeft()) {
            return leftBump();
        } else if (isBumpRight()) {
            return rightBump();
        } else {
            return noBump();
        }
    }
}
