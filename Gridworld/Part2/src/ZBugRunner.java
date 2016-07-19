import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;
/**
* This class runs a world that contains box bugs.
* This class is not tested on the AP CS A and AB exams.
*/
import info.gridworld.actor.Bug;

class ZBug extends Bug
{
    private int steps;
    private int sideLength;
    private int turn;

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public ZBug(int length)
    {
        steps = 0;
        sideLength = length;
        turn = 0;
        turn();
        turn();
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
        if (steps < sideLength && canMove())
        {
            move();
            steps++;
        } else if (steps == sideLength && turn == 0) {
            turn();
            turn();
            turn();
            steps = 0;
            turn++;
        } else {
        	if (turn < 2) {
        	    turn();
        	    turn();
        	    turn();
        	    turn();
        	    turn();
        	    steps = 0;
            	turn++;
        	} else {
        		steps = sideLength+1;
        	}
        }
    }
}

public class ZBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        ZBug alice = new ZBug(10);
        alice.setColor(Color.ORANGE);
        world.add(new Location(2, 2), alice);
        world.show();
    }
}

