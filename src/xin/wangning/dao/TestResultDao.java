package xin.wangning.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
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

    public TestResult findByUsername(String username){
        try {
            String sql = "select * from testResults where username = ?";
            return qr.query(sql,new BeanHandler<TestResult>(TestResult.class),username);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void add(TestResult testResult){
        try{
            String sql = "insert into testResults values(?,?,?,?)";
            Object param[] = {testResult.getUsername(),testResult.getTestNumber(),testResult.getResultJSON()};
            qr.update(sql,param);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
