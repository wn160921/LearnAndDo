package xin.wangning.dao;

import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import xin.wangning.domain.User;

import java.sql.SQLException;


public class UserdDao {
    private TxQueryRunner qr = new TxQueryRunner();
    public User findByName(String username){
        try{
            String sql = "select * from User where username = ?";
            return qr.query(sql,new BeanHandler<>(User.class),username);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}
