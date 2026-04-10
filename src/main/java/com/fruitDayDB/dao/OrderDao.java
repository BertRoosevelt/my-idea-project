package com.fruitDayDB.dao;

import com.fruitDayDB.vo.Order;
import com.fruitDayDB.vo.OrderItem;

import java.util.List;

public interface OrderDao {
    int createOrder(int uid, double totalAmount, String status);

    int addOrderItem(OrderItem item);

    List<Order> findOrdersByUid(int uid);

    List<Order> findAllOrders();

    List<OrderItem> findItemsByOrderId(int orderId);

    Order findById(int orderId);

    int updateStatus(int orderId, String status);
}
