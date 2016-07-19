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

import java.util.ArrayList;

/**
 * A <code>BoundedGrid</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseBoundedGrid<E> extends AbstractGrid<E>
{
	 private SparseGridNode[] occupantArray;
	 private int row;
	 private int col;
	 

    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public SparseBoundedGrid(int rows, int cols)
    {
        if (rows <= 0) {
            throw new IllegalArgumentException("rows <= 0");
        }
        if (cols <= 0) {
            throw new IllegalArgumentException("cols <= 0");
        }
        row = rows;
        col = cols;
        occupantArray = new SparseGridNode[row];
    }

    public int getNumRows()
    {
        return row;
    }

    public int getNumCols()
    {
        // Note: according to the constructor precondition, numRows() > 0, so
        // theGrid[0] is non-null.
        return col;
    }

    public boolean isValid(Location loc)
    {
        return 0 <= loc.getRow() && loc.getRow() < getNumRows()
                && 0 <= loc.getCol() && loc.getCol() < getNumCols();
    }

    public ArrayList<Location> getOccupiedLocations()
    {
        ArrayList<Location> theLocations = new ArrayList<Location>();

        // Look at all grid locations.
        for (int r = 0; r < getNumRows(); r++)
        {
        	SparseGridNode tempNode = occupantArray[r];
            while (tempNode != null)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, tempNode.getCol());
                theLocations.add(loc);
                tempNode = tempNode.getNext();
            }
        }

        return theLocations;
    }

    public E get(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        SparseGridNode tempNode = occupantArray[loc.getRow()];
        while (tempNode != null) {
        	if (tempNode.getCol() == loc.getCol()) {
        		return (E) tempNode.getOccupant();
        	}
        	tempNode = tempNode.getNext();
        }
    
        return null;
    }

    public E put(Location loc, E obj)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        if (obj == null) {
            throw new NullPointerException("obj == null");
        }

        // Add the object to the grid.
        E oldOccupant = remove(loc);
        SparseGridNode tempNode = occupantArray[loc.getRow()];
        occupantArray[loc.getRow()] = new SparseGridNode(obj,
        		loc.getCol(), tempNode);
        return oldOccupant;
    }

    public E remove(Location loc)
    {
        if (!isValid(loc)) {
            throw new IllegalArgumentException("Location " + loc
                    + " is not valid");
        }
        
        // Remove the object from the grid.
        E r = get(loc);
        if (r == null) {
        	return null;
        }
        SparseGridNode tempNode = occupantArray[loc.getRow()];
        if (tempNode != null) {
        	if (tempNode.getCol() == loc.getCol()) {
        		occupantArray[loc.getRow()] = tempNode.getNext();
        	} else {
        		SparseGridNode OtherNode = tempNode.getNext(); 
                while(OtherNode != null && OtherNode.getCol() != loc.getCol()) { 
                	tempNode = tempNode.getNext(); 
                	OtherNode = OtherNode.getNext(); 
                }
                if(OtherNode != null) {
                	tempNode.setNext(tempNode.getNext());
                }
            }
        }

        return r;
    }
}

