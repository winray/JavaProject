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
 * A <code>ChameleonCritter</code> takes on the color of neighboring actors as
 * it moves through the grid. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class BlusterCritter extends Critter
{
	private static int c;
	
	public BlusterCritter() {
		super();
		c = 5;
		setColor(Color.black);
	}
	
	public BlusterCritter(int setC) {
		super();
		c = setC;
		setColor(Color.black);
	}
	
    /**
     * Randomly selects a neighbor and changes this critter's color to be the
     * same as that neighbor's. If there are no neighbors, no action is taken.
     */	
	
	public ArrayList<Actor> getActors()
    {
		Grid<Actor> grid = getGrid();
		ArrayList<Actor> actors = new ArrayList<Actor>();
		for (Location loc : getTwoNeighbors(getLocation())) {
			if (grid.get(loc) != null) {
				actors.add(grid.get(loc));
			}
		}
		
        return actors;
    }
	
	public ArrayList<Location> getTwoNeighbors(Location loc) {
		Grid<Actor> grid = getGrid();
		ArrayList<Location> locations = new ArrayList<Location>();
		for (int row = loc.getRow()-2; row < loc.getRow()+2; row++) {
			for (int col = loc.getCol()-2; col < loc.getCol()+2; col++) {
				if (loc.equals(new Location(row, col))
						&& grid.isValid(new Location(row, col))) {
					locations.add(new Location(row, col));
				}
			}
		}
		return locations;
	}
	
	public int getSize(ArrayList<Actor> actors) {
		int size = 0;
		for (Actor actor : actors) {
			if (actor instanceof Critter) {
				size++;
			}
		}
		return size;
	}
	
	public void turnColorDark(double factor) {
		Color c = getColor();
        int red = (int) (c.getRed() * (1 - factor));
        int green = (int) (c.getGreen() * (1 - factor));
        int blue = (int) (c.getBlue() * (1 - factor));

        setColor(new Color(red, green, blue));
	}
	
	public void turnColorBright(double factor) {
		Color c = getColor();
        int red = (int) (c.getRed());
        int green = (int) (c.getGreen() + factor);
        int blue = (int) (c.getBlue() + factor);
        
        red = weatherOut(red);
        green = weatherOut(green);
        blue = weatherOut(blue);

        setColor(new Color(red, green, blue));
	}
	
	public int weatherOut(int color) {
		if (color > 255) {
			color = 255;
		}
		return color;
	}
	
    public void processActors(ArrayList<Actor> actors)
    {
    	int size = getSize(actors);
        if (size < c) {
        	turnColorBright(5);
        } else {
        	turnColorDark(0.05);
        }
    }

    
}
