package cn.demo.travel.service.impl;

import cn.demo.travel.dao.CategoryDao;
import cn.demo.travel.dao.impl.CategoryDaoImpl;
import cn.demo.travel.domain.Category;
import cn.demo.travel.service.CategoryService;
import cn.demo.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {

        //1.从redis中查询
        //1.1获取Jedis的客户端
        Jedis jedis = JedisUtil.getJedis();
        //1.2可以使用sortedset排序查询
       // Set<String> categorys = jedis.zrange("category", 0, -1);
        //1.3查询sortedset中的分数cid和值cname
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);

        List<Category> cs = null;
        //2.判断查询的集合书否为空
        if (categorys == null || categorys.size() == 0){
            //System.out.println("从数据库中查询");
            //3.如果为空，需要从数据库中查询，再将数据库存入jedis中
            //3.1从数据库中查询
           cs = categoryDao.findAll();
           //3.2将集合数据存储到redis中
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }

        }else {
            //4.如果不为空，将set的数据存入List中
            //System.out.println("从redis中查询");
            cs = new ArrayList<>();
            for (Tuple name : categorys){
                Category category = new Category();
                category.setCid((int)name.getScore());
                category.setCname(name.getElement());
                cs.add(category);
            }
        }
        return cs;
    }
}
