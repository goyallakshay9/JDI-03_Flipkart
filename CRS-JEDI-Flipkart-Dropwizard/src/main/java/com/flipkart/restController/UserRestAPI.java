/**
 * 
 */
package com.flipkart.restController;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.flipkart.bean.Student;
import com.flipkart.constant.Gender;
import com.flipkart.constant.Role;
import com.flipkart.exception.StudentNotRegisteredException;
import com.flipkart.exception.UserNotFoundException;
import com.flipkart.service.StudentInterface;
import com.flipkart.service.StudentOperation;
import com.flipkart.service.UserInterface;
import com.flipkart.service.UserOperation;

/**
 * @author dilpreetkaur
 *
 */

@Path("/user")
public class UserRestAPI {
	StudentInterface studentInterface=StudentOperation.getInstance();
	UserInterface userInterface =UserOperation.getInstance();
	
	
	/**
	 * 
	 * @param userId: email address of the user
	 * @param newPassword: new password to be stored in db.
	 * @return @return 201, if password is updated, else 500 in case of error
	 */
	@GET
	@Path("/updatePassword")
	public Response updatePassword(@QueryParam("userId") String userId, @QueryParam("newPassword") String newPassword) {
	
		if(userInterface.updatePassword(userId, newPassword))
		{
			return Response.status(201).entity("Password updated successfully! ").build();
		}
		else
		{
			return Response.status(500).entity("Something went wrong, please try again!").build();
		}
			
	}
	
	/**
	 * 
	 * @param userId
	 * @param password
	 * @return 
	 */
	
	@GET
	@Path("/verifyCredentials")
	public Response verifyCredentials(@QueryParam("userId") String userId, @QueryParam("password") String password)  {
		
		try 
		{
			if(userInterface.verifyCredentials(userId, password))
			{
				return Response.status(200).entity("Login Successful ").build();
			}
			else
			{
				return Response.status(500).entity("Login Unsuccessful ").build();
			}
		} 
		catch (UserNotFoundException e) 
		{
			return Response.status(500).entity(e.getMessage()).build();
		}
		
		
	}
	
	@GET
	@Path("/getRole")
	public String getRole(@QueryParam("userId") String userId) {
		
		return userInterface.getRole(userId);
	}
	
	/**
	 * 
	 * @param student
	 * @return 201, if user is created, else 500 in case of error
	 */
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response register(Student student)
	{
		//register(String name,String userId,String password,Gender gender,int batch,String branch,String address,String country) throws StudentNotRegisteredException
		try
		{
			studentInterface.register(student.getName(), student.getUserId(), student.getPassword(), student.getGender(), student.getBatch(), student.getBranchName(), student.getAddress(), student.getCountry());
		}
		catch(Exception ex)
		{
			return Response.status(500).entity("Something went wrong! Please try again.").build(); 
		}
		return Response.status(201).entity("Registration Successful for "+student.getUserId()).build(); 
	}
	
	
}