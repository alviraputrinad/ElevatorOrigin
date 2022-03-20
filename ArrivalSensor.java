// This is mutant program.
// Author : ysma

public class ArrivalSensor
{

    private Elevator elevator;

    private Floor floor;

    public ArrivalSensor( Floor floor )
    {
        this.floor = floor;
    }

    public boolean stopAtThisFloor( int elevatorID )
    {
        boolean stopped = false;
        elevator = Elevator.selectElevator( elevatorID );
        stopped = elevator.notifyNewFloor( floor );
        return stopped;
    }

    public Floor getTheFloor()
    {
        return floor;
    }

}
