<%@ page import="com.fruitDayDB.vo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<html>
<head>
  <meta charset="utf-8"/>
  <title>个人中心</title>
  <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<jsp:include page="head/head.jsp"></jsp:include>
<div class="con" style="padding:40px 0;">
  <h2 style="color:#669933;">个人中心</h2>
  <div style="margin-top:20px;line-height:2;">
    <div>用户名：<%=user.getUname()%></div>
    <div>邮箱：<%=user.getEmail()%></div>
    <div>手机号：<%=user.getPhone()%></div>
  </div>
  <div style="margin-top:20px;">
    <a href="<%=request.getContextPath()%>/OrderServlet?key=my">我的订单</a> |
    <a href="<%=request.getContextPath()%>/ShopServlet?key=show&id=<%=user.getId()%>&view=cart">我的购物车</a> |
    <a href="<%=request.getContextPath()%>/ShopServlet?key=show&id=<%=user.getId()%>&view=star">我的收藏</a>
  </div>
</div>
<jsp:include page="footer/footer.jsp"></jsp:include>
</body>
</html>
