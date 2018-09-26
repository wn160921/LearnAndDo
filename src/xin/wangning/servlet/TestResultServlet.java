package xin.wangning.servlet;

import cn.itcast.commons.CommonUtils;
import com.alibaba.fastjson.JSON;
import xin.wangning.domain.TestResult;
import xin.wangning.domain.User;
import xin.wangning.service.TestResultService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TestResultServlet extends BaseServlet {
    private TestResultService service = new TestResultService();
    public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TestResult> resultList = service.findAll();
        return JSON.toJSONString(resultList);
    }

    public String record(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //存在就更新，否则插入
        TestResult testResult = CommonUtils.toBean(req.getParameterMap(),TestResult.class);
        System.out.println("获取的数据"+testResult.getResultJSON()+testResult.getFinish());
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            return "can not find user";
        }
        testResult.setUsername(user.getUsername());
        service.updata(testResult);
        return "success";
    }

    public String findByUsernameAndTestNum(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String username = user.getUsername();
        String testNum = req.getParameter("testNum");
        if(username==null || username.isEmpty() || testNum==null || testNum.isEmpty()){
            return "needed data is empty";
        }
        TestResult testResult = service.findByUsernameAndTestNum(username,testNum);
        if(testResult==null){
            testResult = new TestResult();
            testResult.setFinish("0");
        }
        return JSON.toJSONString(testResult);
    }

}
