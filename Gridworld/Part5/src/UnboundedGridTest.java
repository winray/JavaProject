import static org.junit.Assert.*;
import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.grid.Location;

import org.junit.Before;
import org.junit.Test;


public class UnboundedGridTest {
	UnboundedGrid2<Actor> world;
	Bug bug;

	@Before
	public void init() {
		world = new UnboundedGrid2<Actor>();
		bug = new Bug();
		world.put(new Location(15, 15), bug);
		bug.setDirection(Location.EAST);
	}
	
	// Test weather can run out the grid
	@Test
	public void test1() {
		bug.act();
		assertEquals(world.get(new Location(15, 15)), bug);
	}
	
	// Test weather the bug can act truly in the grid
	@Test
	public void test2() {
		bug.setDirection(Location.WEST);
		bug.act();
		assertEquals(world.get(new Location(15, 15)), bug);
	}
	
	
	/* 
	 * Test weather the grid can large twice if put the
	 * new bug out of th gird.
	 */
	@Test
	public void test3() {
		world.put(new Location(16, 16), new Bug());
		bug.act();
		assertEquals(world.get(new Location(15, 15)), bug);
	}
}
