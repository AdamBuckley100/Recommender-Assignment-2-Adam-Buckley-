package models;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class MovieTest
{ 
	public List<Rating> ratings = new ArrayList<Rating>();

	Movie test = new Movie (Long.valueOf(1),  "E.T", "1990", "www.ET.com");
	Movie test2 = new Movie (Long.valueOf(2),  "Finding Nemo", "2004", "www.findingnemo.com");

	@Test
	public void testCreate()
	{
		assertEquals (Long.valueOf(1),    test.id);
		assertEquals ("E.T",          test.title);
		assertEquals ("1990",       test.year);
		assertEquals ("www.ET.com",			  test.url); 

		assertEquals (Long.valueOf(2),    test2.id);
		assertEquals ("Finding Nemo",          test2.title);
		assertEquals ("2004",       test2.year);
		assertEquals ("www.findingnemo.com",			  test2.url);
	}

	@Test
	public void testToString()
	{
		assertEquals ("{\n "

				+ "\t id: 1\n"

				+ "\t title: E.T\n"

				+ "\t year: 1990\n"

				+ "\t url: www.ET.com\n"

				+ "\t average rating of the E.T movie: "+ test.getAverageRating() + "\n"

		+ "}\n", test.toString());
	}

	@Test
	public void testAverageRating()
	{
		assertEquals(0, test.ratings.size());

		test.ratings.add(new Rating(Long.valueOf(0), Long.valueOf(1), 4));
		assertEquals(1, test.ratings.size());
		assertEquals(4, 0.000000000001, test.getAverageRating());

		test.ratings.add(new Rating(Long.valueOf(1), Long.valueOf(1), 2));
		assertEquals(3, 0.000000000001, test.getAverageRating());
		assertEquals(2, test.ratings.size());

		test.ratings.add(new Rating(Long.valueOf(2), Long.valueOf(1), 5));
		test.ratings.add(new Rating(Long.valueOf(3), Long.valueOf(1), -1));
		assertEquals(4, test.ratings.size());
		assertEquals(2.5, 0.000000000001, test.getAverageRating());
	}

	@Test
	public void testEquals()
	{
		assertFalse(test.equals(test2));
	}

	@Test
	public void testCompareTo()
	{
		test.ratings.add(new Rating(Long.valueOf(0), Long.valueOf(1), 4));
		test2.ratings.add(new Rating(Long.valueOf(0), Long.valueOf(2), 5));

	   /** the compareTo method returns a -1 if the movie object which the original
		*  movie object is being compared to has a lower average rating than the 
		*  movie object which it is being compared to and vice versa.
		*/

		test.compareTo(test2);

		assertEquals(-1, test.compareTo(test2));
	}
}