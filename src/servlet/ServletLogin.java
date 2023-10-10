package servlet;

import cn.csxy.project.domain.User;
import cn.csxy.project.mapper.Link;
import cn.csxy.project.mapper.ProductMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@WebServlet("/login")

public class ServletLogin extends HttpServlet {
    SqlSession sqlSession;
    static int mach=0;
    @Override
    public void init(){
            sqlSession = Link.sqlSession();
    }

    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            mach=0;
        }
    };

    @Override

    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
//        SqlSession sqlSession = Link.sqlSession();
        String password = request.getParameter("password");
        String username = request.getParameter("username");
        ProductMapper mapper = sqlSession.getMapper(ProductMapper.class);
        User users=mapper.selectusername(username);
        if(mach>=3){
            Timer timer=new Timer();
            System.out.println("请30s后重试");
            timer.schedule(timerTask,30000,30000);
            return;
        }
        if(users==null){
            System.out.println("账号不存在");
        }else if (password.equals(users.getPassword())){
            System.out.println("登录成功");
        }else {
            mach++;
            System.out.println("密码错误");
        }

    }




}
