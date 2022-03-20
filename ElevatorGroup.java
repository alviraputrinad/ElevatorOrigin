// This is mutant program.
// Author : ysma

public class ElevatorGroup
{

    public static ElevatorGroup theGroup;

    public static int numFloors;

    public static int numElevators;

    public static java.lang.Thread[] elevatorThread;

    public static boolean[] threadStarted;

    public static Elevator[] e;

    public static FloorInterface[] fli;

    public static Floor[] floor;

    public static ElevatorInterface[] ebi;

    public static ArrivalSensor[] sensor;

    public static ElevatorGroup getGroup( int el, int fl )
    {
        if (theGroup == null) {
            theGroup = new ElevatorGroup( el, fl );
            return theGroup;
        } else {
            return theGroup;
        }
    }

    private ElevatorGroup( int el, int fl )
    {
        numFloors = fl;
        numElevators = el;
        elevatorThread = new java.lang.Thread[numElevators];
        threadStarted = new boolean[numElevators];
        e = new Elevator[numElevators];
        floor = new Floor[numFloors];
        sensor = new ArrivalSensor[numFloors];
        ebi = new ElevatorInterface[numElevators];
        fli = new FloorInterface[numFloors];
    }

    public void startThread( int threadNum )
    {
        if (threadStarted[threadNum] == false) {
            elevatorThread[threadNum].start();
            threadStarted[threadNum] = true;
        }
    }

    public void stopGroup()
    {
        for (int i = 0; i < numElevators; i++) {
            if (threadStarted[i] == true) {
                threadStarted[i] = false;
                e[i].turnOff();
            }
        }
        Floor.removeFloors();
        Elevator.removeAllElevators();
        theGroup = null;
    }

    public void startGroup()
    {
        for (int i = 0; i < numFloors; i++) {
            floor[i] = new Floor( i );
            sensor[i] = floor[i].getSensor();
            fli[i] = new FloorInterface( sensor[i] );
        }
        for (int i = 0; i < numElevators; i++) {
            e[i] = new Elevator();
            elevatorThread[i] = new java.lang.Thread( e[i], "Elevator Thread " + i );
            ebi[i] = new ElevatorInterface( e[i] );
            theGroup.startThread( i );
        }
    }

    public void motorMoving( int elevatorID, int direction, int currentFloor )
    {
        try {
            ElevatorGroup.elevatorDisplay( elevatorID + 1, "Moving from floor " + currentFloor + " in direction " + direction );
            ElevatorGroup.elevatorDisplay( elevatorID + 1, "Motor moving, sleeping to cause delay...." );
            Thread.sleep( 2000 );
        } catch ( java.lang.Exception e ) {
            System.out.println( "Exception in motorMoving." );
        }
        fli[currentFloor].stopAtThisFloor( elevatorID, currentFloor + direction );
    }

    public static void elevatorDisplay( int eid, java.lang.String message )
    {
        System.out.println( "Elevator " + eid + ": " + message );
    }

    public FloorInterface getFloorInterface( int floorID )
    {
        if (floorID >= 0 && floorID < ElevatorGroup.numFloors) {
            return fli[floorID];
        } else {
            System.out.println( "No Such floor." );
            return null;
        }
    }

    public ElevatorInterface getElevatorInterface( int elevatorID )
    {
        if (elevatorID >= 0 && elevatorID < ElevatorGroup.numElevators) {
            return ebi[elevatorID];
        } else {
            System.out.println( "No Such floor." );
            return null;
        }
    }

}
