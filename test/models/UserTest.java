package models;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static models.Fixtures.users;

public class UserTest
{
	static Long   counter = 0l;

	User homer = new User (Long.valueOf(4), "homer", "simpson", "39", "M",  "driver");

	@Test
	public void testCreate()
	{
		assertEquals (Long.valueOf(4),       homer.id);
		assertEquals ("homer",               homer.firstName);
		assertEquals ("simpson",             homer.lastName);
		assertEquals ("39",				     homer.age);  
		assertEquals ("M",				     homer.gender); 
		assertEquals ("driver",              homer.occupation);   
	}

	@Test
	public void testIds()
	{
		Set<Long> ids = new HashSet<>();
		for (User user : users)
		{
			ids.add(user.id);
		}
		assertEquals (users.length, ids.size());
	}

	@Test
	public void testToString()
	{
		assertEquals ("{\n "

			+ "\t id: 4\n"

			+ "\t first name: homer\n"

			+ "\t last name: simpson\n"

			+ "\t age: 39\n"

			+ "\t gender: M\n"

			+ "\t occupation: driver\n"

	+ "}\n", homer.toString());
	}

	@Test
	public void testEquals()
	{
		User homer2 = new User (Long.valueOf(4), "homer", "simpson", "39", "M",  "driver"); 
		User bart   = new User (Long.valueOf(5), "bart", "simpson", "11", "M", "student"); 

		assertEquals(homer, homer);
		assertEquals(homer, homer2);
		assertNotEquals(homer, bart);
	} 
}