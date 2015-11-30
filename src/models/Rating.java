package models;

import com.google.common.base.Objects;

import utils.ToJsonString;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

import utils.ToJsonString;

public class Rating
{
	//rating doesn't need a an id: why would it?
	//static Long   counter = 0l;

	public Long userId;
	public Long movieId;
	public int rating;
	
	public List<Movie> movie = new ArrayList<>();

	public Rating (Long movieId, Long userId, int rating)
	{
		this.userId = userId;
		this.movieId  = movieId;
		this.rating  = rating;
	}

	@Override
	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
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