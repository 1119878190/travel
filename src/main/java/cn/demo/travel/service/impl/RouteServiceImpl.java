package cn.demo.travel.service.impl;

import cn.demo.travel.dao.FavoriteDao;
import cn.demo.travel.dao.RouteDao;
import cn.demo.travel.dao.RouteImgDao;
import cn.demo.travel.dao.SellerDao;
import cn.demo.travel.dao.impl.FavoriteDaoImpl;
import cn.demo.travel.dao.impl.RouteDaoimpl;
import cn.demo.travel.dao.impl.RouteImgDaoImpl;
import cn.demo.travel.dao.impl.SellerDaoImpl;
import cn.demo.travel.domain.*;
import cn.demo.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoimpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 根据分类查询线路
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //这只当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);

        //设置总记录数
       int totalCoutn =  routeDao.findTotalCount(cid,rname);
       pb.setTotalCount(totalCoutn);
       //设置当前页显示的数据集合
        int start = (currentPage -1) * pageSize;
        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCoutn % pageSize == 0 ? totalCoutn / pageSize : totalCoutn / pageSize +1;
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * 更具rid查询route对象
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        //1.根据rid去route表中查询route对象
        Route route = routeDao.findOne(Integer.parseInt(rid));

        //2.根据route的rid，查询图片信息集合
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        //2.2将集合设置到route对象
        route.setRouteImgList(routeImgList);

        //3.根据route的sid查询（商家id） 查询商家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        //4.查询线路收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }


}
