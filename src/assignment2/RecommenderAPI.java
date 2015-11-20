package assignment2;

import java.util.ArrayList;
import java.util.List;
import assignment2.User;

public interface RecommenderAPI {
	
	List <User> users = new ArrayList<User>();

	public void addUser(firstName,lastName,age,gender,occupation)
	{	
	}

	public void removeUser(String userID)
	{
	}

	public void addMovie(String title, int year, String url)
	{
	}

	public void addRating(int userID, long movieID, double rating)
	{

	}

	public void getMovie(String movieID)
	{

	}

	public void getUserRatings(String userID)
	{

	}

	public void getUserRecommendations(String userID)
	{
	}

	public void getTopTenMovies()
	{
	}

	public void load()
	{
	}

	public void write()
	{
	}

	public  void deleteUsers() 
	{
		users.clear();
	}
}