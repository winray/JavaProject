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
public class MazeBug2 extends Bug {
	public Location next;
	public Location last;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	boolean hasShown = false;//final message has been shown
	
	/*
	 * add
	 * target:
	 *   judge which direction to go is better!!!
	 * 分离出四个方向:
	 *   0 : North
	 *   1 : East
	 *   2 : South
	 *   3 : West
	 */
	private int []countStep = new int[4];
	
	/*
	 * 深搜无路，返回之前路径，
	 * 用于记录之前节点，把之前加的减回
	 */
	private int backIndex;

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug2() {
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
			// 对一开始往哪走作出预判
			for (int i = 0; i < 4; i++) {
				countStep[i] = 1;
			}
			backIndex = 0;
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
			if (getValid(getLocation()).size() > 1) {
				backIndex = turnIndex(next);
				//往哪走哪里走的方向加一
				countStep[backIndex]++;
			}
			
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
		//此处可提高，只是减掉了一部分而已
		if (getValid(getLocation()).size() > 1) {
			countStep[backIndex]--;
		}

		next = last;
		move();
		crossLocation.pop();
		if (crossLocation.peek().size() != 0) {
			last = crossLocation.peek().get(0);
		}
	}

	//把角度转为下标
	public int turnIndex(Location location) {
		int angle = getLocation().getDirectionToward(location)-getDirection();
		angle = angle >= 0 ? angle : angle+360;
		angle = angle/90;
		return angle;
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
		
		//作用：选择往哪走
		int tempindex = 0;
		int maxStep = 0;

		for (Location location : nextArrayList) {
			Actor actor = grid.get(location);
			if (actor instanceof Rock) {
				//到达目的地后原地不动
				next = getLocation();
				return true;
			}
			
			//分离出方向为下标
			int toward = turnIndex(location);
			
			/*
			 * 统计出可以走的方向
			 * 然后从可选方向中选取被走过最多的方向
			 */
			if (countStep[toward] > countStep[maxStep]) {
				maxStep = tempindex;
			}
			tempindex++;
		}
		/*
		 * 根据最大概率最后选取方向
		 */
		next = nextArrayList.get(maxStep);
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
