package assignment2;

import utils.ToJsonString;

public class User 
{
	
  public Long   userId;
  public String firstName;
  public String surname;
  public int age;
  public String gender;
  public String occupation;

  public User(Long userId, String firstName, String surname, int age, String gender, String occupation)
  {
	this.userId = userId;
	this.firstName = firstName;
    this.surname = surname;
    this.age = age;
    this.gender = gender;
    this.occupation = occupation;
  }
  
  public String toString()
  {
    return new ToJsonString(getClass(), this).toString();
  }
}