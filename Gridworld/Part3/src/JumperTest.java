import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Location;

import java.awt.Color;

public class JumperTest {
	private Jumper testJumper;
	private ActorWorld actorWorld;
	
	public JumperTest() {}
	
	@Before
	public void before() {
		actorWorld = new ActorWorld(new BoundedGrid<Actor>(20, 20));
		testJumper = new Jumper();
		actorWorld.add(new Location(7, 10), new Flower());
		actorWorld.add(new Location(8, 10), new Rock());
		actorWorld.add(new Location(9, 10), testJumper);
	}
	
	@Test
	public void test1() { //Test Color
		assertEquals(testJumper.getColor(), Color.RED);  //Test Default Color
		testJumper.setColor(Color.BLUE);
		assertEquals(testJumper.getColor(), Color.BLUE); //Test Set Color
	}
	
	@Test
	public void test2() { //Test JumpTwo, Jump
		//Test weather JumpTwo and weather can jump over the rock
		assertEquals(testJumper.canJump(), 2);
		testJumper.act();
		//Test Jump after JumpTwo weather the Jump is OK.
		assertEquals(testJumper.getLocation(), new Location(7, 10));
	}
	
	@Test
	public void test3() {  //Test JumpOne
		actorWorld.add(new Location(5, 10), new Rock());
		testJumper.act();
		assertEquals(testJumper.canJump(), 1);
		testJumper.act();
		assertEquals(testJumper.getLocation(), new Location(6, 10));
	}
	
	@Test
	public void test4() {  //Test JumpZero, turn
		//Test JumpZero
		testJumper.act();
		actorWorld.add(new Location(5, 10), new Rock());
		actorWorld.add(new Location(6, 10), new Rock());
		assertEquals(testJumper.canJump(), 0);
		//Test Turn
		int originalDegree = testJumper.getDirection();
		testJumper.act();
		assertEquals(testJumper.getDirection(), originalDegree+45);
	}
	
	@Test
	public void test5() {  //Test act and jump
		//Test act weather can act out of grid
		Jumper testOutJumper = new Jumper();
		actorWorld.add(new Location(1, 10), testOutJumper);
		testOutJumper.act();
		assertEquals(testOutJumper.getLocation(), new Location(0, 10));
		//Test jump weather can jump out of grid
		testOutJumper.jump(1);
		assertEquals(testOutJumper.getLocation(), null);
	}
}
