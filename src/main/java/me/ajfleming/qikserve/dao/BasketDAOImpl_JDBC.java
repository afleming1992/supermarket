package me.ajfleming.qikserve.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrew on 15/01/17.
 */
public class BasketDAOImpl_JDBC implements BasketDAO {

    private JdbcTemplate jdbc;

    public BasketDAOImpl_JDBC(DataSource ds){
        jdbc = new JdbcTemplate(ds);
    }

    @Override
    public int createBasket() {
        SimpleJdbcInsert insertQuery = new SimpleJdbcInsert(jdbc).withTableName("basket").usingColumns("completed").usingGeneratedKeyColumns("id");
        Map parameters = new HashMap<String, String>();
        parameters.put("completed", false);
        return insertQuery.executeAndReturnKey(parameters).intValue();
    }
}
