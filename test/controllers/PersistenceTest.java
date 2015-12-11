package controllers;
import controllers.LikeMoviesAPI;
import static org.junit.Assert.*;

import java.io.File;

import models.Movie;
import models.Rating;
import models.User;

import org.junit.Test;

import utils.Serializer;
import utils.XMLSerializer;
import static models.Fixtures.users;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;

/**
 * This class is an Junit test case class, this class tests overall persistence functionality.
 * 
 * This test class works with the fixtures.
 * 
 * The following are tested:
 * 
 * 1). if the hash maps are empty before populate. (populate is a method that essentially
 * 	   adds the fixtures to their appropriate hash maps.
 * 
 * 2). If the hash maps are populated appropriately when after the populate method is called.
 * 
 * 3). The serializer was tested to see if it serializes correctly. (the hash maps were
 * 	   tested to see if they were empty prior to serialization too).
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class PersistenceTest
{
	LikeMoviesAPI likeMovies;

	void populate (LikeMoviesAPI likeMovies)
	{
		User.counter = 0L;
		Movie.counter = 0L;

		for (User user : users)
		{
			likeMovies.addUser(user.id, user.firstName, user.lastName, user.age, user.gender, user.occupation);
		}

		for (Movie movie : movies)
		{
			likeMovies.addMovie(movie.id, movie.title, movie.year, movie.url);
		}
		for (Rating rating : ratings)
		{
			likeMovies.addRating(rating.userId,rating.movieId, rating.rating);
		}
	}

	@Test
	public void testEmptyBeforePopulate()
	{ 
		likeMovies = new LikeMoviesAPI(null);
		assertEquals(0, likeMovies.getUsers().size());
		assertEquals(0, likeMovies.getMovies().size());
		assertEquals(0, likeMovies.getRatings().size());
		populate (likeMovies);
	}

	@Test
	public void testPopulate()
	{ 
		likeMovies = new LikeMoviesAPI(null);
		assertEquals(0, likeMovies.getUsers().size());
		assertEquals(0, likeMovies.getMovies().size());
		assertEquals(0, likeMovies.getRatings().size());
		populate (likeMovies);

		assertEquals(5, likeMovies.getUsers().size());
		assertEquals(5, likeMovies.getMovies().size());
		assertEquals(6, likeMovies.getRatings().size()); 
	}

	void deleteFile(String fileName)
	{
		File datastore = new File ("testdatastore.xml");
		if (datastore.exists())
		{
			datastore.delete();
		}
	}

	@Test
	public void testXMLSerializer() throws Exception
	{ 
		String datastoreFile = "testdatastore.xml";
		//deleteFile (datastoreFile);

		Serializer serializer = new XMLSerializer(new File (datastoreFile));

		likeMovies = new LikeMoviesAPI(serializer);
		assertEquals(0, likeMovies.getUsers().size());
		assertEquals(0, likeMovies.getMovies().size());
		assertEquals(0, likeMovies.getRatings().size());

		populate(likeMovies);
		likeMovies.store();

		LikeMoviesAPI likeMoviesAPI2 =  new LikeMoviesAPI(serializer);
		likeMoviesAPI2.load();

		assertEquals (likeMovies.getUsers().size(), likeMoviesAPI2.getUsers().size());
		for (User user : likeMovies.getUsers())
		{
			assertTrue (likeMoviesAPI2.getUsers().contains(user));
		}
		//deleteFile ("testdatastore.xml");
	}
}