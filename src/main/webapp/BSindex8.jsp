<%@ page import="com.fruitDayDB.vo.Order" %>
<%@ page import="com.fruitDayDB.vo.OrderItem" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Boolean isAdmin = (Boolean) session.getAttribute("admin");
  if (isAdmin == null || !isAdmin) {
    response.sendRedirect("login.jsp");
    return;
  }
  List<Order> orders = new ArrayList<Order>();
  if (request.getAttribute("orders") != null) {
    orders = (List<Order>) request.getAttribute("orders");
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <title></title>
  <link rel="stylesheet" type="text/css" href="css/BSindex.css"/>
  <link rel="stylesheet" type="text/css" href="css/main.css"/>
  <script src="js/BSindex.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="mean">
  <div class="logo">
    <a href="index.jsp"><img src="img/alogo.png" alt="" /></a>
  </div>
  <div class="mean_ul">
    <div class="mean_li" onclick="sss('u')">用户管理</div>
    <div class="user_list" id="user_list">
      <div class="mm"><a href="<%=request.getContextPath()%>/BSServlet?key=dashboard">仪表盘</a></div>
      <div class="mm"><a href="<%=request.getContextPath()%>/BSServlet?key=alluser">全部用户</a></div>
      <div class="mm"><a href="BSindex2.jsp">添加用户</a></div>
    </div>
    <div class="mean_li" onclick="sss('f')">商品管理</div>
    <div class="fruit_list" id="fruit_list">
      <div class="mm"><a href="<%=request.getContextPath()%>/BSServlet?key=allfruit">库存水果</a></div>
      <div class="mm"><a href="<%=request.getContextPath()%>/BSServlet?key=hotfruit">热卖水果</a></div>
      <div class="mm"><a href="BSindex5.jsp">水果入库</a></div>
      <div class="mm"><a href="<%=request.getContextPath()%>/BSServlet?key=allshop">购物记录</a></div>
      <div class="mm"><a href="<%=request.getContextPath()%>/BSServlet?key=allorder">订单管理</a></div>
    </div>
  </div>
</div>

<div class="gong" id="x8">
  <div class="con" style="height:auto;">
    <%
      if (request.getAttribute("error") != null) {
        out.print("<div style='color:#cc3300;margin-top:20px;'>" + request.getAttribute("error") + "</div>");
      }
    %>
    <%
      for (Order order : orders) {
        out.print("<div style='margin:20px 0;padding:12px;border:1px solid #ddd;'>");
        out.print("<div>订单号：" + order.getId() + " | 用户ID：" + order.getUid() + " | 用户名：" + order.getUname() + " | 状态：" + order.getStatus() + " | 金额：￥" + order.getTotalAmount() + " | 时间：" + order.getCreatedAt() + "</div>");
        out.print("<ul style='margin:10px 0 0 20px;'>");
        for (OrderItem item : order.getItems()) {
          out.print("<li>商品ID:" + item.getFid() + " " + item.getFname() + " x " + item.getQuantity() + "，单价：￥" + item.getPrice() + "，小计：￥" + item.getSubtotal() + "</li>");
        }
        out.print("</ul>");
        out.print("<div style='margin-top:8px;'>");
        if ("待发货".equals(order.getStatus())) {
          out.print("<a href='" + request.getContextPath() + "/BSServlet?key=shiporder&orderId=" + order.getId() + "'>发货</a>");
        }
        if (!"已完成".equals(order.getStatus()) && !"已取消".equals(order.getStatus())) {
          out.print("<a style='margin-left:10px;' href='" + request.getContextPath() + "/BSServlet?key=cancelorder&orderId=" + order.getId() + "'>取消订单</a>");
        }
        out.print("</div>");
        out.print("</div>");
      }
    %>
  </div>
</div>

</body>
</html>
