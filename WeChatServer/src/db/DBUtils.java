/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jdk.nashorn.internal.runtime.regexp.joni.Option;

/**
 *
 * @author jiajingong
 */
public class DBUtils {
    private  static final String M_DBURL = "jdbc:mysql://localhost:3306/wechat";
    private static final String M_USERNAME = "root";
    private static final String M_PASSWORD = "root";
    
    public static Connection getConnection(DBType dBType) throws SQLException{
        switch(dBType){
            case MYSQL:
                return DriverManager.getConnection(M_DBURL,M_USERNAME,M_PASSWORD);
             default:
                 return null;
        }
    }
}
