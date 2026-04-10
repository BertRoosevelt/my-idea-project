package com.fruitDayDB.service;

import com.fruitDayDB.dao.OrderDao;
import com.fruitDayDB.dao.OrderDaoImpl;
import com.fruitDayDB.vo.Fruit;
import com.fruitDayDB.vo.Order;
import com.fruitDayDB.vo.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {

    public static int createFromCart(int uid, Map<Integer, Integer> quantities) {
        if (uid <= 0 || quantities == null || quantities.isEmpty()) {
            return 0;
        }
        List<OrderItem> items = new ArrayList<OrderItem>();
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
            int fid = entry.getKey();
            int qty = entry.getValue();
            if (fid <= 0 || qty <= 0) {
                continue;
            }
            Fruit fruit = FruitService.info(fid);
            if (fruit == null || fruit.getFid() <= 0 || "商品不存在".equals(fruit.getFname())) {
                continue;
            }
            OrderItem item = new OrderItem();
            item.setFid(fid);
            item.setPrice(fruit.getUp());
            item.setQuantity(qty);
            item.setSubtotal(fruit.getUp() * qty);
            items.add(item);
            total += item.getSubtotal();
        }
        if (items.isEmpty()) {
            return 0;
        }
        OrderDao dao = new OrderDaoImpl();
        int orderId = dao.createOrder(uid, total, "待支付");
        if (orderId <= 0) {
            return 0;
        }
        for (OrderItem item : items) {
            item.setOrderId(orderId);
            dao.addOrderItem(item);
            ShopService.del(uid, item.getFid());
        }
        return orderId;
    }

    public static List<Order> userOrders(int uid) {
        OrderDao dao = new OrderDaoImpl();
        List<Order> orders = dao.findOrdersByUid(uid);
        for (Order order : orders) {
            order.setItems(dao.findItemsByOrderId(order.getId()));
        }
        return orders;
    }

    public static List<Order> allOrders() {
        OrderDao dao = new OrderDaoImpl();
        List<Order> orders = dao.findAllOrders();
        for (Order order : orders) {
            order.setItems(dao.findItemsByOrderId(order.getId()));
        }
        return orders;
    }

    public static boolean userPay(int uid, int orderId) {
        OrderDao dao = new OrderDaoImpl();
        Order order = dao.findById(orderId);
        if (order == null || order.getUid() != uid || !"待支付".equals(order.getStatus())) {
            return false;
        }
        return dao.updateStatus(orderId, "待发货") == 1;
    }

    public static boolean userCancel(int uid, int orderId) {
        OrderDao dao = new OrderDaoImpl();
        Order order = dao.findById(orderId);
        if (order == null || order.getUid() != uid) {
            return false;
        }
        if ("已完成".equals(order.getStatus()) || "已取消".equals(order.getStatus())) {
            return false;
        }
        return dao.updateStatus(orderId, "已取消") == 1;
    }

    public static boolean userConfirm(int uid, int orderId) {
        OrderDao dao = new OrderDaoImpl();
        Order order = dao.findById(orderId);
        if (order == null || order.getUid() != uid || !"待收货".equals(order.getStatus())) {
            return false;
        }
        return dao.updateStatus(orderId, "已完成") == 1;
    }

    public static boolean adminShip(int orderId) {
        OrderDao dao = new OrderDaoImpl();
        Order order = dao.findById(orderId);
        if (order == null || !"待发货".equals(order.getStatus())) {
            return false;
        }
        return dao.updateStatus(orderId, "待收货") == 1;
    }

    public static boolean adminCancel(int orderId) {
        OrderDao dao = new OrderDaoImpl();
        Order order = dao.findById(orderId);
        if (order == null || "已完成".equals(order.getStatus()) || "已取消".equals(order.getStatus())) {
            return false;
        }
        return dao.updateStatus(orderId, "已取消") == 1;
    }
}
