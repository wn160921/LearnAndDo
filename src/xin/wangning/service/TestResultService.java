package xin.wangning.service;

import xin.wangning.dao.TestResultDao;
import xin.wangning.domain.TestResult;

import java.util.List;

public class TestResultService {
    private TestResultDao dao = new TestResultDao();
    public List<TestResult> findAll(){
        return dao.findAll();
    }

    public void add(TestResult result){
        dao.add(result);
    }

    public TestResult findByUsername(String name){
        return  dao.findByUsername(name);
    }
}
