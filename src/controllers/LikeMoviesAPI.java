package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.CSVLoader;
import utils.FileLogger;
import utils.Serializer;
import utils.FileLogger;
import utils.ToJsonString;

import java.util.Collection;

import com.google.common.base.Optional;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import edu.princeton.cs.introcs.In;
import models.User;
import models.Movie;
import models.Rating;

public class LikeMoviesAPI
{

	private Serializer serializer;
	
	private Map<Long, User> userIndex = new HashMap<>();
	
    /* Note: The hash map directly below: the key is a concatenation of a the user's user I.D and
	 * a Movie's movie I.D, (no spaces involved). This is done to ensure the the key is unique.
	 */
	private Map<String, Rating> ratingUserIdPlusMovieIdIndex = new HashMap<>();
	
	private Map<Long, Movie> movieIndex = new HashMap<>();
	
   /* Note: The hash map directly below: the key is a concatenation of a the user's first name and last name,
	* (no spaces involved). This is done to ensure the the key is unique.
	*/
	private Map<String, User> fullNameIndex = new HashMap<>();

	private Map<String, Movie> movieNameIndex = new HashMap<>();
	
	
	public LikeMoviesAPI()
	{}

	public LikeMoviesAPI(Serializer serializer)
	{
		this.serializer = serializer;
	}

	public void prime() throws Exception
	{
		CSVLoader loader = new CSVLoader();
		
		List <User> users = loader.loadUsers("moviedata_small/users5.dat");
		for (User user : users)
		{
			userIndex.put(user.id,user);
		}

		List <Movie> movies = loader.loadMovies("moviedata_small/items5.dat");
		for (Movie movie : movies)
		{
			movieIndex.put(movie.id,movie);
		}
		
		List <Rating> ratings = loader.loadRatings("moviedata_small/ratings5.dat");
		System.out.println(ratings.size());
		for (Rating rating : ratings)
		{
			//ratingUserIdPlusMovieIdIndex.put(rating.userId+rating.movieId,rating);
			addRating(rating.userId,rating.movieId,rating.rating);
		}
	}

	@SuppressWarnings("unchecked")
	public void load() throws Exception
	{
		serializer.read();
		
		movieNameIndex  = (Map<String, Movie>) serializer.pop();
		ratingUserIdPlusMovieIdIndex = (Map<String, Rating>) serializer.pop();
		movieIndex = (Map<Long, Movie>) serializer.pop();
		fullNameIndex      = (Map<String, User>)   serializer.pop();
		userIndex = (Map<Long, User>) serializer.pop();
	}

	public void store() throws Exception
	{
		serializer.push(userIndex);
		serializer.push(fullNameIndex);
		serializer.push(movieIndex);
		serializer.push(movieNameIndex);
		serializer.push(ratingUserIdPlusMovieIdIndex);
		serializer.write(); 
	}

	public Collection<Movie> getMovies()
	{
		return movieIndex.values();
	}

	public Collection<Rating> getRatings()
	{
		return ratingUserIdPlusMovieIdIndex.values();
	}

	public Collection<User> getUsers()
	{
		return userIndex.values();
	}

	public  void deleteUsers() 
	{
		userIndex.clear();
		fullNameIndex.clear();
	}

	public User addUser(String firstName, String surname, String age, String gender, String occupation) 
	{
		User user = new User(firstName, surname, age, gender, occupation);
		userIndex.put(user.id, user);
		fullNameIndex.put(firstName + surname, user);
		//System.out.println(user.id);
		return user;
	}
	
	public Movie addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
		//movieNameIndex.put(name, movie);
		//System.out.println(movie.id);
		return movie;
	}

	public User getUserByUserId(Long userId) 
	{
		return userIndex.get(userId);
	}

	public Movie getMovieByMovieName(String movieName) 
	{
		return movieNameIndex.get(movieName);
	}
	
	public Movie getMovieByMovieId(Long movieId) 
	{
		return movieIndex.get(movieId);
	}

	public User getUserByfullName(String fullName) 
	{
		return fullNameIndex.get(fullName);
	}

	public void removeUser(Long id) 
	{
		// below was previously userIndex.remove(userId);
		userIndex.remove(id);
		// above was previously: User user = userIndex.remove(userId);
		//fullNameIndex.remove(user.fullName);
	}

	public User getUser(Long id) 
	{
		return userIndex.get(id);
	}

	public Rating addRating(Long userId, Long movieId, int rating)
	{
		Rating theRating = new Rating (userId, movieId, rating);
		
		//concatenated Id is the user id and the movie id concatenated together (no space involved).
		
		String concatenatedId = userId.toString() + "," + movieId.toString();
		System.out.println(concatenatedId);
		ratingUserIdPlusMovieIdIndex.put(concatenatedId, theRating);
		User theUser = getUserByUserId(userId);
		//**CHECK theUser.ratings.add(theRating);
		return theRating;
	}

	public Movie getMovie(Long id)
	{
		return movieIndex.get(id);
	}
}