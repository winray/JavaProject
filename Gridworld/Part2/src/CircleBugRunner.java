import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;
/**
* This class runs a world that contains box bugs.
* This class is not tested on the AP CS A and AB exams.
*/
import info.gridworld.actor.Bug;

class CircleBug extends Bug
{
    private int steps;
    private int sideLength;

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public CircleBug(int length)
    {
        steps = 0;
        sideLength = length;
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
        }
        else
        {
            turn();
            steps = 0;
        }
    }
}

public class CircleBugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        CircleBug alice = new CircleBug(2);
        alice.setColor(Color.ORANGE);
        CircleBug bob = new CircleBug(1);
        world.add(new Location(5, 2), alice);
        world.add(new Location(9, 6), bob);
        world.show();
    }
}

