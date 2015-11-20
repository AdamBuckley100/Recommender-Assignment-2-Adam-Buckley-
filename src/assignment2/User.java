package assignment2;

public class User 
{
  public String userId;
  public String firstName;
  public String surname;
  public String age;
  public String gender;
  public String occupation;

  public User(String userId, String firstName, String surname, String age, String gender, String occupation)
  {
	this.userId = userId;
	this.firstName = firstName;
    this.surname = surname;
    this.age = age;
    this.gender = gender;
    this.occupation = occupation;
  }
}