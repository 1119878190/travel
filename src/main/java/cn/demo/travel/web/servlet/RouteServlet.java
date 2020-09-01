package cn.demo.travel.web.servlet;

import cn.demo.travel.domain.PageBean;
import cn.demo.travel.domain.Route;
import cn.demo.travel.domain.User;
import cn.demo.travel.service.FavoriteService;
import cn.demo.travel.service.RouteService;
import cn.demo.travel.service.impl.FavoriteServiceImpl;
import cn.demo.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();
    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.接受参数
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");

        //接受rname，线路名称
        String rname = request.getParameter("rname");
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");//解决rname乱码
        //2.处理参数
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)){
            cid  = Integer.parseInt(cidStr);
        }
        int pageSize = 0; //当前页码，如果不传递，则默认为第一页
        if (pageSizeStr != null && pageSizeStr.length() > 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            pageSize = 5;
        }
        int currentPage = 0; //当前页码，如果不传递，则默认为每页显示5条信息
        if (currentPageStr != null && currentPageStr.length() > 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            currentPage = 1;
        }

        //3.调用Service 查询PageBean对象
        PageBean<Route> pb = service.pageQuery(cid,currentPage,pageSize,rname);


        //4.将PageBean序列化为json返回
        writeValue(pb,response);

    }

    /**
     * 根据rid查询一个旅游线路的详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取rid
        String rid = request.getParameter("rid");

        //2.调用service查询route对象
        Route route = service.findOne(rid);

        //3.转为json写回客户端
        writeValue(route,response);
    }

    /**
     * 判断当前登录用户是否收藏该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取线路rid
        String rid = request.getParameter("rid");

        //2.获取用户uid
        User user =(User) request.getSession().getAttribute("user");
        int uid ;//用户id
        if (user == null){
            //用户尚未登录
            uid = 0;
        }else {
            //用户已经登录
            uid = user.getUid();
        }

        //调用FavoriteService查询线路是否被收藏
       boolean flag =  favoriteService.isFavorite(rid,uid);

        //写回客户端
        writeValue(flag,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取线路rid
        String rid = request.getParameter("rid");
        //2.获取当前登录用户
        User user =(User) request.getSession().getAttribute("user");
        int uid ;//用户id
        if (user == null){
            //用户尚未登录
            return ;
        }else {
            //用户已经登录
            uid = user.getUid();
        }

        //3.调用service添加
        favoriteService.add(rid,uid);
    }

    /**
     * 取消收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public  void remFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid
        String rid = request.getParameter("rid");
        //2.获取uid
        User user = (User) request.getSession().getAttribute("user");
        int uid ;//用户id
        if (user == null){
            //用户尚未登录
            return ;
        }else {
            //用户已经登录
            uid = user.getUid();
        }

        //3.调用service添加
        favoriteService.rem(rid,uid);


    }

}
