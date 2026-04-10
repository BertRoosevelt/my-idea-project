package com.fruitDayDB.service;

import com.fruitDayDB.dao.ShopDao;
import com.fruitDayDB.dao.ShopDaoImpl;
import com.fruitDayDB.vo.Cart;
import com.fruitDayDB.vo.Fruit;
import com.fruitDayDB.vo.ShopRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by soso.
 */
public class ShopService {
    public static boolean del(int id,int fid)
    {
        ShopDao shopDao=new ShopDaoImpl();
        int num=shopDao.del(id,fid);
        if(num==1)
            return true;
        else
            return false;
    }

    public static List<Cart> showAll(int id)
    {
        ShopDao shopDao=new ShopDaoImpl();

        return shopDao.show(id);
    }
    public static List<Fruit> show(int id,boolean boo)
    {
        List<Cart> carts=ShopService.showAll(id);

        List<Fruit> fruits=new ArrayList<Fruit>();

        if(boo)
        {
            for(Cart cart:carts)
            {
                if(cart.isCart())
                {
                    Fruit fruit=new Fruit();
                    fruit=FruitService.info(cart.getFid());
                    fruits.add(fruit);
                }
            }
        }
        else
        {
            for(Cart cart:carts)
            {
                if(cart.isStar())
                {
                    Fruit fruit=new Fruit();
                    fruit=FruitService.info(cart.getFid());
                    fruits.add(fruit);
                }
            }
        }

        return fruits;
    }

    public static void add(int id,Cart cart)
    {
        ShopDao shopDao=new ShopDaoImpl();
        shopDao.add(id, cart);
    }

    public static Cart find(int id,int fid)
    {
        ShopDao shopDao=new ShopDaoImpl();
        return shopDao.find(id,fid);
    }

    public static boolean up(int id,Cart cart)
    {
        ShopDao shopDao=new ShopDaoImpl();
        int num=shopDao.up(id,cart);
        if(num==1)
            return true;
        else
            return false;

    }

    public static List<ShopRecord> allRecords() {
        ShopDao shopDao = new ShopDaoImpl();
        return shopDao.findAllRecords();
    }

    public static int cartCount() {
        int count = 0;
        for (ShopRecord record : allRecords()) {
            if (record.isCart()) {
                count++;
            }
        }
        return count;
    }

    public static int starCount() {
        int count = 0;
        for (ShopRecord record : allRecords()) {
            if (record.isStar()) {
                count++;
            }
        }
        return count;
    }
}
