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
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }


    protected void doAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email=req.getParameter("email");
        String phone=req.getParameter("phone");
        String pwd=req.getParameter("pwd1");

        User u=new User(email,phone,pwd);

       User user=UserService.add(u);

        if(user!=null)
        {
            HttpSession session=req.getSession();
            session.setAttribute("user",user);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else
            req.getRequestDispatcher("/reg.jsp").forward(req, resp);
    }

    protected void doLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String str=req.getParameter("str");
        String pwd=req.getParameter("pwd");
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
            for(int i:uids)
            {
                if(u.getId()==i)
                {
                    session.setAttribute("admin", true);
                    req.getRequestDispatcher("BSindex.jsp").forward(req, resp);
                    return;
                }
            }
        }
        if(u!=null)
        {
            HttpSession session=req.getSession();
            session.setAttribute("user",u);
            session.setAttribute("admin", false);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
        else
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

}
