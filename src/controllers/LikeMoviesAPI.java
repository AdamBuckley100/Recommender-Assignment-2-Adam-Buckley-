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
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class LikeMoviesAPI
{
	private Serializer serializer;
	
	/** There are five hash maps below. They are the following:
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

	/** Note: The hash map directly below: the key is a concatenation of a the user's user I.D and
	 * a Movie's movie I.D, (with a single "," comma character in between).
	 * This is done to ensure the the key is unique.
	 */
	private Map<String, Rating> ratingUserIdPlusMovieIdIndex = new HashMap<>();

	private Map<Long, Movie> movieIndex = new HashMap<>();

	/** Note: The hash map directly below: the key is a concatenation of a the user's first name and last name,
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

	/**
	 * The prime method is a method that takes the parsed in data from the CSVLoader
	 * class and populates the hash maps that have that object type as a value (right
	 * side of the hashmap, not key). This is done for user, movie and rating parsed in
	 * objects.
	 */
	public void prime() throws Exception
	{
		CSVLoader loader = new CSVLoader();

		/**
		 * Please note: in the following method, simply change what is in the
		 * quotes to "data_movieLens/users.dat" to parse in the large data set instead,
		 * and similar changes for the two methods below. 
		 */
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

	/**
	 * This method finds the largest number user I.D of all the users
	 * that are currently in the userIndex hashmap and returns it.
	 * 
	 * @return Long - largest number user id found of users in the userIndex hashmap.
	 */
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

	/**
	 * This method finds the largest number movie I.D of all the movies
	 * that are currently in the movieIndex hashmap and returns it.
	 * 
	 * @return Long - largest number movie id found of movies in the userIndex hashmap.
	 */
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
		return user;
	}

	public User addUser(String firstName, String lastName, String age, String gender, String occupation) 
	{
		User user = new User(firstName, lastName, age, gender, occupation);
		userIndex.put(user.id, user);
		fullNameIndex.put(firstName + " " + lastName, user);
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
		// concatId means concatenated Id. userId and MovieId and concatenated together
		// but a "," comma goes in between them
		String concatId = userId + "," + movieId;
		return ratingUserIdPlusMovieIdIndex.get(concatId);
	}

	public Rating addRating(Long userId, Long movieId, int rating)
	{
		Rating theRating = new Rating (userId, movieId, rating);

		// concatenatedId is the user id and the movie id concatenated together,
		// with a "," comma in between them.
		
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

		// the sort which is implemented below is a modified merge sort.
		Collections.sort(moviesList);

		Collections.reverse(moviesList);

		// a sublist List called subItems is created where it will be populated with 10 movies
		// (0 is inclusive but 10 is exclusive - so it's movies 0 to 9 (inclusive) from the
		// moviesList array List).
		List<Movie> subItems = moviesList.subList(0, 10 > moviesList.size() ? moviesList.size() : 10);

		return subItems;
	}

	public List<Movie> getRecommendations(User user)
	{
		//temporary array list of all movies the user has rated.
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
		
		//the moviesNotRated array list is reversed (starting with highest rated movie first).
		Collections.reverse(moviesNotRated);
		
		// a sublist List called movies is created where it will be populated with 5 movies
		// (0 is inclusive but 5 is exclusive - so it's movies 0, 1, 2 ,3 and 4 from the
		// moviesNotRated array List).
		List<Movie> movies = moviesNotRated.subList(0, 5 > moviesNotRated.size() ? moviesNotRated.size() : 5);

		return movies;
	}
}