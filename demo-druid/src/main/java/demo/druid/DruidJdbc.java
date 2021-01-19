package demo.druid;

import org.apache.calcite.avatica.AvaticaConnection;
import org.apache.calcite.avatica.AvaticaStatement;

import java.sql.DriverManager;
import java.util.Properties;

public class DruidJdbc {


    public static void main(String[] args) throws Exception{

        String url = "jdbc:avatica:remote:url=http://brokerwx.druid.data.sina.com.cn/druid/v2/sql/acatica/";
        Properties config = new Properties();
        AvaticaConnection conn = (AvaticaConnection)DriverManager.getConnection(url,config);
        /*AvaticaStatement statement = conn.createStatement();
        statement.execute();*/


    }

}
