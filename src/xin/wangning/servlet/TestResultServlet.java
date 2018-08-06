package xin.wangning.servlet;

import com.alibaba.fastjson.JSON;
import xin.wangning.domain.TestResult;
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
}
