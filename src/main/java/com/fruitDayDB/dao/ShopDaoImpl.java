package com.fruitDayDB.dao;

import com.fruitDayDB.db.DBUtils;
import com.fruitDayDB.vo.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soso.
 */
public class ShopDaoImpl implements ShopDao {


    public int del(int id,int fid) {
        Connection conn = null;
        PreparedStatement ps = null;
        int num=0;
        String sql = "DELETE FROM shop WHERE uid=? AND fid=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, fid);
            num=ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtils.close(null, ps, conn);
        }
        return num;
    }

    public List<Cart> show(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cart> carts = new ArrayList<Cart>();
        String sql="SELECT fid,isCart,isStar FROM shop WHERE uid=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while(rs.next()){
                Cart cart=new Cart();
                cart.setFid(rs.getInt("fid"));
                cart.setIsCart(rs.getBoolean("isCart"));
                cart.setIsStar(rs.getBoolean("isStar"));
                carts.add(cart);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return carts;
    }

    public Cart find(int id,int fid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Cart cart=null;
        String sql="SELECT fid,isCart,isStar FROM shop WHERE uid=? AND fid=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, fid);
            rs = ps.executeQuery();
            if(rs.next()){
                cart=new Cart();
                cart.setFid(fid);
                cart.setIsCart(rs.getBoolean("isCart"));
                cart.setIsStar(rs.getBoolean("isStar"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtils.close(rs, ps, conn);
        }
        return cart;
    }

    public int up(int id, Cart cart) {
        Connection conn = null;
        PreparedStatement ps = null;
        int num=0;
        String sql = "UPDATE shop SET isCart=?,isStar=? WHERE uid=? AND fid=?";
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setBoolean(1, cart.isCart());
            ps.setBoolean(2, cart.isStar());
            ps.setInt(3,id);
            ps.setInt(4,cart.getFid());
            num = ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtils.close(null, ps, conn);
        }
        return num;
    }

    public int add(int id,Cart cart) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO shop(uid,fid,isCart,isStar) VALUES(?,?,?,?)";
        int num=0;
        try{
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setInt(2,cart.getFid());
            ps.setBoolean(3,cart.isCart());
            ps.setBoolean(4, cart.isStar());
            num=ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            DBUtils.close(null, ps, conn);
            return num;
        }
    }
}
