// This is mutant program.
// Author : ysma

public class ElevatorControl
{

    private Elevator myElevator;

    private ElevatorInterface ei;

    public ElevatorControl( Elevator elevator )
    {
        myElevator = elevator;
        ei = ElevatorInterface.getFromList( this );
    }

    public ElevatorControl( int EID )
    {
        myElevator = Elevator.selectElevator( EID );
        ei = ElevatorInterface.getFromList( this );
    }

    public void stopElevator()
    {
        myElevator.stopElevator();
    }

    public void openDoor()
    {
        myElevator.openDoor();
    }

    public void closeDoor()
    {
        myElevator.closeDoor();
    }

    public void requestStop( int floor )
    {
        myElevator.addStop( floor, true );
    }

    public Elevator getElevator()
    {
        return myElevator;
    }

    public void motorMoveDown()
    {
        ei.motorMoveDown();
    }

    public void motorMoveUp()
    {
        ei.motorMoveUp();
    }

    public void motorStop()
    {
        ei.motorStop();
    }

}
