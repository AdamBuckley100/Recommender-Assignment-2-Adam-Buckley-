package controllers;

import java.io.File;
import java.util.Collection;
import java.util.List;

import models.User;
import utils.Serializer;
import models.Movie;
import models.Rating;

import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import controllers.Main;

/**
 * 
 * This class provides the user with a command line interface
 * (using Cliche Command-Line Shell). This is the class that allows the
 * user to choose (through the CLI) among several options that act on
 * the hash maps that exist in the LikeMoviesAPI class and return to the user
 * different lists depending on what the user wants (eg. get recommendations
 * returns to the user a printed out List of the top 5 movies that they would
 * most likely want to see.
 *
 * @author Adam Buckley (Student I.D: 20062910)
 * @version 1
 * @date 10/12/2015
 */

public class Main
{
	public LikeMoviesAPI likeMovies;

	public Main() throws Exception
	{
		File datastore = new File("datastore.xml");
		Serializer serializer = new XMLSerializer(datastore);

		likeMovies = new LikeMoviesAPI(serializer);
		if (datastore.isFile())
		{
			likeMovies.load();

			User.counter = (likeMovies.getMaxUserId())+1;

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

		Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to Recommender-console - ?help for instructions", main);
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

	@Command(description="Get recommendations")
	public void getRecommendation (@Param(name="User's Full Name") String fullName)
	{
		User theUser = likeMovies.getUserByFullName(fullName);
		List<Movie> theMovies = likeMovies.getRecommendations(theUser);

		for (int i = 0; i < theMovies.size(); i++)
		{
			System.out.println("movie: " + theMovies.get(i).getMovieTitle());
		}
	}

	@Command(description="Delete a Rating")
	public void removeRating (@Param(name="User fullname") String fullName,
			@Param(name="Movie Name") String movieName)
	{

		User user = likeMovies.getUserByFullName(fullName);
		Movie movie = likeMovies.getMovieByMovieName(movieName);

		Rating theRating = likeMovies.getRatingByUserIdAndMovieId(user.id, movie.id);
		likeMovies.removeRating(theRating);
	}

	@Command(description="Add a movie")
	public void addMovie (@Param(name="title") String title, @Param(name="year") String year,
			@Param(name="url") String url)
	{
		likeMovies.addMovie(title, year, url);
	}

	@Command(description="Add a rating to a movie")
	public void addRating (@Param(name="User's fullname") String fullName,
			@Param(name="movie name")  String  movieName, 
			@Param(name="rating") int rating)
	{  
		User user = likeMovies.getUserByFullName(fullName);
		Movie movie = likeMovies.getMovieByMovieName(movieName);

		Long theIdOfTheUser = user.getUserId();
		Long theIdOfTheMovie = movie.getMovieId();

		likeMovies.addRating(theIdOfTheUser, theIdOfTheMovie, rating);
	}

	@Command(description="Get top ten movies")
	public void getTopTenMovies()
	{
		likeMovies.getTopTenMovies();
		
		System.out.println(likeMovies.getTopTenMovies());
	}
}