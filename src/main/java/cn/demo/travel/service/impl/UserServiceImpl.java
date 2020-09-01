package cn.demo.travel.service.impl;

import cn.demo.travel.dao.UserDao;
import cn.demo.travel.dao.impl.UserDaoImpl;
import cn.demo.travel.domain.User;
import cn.demo.travel.service.UserService;
import cn.demo.travel.util.MailUtils;
import cn.demo.travel.util.UuidUtil;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     * @param user
     * @return
     */
    @Override
    public boolean regist(User user) {
        //1.调用dao查询查询用户名
        User u = userDao.findByUsername(user.getUsername());
        if (u != null){
            //用户名存在，保存失败
            return false;
        }
        //2.保存
        //2.1设置激活码，唯一字符串
        user.setCode(UuidUtil.getUuid());
        //2.2设置激活状态
        user.setStatus("N");
        userDao.save(user);

        //3.激活邮件发送，邮件正文
        String content = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    /**
     * 邮件激活
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        //1.根据激活码查询用户对象
        User user = userDao.findByCode(code);
       if (user != null){
           //2.调用dao的修改激活状态的方法
           userDao.updateStatus(user);
            return true;
       }else {
           return false;
       }

    }

    /**
     * 用户登录
      * @param user
     * @return
     */
    @Override
    public User login(User user) {

        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
