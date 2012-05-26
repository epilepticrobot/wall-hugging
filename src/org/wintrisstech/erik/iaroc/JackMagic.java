//once the beacon is detected, the robot stops.

package org.wintrisstech.erik.iaroc;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 * A Ferrari is an implementation of the IRobotCreateInterface.
 *
 * @author Erik
 */
public class JackMagic extends Ferrari
{
    private static final String TAG = "Ferrari";
    private static final int RED_BUOY_CODE = 248;
    private static final int GREEN_BUOY_CODE = 244;
    private static final int FORCE_FIELD_CODE = 242;
    private static final int BOTH_BUOY_CODE = 252;
    private static final int RED_BUOY_FORCE_FIELD_CODE = 250;
    private static final int GREEN_BUOY_FORCE_FIELD_CODE = 246;
    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
    /*
     * The maze can be thought of as a grid of quadratic cells, separated by
     * zero-width walls. The cell width includes half a pipe diameter on each
     * side, i.e the cell edges pass through the center of surrounding pipes.
     * <p> Row numbers increase northward, and column numbers increase eastward.
     * <p> Positions and direction use a reference system that has its origin at
     * the west-most, south-most corner of the maze. The x-axis is oriented
     * eastward; the y-axis is oriented northward. The unit is 1 mm. <p> What
     * the Ferrari knows about the maze is:
     */
    private final static int NUM_ROWS = 12;
    private final static int NUM_COLUMNS = 4;
    private final static int CELL_WIDTH = 712;
    /*
     * State variables:
     */
    private int speed = 300; // The normal speed of the Ferrari when going straight
    // The row and column number of the current cell. 
    private int row;
    private int column;
    private boolean running = true;
    private final static int SECOND = 1000; // number of millis in a second
    private int[] c =
    {
        60, 200
    };
    private int[] e =
    {
        64, 200
    };
    private int[] g =
    {
        67, 200
    };

    /**
     * Constructs a Ferrari, an amazing machine!
     *
     * @param ioio the IOIO instance that the Ferrari can use to communicate
     * with other peripherals such as sensors
     * @param create an implementation of an iRobot
     * @param dashboard the Dashboard instance that is connected to the Ferrari
     * @throws ConnectionLostException
     */
    public JackMagic(IOIO ioio, IRobotCreateInterface create, Dashboard dashboard) throws ConnectionLostException
    {
        super(ioio, create, dashboard);
    }

    /**
     * Main method that gets the Ferrari running.
     *
     */
    public void run() {
        dashboard.speak("i am jack version 5");
        try
        {
            
            wallHugger();
            //StateControllerInterface jackStateController = new StateControllerVic(delegate, dashboard);
            //jackStateController.startStateController();
            StateControllerInterface jackStateController = new StateControllerJackBasic(delegate, dashboard);
            jackStateController.startStateController();
            //readBeacon();
        } catch (Exception ex)
        {
            dashboard.log("problem: " + ex.getMessage());
        }
        dashboard.log("Run completed.");
        setRunning(false);
        shutDown();
        setRunning(false);
    }

    private void wallHugger()
    {

        dashboard.speak("hugging wall");
        while (true)
        {
            try
            {
                readSensors(SENSORS_BUMPS_AND_WHEEL_DROPS);
                driveDirect(500, 500);
                if (isBumpRight())
                {
                    readSensors(SENSORS_ANGLE);
                    int currentDegree = getAngle();
                    while(currentDegree > -5)
                   {
                       driveDirect(-500,500);
                       readSensors(SENSORS_ANGLE);
                       currentDegree = +getAngle();
                       dashboard.log("" + currentDegree);
                   }

                }
            } catch (ConnectionLostException ex)
            {
            }
        }
    }


    private void turnSpecifiedDegree() throws ConnectionLostException
    {
        
        int currentDegree = getAngle();
        readSensors(SENSORS_ANGLE);
        dashboard.log("turning specified degree");
        while(true)
               {
                readSensors(SENSORS_ANGLE);
             //while not at 10 degrees
                   while(currentDegree > -10)
                   {
                       driveDirect(-500,500);
                       readSensors(SENSORS_ANGLE);
                       currentDegree = +getAngle();
                       dashboard.log("" + currentDegree);
                   }
                   driveDirect(0,0);
                   dashboard.log("stop");
             //keep turning
                }
    }

    
//    public void readBeacon()
//    {
//        try
//        {
//            dashboard.log("hahahahhah");
//            dashboard.speak("Ha Ha Ha Ha Ha");
//            readSensors(SENSORS_INFRARED_BYTE);
//            if (getInfraredByte() != 255)
//            {
//                dashboard.log("Sensing Sensing Sensing");
//                dashboard.speak("Sensing Sensing Sensing");
//                this.demo(1);
//            }
//            //    private static final int RED_BUOY_CODE = 248;
//            //    private static final int GREEN_BUOY_CODE = 244;
//            //    private static final int FORCE_FIELD_CODE = 242;
//            //    private static final int BOTH_BUOY_CODE = 252;
//            //    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
//            //    private static final int GREEN_BUOY_FORCE_FIELD_CODE = 246;
//            //    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
//        } catch (ConnectionLostException ex)
//        {
//            dashboard.log("Reading infrared sensors!");
//        }
//    }

}
