package com.java;

import java.sql.*;  
public class OracleConnectionNew{  
public static void main(String args[]) throws Exception{  
try{  
 
Class.forName("oracle.jdbc.driver.OracleDriver");      
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@45.79.135.253:1521/xe","vmannepa","02160438");  
Statement stmt=con.createStatement();  
System.out.println("Connected to database");

//reading data from application Table
ResultSet rs1=stmt.executeQuery("select *from application");  
while(rs1.next())  
System.out.println(rs1.getString(1)+"  "+rs1.getInt(2)+"  "+rs1.getDate(3)+"  "+rs1.getDate(4)+"  "+rs1.getInt(5)+"  "+rs1.getString(6)+"  "+rs1.getString(7));  
System.out.println("\n\n");

//reading data from funding_opportunity Table
ResultSet rs=stmt.executeQuery("select *from funding_opportunity");  
while(rs.next())  
System.out.println(rs.getString(1)+"  "+rs.getString(2)+"  "+rs.getInt(3)+"  "+rs.getDate(4)+"  "+rs.getDate(5)+"  "+rs.getDate(6)+"  "+rs.getString(7)+"  "+rs.getString(8));  
System.out.println("\n\n");

//inserting data into Admin Table
String sql = "INSERT INTO Admin (ADMIN_ID, MAIL_ID, FIRST_NAME, LAST_NAME) VALUES (?, ?, ?, ?)";
PreparedStatement statement = con.prepareStatement(sql);
statement.setString(1, "700");
statement.setString(2, "adm7@vu.edu");
statement.setString(3, "dummy Test");
statement.setString(4, "One day"); 
int rowsInserted = statement.executeUpdate();
if (rowsInserted > 0) {
    System.out.println("A new Admin was inserted successfully!");
}

//updating the data in Application Table
String updatesql = "UPDATE Application SET FUNDING_OPP_ID=? WHERE APPLICATION_ID=?";
PreparedStatement statement1 = con.prepareStatement(updatesql);
statement1.setString(1, "F02");
statement1.setString(2, "A04"); 
int rowsUpdated = statement1.executeUpdate();
if (rowsUpdated > 0) {
    System.out.println("An existing Application was updated successfully!");
}

//Deleting the data in academic_year Table
String deletesql = "DELETE FROM academic_year WHERE ACADEMICYEAR_TITLE=?";
PreparedStatement deleteStatement = con.prepareStatement(deletesql);
deleteStatement.setString(1, "F2021"); 
int rowsDeleted = deleteStatement.executeUpdate();
if (rowsDeleted > 0) {
    System.out.println("deleted successfully!");
}


//Calling Stored procedure AddAdmin
CallableStatement cst = con.prepareCall("{CALL AddAdmin(?, ?, ?, ?)}");
cst.setInt(1, 600);
cst.setString(2, "adm5@vu.edu");
cst.setString(3, "Mayanti");
cst.setString(4, "Langer");
// Calling procedure
cst.execute();


//Calling Stored procedure updateFunding
CallableStatement cst1 = con.prepareCall("{CALL updateFunding(?, ?, ?)}");
cst1.setInt(1, 10000);
cst1.setString(2, "F03");
//register output parameters
cst1.registerOutParameter(3, java.sql.Types.INTEGER);
//Calling procedure
cst1.execute();
int avgFundAmount = cst1.getInt(3);
System.out.println("avgFundAmount is:" +avgFundAmount);
cst.close();
cst1.close();
//System.out.println("Commiting data here....");
//con.commit();
//close the connection object  
con.close();  
   
}

catch(Exception e)
{
	System.out.println(e);
}  
  
}  
}