package fr.gclo.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseInstance {

	private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DataBaseInstance.class);
	
	public static Connection 	connection 	= null;
	private static Statement 	statement 	= null;
   	private static ResultSet 	resultset 	= null;

	private DataBaseInstance() {}

	public synchronized static boolean openConnection() {
		   	
	   	final long temps = System.currentTimeMillis();
			  
	    try {
	    	String url = "jdbc:postgresql://localhost:5432/gclo_db?user=54j48gyFJt7BFv-user&password=t7Fy5B84vJFg4j";
          	connection = DriverManager.getConnection(url);			
			statement	= connection.createStatement();
		} catch (SQLException e) {
		    log.error("Erreur connection : " + e);
		    return false;
		}	 

	    log.info("openConnection en " + (System.currentTimeMillis() - temps) + " ms.");
		
	    return true;
    }

	   
	public synchronized static boolean simpleUpdateQuery(String SQLQuery) 
	{
		try {
	         statement.executeUpdate(SQLQuery);
		}
		catch(SQLException sqle) {
			log.error("Impossible d'ex�cuter la requ�te : " + SQLQuery + " ; " + sqle);
			return false;
		}
	
		//log.info("Requ�te SQL [" + SQLQuery + "] ex�cut�e en " + (System.currentTimeMillis() - temps) + " ms.");
	      
		return true;
	}	
	   
	public synchronized static ResultSet executeQuery(String SQLQuery) 
	{
		try {
			resultset = statement.executeQuery(SQLQuery);
		}
		catch(SQLException sqle) {
			log.error("Impossible d'ex�cuter la requ�te : " + SQLQuery + " ; " + sqle);
			return null;
		}

//		log.info("Requ�te SQL [" + SQLQuery + "] ex�cut�e en " + (System.currentTimeMillis() - temps) + " ms.");

      	return resultset;
	}	   

	public synchronized static int getIDAfterexecuteQuery(String SQLQuery) 
	{
		int ID = 0;
		
		try {
			resultset = statement.executeQuery(SQLQuery);
			if (resultset.next()) {
				ID = resultset.getInt("ID");
			}
		}
		catch(SQLException sqle) {
			log.error("Impossible d'ex�cuter la requ�te : " + SQLQuery + " ; " + sqle);
			return ID;
		}

		//log.info("Requ�te SQL [" + SQLQuery + "] ex�cut�e en " + (System.currentTimeMillis() - temps) + " ms.");

      	return ID;
	}	   

	public synchronized static void closeConnection() 
	{
		try {
			if ( connection != null ) connection.close();
		}
		catch(SQLException sqle) {
			log.error("Impossible de fermer la connection !");
		}
		finally {
			resultset 	= null;
			statement 	= null;
			connection 	= null;
		}
	}
}
