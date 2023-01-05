package com.groupeisi.dao;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import com.groupeisi.entities.LoginInfo;

public class DB {
	private PreparedStatement pstm;
	private ResultSet rs;
	private int result;
	private Connection cnx;
	
	public void openConnection() {
		String dbname = "root";
		String dbpassword = "";
		String dburl = "jdbc:mysql://localhost:3306/logindb";
		try {
			//chargement du pilote
			Class.forName("com.mysql.jdbc.Driver") ;
			 cnx = DriverManager.getConnection(dburl, dbname, dbpassword);
			System.out.println("Connexion ok");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void initPrepar(String sql) {
		try {
			openConnection();
			pstm = cnx.prepareStatement(sql);
		} catch (Exception ex) {
			ex.printStackTrace();
		} 
	}
	public ResultSet executeSelect () {
		try {
			rs =pstm.executeQuery();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}
	public int executeMaj() {
		try {
			result = pstm.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}
	public void closeConnection() {
		try {
			if (cnx !=null) {
				cnx.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	public PreparedStatement getPstm() {
		return this.pstm;
	}
	
	public boolean validate(LoginInfo logininfo) {
		try {
			openConnection();
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		boolean status = false;
		String sql = "select * from login where username = ? and password = ?";
		
		try {
			initPrepar(sql);
			pstm = cnx.prepareStatement(sql);	
			pstm.setString(1, logininfo.getUsername());
			pstm.setString(2, logininfo.getPasswod());
			
			ResultSet rs = executeSelect();
			status = rs.next();
			
			
			} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return status;
		
	}
}
