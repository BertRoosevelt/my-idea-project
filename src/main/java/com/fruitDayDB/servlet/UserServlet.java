package com.fruitDayDB.servlet;


import com.fruitDayDB.service.UserService;
import com.fruitDayDB.vo.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by soso.
 */
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String key=req.getParameter("key");
        if (key == null) {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        if(key.equals("add"))
            doAdd(req,resp);
        else if(key.equals("login"))
            doLogin(req,resp);
        else if(key.equals("logout"))
            doLogout(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    protected void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("email");
        String phone=req.getParameter("phone");
        String pwd=req.getParameter("pwd1");
        String pwd2=req.getParameter("pwd2");

        if (email == null || phone == null || pwd == null || pwd.trim().isEmpty() || !pwd.equals(pwd2)) {
            req.setAttribute("error", "注册信息不合法或两次密码不一致");
            req.getRequestDispatcher("/reg.jsp").forward(req, resp);
            return;
        }

        User u=new User(email,phone,pwd);

       User user=UserService.add(u);

        if(user!=null)
        {
            HttpSession session=req.getSession();
            session.setAttribute("user",user);
            session.setAttribute("admin", false);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else {
            req.setAttribute("error", "注册失败，请检查邮箱/手机号是否已存在");
            req.getRequestDispatcher("/reg.jsp").forward(req, resp);
        }
    }

    protected void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str=req.getParameter("str");
        String pwd=req.getParameter("pwd");
        String loginType=req.getParameter("loginType");
        if (str == null || pwd == null) {
            req.setAttribute("error", "请输入账号和密码");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        boolean boo;
        if(str.indexOf("@")!=-1)
            boo=true;
        else
            boo=false;

        User u=UserService.login(str,pwd,boo);

        if(u!=null)
        {
            HttpSession session=req.getSession();
            session.setAttribute("user",u);
            List<Integer> uids=UserService.root();
            boolean isAdmin = false;
            for(int i:uids)
            {
                if(u.getId()==i)
                {
                    isAdmin = true;
                    break;
                }
            }

            if ("admin".equals(loginType)) {
                if (isAdmin) {
                    session.setAttribute("admin", true);
                    req.getRequestDispatcher("/BSServlet?key=dashboard").forward(req, resp);
                } else {
                    session.setAttribute("admin", false);
                    req.setAttribute("error", "当前账号不是管理员账号");
                    req.getRequestDispatcher("/login.jsp").forward(req, resp);
                }
                return;
            }

            session.setAttribute("admin", false);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else {
            req.setAttribute("error", "账号或密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    protected void doLogout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session=req.getSession(false);
        if(session!=null)
            session.invalidate();
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

}
