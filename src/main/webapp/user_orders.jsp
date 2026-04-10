<%@ page import="com.fruitDayDB.vo.User" %>
<%@ page import="com.fruitDayDB.vo.Order" %>
<%@ page import="com.fruitDayDB.vo.OrderItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User user=(User)session.getAttribute("user");
  if(user==null){
    response.sendRedirect("login.jsp");
    return;
  }
  List<Order> orders = new ArrayList<Order>();
  if (request.getAttribute("orders") != null) {
    orders = (List<Order>) request.getAttribute("orders");
  }
%>
<html>
<head>
  <meta charset="utf-8"/>
  <title>我的订单</title>
  <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<jsp:include page="head/head.jsp"></jsp:include>
<div class="con" style="padding:40px 0;">
  <h2 style="color:#669933;">我的订单</h2>
  <%
    if (request.getAttribute("msg") != null) {
      out.print("<div style='color:#228b22;margin:15px 0;'>" + request.getAttribute("msg") + "</div>");
    }
    if (request.getAttribute("error") != null) {
      out.print("<div style='color:#cc3300;margin:15px 0;'>" + request.getAttribute("error") + "</div>");
    }
  %>

  <%
    if (orders.isEmpty()) {
      out.print("<div>暂无订单，去购物车结算后会显示在这里。</div>");
    }
    for (Order order : orders) {
      out.print("<div style='border:1px solid #ddd;padding:15px;margin-bottom:20px;'>");
      out.print("<div>订单号：" + order.getId() + " | 状态：" + order.getStatus() + " | 金额：￥" + order.getTotalAmount() + " | 下单时间：" + order.getCreatedAt() + "</div>");
      out.print("<ul style='margin:10px 0 0 20px;'>");
      for (OrderItem item : order.getItems()) {
        out.print("<li>" + item.getFname() + " x " + item.getQuantity() + "，单价：￥" + item.getPrice() + "，小计：￥" + item.getSubtotal() + "</li>");
      }
      out.print("</ul>");
      out.print("<div style='margin-top:10px;'>");
      if ("待支付".equals(order.getStatus())) {
        out.print("<a href='" + request.getContextPath() + "/OrderServlet?key=pay&orderId=" + order.getId() + "'>模拟支付</a> ");
      }
      if ("待支付".equals(order.getStatus()) || "待发货".equals(order.getStatus()) || "待收货".equals(order.getStatus())) {
        out.print("<a href='" + request.getContextPath() + "/OrderServlet?key=cancel&orderId=" + order.getId() + "' style='margin-left:10px;'>取消订单</a> ");
      }
      if ("待收货".equals(order.getStatus())) {
        out.print("<a href='" + request.getContextPath() + "/OrderServlet?key=confirm&orderId=" + order.getId() + "' style='margin-left:10px;'>确认收货</a> ");
      }
      out.print("</div>");
      out.print("</div>");
    }
  %>
</div>
<jsp:include page="footer/footer.jsp"></jsp:include>
</body>
</html>
