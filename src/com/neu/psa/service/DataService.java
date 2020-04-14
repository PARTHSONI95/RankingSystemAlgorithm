package com.neu.psa.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.neu.psa.dao.DBOperation;
import com.neu.psa.pojo.MatchData;



public class DataService {
	
	 
		   public static void main(String[] args) {
			   
			   DBOperation dbOps = new DBOperation();
			   List<MatchData> matchList = dbOps.getDataFromDB();
			   
			   System.out.println(matchList);
		   }
		
	}

