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

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.util.ArrayList;

/**
 * A <code>CrabCritter</code> looks at a limited set of neighbors when it eats and moves.
 * <br />
 * This class is not tested on the AP CS A and AB exams.
 */
public class QuickCrab extends CrabCritter
{
   @Override
   public ArrayList<Location> getMoveLocations() {
	   ArrayList<Location> locations = canMove(getLocation(), getDirection());
	   if (locations == null)	   
		   return super.getMoveLocations();
	   return locations;
   }
   
   public ArrayList<Location> canMove(Location loc, int direction) {
	   ArrayList<Location> locations = new ArrayList<Location>();
	   int dirs[] = {Location.LEFT, Location.RIGHT};
	   Grid grid = getGrid();
	   for (int dir : dirs) {
		   Location tempLocation = loc.getAdjacentLocation(direction+dir);
		   if (grid.isValid(tempLocation) && grid.get(tempLocation) == null) {
			   tempLocation = tempLocation.getAdjacentLocation(direction+dir);
			   if (grid.isValid(tempLocation) && grid.get(tempLocation) == null)
				   locations.add(tempLocation);
		   }
	   }
	   return locations;
   }
   
}
