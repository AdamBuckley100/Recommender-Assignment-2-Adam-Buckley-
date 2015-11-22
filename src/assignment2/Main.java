package assignment2;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Optional;

import utils.Serializer;
import utils.XMLSerializer;
import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;


public class Main
{
  public RecommenderAPI recoApi;

  public Main() throws Exception
  {
    File datastore = new File("datastore.xml");
    Serializer serializer = new XMLSerializer(datastore);

    recoApi = new RecommenderAPI(serializer);
    if (datastore.isFile())
    {
      recoApi.load();
    }
  }

  public static void main(String[] args) throws Exception
  {
    Main main = new Main();
    
    main.recoApi.createUser(new Long(1), "adam", "B", 20, "M", "Student");

    Shell shell = ShellFactory.createConsoleShell("pm", "Welcome to pacemaker-console - ?help for instructions", main);
    shell.commandLoop();

    main.recoApi.store();
  }
  
  @Command(description="Get all users details")
  public void getUsers ()
  {
    Collection<User> users = recoApi.getUsers();
    System.out.println(users);
  }
  
  @Command(description="Create a new User")
  public void createUser (@Param(name="User Id") Long userId, @Param(name="first name") String firstName, @Param(name="surname") String surname, 
                          @Param(name="Age")      int age,     @Param(name="gender")  String gender,	@Param(name="occupation")  String occupation)
  {
    recoApi.createUser(userId, firstName, surname, age, gender, occupation);
  }
  
  @Command(description="Get a Users detail")
  public void getUser (@Param(name="User Id") Long userId)
  {
    User user = recoApi.getUserByUserId(userId);
    System.out.println(user);
  }
  
  @Command(description="Delete a User")
  public void deleteUser (@Param(name="User Id") Long userId)
  {
    Optional<User> user = Optional.fromNullable(recoApi.getUserByUserId(userId));
    if (user.isPresent())
    {
      recoApi.deleteUser(user.get().userId);
    }
  }
}