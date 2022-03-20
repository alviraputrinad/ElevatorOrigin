// This is mutant program.
// Author : ysma

public class FloorInterface
{

    FloorControl fc;

    ArrivalSensor sensor;

    public FloorInterface( ArrivalSensor sensor )
    {
        this.sensor = sensor;
    }

    public Elevator requestUp( int floorID )
    {
        Elevator e = null;
        if (floorID >= 0 && floorID < ElevatorGroup.numFloors) {
            fc = new FloorControl( sensor );
            e = fc.requestUp( floorID );
            fc = null;
        } else {
            System.out.println( "No such floor " + floorID + "." );
        }
        return e;
    }

    public Elevator requestDown( int floorID )
    {
        Elevator e = null;
        if (floorID >= 0 && floorID < ElevatorGroup.numFloors) {
            fc = new FloorControl( sensor );
            e = fc.requestDown( floorID );
            fc = null;
        } else {
            System.out.println( "No such floor " + floorID + "." );
        }
        return e;
    }

    public void stopAtThisFloor( int elevatorID, int floorID )
    {
        fc = new FloorControl( sensor );
        fc.stopAtThisFloor( elevatorID, floorID );
        fc = null;
    }

    public Floor getFloor()
    {
        return sensor.getTheFloor();
    }

}
