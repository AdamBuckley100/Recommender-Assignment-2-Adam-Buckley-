package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import models.User;
import utils.CSVLoader;
import utils.FileLogger;
import utils.Serializer;
import utils.FileLogger;
import utils.ToJsonString;
import models.User;
import models.Movie;
import models.Rating;

import com.google.common.base.Optional;

import utils.Serializer;
import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Movie;
import models.User;
import models.Rating;
import models.Movie;
import controllers.Main;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Optional;

import utils.Serializer;
import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Movie;
import models.User;

public class Main
{
	public LikeMoviesAPI likeMovies;
	
	@Command(description="prime")
	public void prime () throws Exception
	{
		likeMovies.prime();
	}

	public Main() throws Exception
	{
		File datastore = new File("datastore.xml");
		Serializer serializer = new XMLSerializer(datastore);

		likeMovies = new LikeMoviesAPI(serializer);
		if (datastore.isFile())
		{
			likeMovies.load();
			
			likeMovies.getMaxUserId();
			User.counter = (likeMovies.getMaxUserId())+1;
			
			likeMovies.getMaxMovieId();
			Movie.counter = (likeMovies.getMaxMovieId())+1;
		}
		else
		{
			likeMovies.prime();
		}
	}

	public static void main(String[] args) throws Exception
	{
		
		Main main = new Main();

		Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to pacemaker-console - ?help for instructions", main);
		shell.commandLoop();

		main.likeMovies.store();
	}

	@Command(description="Get all users details")
	public void getUsers ()
	{
		Collection<User> users = likeMovies.getUsers();
		System.out.println(users);
	}
	
	@Command(description="Get all movies details")
	public void getMovies ()
	{
		Collection<Movie> movies = likeMovies.getMovies();
		System.out.println(movies);
	}
	
	@Command(description="Get user by full name")
	public void getUserByFullName (@Param(name="full name") String fullName)
	{
		User theUser = likeMovies.getUserByFullName(fullName);
		System.out.println(theUser);
	}
	
	@Command(description="Get movie by movie name")
	public void getMovieByMovieName (@Param(name="movie name") String movieName)
	{
		Movie theMovie = likeMovies.getMovieByMovieName(movieName);
		System.out.println(theMovie);
	}
	
	@Command(description="Get all ratings details")
	public void getRatings ()
	{
		Collection<Rating> ratings = likeMovies.getRatings();
		System.out.println(ratings);
	}

	@Command(description="Add a new User")
	public void addUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName, 
			@Param(name="age") String age,     @Param(name="gender")  String gender,	@Param(name="occupation")  String occupation)
	{
		likeMovies.addUser(firstName, lastName, age, gender, occupation);
	}

	@Command(description="Get a Users detail")
	public void getUser (@Param(name="User Id") Long userId)
	{
		User user = likeMovies.getUserByUserId(userId);
		System.out.println(user);
	}

	@Command(description="Delete a User")
	public void removeUser (@Param(name="full Name") String fullName)
	{
		User theUser = likeMovies.getUserByFullName(fullName);
		likeMovies.removeUser(theUser);
	}
	
	@Command(description="Delete a Movie")
	public void removeMovie (@Param(name="Movie name") String movieName)
	{
		Movie theMovie = likeMovies.getMovieByMovieName(movieName);
		likeMovies.removeMovie(theMovie);
	}
	
	@Command(description="Get top ten movies")
	public void getTopTenMovies()
	{
		likeMovies.getTopTenMovies();
		
		System.out.println(likeMovies.getTopTenMovies());
	}
	
	@Command(description="Get recommendations")
	public void getRecommendation (@Param(name="User's Full Name") String fullName)
	{
		User theUser = likeMovies.getUserByFullName(fullName);
		List<Movie> theMovies = likeMovies.getRecommendations(theUser);
		
		for (int i = 0; i < theMovies.size(); i++)
		{
			System.out.println("movie: " + theMovies.get(i).getMovieTitle());
			//Movie movie = theMovies.get(i);
		}
	}
	
	@Command(description="Delete a Rating")
	public void removeRating (@Param(name="User fullname") String fullName,
			@Param(name="Movie Name") String movieName)
	{
		//User theUser = likeMovies.getUserByFullName(fullName);
		//Movie theMovie = likeMovies.getMovieByMovieName(movieName);
		
		User user = likeMovies.getUserByFullName(fullName);
		Movie movie = likeMovies.getMovieByMovieName(movieName);
		//System.out.println(user);
		//System.out.println(movie);
		
		Rating theRating = likeMovies.getRatingByUserIdAndMovieId(user.id, movie.id);
		likeMovies.removeRating(theRating);
	}

	@Command(description="Add a movie")
	public void addMovie (@Param(name="title") String title, @Param(name="year") String year,
			@Param(name="url") String url)
	{
		likeMovies.addMovie(title, year, url);
	}
	
	 /* @Command(description="Add Rating to an Movie")
	  public void addRating (@Param(name="User-id")  Long  userId,   
	                           @Param(name="movie-id") Long movieId, @Param(name="rating") int rating)
	  {
	    Optional<Movie> movie = Optional.fromNullable(likeMovies.getMovie(movieId));
	    if (movie.isPresent())
	    {
	      likeMovies.addRating(userId, movieId, rating);
	    }
	  } */

	@Command(description="Add a rating to a movie")
	public void addRating (@Param(name="User's fullname") String fullName,
			@Param(name="movie name")  String  movieName, 
			@Param(name="rating") int rating)
	{  
		User user = likeMovies.getUserByFullName(fullName);
		Movie movie = likeMovies.getMovieByMovieName(movieName);
		
		Long theIdOfTheUser = user.getUserId();
		Long theIdOfTheMovie = movie.getMovieId();
		
		//Rating theRating = likeMovies.getRatingByUserIdAndMovieId(user.id, movie.id);
		likeMovies.addRating(theIdOfTheUser, theIdOfTheMovie, rating);
	}
	
	@Command(description="Get top ten movies")
	public void getTopTopMovies()
	{
		likeMovies.getTopTenMovies();
	}
}