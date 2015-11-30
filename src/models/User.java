package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controllers.LikeMoviesAPI;

import com.google.common.base.Objects;

import utils.ToJsonString;

public class User 
{
	static Long   counter = 0l;

	public Long   id;
	public String firstName;
	public String lastName;
	public String age;
	public String gender;
	public String occupation;
	
	//note this list below is a utils list not an awt array list or list
	public List<Rating> ratings = new ArrayList<>();

	public User(String firstName, String lastName, String age, String gender, String occupation)
	{
		this.id = counter++;
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

	public String toString()
	{
		return new ToJsonString(getClass(), this).toString();
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