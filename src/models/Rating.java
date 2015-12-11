package models;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * This Rating class is a class that is representative of what a rating object will
 * possess upon creation (ie. the rating object of type Rating will have the
 * fields userId, movieId and rating. The rating field is the rating itself: a number
 * between -5 and 5 (all inclusive).
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class Rating
{
	public Long userId;
	public Long movieId;
	public int rating;

	public List<Movie> movie = new ArrayList<Movie>();

	public Rating (Long userId, Long movieId, int rating)
	{
		this.userId = userId;
		this.movieId  = movieId;
		this.rating  = rating;
	}

	/**
	 * This method just returns a String representation of an object, namely
	 * the movie object of type Rating in this case
	 */
	@Override
	public String toString()
	{
		return "{\n "

				+ "\t rating: " + rating + "\n"

				+ "\t movie id: " + movieId + "\n"

				+ "\t user id: " + userId + "\n"

		+ "}\n";
	}

	/**
	 * this method provides the hash code of an object - namely a Rating object in this case.
	 */
	@Override  
	public int hashCode()  
	{  

		return Objects.hashCode(this.userId, this.movieId, this.rating);  
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
		if (obj instanceof Rating)
		{
			final Rating other = (Rating) obj;
			return Objects.equal(userId,  other.userId)
					&& Objects.equal(movieId,     other.movieId)
					&& Objects.equal(rating,     other.rating);
		}
		else
		{
			return false;
		}
	}
}