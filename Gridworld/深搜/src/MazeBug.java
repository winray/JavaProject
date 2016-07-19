import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */
public class MazeBug extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown
	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		last = new Location(0, 0);
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		if (stepCount == 0) {
			ArrayList<Location> neighbors = getValid(getLocation());
			neighbors.add(getLocation());
			crossLocation.push(neighbors);
		}
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			ArrayList<Location> tempArrayList = new ArrayList<Location>();
			tempArrayList.add(getLocation());
			crossLocation.push(tempArrayList);
			last = getLocation();
			move();
			//increase step count when move 
			stepCount++;
		} else {
			Back();
			stepCount++;
		}
	}
	
	//用于返回分方向时的路
	public void Back() {
		next = last;
		move();
		crossLocation.pop();
		if (crossLocation.peek().size() != 0) {
			last = crossLocation.peek().get(0);
		}
	}
	
	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();
		/*
		 * add
		 * target: 
		 *    To find its four direction(N,E,S,W) where there is null if
		 *  it's the rock and if the rock is red also can move and it is
		 *  the end!
		 */
		for (int i = 0; i < 4; i++) {
			Location tempLocation = loc.getAdjacentLocation(getDirection() + i*90);
			if (gr.isValid(tempLocation)) {
				Actor tempActor = gr.get(tempLocation);
				//红色石头则成功走出迷宫
				if (tempActor instanceof Rock) {
					if (tempActor.getColor().equals(Color.RED)) {
						valid.add(tempLocation);
						isEnd = true;
						break;
					}
				}
				/*
				 * 不加花朵即可不用记录以前走过的路径
				 * 那么只要记录分叉即可实现回走
				 */
				else if (tempActor == null) {
					valid.add(tempLocation);
				}
			}
		}

		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		ArrayList<Location> nextArrayList = getValid(getLocation());
		Grid<Actor> grid = getGrid();
		if (grid == null || nextArrayList.isEmpty()) {
			return false;
		}
		
		for (Location location : nextArrayList) {
			Actor actor = grid.get(location);
			if (actor instanceof Rock) {
				//到达目的地后原地不动
				next = getLocation();
				return true;
			}
		}
		int select = (int)(Math.random()*nextArrayList.size());
		next = nextArrayList.get(select);
		return true;
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		Location loc = getLocation();
		if (gr.isValid(next)) {
			setDirection(getLocation().getDirectionToward(next));
			moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);
	}
}
