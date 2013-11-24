package god.oil.v587;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestEntry {
	@Test
	public void testTestv587_1() {
		GodOil godOil = new GodOil();
		assertTrue (godOil.testV587("GodOil"));
	}
	
	@Test
	public void testTestv587_2() {
		GodOil godOil = new GodOil();
		assertTrue (godOil.testV587("Mie"));
	}
}
