package controllers;

import java.io.File;
import java.util.Collection;

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
	public void removeUser (@Param(name="id") Long id)
	{
			likeMovies.removeUser(id);
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
	public void addRating (@Param(name="User's fullname (no space between first & last name)") String fullName,
			@Param(name="movie name")  String  movieName, 
			@Param(name="rating") int rating)
	{  
		//method is returning null instead to user
		User theUserObject = likeMovies.getUserByfullName(fullName);
		Movie theMovieObject = likeMovies.getMovieByMovieName(movieName);
		
		Long theIdOfTheUser = theUserObject.getUserId();
		Long theIdOfTheMovie = theMovieObject.getMovieId();
		
		likeMovies.addRating(theIdOfTheUser, theIdOfTheMovie, rating);
	}
}