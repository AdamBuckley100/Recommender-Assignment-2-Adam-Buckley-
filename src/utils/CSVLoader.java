package utils;

import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.In;
import models.Movie;
import models.Rating;
import models.User;

/** This class is The CSVLoader class. This class parses in the CSV text format information
 * into the method's temporary Array List, separating each and every different section
 * (eg. age would be one section) with a "|". Each section is a separate field or 
 * member variable in the actual class (eg. User class has fields firstName, lastName
 * and so on to occupation)
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class CSVLoader
{
	public List<User> loadUsers(String filename) throws Exception
	{
		In inUsers = new In(filename);

		String delims = "[|]";
		List<User> users = new ArrayList<User>();
		while (!inUsers.isEmpty())
		{
			String userDetails = inUsers.readLine();
			String[] ratingTokens = userDetails.split(delims);
			if (ratingTokens.length == 7)
			{
				// Not parsing in the last section (seventh section).
				Long id = Long.valueOf(ratingTokens[0]);
				String firstName = ratingTokens[1];
				String lastName = ratingTokens[2];
				String age = ratingTokens[3];
				String gender = ratingTokens[4];
				String occupation = ratingTokens[5];

				users.add(new User(id, firstName, lastName, age, gender, occupation));
			}
			else
			{
				throw new Exception("Invalid member length: " + ratingTokens.length);
			}
		}
		return users;
	}

	public List<Rating> loadRatings(String filename) throws Exception
	{
		In inRatings = new In(filename);

		String delims = "[|]";
		List<Rating> ratings = new ArrayList<Rating>();
		while (!inRatings.isEmpty())
		{
			String ratingDetails = inRatings.readLine();
			String[] ratingTokens = ratingDetails.split(delims);
			if (ratingTokens.length == 4)
			{
				// Not parsing in the fourth section (the time-stamp).
				Long userId = Long.valueOf(ratingTokens[0]);
				Long movieId = Long.valueOf(ratingTokens[1]);
				int rating = Integer.valueOf(ratingTokens[2]);

				ratings.add(new Rating(userId, movieId, rating));
			}
			else
			{
				throw new Exception("Invalid member length: " + ratingTokens.length);
			}
		}
		return ratings;
	}

	public List<Movie> loadMovies (String filename) throws Exception
	{
		In inMovies = new In(filename);

		String delims = "[|]";
		List<Movie> movies = new ArrayList<Movie>();
		while (!inMovies.isEmpty())
		{
			String movieDetails = inMovies.readLine();
			String[] movieTokens = movieDetails.split(delims);
			// There's 23 different sections, but I'm only parsing in the first four. (below)
			if (movieTokens.length == 23)
			{
				Long movieId = Long.valueOf(movieTokens[0]);
				String movieName = movieTokens[1];
				String dateOfRelease = movieTokens[2];
				String webLink = movieTokens[3];

				movies.add(new Movie(movieId, movieName, dateOfRelease, webLink));
			}
			else
			{
				throw new Exception("Invalid member length: " + movieTokens.length);
			}
		}
		return movies;
	}
}