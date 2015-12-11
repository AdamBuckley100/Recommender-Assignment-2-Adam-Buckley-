package models;

import static org.junit.Assert.*;

import org.junit.Test;

import static models.Fixtures.ratings;

public class RatingTest
{ 
	Rating test = new Rating (Long.valueOf(1), Long.valueOf(3), Integer.valueOf(1));
	Rating test2 = new Rating (Long.valueOf(2), Long.valueOf(4), Integer.valueOf(2));
	Rating test3 = new Rating (Long.valueOf(1), Long.valueOf(3), Integer.valueOf(1));

	@Test
	public void testCreate()
	{  
		assertEquals (Long.valueOf(0), ratings[0].userId);
		assertEquals (Long.valueOf(0), ratings[0].movieId);
		assertEquals (-2, ratings[0].rating);
	}

	@Test
	public void testIds()
	{
		assertNotEquals(ratings[1].userId, ratings[2].userId);
	}

	@Test
	public void testToString()
	{
		assertEquals ("{\n "

			+ "\t rating: " + test.rating + "\n"

			+ "\t movie id: " + test.movieId + "\n"

			+ "\t user id: " + test.userId + "\n"

	+ "}\n", test.toString());
	}

	@Test
	public void testEquals()
	{
		assertFalse(test.equals(test2));
		assertTrue(test.equals(test3));
	}
}