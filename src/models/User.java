package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

/**
 * This User class is a class that is representative of what a user object will
 * possess upon creation (ie. the user object of type User will have the
 * fields id, firstName, lastName, age, gender and occupation.
 * 
 * @author Adam Buckley (Student I.D: 20062910).
 * @version 1.
 * @date 10/12/2015.
 */

public class User 
{
	public static Long   counter = 0l;

	public Long   id;
	public String firstName;
	public String lastName;
	public String age;
	public String gender;
	public String occupation;

	/**
	 * note this list below is a java.util list not an java.awt array list or list
	 */
	public List<Rating> ratings = new ArrayList<Rating>();

	/** this constructor is one of the two overloaded constructors in the User class,
	 *  this overloaded constructor method is used exclusively when a user is being
	 *  added at the cliche shell command line, by the user of the CLI.
	 */
	public User(String firstName, String lastName, String age, String gender, String occupation)
	{
		this(counter++, firstName, lastName, age, gender, occupation);
	}

	/** this constructor is one of the two overloaded constructors in the User class,
	 *  this overloaded constructor method is used exclusively when a user is being
	 *  parsed in from the CVS text files (ie. the .dat files: users.dat or users5.dat)
	 */
	public User(Long id, String firstName, String lastName, String age, String gender, String occupation)
	{
		this.id = id;
		if (id >= counter) {
			counter = id + 1;
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.gender = gender;
		this.occupation = occupation;
	}

	public Long getUserId()
	{
		return id;
	}

	/**
	 * This method just returns a String representation of an object, namely
	 * the movie object of type User in this case.
	 */
	@Override
	public String toString()
	{
		return "{\n "

				+ "\t id: " + id + "\n"

				+ "\t first name: " + firstName + "\n"

				+ "\t last name: " + lastName + "\n"

				+ "\t age: " + age + "\n"

				+ "\t gender: " + gender + "\n"

				+ "\t occupation: " + occupation + "\n"

		+ "}\n";
	}

	/**
	 * this method provides the hash code of an object - namely a User object in this case.
	 */
	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.lastName, this.firstName, this.age, this.gender);  
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
		if (obj instanceof User)
		{
			final User other = (User) obj;
			return Objects.equal(firstName, other.firstName) 
					&& Objects.equal(lastName,  other.lastName)
					&& Objects.equal(age,     other.age)
					&& Objects.equal(gender,  other.gender)
					&&  Objects.equal(occupation,  other.occupation)
					&& Objects.equal(ratings, other.ratings);
		}
		else
		{
			return false;
		}
	}
}