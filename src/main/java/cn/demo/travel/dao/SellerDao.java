package cn.demo.travel.dao;

import cn.demo.travel.domain.Seller;

//查询卖家
public interface SellerDao {
    /**
     * 根据sid查询卖家对象
     * @param rid
     * @return
     */
    public Seller findById(int sid);
}
