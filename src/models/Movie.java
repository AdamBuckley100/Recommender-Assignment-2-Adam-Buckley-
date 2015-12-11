package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

/**
 * This Movie class is a class that is representative of what a movie object will
 * possess upon creation (ie. the movie object of type Movie will have the
 * fields id, title, year and url.
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class Movie implements Comparable<Movie>
{ 
	public static Long   counter = 0l;

	public Long   id;

	public String title;
	public String year;
	public String url;

	public List<Rating> ratings = new ArrayList<Rating>();

	/** this constructor is one of the two overloaded constructors in the Movie class,
	 *  this overloaded constructor method is used exclusively when a movie is being
	 *  added at the cliche shell command line, by the user of the CLI.
	 */
	public Movie(String title, String year, String url)
	{
		this(counter++, title, year, url);
	}

	/** this constructor is one of the two overloaded constructors in the Movie class,
	 *  this overloaded constructor method is used exclusively when a movie is being
	 *  parsed in from the CVS text files (ie. the .dat files: items.dat or items5.dat)
	 */
	public Movie(Long id, String title, String year, String url)
	{
		this.id = id;
		if (id >= counter) {
			counter = id + 1;
		}
		this.title = title;
		this.year = year;
		this.url = url;
	}

	public Long getMovieId()
	{
		return id;
	}

	public String getMovieTitle()
	{
		return title;
	}

	/**
	 * This method gets the average rating of the Movie instantiated movie object
	 * in question. This is gotten by going through the array list of ratings that
	 * every Movie object has and and adding each rating to a running total and keeping
	 * a count of how many ratings were left, finally running total is divded by the count
	 * to get the average rating of the movie.
	 * 
	 * @return double - the double value that is the average rating (is double because the
	 * average could possibly not be a whole number exclusively.
	 */
	public double getAverageRating()
	{
		double runningTotal = 0;
		int count = 0;

		for (Rating rating : ratings)
		{
			runningTotal += rating.rating;
			count++;
		}
		if (count != 0)
		{
			return runningTotal/count;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * This method just returns a String representation of an object, namely
	 * the movie object of type Movie in this case
	 */
	@Override
	public String toString()
	{
		return "{\n "

				+ "\t id: " + id + "\n"

				+ "\t title: " + title + "\n"

				+ "\t year: " + year + "\n"

				+ "\t url: " + url + "\n"

				+ "\t average rating of the " + title + " movie: "+ getAverageRating() + "\n"

		+ "}\n";
	}

	/**
	 * this method provides the hash code of an object - namely a Movie object in this case.
	 */
	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.id, this.title, this.year, this.url);  
	} 

	/**
	 * The equals() method compares two objects for equality and returns true
	 * if they are equal.
	 * 
	 * @return - true or false (boolean).
	 * 
	 */
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof Movie)
		{
			final Movie other = (Movie) obj;
			return Objects.equal(title, other.title) 
					&& Objects.equal(year,  other.year)
					&& Objects.equal(url,  other.url); 
		}
		else
		{
			return false;
		}
	}

	/** this method implements the Comparable interface. This specific movie object which will
	 * be an instance of this class will be compared to a movie object that will be passed into
	 * this method as an argument. An Integer value of 0 will be returned if the two
	 * movie object's rating values are the same, and a 1 will be returned if "this" movie object
	 * has a rating greater than the movie object passed in as an argument and vice versa
	 * gives a -1.
	 * 
	 * @return int - 0, 1 or -1 depend on circumstance (see directly above).
	 */
	@Override
	public int compareTo(Movie theOtherMovie)
	{
		return Double.compare(this.getAverageRating(), theOtherMovie.getAverageRating());
	}
}