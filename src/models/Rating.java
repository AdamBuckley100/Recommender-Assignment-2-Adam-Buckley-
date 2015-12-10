package models;

import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

public class Rating
{
	//rating doesn't need a an id: why would it?
	//static Long   counter = 0l;

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

	//@Override
	//public String toString()
	//{
	//	return new ToJsonString(getClass(), this).toString();
	//}

	@Override
	public String toString()
	{
		//return new ToJsonString(getClass(), this).toString();

		return "{\n "

				+ "\t rating: " + rating + "\n"

				+ "\t movie id: " + movieId + "\n"

				+ "\t user id: " + userId + "\n"

		+ "}\n";
	}

	@Override  
	public int hashCode()  
	{  

		return Objects.hashCode(this.userId, this.movieId, this.rating);  
	} 

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