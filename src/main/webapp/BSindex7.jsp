<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.fruitDayDB.vo.ShopRecord" %>
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
<body>
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
    </div>
  </div>
</div>

<div class="gong" id="x7">
  <div class="con">
    <div class="tit">
      <ul>
        <li>用户ID</li>
        <li class="i">&nbsp;</li>
        <li>商品ID</li>
        <li class="i">&nbsp;</li>
        <li>购物车</li>
        <li class="i">&nbsp;</li>
        <li>收藏</li>
        <li class="i">&nbsp;</li>
        <li>操作</li>
      </ul>
    </div>

    <%
      List<ShopRecord> records = new ArrayList<ShopRecord>();
      if(request.getAttribute("shopRecords") != null) {
        records = (List<ShopRecord>) request.getAttribute("shopRecords");
      }
      if (request.getAttribute("error") != null) {
        out.print("<div style='color:#cc3300;margin-top:20px;'>" + request.getAttribute("error") + "</div>");
      }
      for(ShopRecord record : records) {
        out.print("<div class=\"info\"><ul>" +
                "<li>" + record.getUid() + "</li>" +
                "<li class=\"i\">&nbsp;</li>" +
                "<li>" + record.getFid() + "</li>" +
                "<li class=\"i\">&nbsp;</li>" +
                "<li>" + (record.isCart() ? "是" : "否") + "</li>" +
                "<li class=\"i\">&nbsp;</li>" +
                "<li>" + (record.isStar() ? "是" : "否") + "</li>" +
                "<li class=\"i\">&nbsp;</li>" +
                "<li><a href=\"/x-test/BSServlet?key=delshop&uid=" + record.getUid() + "&fid=" + record.getFid() + "\">删除</a></li>" +
                "</ul></div>");
      }
    %>
  </div>
</div>
</body>
</html>
