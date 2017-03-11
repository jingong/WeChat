package tables;

import beans.Member;
import db.DBType;
import db.DBUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class MemberManager {
    public static void displayAllRow() throws SQLException{
        String sql = "select * from member";
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection(DBType.MYSQL);
            System.out.println("数据库连接成功");
            st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery(sql);
            rs.last();
            int nRows = rs.getRow();
            if (nRows == 0) {
                System.out.println("空");
            }else{
                rs.beforeFirst();
                StringBuffer buffer = new StringBuffer();
                while (rs.next()) {                    
                    buffer.append(rs.getInt("id") + " ");
                    buffer.append(rs.getString("name") + " ");
                    buffer.append(rs.getString("password") + " ");
                    buffer.append(rs.getString("email") + " ");
                    buffer.append(rs.getString("time") + "\n");
                }
                System.out.println(buffer.toString());
            }
        } catch (SQLException ex) {
            
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    public static Member getRowById(int id) throws SQLException{
        String sql = "select * from user where id=?";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection(DBType.MYSQL);
            System.out.println("数据库连接成功");
            st = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Member bean = new Member();
                bean.setId(rs.getInt("id"));
                bean.setName(rs.getString("name"));
                bean.setPassword(rs.getString("password"));
                bean.setEmail(rs.getString("email"));
                bean.setTime(rs.getString("time"));
                return bean;
            }else{
                System.out.println("没有此用户");
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void Register(Member member) throws SQLException{
        String sql = "insert into member values(?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection(DBType.MYSQL);
            st = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setInt(1, member.getId());
            st.setString(2, member.getName());
            st.setString(3, member.getPassword());
            st.setString(4, member.getEmail());
            st.setString(5, member.getTime());
            int count = st.executeUpdate();
            if(count > 0){
                JOptionPane.showMessageDialog(null, "注册成功","提示",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "注册失败1","提示",JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            JOptionPane.showMessageDialog(null, "注册失败2","提示",JOptionPane.ERROR_MESSAGE);
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static int Login(String name,String password) throws SQLException{
        String sql = "select * from user where userName=? and password=?";
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection(DBType.MYSQL);
            st = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            st.setString(1, name);
            st.setString(2, password);
            rs = st.executeQuery();
            if(rs.next() != false){
                return 1;
            }else{
                return -1;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
            return -1;
        }finally{
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}
