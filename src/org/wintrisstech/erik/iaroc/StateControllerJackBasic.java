/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 *
 * @author Rob
 */
public class StateControllerJackBasic extends IRobotCreateAdapter implements StateControllerInterface {

    public final JackState FORWARD;
    public final JackState BACKFACELEFT;
    public final JackState BACKFACERIGHT;
    public final JackState FINAL;
    
    private final Dashboard dashboard;
    private JackState state;

    public StateControllerJackBasic(IRobotCreateInterface create, Dashboard dashboard) {
        // TODO: would be safer if this were a singleton
        super(create);
        this.dashboard = dashboard;
        
        FORWARD       = new JackForwardBasic(delegate, this);
        BACKFACELEFT  = new JackBackFaceLeftBasic(delegate, this);
        BACKFACERIGHT = new JackBackFaceRightBasic(delegate, this);
        FINAL         = new JackFinal(delegate, this);

        state = FORWARD;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void startStateController() throws ConnectionLostException {
        while( state != FINAL) {
            state = state.action();
        }
        
        // stop the Roomba
        driveDirect(0, 0);
    }
}
