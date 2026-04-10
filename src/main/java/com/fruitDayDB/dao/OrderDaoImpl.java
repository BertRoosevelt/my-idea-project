package com.fruitDayDB.dao;

import com.fruitDayDB.db.DBUtils;
import com.fruitDayDB.vo.Order;
import com.fruitDayDB.vo.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    public int createOrder(int uid, double totalAmount, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int orderId = 0;
        String sql = "INSERT INTO orders(uid,total_amount,status,created_at) VALUES(?,?,?,NOW())";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, uid);
            ps.setDouble(2, totalAmount);
            ps.setString(3, status);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs, ps, conn);
        }
        return orderId;
    }

    public int addOrderItem(OrderItem item) {
        Connection conn = null;
        PreparedStatement ps = null;
        int num = 0;
        String sql = "INSERT INTO order_item(order_id,fid,price,quantity,subtotal) VALUES(?,?,?,?,?)";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getFid());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getSubtotal());
            num = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(null, ps, conn);
        }
        return num;
    }

    public List<Order> findOrdersByUid(int uid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orders = new ArrayList<Order>();
        String sql = "SELECT o.id,o.uid,o.total_amount,o.status,o.created_at,u.uname FROM orders o LEFT JOIN user u ON o.uid=u.id WHERE o.uid=? ORDER BY o.id DESC";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUid(rs.getInt("uid"));
                order.setUname(rs.getString("uname"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs, ps, conn);
        }
        return orders;
    }

    public List<Order> findAllOrders() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orders = new ArrayList<Order>();
        String sql = "SELECT o.id,o.uid,o.total_amount,o.status,o.created_at,u.uname FROM orders o LEFT JOIN user u ON o.uid=u.id ORDER BY o.id DESC";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setUid(rs.getInt("uid"));
                order.setUname(rs.getString("uname"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs, ps, conn);
        }
        return orders;
    }

    public List<OrderItem> findItemsByOrderId(int orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<OrderItem> items = new ArrayList<OrderItem>();
        String sql = "SELECT oi.id,oi.order_id,oi.fid,oi.price,oi.quantity,oi.subtotal,f.fname FROM order_item oi LEFT JOIN fruits f ON oi.fid=f.fid WHERE oi.order_id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setFid(rs.getInt("fid"));
                item.setFname(rs.getString("fname"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setSubtotal(rs.getDouble("subtotal"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs, ps, conn);
        }
        return items;
    }

    public Order findById(int orderId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Order order = null;
        String sql = "SELECT id,uid,total_amount,status,created_at FROM orders WHERE id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                order = new Order();
                order.setId(rs.getInt("id"));
                order.setUid(rs.getInt("uid"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs, ps, conn);
        }
        return order;
    }

    public int updateStatus(int orderId, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        int num = 0;
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try {
            conn = DBUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            num = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(null, ps, conn);
        }
        return num;
    }
}
