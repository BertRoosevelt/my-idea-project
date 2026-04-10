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
  <%
    String show="x0";
    if(request.getAttribute("sky")!=null)
      show=(String)request.getAttribute("sky");
  %>
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
    </div>
  </div>

</div>

<div class="gong" id="x5">
  <div class="con">
    <div class="form" style="margin-top: 40px;">
      <form action="/x-test/BSServlet?key=addfruit" method="post">
        <div class="add">
          <span class="add_tit">玩具编号 ：</span>
          <span class="add_text"><input type="text" name="fid" id="fid" value="" /></span>
        </div>
        <div class="add">
          <span class="add_tit">玩具 ：</span>
          <span class="add_text"><input type="text" name="fname" id="fname1" value="" /></span>
        </div>

        <div class="add">
          <span class="add_tit">规格 ：</span>
          <span class="add_text"><input type="text" name="spec" id="spec1" value="" /></span>
        </div>

        <div class="add">
          <span class="add_tit">单价 ：</span>
          <span class="add_text"><input type="text" name="up" id="up1" value="" /></span>
        </div>

        <div class="add">
          <span class="add_tit" >商品简介 ：</span>
          <span class="add_text"><input type="text" class="long" name="t1" id="t11" value="" /></span>
        </div>

        <div class="add">
          <span class="add_tit">温馨提示 ：</span>
          <span class="add_text"><input type="text" class="long" name="t2" id="t21" value="" /></span>
        </div>

        <div class="add">
          <span class="add_tit">图片个数 ：</span>
          <span class="add_text"><input type="text" name="inum" id="inum1" value="" /></span>
        </div>

        <div class="add_sublmit">
          <input type="submit" value="添加"/>
        </div>
        <%
          if (request.getAttribute("error") != null) {
            out.print("<div style='color:#cc3300;margin-left:200px;'>" + request.getAttribute("error") + "</div>");
          }
        %>
      </form>
    </div>
  </div>
</div>


</body>
</html>
