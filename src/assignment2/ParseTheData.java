package assignment2;

import java.io.File;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import assignment2.User;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import utils.FileLogger;
import edu.princeton.cs.introcs.In;

public class ParseTheData {

	public static void main(String[] args) throws Exception{
		parse();
	}

	public static void parse() throws Exception 
	{
		//added by me
	    List<User> users = new ArrayList<User>();
		
	    //added by me
	    FileLogger logger = FileLogger.getLogger();
	    logger.log("Creating user list");
	    
		File usersFile = new File("data_movieLens/users5.dat");
		In inUsers = new In(usersFile);
		//each field is separated(delimited) by a '|'
		String delims = "[|]";
		while (!inUsers.isEmpty()) {
			// get user and rating from data source
			String userDetails = inUsers.readLine();

			// parse user details string
			String[] userTokens = userDetails.split(delims);

			// output user data to console.
			if (userTokens.length == 7) {
				System.out.println("UserID: "+userTokens[0]+",First Name:"+
						userTokens[1]+",Surname:" + userTokens[2]+",Age:"+
						Integer.parseInt(userTokens[3])+",Gender:"+userTokens[4]+",Occupation:"+
						userTokens[5]);
				//added by me
				users.add(new User(Long.valueOf(userTokens[0]) , userTokens[1] , userTokens[2] , Integer.parseInt(userTokens[3]) , userTokens[4] , userTokens[5]));
				//added by me
			    logger.log("Serializing contacts to XML");
			    XStream xstream = new XStream(new DomDriver());
			    ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter("users.xml"));
			    out.writeObject(users);
			    out.close();    
				
				//added by me
			    logger.log("Finished - shutting down");

			}else
			{
				throw new Exception("Invalid member length: "+userTokens.length);
			}
		}
	}
}