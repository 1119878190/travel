package cn.demo.travel.web.servlet;

import cn.demo.travel.domain.ResultInfo;
import cn.demo.travel.domain.User;
import cn.demo.travel.service.UserService;
import cn.demo.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码
        Map<String, String[]> map = request.getParameterMap();

        //封装
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用Service查询
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        //将数据存入session
        HttpSession session = request.getSession();
        session.setAttribute("user",u);

        ResultInfo info = new ResultInfo();
        //4.判断用户对象是否为null
        if (u == null){
            //用户名密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        if (u != null){
            //用户对象不为空,判断用户状态
            if ("Y".equals(u.getStatus())){
                //已激活
                info.setFlag(true);
            }else if (!"Y".equals(u.getStatus())){
                //尚未激活
                info.setFlag(false);
                info.setErrorMsg("您尚未激活，请激活");
            }
        }

        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
