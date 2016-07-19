import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.Location;
import java.awt.Color;
/**
* This class runs a world that contains box bugs.
* This class is not tested on the AP CS A and AB exams.
*/
import info.gridworld.actor.Bug;

class DancingBug extends Bug
{
    private int[] turn;
    private int turn_index;

    /**
     * Constructs a box bug that traces a square of a given side length
     * @param length the side length
     */
    public DancingBug(int[] turnArray)
    {
    	for (int i = 0; i < turnArray.length; i++)
            turn[i] = turnArray[i];
        turn_index = 0;
    }

    /**
     * Moves to the next location of the square.
     */
    public void act()
    {
    	for (int i = 0; i < turn[turn_index]; i++) {
        	turn();
        }
        if (canMove())
            move();
        else
            turn();
        turn_index++;
    	if (turn_index == turn.length)
    		turn_index = 0;
    }
}

public class DancingBugRunner
{
    public static void main(String[] args)
    {
    	int[] turnArray = {1, 2, 3, 4, 9, 10, 5, 6, 7, 8};
        ActorWorld world = new ActorWorld();
        DancingBug alice = new DancingBug(turnArray);
        alice.setColor(Color.ORANGE);
        world.add(new Location(4, 4), alice);
        world.show();
    }
}

