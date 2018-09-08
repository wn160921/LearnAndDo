package xin.wangning.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import xin.wangning.domain.TestResult;

import java.sql.SQLException;
import java.util.List;

public class TestResultDao {
    private TxQueryRunner qr = new TxQueryRunner();

    public List<TestResult> findAll(){
        try{
            String sql = "select * from testResults";
            return qr.query(sql,new BeanListHandler<TestResult>(TestResult.class));
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public TestResult findByUsernameAndTestNum(String username,String testNUm){
        try {
            String sql = "select * from testResults where username = ? and testNumber = ?";
            return qr.query(sql,new BeanHandler<TestResult>(TestResult.class),username,testNUm);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void add(TestResult testResult){
        System.out.println("add testResult");
        try{
            String sql = "insert into testResults values(?,?,?,?)";
            Object param[] = {testResult.getUsername(),testResult.getTestNumber(),testResult.getResultJSON(),testResult.getFinish()};
            qr.update(sql,param);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void update(TestResult testResult){
        System.out.println("update testResult");
        System.out.println(testResult.getResultJSON());
        try{
            String sql = "update testResults set resultJSON = ?,finish = ? where username=? and testNumber=?";
            Object param[] = {testResult.getResultJSON(),testResult.getFinish(),testResult.getUsername(),testResult.getTestNumber()};
            qr.update(sql,param);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}
