package models;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class User 
{
	public static Long   counter = 0l;

	public Long   id;
	public String firstName;
	public String lastName;
	public String age;
	public String gender;
	public String occupation;

	//note this list below is a utils list not an awt array list or list
	public List<Rating> ratings = new ArrayList<Rating>();

	public User(String firstName, String lastName, String age, String gender, String occupation)
	{
		this(counter++, firstName, lastName, age, gender, occupation);
	}

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

	//public String toString()
	//{
	//	return new ToJsonString(getClass(), this).toString();
	//}

	@Override
	public String toString()
	{
		//return new ToJsonString(getClass(), this).toString();

		return "{\n "

				+ "\t id: " + id + "\n"

				+ "\t first name: " + firstName + "\n"

				+ "\t last name: " + lastName + "\n"

				+ "\t age: " + age + "\n"

				+ "\t gender: " + gender + "\n"

				+ "\t occupation: " + occupation + "\n"

		+ "}\n";
	}

	@Override  
	public int hashCode()  
	{  
		return Objects.hashCode(this.lastName, this.firstName, this.age, this.gender);  
	}  

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