package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
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

	public Map<Long, User> userIndex = new HashMap<>();

	/* Note: The hash map directly below: the key is a concatenation of a the user's user I.D and
	 * a Movie's movie I.D, (no spaces involved). This is done to ensure the the key is unique.
	 */
	private Map<String, Rating> ratingUserIdPlusMovieIdIndex = new HashMap<>();

	//created 02/12: 
	//private Map<String, Rating> movieIdToRatingIndex = new HashMap<>();

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

		List <User> users = loader.loadUsers("data_movieLens/users.dat");
		for (User user : users)
		{
			//userIndex.put(user.id,user);
			//addUser(user.id, user.firstName, user.lastName, user.age, user.gender, user.occupation);
			userIndex.put(user.id, user);
			fullNameIndex.put(user.firstName + " " + user.lastName, user);
		}

		List <Movie> movies = loader.loadMovies("data_movieLens/items.dat");
		for (Movie movie : movies)
		{
			//movieIndex.put(movie.id,movie);
			//addMovie(movie.title, movie.year, movie.url);
			movieIndex.put(movie.id, movie);
			movieNameIndex.put(movie.title, movie);
		}

		List <Rating> ratings = loader.loadRatings("data_movieLens/ratings.dat");
		//System.out.println(ratings.size());
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
		//System.out.println(movie.id);
		return movie;
	}


	public Movie addMovie(String title, String year, String url)
	{
		Movie movie = new Movie (title, year, url);
		movieIndex.put(movie.id, movie);
		movieNameIndex.put(title, movie);
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

	public User getUserByFullName(String fullName) 
	{
		//System.out.println(fullNameIndex.keySet());
		return fullNameIndex.get(fullName);
	}

	public void removeUser(User user) 
	{
		// below was previously userIndex.remove(userId);
		userIndex.remove(user.id);
		fullNameIndex.remove(user.firstName + " " + user.lastName);
		// above was previously: User user = userIndex.remove(userId);
		//fullNameIndex.remove(user.fullName);
	}

	public void removeMovie(Movie movie) 
	{
		// below was previously userIndex.remove(userId);
		movieIndex.remove(movie.id);
		movieNameIndex.remove(movie.title);
		// above was previously: User user = userIndex.remove(userId);
		//fullNameIndex.remove(user.fullName);
	}

	public void removeRating(Rating rating)
	{
		//Rating theRating = getRatingByUserIdAndMovieId(userId + "," + movieId);
		//ratingUserIdPlusMovieIdIndex.remove(theRating.userId + "," + theRating.movieId);
		ratingUserIdPlusMovieIdIndex.remove(rating.userId.toString() + "," + rating.movieId.toString());

		User theUser = getUserByUserId(rating.userId);
		theUser.ratings.remove(rating);

		Movie theMovie = getMovieByMovieId(rating.movieId);
		theMovie.ratings.remove(rating);

		//added 2/12 removing the rating from the movie id to rating object hashmap too!
	}

	public User getUser(Long id) 
	{
		return userIndex.get(id);
	}

	public Rating getRatingByUserIdAndMovieId(Long userId, Long movieId) 
	{
		//String concatenatedId = userId + "," + movieId;
		String concatId = userId + "," + movieId;
		return ratingUserIdPlusMovieIdIndex.get(concatId);
	}

	public Rating addRating(Long userId, Long movieId, int rating)
	{
		Rating theRating = new Rating (userId, movieId, rating);

		//concatenated Id is the user id and the movie id concatenated together (no space involved).

		String concatenatedId = userId.toString() + "," + movieId.toString();
		//System.out.println(concatenatedId);
		ratingUserIdPlusMovieIdIndex.put(concatenatedId, theRating);

		//added 2/12: putting to the hashmap where a movie id is mapped to a rating object.
		String movieIdInString = movieId.toString();
		//movieIdToRatingIndex.put(movieIdInString, theRating);

		//System.out.println(userId);
		User theUser = getUserByUserId(userId);
		Movie theMovie = getMovieByMovieId(movieId);
		//System.out.println("User start: " + theUser + "User End");
		//System.out.println("Rating start: " + theRating + "rating end");
		theUser.ratings.add(theRating);
		//System.out.println(theUser.ratings);
		//System.out.println(theMovie);
		//System.out.println(theMovie.ratings);
		//System.out.println("Movie start: " + theMovie + " movie end.");
		//System.out.println("rating startt: " + theRating + "rating end.");
		theMovie.ratings.add(theRating);
		return theRating;
	}

	public Movie getMovie(Long id)
	{
		return movieIndex.get(id);
	}

	public List<Movie> getTopTenMovies()
	{
		//List<Movie> theTopTenMovies = (List<Movie>) movieNameIndex.values();
		//List<Movie> allMovies = (List<Movie>) movieNameIndex.values();
		//private Map<String, Rating> ratingUserIdPlusMovieIdIndex = new HashMap<>();
		//List<Rating> allRating = (List<Rating>) movieIdToRatingIndex.values();

		Collection<Movie> allMovies = getMovies();

		List<Movie> moviesList = new ArrayList<Movie>(allMovies);

		//modified merge sort
		Collections.sort(moviesList);

		Collections.reverse(moviesList);

		List<Movie> subItems = moviesList.subList(0,10);

		return subItems;
	}

	public List<Movie> getRecommendations(User user)
	{
		//User theUser = getUserByFullName(fullName);
		//Long theUserId = theUser.id;
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

		//now sort by ratings (just in case its not already sorted?).
		//sort is a modified version of merge sort.
		Collections.sort(moviesNotRated);
		Collections.reverse(moviesNotRated);
		List<Movie> movies = moviesNotRated.subList(0, 6 > moviesNotRated.size() ? moviesNotRated.size() : 6);

		return movies;
		//List<Movie> theMoviesRated = new ArrayList<Movie>();

		//for (Movie movie : theMoviesRated)
		//{
		//	theMoviesRated.add(movie);
		//}

		//for (int i = 0; i < movies.size(); i++)
		//{
		//	System.out.println("movie: " + movies.get(i).getMovieTitle());
		//Movie movie = movies.get(i);
		//}
	}
}