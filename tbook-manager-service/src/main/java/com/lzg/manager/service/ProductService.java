package com.lzg.manager.service;

import com.lzg.common.exception.TBookException;
import com.lzg.common.utlis.KeyUtil;
import com.lzg.manager.redis.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者：LizG on 2018/8/3 22:44
 * 描述：
 */
@Service
public class ProductService {

    @Autowired
    RedisLock redisLock;
    //商品
    static Map<String,Integer> product = new HashMap<>();

    //库存
    static Map<String,Integer> stock = new HashMap<>();

    //用户购买
    static Map<String,String> order = new HashMap<>();

    static {
        product.put("5566",10000);
        stock.put("5566",10000);
    }

    public String queryMap(String productId){
        return "商品总数有：" + product.get(productId) + "库存还剩：" + stock.get(productId) +"--"+ order.size() + "人购买";
    }

    public void buy(String productId){
        String identity = redisLock.tryGetDistributedLock(productId);
        if (identity == null){
            System.out.println("访问人太多了");
            return;
        }
        Integer pNum = stock.get(productId);
        if(pNum == 0){
            System.out.println("没有这个商品");
        }else {
            order.put(KeyUtil.getKey(),productId);
            pNum-=1;
            stock.put(productId,pNum);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        redisLock.releaseLock(productId,identity);
        System.out.println("释放锁");
    }
}
