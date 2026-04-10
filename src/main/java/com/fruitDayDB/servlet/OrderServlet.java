package com.fruitDayDB.servlet;

import com.fruitDayDB.service.OrderService;
import com.fruitDayDB.vo.Order;
import com.fruitDayDB.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String key = req.getParameter("key");
        if ("my".equals(key)) {
            doMy(req, resp);
        } else if ("pay".equals(key)) {
            doPay(req, resp);
        } else if ("cancel".equals(key)) {
            doCancel(req, resp);
        } else if ("confirm".equals(key)) {
            doConfirm(req, resp);
        } else {
            doMy(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String key = req.getParameter("key");
        if ("create".equals(key)) {
            doCreate(req, resp);
        } else {
            doMy(req, resp);
        }
    }

    private void doCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getLoginUser(req);
        if (user == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        String[] fids = req.getParameterValues("fid");
        Map<Integer, Integer> quantities = new HashMap<Integer, Integer>();
        if (fids != null) {
            for (String fidStr : fids) {
                int fid = parseInt(fidStr, 0);
                int qty = parseInt(req.getParameter("qty_" + fid), 1);
                if (fid > 0 && qty > 0) {
                    quantities.put(fid, qty);
                }
            }
        }
        int orderId = OrderService.createFromCart(user.getId(), quantities);
        if (orderId <= 0) {
            req.setAttribute("error", "结算失败，请确认购物车中有有效商品");
        } else {
            req.setAttribute("msg", "下单成功，订单号：" + orderId);
        }
        doMy(req, resp);
    }

    private void doMy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getLoginUser(req);
        if (user == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        List<Order> orders = OrderService.userOrders(user.getId());
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("user_orders.jsp").forward(req, resp);
    }

    private void doPay(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getLoginUser(req);
        if (user == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        int orderId = parseInt(req.getParameter("orderId"), 0);
        if (!OrderService.userPay(user.getId(), orderId)) {
            req.setAttribute("error", "支付失败，订单状态不允许支付");
        }
        doMy(req, resp);
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getLoginUser(req);
        if (user == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        int orderId = parseInt(req.getParameter("orderId"), 0);
        if (!OrderService.userCancel(user.getId(), orderId)) {
            req.setAttribute("error", "取消失败，订单状态不允许取消");
        }
        doMy(req, resp);
    }

    private void doConfirm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = getLoginUser(req);
        if (user == null) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        int orderId = parseInt(req.getParameter("orderId"), 0);
        if (!OrderService.userConfirm(user.getId(), orderId)) {
            req.setAttribute("error", "确认收货失败，订单状态不允许确认");
        }
        doMy(req, resp);
    }

    private int parseInt(String value, int defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private User getLoginUser(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        return (User) session.getAttribute("user");
    }
}
