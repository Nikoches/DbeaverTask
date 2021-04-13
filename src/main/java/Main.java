import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        ResultSet resultSet =  new HqlRun().getGroupingBy();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2));
        }
    }
}
