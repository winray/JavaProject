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
 * @author Chris Nevison
 * @author Barbara Cloud Wells
 * @author Cay Horstmann
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * A <code>CrabCritter</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class KingCrab extends CrabCritter
{
    @Override
    public void processActors(ArrayList<Actor> actors) {
    	for (Actor actor : actors) {
    		move(actor);
    	}
    }
    
    @Override
    public ArrayList<Actor> getActors()
    {
        ArrayList<Actor> actors = new ArrayList<Actor>();
        int[] dirs =
            { Location.AHEAD, Location.HALF_LEFT, Location.HALF_RIGHT 
        		, Location.LEFT, Location.RIGHT};
        for (Location loc : getLocationsInDirections(dirs))
        {
            Actor a = getGrid().get(loc);
            if (a != null) {
                actors.add(a);
            }
        }

        return actors;
    }
    
    public void move(Actor actor) {
    	Location location = actor.getLocation().getAdjacentLocation(
				getLocation().getDirectionToward(actor.getLocation()));
		if (getGrid().isValid(location) && getGrid().get(location) == null) {
			actor.moveTo(location);
		} else {
			if (actor instanceof KingCrab) {
				int dir = (int) (Math.random()*45);
				actor.setDirection(getDirection()+dir);
			} else {
				actor.removeSelfFromGrid();
			}
		}
	}
}
