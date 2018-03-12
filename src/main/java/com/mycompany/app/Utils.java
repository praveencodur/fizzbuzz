package com.mycompany.app;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Utils {
     static String getResultString(ResultSet rs) throws SQLException {
        StringBuilder build = new StringBuilder();
        build.append("<HTML><BODY><TABLE border=1>");
        build.append("<TR>");
        for(int i =0 ; i < rs.getMetaData().getColumnCount() ; i ++){
            build.append("<TH>");
            build.append(rs.getMetaData().getColumnName(i+1));
            build.append("</TH>");
        }
        build.append("</TR>");
        while (rs.next()){
            build.append("<TR>");
            for(int i =0 ; i < rs.getMetaData().getColumnCount() ; i ++){

                build.append("<TD>");
                build.append(rs.getString(i+1));
                build.append("</TD>");
            }
            build.append("</TR>");
        }
        build.append("</TABLE></BODY></HTML>");
        return build.toString();
    }
}
