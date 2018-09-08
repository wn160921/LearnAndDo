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

    public TestResult findByUsername(String name,String testNum){
        return  dao.findByUsernameAndTestNum(name,testNum);
    }

    //存在更新，否则插入
    public void updata(TestResult testResult){
        TestResult testResult1 = dao.findByUsernameAndTestNum(testResult.getUsername(),testResult.getTestNumber());
        if(testResult1==null){
            dao.add(testResult);
        }else {
            dao.update(testResult);
        }
    }
}
