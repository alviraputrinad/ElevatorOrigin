// This is mutant program.
// Author : ysma

import java.util.Vector;
import java.util.Enumeration;


public class ElevatorInterface
{

    private static java.util.Vector list = new java.util.Vector();

    private int elevatorID;

    public ElevatorInterface( Elevator e )
    {
        list.add( this );
        elevatorID = e.getElevatorID();
    }

    public static ElevatorInterface getFromList( ElevatorControl ec )
    {
        int EIDNeeded = ec.getElevator().getElevatorID();
        for (java.util.Enumeration e = list.elements(); e.hasMoreElements();) {
            ElevatorInterface testIF = (ElevatorInterface) e.nextElement();
            if (testIF.elevatorID == EIDNeeded) {
                return testIF;
            }
        }
        return null;
    }

    public void requestStop( int floor )
    {
        if (floor >= 0 && floor < ElevatorGroup.numFloors) {
            ElevatorControl ec = new ElevatorControl( elevatorID );
            ec.requestStop( floor );
            ElevatorGroup.elevatorDisplay( elevatorID + 1, "Stop requested at floor " + floor );
        } else {
            System.out.println( "No such floor: " + floor + " ." );
        }
    }

    public int getElevatorID()
    {
        return elevatorID;
    }

    public void motorMoveDown()
    {
        ElevatorGroup group = ElevatorGroup.getGroup( ElevatorGroup.numElevators, ElevatorGroup.numFloors );
        group.motorMoving( elevatorID, -1, Elevator.selectElevator( elevatorID ).getFloor().getFloorID() );
    }

    public void motorMoveUp()
    {
        ElevatorGroup group = ElevatorGroup.getGroup( ElevatorGroup.numElevators, ElevatorGroup.numFloors );
        group.motorMoving( elevatorID, 1, Elevator.selectElevator( elevatorID ).getFloor().getFloorID() );
    }

    public void motorStop()
    {
        ElevatorGroup.elevatorDisplay( elevatorID + 1, "Motor stopped." );
    }

}
