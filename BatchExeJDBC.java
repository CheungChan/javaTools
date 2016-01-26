package com.cheungchan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class BatchExeJDBC {
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3307/test";
   static final String USER = "root";
   static final String PASS = "1234";
   static final String sql = "insert into studentInfo (id, name, age, grade) values (?, ?, ?, ?)";
   static final int len = 3450;
	public static void main(String[] args) {
		   Connection conn = null;
		   PreparedStatement ps = null;
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.jdbc.Driver");

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      //STEP 4: Execute a query
		      System.out.println("Creating preparedStatement...");
		      ps = conn.prepareStatement(sql);
		      final int batchSize = 1000;
		      int count = 0;
		      StudentInfo[] students = getStudent();
		      
		      for (StudentInfo st: students) {
		          ps.setString(1, st.getId());
		          ps.setString(2, st.getName());
		          ps.setInt(3, st.getAge());
		          ps.setDouble(4, st.getGrade());
		          ps.addBatch();
		          if(count == 0){
		        	  System.out.println("begin insert");
		          }
		          if(++count % batchSize == 0) {
		              ps.executeBatch();
		              System.out.println("insert " + count + " records");
		          }
		      }
		      ps.executeBatch(); // insert remaining records
		      System.out.println("insert " + count + " records");
		      System.out.println("BatchInsertOK");
		      //STEP 6: Clean-up environment
		      ps.close();
		      conn.close();
		   }catch(SQLException se){
		      //Handle errors for JDBC
		      se.printStackTrace();
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }finally{
		      //finally block used to close resources
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }//end try
		   System.out.println("Goodbye!");
		}//end main
	   public static StudentInfo[] getStudent(){
		   StudentInfo[] students = new StudentInfo[len];
		   for (int i = 0; i < len; i++) {
				students[i] = new StudentInfo(""+i,"小韩",20+i,80+i);
			}
		   return students;
	   }
}
