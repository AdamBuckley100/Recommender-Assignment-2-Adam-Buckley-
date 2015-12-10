package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class Movie implements Comparable<Movie>
{ 
	public static Long   counter = 0l;

	public Long   id;

	public String title;
	public String year;
	public String url;

	public List<Rating> ratings = new ArrayList<Rating>();

	//get average rating for movie method

	public Movie(String title, String year, String url)
	{
		this(counter++, title, year, url);
	}

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

	@Override
	public String toString()
	{
		//return new ToJsonString(getClass(), this).toString();

		return "{\n "

				+ "\t id: " + id + "\n"

				+ "\t title: " + title + "\n"

				+ "\t year: " + year + "\n"

				+ "\t url: " + url + "\n"

				+ "\t average rating of the " + title + " movie: "+ getAverageRating() + "\n"

		+ "}\n";
	}

	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.id, this.title, this.year, this.url);  
	} 

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

	@Override
	public int compareTo(Movie theOtherMovie)
	{
		return Double.compare(this.getAverageRating(), theOtherMovie.getAverageRating());
	}
}