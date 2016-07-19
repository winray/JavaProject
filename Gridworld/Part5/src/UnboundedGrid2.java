/* 
 * AP(r) Computer Science GridWorld Case Study:
 * Copyright(c) 2002-2006 College Entrance Examination Board 
 * (http://www.collegeboard.com).
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
 * @author Alyce Brady
 * @author APCS Development Committee
 * @author Cay Horstmann
 */

import info.gridworld.grid.AbstractGrid;
import info.gridworld.grid.Location;

import java.util.*;

/**
 * An <code>UnboundedGrid</code> is a rectangular grid with an unbounded number of rows and
 * columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class UnboundedGrid2<E> extends AbstractGrid<E>
{
    private Object[][] occupantArray;
    private int size;

    /**
     * Constructs an empty unbounded grid.
     */
    public UnboundedGrid2()
    {
    	size = 16;
        occupantArray = new Object[16][16];
    }

    public int getNumRows()
    {
        return -1;
    }

    public int getNumCols()
    {
        return -1;
    }

    public boolean isValid(Location loc)
    {
    	if (loc == null) {
            throw new NullPointerException("loc == null");
    	}
        return (loc.getRow() >= 0 && loc.getCol() >= 0
        		&& loc.getRow() <= size && loc.getCol() <= size);
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> a = new ArrayList<Location>();
        for (int i = 0; i < size; i++) {
        	for (int j = 0; j < size; j++) {
        		Location loc = new Location(i, j);
        		if (get(loc) != null) {
        				a.add(loc);
        		}
        	}
        }
        return a;
    }

    public E get(Location loc)
    {
        if (isValid(loc) && loc.getRow() < size && loc.getCol() < size) {
        	return (E) occupantArray[loc.getRow()][loc.getCol()];
        }
        return null;
    }

    public E put(Location loc, E obj)
    {
        if (loc == null) {
            throw new NullPointerException("loc == null");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }
        if (loc.getRow() >= size || loc.getCol() >= size) {
        	int oldsize = size;
        	size *= 2;
        	Object[][] tempObjects = new Object[size][size];
        	for (int i = 0; i < oldsize; i++)
        		for (int j = 0; j < oldsize; j++)
        			tempObjects[i][j] = occupantArray[i][j];
        	occupantArray = tempObjects;
        }
        E old = get(loc);
        occupantArray[loc.getRow()][loc.getCol()] = obj;
        return old;
    }

    public E remove(Location loc)
    {
        if (isValid(loc) && loc.getRow() < size && loc.getCol() < size) {
        	E old = get(loc);
        	occupantArray[loc.getRow()][loc.getCol()] = null;
        	return old;
        }
        return null;
    }
}