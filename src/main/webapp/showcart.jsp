<%@ page import="com.fruitDayDB.vo.Fruit" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fruitDayDB.vo.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  List<Fruit> fruits = new ArrayList<Fruit>();
  if (request.getAttribute("fruits") != null) {
    fruits = (List<Fruit>) request.getAttribute("fruits");
  }
  User user = (User) session.getAttribute("user");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<html>
<head>
  <meta charset="utf-8">
  <title>购物车</title>
  <link rel="stylesheet" href="css/showcart.css"/>
  <link rel="stylesheet" type="text/css" href="css/main.css"/>
</head>
<body>
<div class="con">
  <div class="head">
    <a href="index.jsp">
      <img src="img/logo_login.png" alt=""/>
    </a>
  </div>
  <div class="shop_box">
    <div class="head_text_box">
      <span id="head_text">我的购物车</span>
    </div>

    <div class="shop_title">
      <div id="st1">商品</div>
      <div id="st2">规格</div>
      <div id="st3">单价</div>
      <div id="st4">数量</div>
      <div id="st5">小计</div>
      <div id="st6">操作</div>
    </div>

    <form action="<%=request.getContextPath()%>/OrderServlet?key=create" method="post">
      <input type="hidden" name="uid" value="<%=user.getId()%>"/>
      <%
        double total = 0;
        for (Fruit fruit : fruits) {
          total += fruit.getUp();
          out.print("<div class=\"shop\">" +
                  "<div class=\"s1\">" +
                  "<div class=\"s1_img\"><a href=\"" + request.getContextPath() + "/FruitServlet?key=info&id=" + user.getId() + "&fid=" + fruit.getFid() + "\"><img src=\"img/fruits/" + fruit.getFid() + "/(1).jpg\" /></a></div>" +
                  "<div class=\"s1_text\"><a href=\"" + request.getContextPath() + "/FruitServlet?key=info&id=" + user.getId() + "&fid=" + fruit.getFid() + "\">" + fruit.getFname() + "</a></div>" +
                  "</div>" +
                  "<div class=\"s2\">" + fruit.getSpec() + "</div>" +
                  "<div class=\"s3\">￥" + fruit.getUp() + "</div>" +
                  "<div class=\"s4\"><input type=\"number\" min=\"1\" name=\"qty_" + fruit.getFid() + "\" value=\"1\" style=\"width:60px;\"/></div>" +
                  "<div class=\"s5\">按数量结算</div>" +
                  "<div class=\"s6\">" +
                  "<a href=\"" + request.getContextPath() + "/ShopServlet?key=del&uid=" + user.getId() + "&fid=" + fruit.getFid() + "&str=cart\">删除</a>" +
                  "</div>" +
                  "<input type=\"hidden\" name=\"fid\" value=\"" + fruit.getFid() + "\"/>" +
                  "</div>");
        }
      %>

      <div class="shop_footer">&nbsp;</div>
      <div class="sum_mon" style="position: static;margin: 20px 0;">
        <div class="money">参考金额：<span id="money">￥<%=total%></span></div><br/>
        <input type="submit" id="addmon" value="订单结算"/>
        <a href="<%=request.getContextPath()%>/OrderServlet?key=my" style="margin-left: 15px;">查看我的订单</a>
      </div>
    </form>
  </div>
</div>
<jsp:include page="footer/footer.jsp"></jsp:include>
</body>
</html>
