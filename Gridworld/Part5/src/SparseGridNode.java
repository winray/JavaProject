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

/**
 * A <code>BoundedGrid</code> is a rectangular grid with a finite number of
 * rows and columns. <br />
 * The implementation of this class is testable on the AP CS AB exam.
 */
public class SparseGridNode
{
	private Object occupant;
    private int col;
    private SparseGridNode next;

    /**
     * Constructs an empty bounded grid with the given dimensions.
     * (Precondition: <code>rows > 0</code> and <code>cols > 0</code>.)
     * @param rows number of rows in BoundedGrid
     * @param cols number of columns in BoundedGrid
     */
    public SparseGridNode(Object occupants, int cols, SparseGridNode nexts)
    {
        occupant = occupants;
        col = cols;
        next = nexts;
    }
    
    public Object getOccupant() {
    	return occupant;
    }

    public int getCol()
    {
        return col;
    }

    public SparseGridNode getNext()
    {
        return next;
    }

    public void setOccupant(Object occupants) {
    	occupant = occupants;
    }
    
    public void setCol(int cols) {
		col = cols;
	}
    
    public void setNext(SparseGridNode nexts) {
		next = nexts;
	}
}
