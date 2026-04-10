package com.fruitDayDB.servlet;

import com.fruitDayDB.service.FruitService;
import com.fruitDayDB.service.OrderService;
import com.fruitDayDB.service.ShopService;
import com.fruitDayDB.service.UserService;
import com.fruitDayDB.vo.Fruit;
import com.fruitDayDB.vo.Order;
import com.fruitDayDB.vo.ShopRecord;
import com.fruitDayDB.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by xi on 2015/10/18.
 */
public class BSServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        if (!isAdmin(req)) {
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }
        String key=req.getParameter("key");
        if (key == null) {
            doDashboard(req, resp);
            return;
        }

        if("dashboard".equals(key))
            doDashboard(req, resp);
        else if("alluser".equals(key))
            doAlluser(req,resp);
        else if("deluser".equals(key))
            doDeluser(req,resp);
        else if("adduser".equals(key))
            doAdduser(req,resp);
        else if("upuser".equals(key))
            doUpuser(req,resp);
        else if("finduser".equals(key))
            doFinduser(req, resp);
        else if("allfruit".equals(key))
            doAllfruit(req,resp);
        else if("addfruit".equals(key))
            doAddfruit(req,resp);
        else if("findfruit".equals(key))
            doFindfruit(req,resp);
        else if("delfruit".equals(key))
            doDelfruit(req,resp);
        else if("hotfruit".equals(key))
            doHotfruit(req,resp);
        else if("upfruit".equals(key))
            doUpfruit(req,resp);
        else if("allshop".equals(key))
            doAllshop(req, resp);
        else if("delshop".equals(key))
            doDelshop(req, resp);
        else if("allorder".equals(key))
            doAllorder(req, resp);
        else if("shiporder".equals(key))
            doShiporder(req, resp);
        else if("cancelorder".equals(key))
            doCancelorder(req, resp);
        else
            doDashboard(req, resp);

    }

    protected void doDashboard(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ShopRecord> records = ShopService.allRecords();
        req.setAttribute("userCount", UserService.alluser().size());
        req.setAttribute("fruitCount", FruitService.all().size());
        req.setAttribute("cartCount", ShopService.cartCount(records));
        req.setAttribute("starCount", ShopService.starCount(records));
        req.setAttribute("orderCount", OrderService.allOrders().size());
        req.getRequestDispatcher("BSindex.jsp").forward(req, resp);
    }

    protected void doAllshop(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ShopRecord> records = ShopService.allRecords();
        req.setAttribute("shopRecords", records);
        req.getRequestDispatcher("BSindex7.jsp").forward(req, resp);
    }

    protected void doDelshop(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = parseInt(req.getParameter("uid"), 0);
        int fid = parseInt(req.getParameter("fid"), 0);
        if (uid > 0 && fid > 0) {
            ShopService.del(uid, fid);
        } else {
            req.setAttribute("error", "删除参数不合法");
        }
        doAllshop(req, resp);
    }

    protected void doAllorder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orders = OrderService.allOrders();
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("BSindex8.jsp").forward(req, resp);
    }

    protected void doShiporder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = parseInt(req.getParameter("orderId"), 0);
        if (!OrderService.adminShip(orderId)) {
            req.setAttribute("error", "发货失败，订单状态不允许发货");
        }
        doAllorder(req, resp);
    }

    protected void doCancelorder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = parseInt(req.getParameter("orderId"), 0);
        if (!OrderService.adminCancel(orderId)) {
            req.setAttribute("error", "取消失败，订单状态不允许取消");
        }
        doAllorder(req, resp);
    }

    protected void doUpfruit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fid= parseInt(req.getParameter("fid"), 0);
        if (fid <= 0) {
            doAllfruit(req, resp);
            return;
        }
        String fname=req.getParameter("fname");
        String spec=req.getParameter("spec");
        double up= parseDouble(req.getParameter("up"), 0);
        String t1=req.getParameter("t1");
        String t2=req.getParameter("t2");
        int inum= parseInt(req.getParameter("inum"), 0);
        if (up <= 0 || inum < 0) {
            req.setAttribute("error", "商品参数不合法");
            doFindfruit(req, resp);
            return;
        }
        Fruit fruit=new Fruit(fid,fname,spec,up,t1,t2,inum);

        FruitService.up(fruit);

        doAllfruit(req,resp);

    }
    protected void doHotfruit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Fruit> fruits= FruitService.hot();

        req.setAttribute("allfruit",fruits);

        req.getRequestDispatcher("BSindex4.jsp").forward(req, resp);
    }
    protected void doDelfruit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fid= parseInt(req.getParameter("fid"), 0);
        if (fid <= 0) {
            doAllfruit(req, resp);
            return;
        }

        FruitService.del(fid);

        doAllfruit(req, resp);


    }
    protected void doFindfruit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int fid= parseInt(req.getParameter("fid"), 0);
        if (fid <= 0) {
            doAllfruit(req, resp);
            return;
        }

        Fruit fruit=null;

        fruit=FruitService.info(fid);

        req.setAttribute("fruit",fruit);

        req.getRequestDispatcher("BSindex6.jsp").forward(req, resp);
    }

    protected void doAddfruit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname=req.getParameter("fname");
        String spec=req.getParameter("spec");
        double up= parseDouble(req.getParameter("up"), 0);
        String t1=req.getParameter("t1");
        String t2=req.getParameter("t2");
        int inum= parseInt(req.getParameter("inum"), 0);
        int fid= parseInt(req.getParameter("fid"), 0);
        if (fid <= 0 || fname == null || fname.trim().isEmpty() || up <= 0 || inum < 0) {
            req.setAttribute("error", "参数不合法");
            req.getRequestDispatcher("BSindex5.jsp").forward(req, resp);
            return;
        }
        Fruit fruit=new Fruit(fid,fname,spec,up,t1,t2,inum);

        boolean boo=FruitService.add(fruit);

        if(boo)
            doAllfruit(req,resp);
        else
            req.getRequestDispatcher("BSindex5.jsp").forward(req, resp);
    }
    protected void doAllfruit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Fruit> fruits= FruitService.all();

        req.setAttribute("allfruit",fruits);

        req.getRequestDispatcher("BSindex4.jsp").forward(req, resp);

    }

    protected void doAlluser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users= UserService.alluser();
        req.setAttribute("allusers",users);
        req.getRequestDispatcher("BSindex1.jsp").forward(req, resp);


    }

    protected void doDeluser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id=parseInt(req.getParameter("id"), 0);
        if (id <= 0) {
            doAlluser(req, resp);
            return;
        }

        User user=new User(id);

        UserService.del(user);


        doAlluser(req, resp);

    }

    protected void doAdduser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname=req.getParameter("name1");
        String email=req.getParameter("email1");
        String phone=req.getParameter("phone1");
        String pwd=req.getParameter("pwd1");
        if ((email == null || email.trim().isEmpty()) || (phone == null || phone.trim().isEmpty()) || (pwd == null || pwd.trim().isEmpty())) {
            req.setAttribute("error", "用户参数不合法");
            req.getRequestDispatcher("BSindex2.jsp").forward(req, resp);
            return;
        }

        User user=new User(email,phone,pwd,uname);

        User createdUser=UserService.add(user);
        if(createdUser!=null)
        {
            doAlluser(req,resp);
        } else {
            req.setAttribute("error", "新增用户失败，请检查邮箱/手机号是否重复");
            req.getRequestDispatcher("BSindex2.jsp").forward(req, resp);
        }


    }

    protected void doUpuser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uname=req.getParameter("uname2");
        String email=req.getParameter("email2");
        String phone=req.getParameter("phone2");
        String pwd=req.getParameter("pwd2");
        int id=parseInt(req.getParameter("id"), 0);
        if (id <= 0 || email == null || phone == null || pwd == null || uname == null) {
            doAlluser(req, resp);
            return;
        }

        User user=new User(id,email,phone,pwd,uname);

        boolean boo=UserService.upUser(user);

        if(boo)
        {
            doAlluser(req,resp);
        }

    }

    protected void doFinduser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=parseInt(req.getParameter("id"), 0);
        if (id <= 0) {
            doAlluser(req, resp);
            return;
        }

        User user=UserService.findById(id);

        if(user!=null)
        {
            req.setAttribute("user",user);
        }

        req.getRequestDispatcher("BSindex3.jsp").forward(req, resp);
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

    private double parseDouble(String value, double defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private boolean isAdmin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && Boolean.TRUE.equals(session.getAttribute("admin"));
    }
}
