package models;

/**
 * This class is a fixtures Junit test case class, this class holds the users, movie and
 * rating objects that will be utilized by other Junit test case classes.
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class Fixtures
{
	public static User[] users =
		{
		new User (Long.valueOf(0), "marge", "simpson", "18", "F", "driver"),
		new User (Long.valueOf(1), "lisa",  "simpson", "56", "F", "teacher"),
		new User (Long.valueOf(2),"bart",  "simpson", "67", "M", "doctor"),
		new User (Long.valueOf(3), "maggie","simpson", "34", "F", "lecturer"),
		new User (Long.valueOf(4), "homer","simpson", "44", "M", "lecturer")
		};

	public static Movie[] movies =
		{
		new Movie (Long.valueOf(0), "The day after tomorrow", "01-Jan-1994", "www.dayaftertomorrow.com"),
		new Movie (Long.valueOf(1), "Austin Powers",  "01-Jan-1994", "www.austinpowers.com"),
		new Movie (Long.valueOf(2), "Scary movie",   "01-Jan-1994", "www.scarymovie.com"),
		new Movie (Long.valueOf(3), "Kill Bill",  "01-Jan-1994", "www.killbill.com"),
		new Movie (Long.valueOf(4), "War of the worlds", "01-Jan-1994", "www.waroftheworlds.com")
		};

	public static Rating[] ratings =
		{
		new Rating(Long.valueOf(0), Long.valueOf(0), -2),
		new Rating(Long.valueOf(1), Long.valueOf(1), -2),  
		new Rating(Long.valueOf(2), Long.valueOf(4), 4),
		new Rating(Long.valueOf(3), Long.valueOf(3), -1),
		new Rating(Long.valueOf(4), Long.valueOf(2), -4),
		new Rating(Long.valueOf(2), Long.valueOf(0), 2)
		};
}