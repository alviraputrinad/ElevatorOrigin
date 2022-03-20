// This is mutant program.
// Author : ysma

public class FloorControl
{

    private Floor floor;

    private ArrivalSensor sensor;

    private Elevator elevator;

    public FloorControl( ArrivalSensor sensor )
    {
        this.sensor = sensor;
    }

    public Elevator requestUp( int floorID )
    {
        floor = Floor.selectFloor( floorID );
        Elevator e = floor.requestUp();
        return e;
    }

    public Elevator requestDown( int floorID )
    {
        floor = Floor.selectFloor( floorID );
        Elevator e = floor.requestDown();
        return e;
    }

    public void stopAtThisFloor( int elevatorID, int floorID )
    {
        elevator = Elevator.selectElevator( elevatorID );
        floor = Floor.selectFloor( floorID );
        sensor = floor.getSensor();
        boolean stop = sensor.stopAtThisFloor( elevatorID );
        if (stop == true) {
            if (floor.getFloorID() == 0) {
                floor.requestUpServiced();
            } else {
                if (floor.getFloorID() == Floor.getNoFloors() - 1) {
                    floor.requestDownServiced();
                } else {
                    if (elevator.getDirection() == -1) {
                        if (floor.requestDownMade() == true) {
                            floor.requestDownServiced();
                        }
                    } else {
                        if (elevator.getDirection() == 1) {
                            if (floor.requestUpMade() == true) {
                                floor.requestUpServiced();
                            }
                        }
                    }
                }
            }
        }
    }

}
