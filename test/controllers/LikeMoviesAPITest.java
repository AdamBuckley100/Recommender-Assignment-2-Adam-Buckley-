package controllers;

import static org.junit.Assert.*;

import java.util.List;
//import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.LikeMoviesAPI;
import models.Movie;
import models.Rating;
import models.User;
import static models.Fixtures.users;
import static models.Fixtures.movies;
import static models.Fixtures.ratings;

/**
 * This class is an Junit test case class, this class tests the methods in
 * the LikeMoviesAPI class.
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class LikeMoviesAPITest
{
	private LikeMoviesAPI likeMovies;

	@Before
	public void setup()
	{
		User.counter = 0L;
		Movie.counter = 0L;

		likeMovies = new LikeMoviesAPI();
		for (User user : users)
		{
			likeMovies.addUser(user.id, user.firstName, user.lastName, user.age, user.gender, user.occupation);
		}

		for (Movie movie : movies)
		{
			likeMovies.addMovie(movie.id, movie.title, movie.year, movie.url);
		}
	}

	@After
	public void tearDown()
	{
		likeMovies = null;
	}

	@Test
	public void testUser()
	{
		assertEquals (users.length, likeMovies.getUsers().size());
		likeMovies.addUser("homer", "simpson", "39", "M", "driver");
		assertEquals (users.length+1, likeMovies.getUsers().size());
		assertEquals (users[0], likeMovies.getUserByUserId(users[0].id));
	}  

	@Test
	public void testMovie()
	{
		assertEquals (movies.length, likeMovies.getMovies().size());
		likeMovies.addMovie("The day after tomorrow","01-Jan-1994","www.dayaftertomorrow.com");
		assertEquals (movies.length+1, likeMovies.getMovies().size());
		assertEquals (movies[0], likeMovies.getMovieByMovieId(movies[0].id));
	} 

	@Test
	public void testRating()
	{

		for (Rating rating : ratings)
		{
			likeMovies.addRating(rating.userId, rating.movieId, rating.rating);
		}

		assertEquals (ratings.length, likeMovies.getRatings().size());
		likeMovies.addRating(Long.valueOf(2), Long.valueOf(2), -1);
		assertEquals (ratings.length+1, likeMovies.getRatings().size());
		assertEquals (ratings[0], likeMovies.getRatingByUserIdAndMovieId(ratings[0].userId, ratings[0].movieId));
	} 

	@Test
	public void testUsers()
	{
		assertEquals (users.length, likeMovies.getUsers().size());
		for (User user: users)
		{
			User eachUser = likeMovies.getUserByUserId(user.id);
			assertEquals (user, eachUser);
			assertNotSame(user, eachUser);
		}
	}

	@Test
	public void testRemoveUser()
	{
		assertEquals (users.length, likeMovies.getUsers().size());
		User marge = likeMovies.getUserByUserId(Long.valueOf(0));
		likeMovies.removeUser(marge);
		assertEquals (users.length-1, likeMovies.getUsers().size());    
	}  

	@Test
	public void testAddMovie()
	{
		//User marge = likeMovies.getUserByUserId(Long.valueOf(0));
		Movie movie = likeMovies.addMovie(movies[0].title, movies[0].year, movies[0].url);
		Movie returnedMovie = likeMovies.getMovie(movie.id);
		assertEquals(movies[0],  returnedMovie);
		assertNotSame(movies[0], returnedMovie);
	}

	@Test
	public void testAddMovieWithSingleRating()
	{
		User marge = likeMovies.getUserByUserId(Long.valueOf(0));
		Long movieId = likeMovies.addMovie(movies[0].title, movies[0].year, movies[0].url).id;
		
		likeMovies.addRating(marge.getUserId(), movieId, ratings[0].rating);

		Movie movie = likeMovies.getMovie(movieId);
		assertEquals (1, movie.ratings.size());
		assertEquals(-2, ratings[0].rating, movie.ratings.get(0).rating);
	}

	@Test
	public void testAddMovieWithMultipleRating()
	{
		for (Rating rating : ratings)
		{
			likeMovies.addRating(rating.userId, rating.movieId, rating.rating);      
		}

		Movie movie = likeMovies.getMovieByMovieId(Long.valueOf(0));
		assertEquals (2, movie.ratings.size());
	}

	@Test
	public void testGetUserByUserId()
	{
		User user = users[0];
		User returnedUser = likeMovies.getUserByUserId(Long.valueOf(user.id));
		assertEquals(users[0],  returnedUser);
		assertNotSame(movies[0], returnedUser);
	}

	@Test
	public void testGetMovieByMovieName()
	{
		Movie returnedMovie = likeMovies.getMovieByMovieName("Austin Powers");
		assertEquals(movies[1],  returnedMovie);
		assertNotSame(users[1], returnedMovie);
		assertNotNull(returnedMovie);
	}

	@Test
	public void testGetMovieByMovieId()
	{
		Movie movie = movies[0];
		Movie returnedMovie = likeMovies.getMovieByMovieId(Long.valueOf(movie.id));
		assertEquals(movies[0],  returnedMovie);
		assertNotSame(users[0], returnedMovie);
		assertNotNull(returnedMovie);
	}

	@Test
	public void testGetUserByFullName()
	{
		User returnedUser = likeMovies.getUserByFullName("marge simpson");
		assertEquals(users[0],  returnedUser);
		assertNotSame(movies[0], returnedUser);
	}

	@Test
	public void testGetUserByFullNameVersionTwo()
	{
		User user = users[0];
		User returnedUser = likeMovies.getUserByFullName(user.firstName + " " + user.lastName);
		assertEquals(users[0],  returnedUser);
		assertNotSame(users[0], returnedUser);
		assertNotNull(returnedUser);
	}

	@Test
	public void testRemoveMovie()
	{
		assertEquals (movies.length, likeMovies.getMovies().size());
		Movie theMovie = likeMovies.getMovieByMovieId(Long.valueOf(0));
		likeMovies.removeMovie(theMovie);
		assertEquals (movies.length-1, likeMovies.getMovies().size());    
	} 

	@Test
	public void testRemoveRating()
	{
		for (Rating rating : ratings)
		{
			likeMovies.addRating(rating.userId, rating.movieId, rating.rating);      
		}

		assertEquals (ratings.length, likeMovies.getRatings().size());
		Rating rating = ratings[3];
		Rating theRating = likeMovies.getRatingByUserIdAndMovieId(rating.userId, rating.movieId);
		likeMovies.removeRating(theRating);
		assertEquals (ratings.length-1, likeMovies.getRatings().size()); 
	}

	@Test
	public void testGetRatingByUserIdAndMovieId()
	{
		for (Rating rating : ratings)
		{
			likeMovies.addRating(rating.userId, rating.movieId, rating.rating);      
		}

		Rating rating = ratings[4];
		Rating returnedRating = likeMovies.getRatingByUserIdAndMovieId(rating.userId, rating.movieId);
		assertEquals(ratings[4],  returnedRating);
		assertNotSame(users[4], returnedRating);
		assertNotNull(returnedRating);
	}

	@Test
	public void testGetRecommendationsWithoutRatings()
	{
		User theUser = users[2];
		List<Movie> theTopMoviesNotRated = likeMovies.getRecommendations(theUser);

		for (Movie movie: theTopMoviesNotRated)
		{
			for (Rating rating : movie.ratings)
			{
				assertNotEquals(rating.userId, theUser.id);
			}
		}	

		assertEquals(5, theTopMoviesNotRated.size());
	}

	@Test
	public void testGetRecommendationsWithRatings()
	{
		for (Rating rating : ratings)
		{
			likeMovies.addRating(rating.userId, rating.movieId, rating.rating);      
		}

		User theUser = users[2];
		List<Movie> theTopMoviesNotRated = likeMovies.getRecommendations(theUser);

		for (Movie movie: theTopMoviesNotRated)
		{
			for (Rating rating : movie.ratings)
			{
				assertNotEquals(rating.userId, theUser.id);
			}
		}	

		assertEquals(3, theTopMoviesNotRated.size());
	}
}