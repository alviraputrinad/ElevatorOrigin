// This is mutant program.
// Author : ysma

import java.util.Vector;
import java.util.Enumeration;


public class Elevator implements java.lang.Runnable
{

    private static java.util.Vector allElevators = new java.util.Vector();

    private int elevatorID;

    private boolean running;

    public int direction;

    private int state;

    private Floor currentFloor;

    private boolean[] stops = new boolean[Floor.getNoFloors()];

    private int nStops;

    private boolean motorMoving;

    private boolean doorOpen;

    public static final int IDLE = 0;

    public static final int PREPARE = 1;

    public static final int MOVING = 2;

    public static final int FINDNEXT = 3;

    public Elevator()
    {
        int newIndex = allElevators.size();
        this.elevatorID = newIndex;
        allElevators.add( this );
        this.direction = 0;
        currentFloor = Floor.selectFloor( 0 );
        state = Elevator.IDLE;
        nStops = 0;
        doorOpen = true;
        motorMoving = false;
        running = true;
        for (int i = 0; i < Floor.getNoFloors(); i++) {
            Floor floor = Floor.selectFloor( i );
            stops[floor.getFloorID()] = false;
        }
    }

    public static Elevator selectElevator( int elevatorID )
    {
        return (Elevator) allElevators.elementAt( elevatorID );
    }

    public static Elevator getBestElevator( int floorID )
    {
        Elevator bestElevator = null;
        for (java.util.Enumeration e = allElevators.elements(); e.hasMoreElements();) {
            Elevator testElevator = (Elevator) e.nextElement();
            if (bestElevator == null) {
                bestElevator = testElevator;
            } else {
                if (bestElevator.getState() != Elevator.IDLE && testElevator.getState() == Elevator.IDLE) {
                    bestElevator = testElevator;
                } else {
                    if (bestElevator.getState() == Elevator.IDLE && testElevator.getState() == Elevator.IDLE) {
                        int bestDistance = java.lang.Math.abs( bestElevator.getFloor().getFloorID() - floorID );
                        int testDistance = java.lang.Math.abs( testElevator.getFloor().getFloorID() - floorID );
                        if (testDistance < bestDistance) {
                            bestElevator = testElevator;
                        }
                    } else {
                        if (bestElevator.getState() != Elevator.IDLE && testElevator.getState() != Elevator.IDLE) {
                            if ((testElevator.getFloor().getFloorID() - floorID) * testElevator.direction <= 0) {
                                if ((bestElevator.getFloor().getFloorID() - floorID) * bestElevator.direction > 0) {
                                    bestElevator = testElevator;
                                } else {
                                    int bestDistance = java.lang.Math.abs( bestElevator.getFloor().getFloorID() - floorID );
                                    int testDistance = java.lang.Math.abs( testElevator.getFloor().getFloorID() - floorID );
                                    if (testDistance < bestDistance) {
                                        bestElevator = testElevator;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return bestElevator;
    }

    public boolean notifyNewFloor( Floor newFloor )
    {
        currentFloor = newFloor;
        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Reached floor " + newFloor.getFloorID() );
        if (stops[newFloor.getFloorID()] == true) {
            stopElevator();
            return true;
        } else {
            if (newFloor.requestUpMade() && direction == 1) {
                stopElevator();
                return true;
            } else {
                if (newFloor.requestDownMade() && direction == -1) {
                    stopElevator();
                    return true;
                } else {
                    if (direction == 1) {
                        motorMoveUp();
                    } else {
                        motorMoveDown();
                    }
                    return false;
                }
            }
        }
    }

    private void motorMoveDown()
    {
        ElevatorControl ec = new ElevatorControl( this );
        motorMoving = true;
        ec.motorMoveDown();
        ec = null;
    }

    private void motorMoveUp()
    {
        ElevatorControl ec = new ElevatorControl( this );
        motorMoving = true;
        ec.motorMoveUp();
        ec = null;
    }

    public void moveElevator()
    {
        if (this.state == PREPARE) {
            ElevatorGroup.elevatorDisplay( elevatorID + 1, "Door closed." );
            closeDoor();
            this.state = MOVING;
            if (direction == 1) {
                motorMoveUp();
            } else {
                motorMoveDown();
            }
        }
        while (this.state == MOVING) {
            try {
                Thread.sleep( 500 );
            } catch ( java.lang.Exception e ) {
            }
        }
        getNextDestination();
    }

    public int getNextDestination()
    {
        while (this.state == FINDNEXT) {
            if (nStops == 0) {
                this.state = Elevator.IDLE;
                direction = 0;
                ElevatorGroup.elevatorDisplay( elevatorID + 1, "All stops handled.  Idling." );
                return -1;
            } else {
                int stopToCheck = currentFloor.getFloorID() + direction;
                while (Floor.selectFloor( stopToCheck ) != null && state == FINDNEXT) {
                    if (stops[stopToCheck] == true) {
                        this.state = PREPARE;
                        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Next Stop = floor " + stopToCheck );
                        return stopToCheck;
                    } else {
                        stopToCheck += direction;
                    }
                }
                if (Floor.selectFloor( stopToCheck ) == null && state == FINDNEXT) {
                    direction = -direction;
                }
            }
        }
        return -1;
    }

    public void stopElevator()
    {
        state = FINDNEXT;
        addStop( currentFloor.getFloorID(), false );
        motorStop();
        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Stopped.  " );
        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Door open." );
        openDoor();
    }

    private void motorStop()
    {
        ElevatorControl ec = new ElevatorControl( this );
        motorMoving = false;
        ec.motorStop();
        ec = null;
    }

    public void openDoor()
    {
        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Door is open on floor " + getFloor().getFloorID() + "." );
        try {
            doorOpen = true;
            Thread.sleep( 1000 );
        } catch ( java.lang.InterruptedException e ) {
        }
    }

    public void closeDoor()
    {
        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Door is closed on floor " + getFloor().getFloorID() + "." );
        try {
            doorOpen = false;
            Thread.sleep( 1000 );
        } catch ( java.lang.InterruptedException e ) {
        }
    }

    public void addStop( int floorID, boolean stopState )
    {
        if (stopState) {
            if (floorID != currentFloor.getFloorID() || this.getState() == Elevator.MOVING) {
                nStops++;
                stops[floorID] = stopState;
                if (this.state == Elevator.IDLE) {
                    if (this.currentFloor.getFloorID() < floorID) {
                        direction = 1;
                        this.state = PREPARE;
                    } else {
                        direction = -1;
                        this.state = PREPARE;
                    }
                }
            } else {
                ElevatorGroup.elevatorDisplay( elevatorID + 1, "Elevator is at requested floor." );
            }
        } else {
            stops[floorID] = stopState;
            nStops--;
        }
    }

    public void run()
    {
        while (running) {
            try {
                if (this.state == Elevator.IDLE) {
                    Thread.sleep( 100 );
                } else {
                    moveElevator();
                }
            } catch ( java.lang.Exception e ) {
            }
        }
    }

    public int getDirection()
    {
        return direction;
    }

    public int getState()
    {
        return state;
    }

    public Floor getFloor()
    {
        return currentFloor;
    }

    public int getElevatorID()
    {
        return elevatorID;
    }

    public boolean getDoorOpen()
    {
        return doorOpen;
    }

    public boolean getMotorMoving()
    {
        return motorMoving;
    }

    public boolean getStop( int i )
    {
        return stops[i];
    }

    public int getNumberOfStops()
    {
        return nStops;
    }

    public static void removeAllElevators()
    {
        allElevators.removeAllElements();
    }

    public void turnOff()
    {
        running = false;
    }

}
