<%@ page import="com.fruitDayDB.vo.User" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: xi
  Date: 2015/10/18
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  Boolean isAdmin = (Boolean) session.getAttribute("admin");
  if (isAdmin == null || !isAdmin) {
    response.sendRedirect("login.jsp");
    return;
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
<body >
<div class="mean">
  <div class="logo">
    <a href="index.jsp"><img src="img/alogo.png" alt="" /></a>
  </div>

  <div class="mean_ul">
    <div class="mean_li" onclick="sss('u')">用户管理</div>
    <div class="user_list" id="user_list">
      <div class="mm"><a href="/x-test/BSServlet?key=dashboard">仪表盘</a></div>
      <div class="mm"><a href="/x-test/BSServlet?key=alluser">全部用户</a></div>
      <div class="mm"><a href="BSindex2.jsp">添加用户</a></div>
    </div>
    <div class="mean_li" onclick="sss('f')">商品管理</div>
    <div class="fruit_list" id="fruit_list">
      <div class="mm"><a href="/x-test/BSServlet?key=allfruit">库存水果</a></div>
      <div class="mm"><a href="/x-test/BSServlet?key=hotfruit">热卖水果</a></div>
      <div class="mm"><a href="BSindex5.jsp">水果入库</a></div>
      <div class="mm"><a href="/x-test/BSServlet?key=allshop">购物记录</a></div>
      <div class="mm"><a href="/x-test/BSServlet?key=allorder">订单管理</a></div>
    </div>
  </div>
</div>

<div class="gong" id="x0" >
  <div class="con">
    <div class="hello">
      <h1>欢迎进入天天果园后台管理系统</h1>
      <p>用户总数：<%=request.getAttribute("userCount") == null ? "-" : request.getAttribute("userCount")%></p>
      <p>商品总数：<%=request.getAttribute("fruitCount") == null ? "-" : request.getAttribute("fruitCount")%></p>
      <p>购物车记录：<%=request.getAttribute("cartCount") == null ? "-" : request.getAttribute("cartCount")%></p>
      <p>收藏记录：<%=request.getAttribute("starCount") == null ? "-" : request.getAttribute("starCount")%></p>
      <p>订单总数：<%=request.getAttribute("orderCount") == null ? "-" : request.getAttribute("orderCount")%></p>
    </div>
  </div>
</div>


</body>
</html>
