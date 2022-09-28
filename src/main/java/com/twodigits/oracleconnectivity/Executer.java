package com.twodigits.oracleconnectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * execute SQL statements as main class
 * @author robert.diers
 */
public class Executer {

    public static void main(String[] args) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
			
		try{
			if (args.length != 5)
			{				
				System.out.println("usage:");
				System.out.println("java -cp oracle-connectivity-java-jdbc-jar-with-dependencies:<additional-database-driver.jar> com.twodigits.oracleconnectivity.Executer <url> <DriverClass> <User> <Password> \"<SQL>\"");
                for (String arg : args) System.out.println(arg+";");
				System.exit(1);
			}
			
			String url = args[0];
            System.out.println("url: "+url);
			String driver = args[1];
            System.out.println("driver: "+driver);
			String user = args[2];
            System.out.println("user: "+user);
			String password = args[3];	
            System.out.println("password: "+password);
            String sql = args[4];
            System.out.println("sql: "+sql);

            //register driver
            Class.forName(driver);

            //create connection
            con = DriverManager.getConnection(url, user, password);

            //prepare statement
            ps = con.prepareStatement(sql);

            //execute statement
            rs = ps.executeQuery();

            //read column names
            ResultSetMetaData md = rs.getMetaData();
            int numCols = md.getColumnCount();
            List<String> colNames = new ArrayList<>();
            for (int i = 1; i<= numCols; i++) {
                colNames.add(md.getColumnName(i));
            }      

            //print to stdout    
            StringBuilder row = new StringBuilder(); 
            for (String col : colNames) {
                row.append(col+";");
            }  
            System.out.println(row.toString());      
            while (rs.next()) {   
                row = new StringBuilder();  
                for (String col : colNames) {
                    row.append(rs.getObject(col)+";");
                }    
                System.out.println(row.toString());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.err.println(e.getMessage());
            e.printStackTrace();    
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (ps != null) try { ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (con != null) try { con.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }
    
}