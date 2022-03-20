// This is mutant program.
// Author : ysma

public class Floor
{

    private ArrivalSensor arrivalsensor;

    private Elevator elevator;

    private static Floor[] allFloors = null;

    private int floorID;

    private boolean upButtonPressed = false;

    private boolean downButtonPressed = false;

    public Floor( int floorID )
    {
        if (allFloors == null) {
            allFloors = new Floor[ElevatorGroup.numFloors];
        }
        arrivalsensor = new ArrivalSensor( this );
        this.floorID = floorID;
        Floor.allFloors[floorID] = this;
    }

    public static Floor selectFloor( int floorID )
    {
        if (floorID < ElevatorGroup.numFloors && floorID > -1) {
            return allFloors[floorID];
        } else {
            return null;
        }
    }

    public static int getNoFloors()
    {
        return ElevatorGroup.numFloors;
    }

    public Elevator requestUp()
    {
        upButtonPressed = true;
        elevator = Elevator.getBestElevator( floorID );
        if (floorID == ElevatorGroup.numFloors - 1) {
            System.out.println( "No up requests are permitted at this floor." );
        } else {
            if (elevator.getFloor().getFloorID() != this.getFloorID()) {
                System.out.println( "Request for elevator going UP made at floor " + floorID );
                System.out.println( "Best Elevator is: " + (elevator.getElevatorID() + 1) );
                elevator.addStop( floorID, true );
                System.out.println( "Stop added at " + floorID );
            } else {
                elevator.addStop( floorID, true );
            }
        }
        return elevator;
    }

    public Elevator requestDown()
    {
        downButtonPressed = true;
        elevator = Elevator.getBestElevator( floorID );
        if (floorID == 0) {
            System.out.println( "No down requests are permitted at this floor." );
        } else {
            if (elevator.getFloor().getFloorID() != this.getFloorID()) {
                System.out.println( "Request for elevator going DOWN made at floor " + floorID );
                System.out.println( "Best Elevator is: " + (elevator.getElevatorID() + 1) );
                elevator.addStop( floorID, true );
                System.out.println( "Stop added at " + floorID );
            } else {
                elevator.addStop( floorID, true );
            }
        }
        return elevator;
    }

    public boolean requestUpMade()
    {
        if (upButtonPressed == true) {
            return true;
        } else {
            return false;
        }
    }

    public boolean requestDownMade()
    {
        if (downButtonPressed == true) {
            return true;
        } else {
            return false;
        }
    }

    public void requestUpServiced()
    {
        System.out.println( "Elevator going UP has arrived at floor " + floorID + "." );
        upButtonPressed = false;
    }

    public void requestDownServiced()
    {
        System.out.println( "Elevator going DOWN has arrived at floor " + floorID + "." );
        downButtonPressed = false;
    }

    public int getFloorID()
    {
        return floorID;
    }

    public ArrivalSensor getSensor()
    {
        return arrivalsensor;
    }

    public static void removeFloors()
    {
        allFloors = null;
    }

}
