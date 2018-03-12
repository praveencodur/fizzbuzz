package com.mycompany.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.ResultSet;

import static com.mycompany.app.Utils.getResultString;

@RestController
public class SQLLiteEmbededController {

    @Autowired
    @Qualifier("sqllite")
    private DataSource dataSource;

    @GetMapping(value = "/sql/exec")
    public String  exec(@RequestParam String sql) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery(sql);
        return Utils.getResultString(rs);
    }

    @GetMapping(value = "/sql/search")
    public String  search(@RequestParam String searchText) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("Select * from DimensionValue where CODE match '"+searchText+"'");
        return Utils.getResultString(rs);
    }

    @GetMapping(value = "/sql/query")
    public String  query(@RequestParam String searchText) throws Exception {
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("select * from DimensionValue where CODE like ('%" + searchText + "%')");
        return getResultString(rs);
    }


    @GetMapping(value = "/sql/count")
    public String count() throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("SELECT count(1) FROM DimensionValue");
        StringBuilder build = new StringBuilder();
        while(rs.next()){
            build.append("ID:");
            build.append(rs.getInt(1));
        }
        return build.toString();
    }

}
