package com.mycompany.app;

import org.h2.fulltext.FullTextLucene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.mycompany.app.Utils.getResultString;

@RestController
public class H2EmbededController {


    @Autowired
    @Qualifier("h2")
    private DataSource dataSource;

    @GetMapping(value = "/h2/searchNative")
    public String  searchNative(@RequestParam String searchText) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("select * from FT_SEARCH_DATA('"+searchText+"',0,0)");
        return getResultString(rs);
    }

    @GetMapping(value = "/h2/searchLuceneP")
    public String  searchLuceneP(@RequestParam String searchText) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("select * from FTL_SEARCH_DATA('%"+searchText+"%',0,0)");
        return getResultString(rs);
    }

    @GetMapping(value = "/h2/searchLucene")
    public String  searchLucene(@RequestParam String searchText) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("select * from FTL_SEARCH_DATA('"+searchText+"',0,0)");
        return getResultString(rs);
    }

    @GetMapping(value = "/h2/query")
    public String  query(@RequestParam String searchText) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("select * from DimensionValue where CODE like ('%"+searchText+"%')");
        return getResultString(rs);
    }

    @GetMapping(value = "/h2/searchJava")
    public String  searchJava(@RequestParam String searchText, @RequestParam Integer rows) throws Exception{
        ResultSet rs =  FullTextLucene.searchData(dataSource.getConnection(), searchText, rows, 0);
        return getResultString(rs);
    }



    @GetMapping(value = "/h2/count")
    public String count() throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery("SELECT count(1) FROM DimensionValue");
        StringBuilder build = new StringBuilder();
        while(rs.next()){
            build.append("ID:");
            build.append(rs.getInt(1));
        }
        return build.toString();
    }

    @GetMapping(value = "/h2/exec")
    public String  exec(@RequestParam String sql) throws Exception{
        ResultSet rs = dataSource.getConnection().createStatement().executeQuery(sql);
        return getResultString(rs);
    }

}
