package controllers;

import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.CSVLoader;
import utils.Serializer;

import java.util.Collection;

import models.User;
import models.Movie;
import models.Rating;

/**
 * This class is an API (java application program interface) class.
 * This class performs the bulk of the actual actions that the options in the CLI
 * from the main class say they will do (eg. get recommendations).
 * 
 * @author Adam Buckley (Student I.D: 20062910)
 * @version 1
 * @date 10/12/2015
 */

public class LikeMoviesAPI
{
	private Serializer serializer;
	
	/* There are five hash maps below. They are the following:
	 * 1). a user id mapped to User object.
	 * 
	 * 2). a user id and a movie id combined (and separated by a single "," comma) 
	 * mapped to a rating object. (note the two's ids will be in String form, not Long anymore).
	 * 
	 * 3). A movie id mapped to a Movie object.
	 * 
	 * 4). a user's full name (complete with a single space " " character between first and
	 * last name) mapped to a User object.
	 * 
	 * 5). a movie name mapped to a Movie object. (that Movie object's name).
	 */

	public Map<Long, User> userIndex = new HashMap<>();

	/* Note: The hash map directly below: the key is a concatenation of a the user's user I.D and
	 * a Movie's movie I.D, (with a single "," comma character in between).
	 * This is done to ensure the the key is unique.
	 */
	private Map<String, Rating> ratingUserIdPlusMovieIdIndex = new HashMap<>();

	private Map<Long, Movie> movieIndex = new HashMap<>();

	/* Note: The hash map directly below: the key is a concatenation of a the user's first name and last name,
	 * (with a single space in between). This is done to ensure the the key is unique.
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
			userIndex.put(user.id, user);
			fullNameIndex.put(user.firstName + " " + user.lastName, user);
		}

		List <Movie> movies = loader.loadMovies("moviedata_small/items5.dat");
		for (Movie movie : movies)
		{
			movieIndex.put(movie.id, movie);
			movieNameIndex.put(movie.title, movie);
		}

		List <Rating> ratings = loader.loadRatings("moviedata_small/ratings5.dat");
		for (Rating rating : ratings)
		{
			addRating(rating.userId,rating.movieId,rating.rating);
		}
	}

	@SuppressWarnings("unchecked")
	public void load() throws Exception
	{
		serializer.read();

		ratingUserIdPlusMovieIdIndex = (Map<String, Rating>) serializer.pop();
		movieNameIndex  = (Map<String, Movie>) serializer.pop();
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

	public Long getMaxUserId()
	{
		Long maxId = 0L;
		for(Long id : userIndex.keySet())
		{
			if (id > maxId)
			{
				maxId = id;
			}
		}
		return maxId;
	}

	public Long getMaxMovieId()
	{
		Long maxId = 0L;
		for(Long id : movieIndex.keySet())
		{
			if (id > maxId)
			{
				maxId = id;
			}
		}
		return maxId;
	}

	public User addUser(Long id, String firstName, String lastName, String age, String gender, String occupation) 
	{
		User user = new User(id, firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
		fullNameIndex.put(firstName + " " + lastName, user);
		//System.out.println(user.id);
		return user;
	}

	public User addUser(String firstName, String lastName, String age, String gender, String occupation) 
	{
		User user = new User(firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
		fullNameIndex.put(firstName + " " + lastName, user);
		//System.out.println(user.id);
		return user;
	}

	public Movie addMovie(Long id, String title, String year, String url)
	{
		Movie movie = new Movie (id, title, year, url);
		movieIndex.put(movie.id, movie);
		movieNameIndex.put(title, movie);
		return movie;
	}


	public Movie addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
		movieNameIndex.put(title, movie);
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

	public User getUserByFullName(String fullName) 
	{
		return fullNameIndex.get(fullName);
	}

	public void removeUser(User user) 
	{
		userIndex.remove(user.id);
		fullNameIndex.remove(user.firstName + " " + user.lastName);
	}

	public void removeMovie(Movie movie) 
	{
		movieIndex.remove(movie.id);
		movieNameIndex.remove(movie.title);
	}

	public void removeRating(Rating rating)
	{
		ratingUserIdPlusMovieIdIndex.remove(rating.userId.toString() + "," + rating.movieId.toString());

		User theUser = getUserByUserId(rating.userId);
		theUser.ratings.remove(rating);

		Movie theMovie = getMovieByMovieId(rating.movieId);
		theMovie.ratings.remove(rating);
	}

	public Rating getRatingByUserIdAndMovieId(Long userId, Long movieId) 
	{
		//String concatenatedId = userId + "," + movieId;
		//concatId means concatenated Id.
		String concatId = userId + "," + movieId;
		return ratingUserIdPlusMovieIdIndex.get(concatId);
	}

	public Rating addRating(Long userId, Long movieId, int rating)
	{
		Rating theRating = new Rating (userId, movieId, rating);

		//concatenated Id is the user id and the movie id concatenated together (no space involved).

		String concatenatedId = userId.toString() + "," + movieId.toString();

		ratingUserIdPlusMovieIdIndex.put(concatenatedId, theRating);

		User theUser = getUserByUserId(userId);
		Movie theMovie = getMovieByMovieId(movieId);

		theUser.ratings.add(theRating);

		theMovie.ratings.add(theRating);
		return theRating;
	}

	public Movie getMovie(Long id)
	{
		return movieIndex.get(id);
	}

	public List<Movie> getTopTenMovies()
	{
		Collection<Movie> allMovies = getMovies();

		List<Movie> moviesList = new ArrayList<Movie>(allMovies);

		//modified merge sort
		Collections.sort(moviesList);

		Collections.reverse(moviesList);

		List<Movie> subItems = moviesList.subList(0, 10 > moviesList.size() ? moviesList.size() : 10);

		return subItems;
	}

	public List<Movie> getRecommendations(User user)
	{
		//temporary array list of all movies the user has NOT rated
		ArrayList<Movie> moviesRated = new ArrayList<Movie>();
		Long theUserId = user.id;

		for (Rating rating : ratingUserIdPlusMovieIdIndex.values())
		{
			if (rating.userId == theUserId)
			{
				Movie theMovie = getMovieByMovieId(rating.movieId);
				if (!moviesRated.contains(theMovie))
				{
					moviesRated.add(theMovie);
				}
			}
		}

		ArrayList<Movie> moviesNotRated = new ArrayList<Movie>();

		for (Movie movie : movieIndex.values())
		{
			if (!moviesRated.contains(movie))
			{
				moviesNotRated.add(movie);
			}	
		}

		//sort is a modified version of merge sort.
		Collections.sort(moviesNotRated);
		Collections.reverse(moviesNotRated);
		List<Movie> movies = moviesNotRated.subList(0, 6 > moviesNotRated.size() ? moviesNotRated.size() : 6);

		return movies;
	}
}