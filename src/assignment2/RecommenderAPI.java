package assignment2;

import java.util.HashMap;
import java.util.Map;

import utils.Serializer;

import java.util.Collection;

public class RecommenderAPI {

	private Map<Long,   User>   userIndex       = new HashMap<>();
	private Serializer serializer;
	
	  public RecommenderAPI()
	  {}
	
	  public RecommenderAPI(Serializer serializer)
	  {
	    this.serializer = serializer;
	  }
	  
	  @SuppressWarnings("unchecked")
	  public void load() throws Exception
	  {
	    serializer.read();
	    userIndex       = (Map<Long, User>)     serializer.pop();
	  }
	  
	  public void store() throws Exception
	  {
	    serializer.push(userIndex);
	    serializer.write(); 
	  }
	  
	  public Collection<User> getUsers ()
	  {
	    return userIndex.values();
	  }
	  
	  public  void deleteUsers() 
	  {
	    userIndex.clear();
	  }
	  
	  public User createUser(Long userId, String firstName, String surname, int age, String gender, String occupation) 
	  {
	    User user = new User (userId, firstName, surname, age, gender, occupation);
	    userIndex.put(user.userId, user);
	    return user;
	  }

	  public User getUserByUserId(Long userId) 
	  {
	    return userIndex.get(userId);
	  }

	  public void deleteUser(Long userId) 
	  {
	    User user = userIndex.remove(userId);
	  }
}