package com.neu.psa.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.neu.psa.pojo.MatchData;

/**
 *<h1>Data Access Object class</h1>
 *<p>Use to perform database operation like fetching test cricket data from MySQL server</p> 
 *
 */
public class DBOperation {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/psadb";

	private static final String USER = "root";
	private static final String PASS = "admin";

	public List<MatchData> getDataFromDB(){


		Connection conn = null;
		Statement stmt = null;
		List<MatchData> matchDataList = new ArrayList<MatchData>();
		try{

			Class.forName(JDBC_DRIVER);

			conn = (Connection) DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();

			String sql = "SELECT * FROM matcheshistory";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){

				MatchData matchData = new MatchData();
				matchData.setAverage(rs.getDouble("average"));
				matchData.setLost(rs.getInt("lost"));
				matchData.setMatches(rs.getInt("matches"));
				matchData.setOpponent(rs.getString("opponent"));
				matchData.setRpo(rs.getDouble("rpo"));
				matchData.setTeam(rs.getString("team"));
				matchData.setWon(rs.getInt("won"));

				matchDataList.add(matchData);  
			}
			rs.close();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{

			try{
				if(stmt!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
			try{
				if(conn!=null)
					conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println(matchDataList);

		return matchDataList;
	}

}
