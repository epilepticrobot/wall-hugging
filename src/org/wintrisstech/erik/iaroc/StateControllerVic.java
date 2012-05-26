/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wintrisstech.erik.iaroc;

import android.os.SystemClock;
import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 *
 * @author Rob
 */
public class StateControllerVic extends IRobotCreateAdapter implements StateControllerInterface {
    // extending IRobotCreateAdapter to get all of the convenience methods
    

    public StateControllerVic(IRobotCreateInterface create, Dashboard dashboard) {
        // TODO: would be safer if this were a singleton
        super(create);
        this.dashboard = dashboard;
    }
    
    private final Dashboard dashboard;
    private int presentState = 0;
    private int statePointer = 0;
    private final int[][] stateTable = {
        {
            0, 1, 2, 3
        },
        {
            1, 1, 2, 3
        },
        {
            2, 1, 2, 3
        },
        {
            3, 1, 2, 3
        }
    };

    private void backingUp(String direction) throws ConnectionLostException {

        dashboard.log("backingup");
        driveDirect(-200, -200);
        SystemClock.sleep(1000);
        if (direction.equals("right")) {
            driveDirect(100, -100);
            SystemClock.sleep(1000);
            /*
             * spins 45 degrees to the right
             */
        }
        if (direction.equals("left")) {
            driveDirect(-100, 100);
            SystemClock.sleep(1000);
            /*
             * spins45 degrees to the left
             */
        }
        if (direction.equals("straight")) {
            int r = (int) (Math.random() * 2);

            if (r == 1) {
                driveDirect(100, -100);
                dashboard.log("~right /" + r);
            }
            if (r == 0) {
                driveDirect(-100, 100);
                dashboard.log("~left /" + r);
            }
            SystemClock.sleep(1000);
        }
//        SystemClock.sleep(2000);
        driveDirect(100, 100);//drive direct needs to go forward more
        statePointer = 0;
        presentState = 0;
        // dashboard.log("hi");
    }

    public void startStateController() throws ConnectionLostException {
        driveDirect(100, 100);
        // dashboard.log("in state contol");
        while (true) {
            setStatePointer();
            switch (stateTable[presentState][statePointer]) {
                case 0:
                    presentState = 0;
//                    dashboard.log("0");
                    break;
                case 1:
                    presentState = 1;
                    backingUp("right");
                    dashboard.log("bumpright");
                    break;
                case 2:
                    presentState = 2;
                    backingUp("left");
                    break;
                case 3:
                    presentState = 3;
                    backingUp("straight");
                    break;
            }
        }
    }

    public void setStatePointer() throws ConnectionLostException {
        readSensors(SENSORS_BUMPS_AND_WHEEL_DROPS);
//        SystemClock.sleep(00);
        // dashboard.log("statepointer");
        // dashboard.log("is bump right" + isBumpRight());
        // dashboard.log("is bump left" + isBumpLeft());

        if (isBumpRight() && !isBumpLeft())//Right
        {
            dashboard.log("rightbump det");
            statePointer = 1;
        }
        if (isBumpLeft() && !isBumpRight())//left
        {
            dashboard.log("left bump detected");
            statePointer = 2;
        }
        if (isBumpRight() && isBumpLeft())//straight
        {
            dashboard.log("front bump detected");
            statePointer = 3;
        }
        if (!isBumpLeft() && !isBumpRight())//none
        {
            // dashboard.log("no bump detected");
            statePointer = 0;
        }
    }
}
