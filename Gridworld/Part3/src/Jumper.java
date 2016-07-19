/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2005-2006 Cay S. Horstmann (http://horstmann.com)
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * @author Cay Horstmann
 */

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.actor.Flower;
import java.awt.Color;

/**
 * A <code>Bug</code> is an actor that can jump and turn. It doesn't drop flowers as
 * it jumps. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class Jumper extends Actor
{
    /**
     * Constructs a red bug.
     */
    public Jumper()
    {
        setColor(Color.RED);
    }

    /**
     * Constructs a bug of a given color.
     * @param bugColor the color for this bug
     */
    public Jumper(Color bugColor)
    {
        setColor(bugColor);
    }

    /**
     * Moves if it can jump, turns otherwise.
     */
    public void act()
    {
    	int jumpSteps = canJump();
        if (jumpSteps > 0) {
        	jump(jumpSteps);
        } else {
            turn();
        }
    }

    /**
     * Turns the bug 45 degrees to the right without changing its location.
     */
    public void turn()
    {
        setDirection(getDirection() + Location.HALF_RIGHT);
    }

    /**
     * Jumps the bug forward,  do not put a flower into the location it previously
     * occupied.
     */
    public void jump(int jumpSteps)
    {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return;
        }
        Location loc = getLocation();
        Location tempNext = loc.getAdjacentLocation(getDirection());
        Location Next = tempNext.getAdjacentLocation(getDirection());
        
        if (jumpSteps == 2 && gr.isValid(Next)) {
        	moveTo(Next);
        } else if (jumpSteps == 1 && gr.isValid(tempNext)) {
			moveTo(tempNext);
        } else {
			removeSelfFromGrid();
        }
    }

    /**
     * Tests whether this bug can jump forward into a location that is empty or
     * contains a flower.
     * @return true if this bug can jump.
     */
    public int canJump()
    {
        Grid<Actor> gr = getGrid();
        if (gr == null) {
            return 0;
        }
     
        Location loc = getLocation();
        Location tempNext = loc.getAdjacentLocation(getDirection());
        Location Next = tempNext.getAdjacentLocation(getDirection());
        
        if (gr.isValid(Next)) {
        	Actor neighbor = gr.get(Next);
        	if ((neighbor == null) || (neighbor instanceof Flower)) {
        	    return 2;
        	}
        }
        if (gr.isValid(tempNext)) {
        	Actor neighbor = gr.get(tempNext);
        	if ((neighbor == null) || (neighbor instanceof Flower)) {
        	    return 1;
        	}
        }

       return 0;
        // ok to move into empty location or onto flower
        // not ok to move onto any other actor
    }
}




