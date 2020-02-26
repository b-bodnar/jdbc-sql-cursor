package cursor.jdbc.util;

import cursor.jdbc.annotation.Name;
import cursor.jdbc.annotation.Table;
import cursor.jdbc.model.Account;
import cursor.jdbc.model.Model;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class DbUtil {

    private static DbUtil instance;
    private static final String URL = "jdbc:mysql://localhost:3306/dev_profiles_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1111";

    public static DbUtil getInstance() {
        return Objects.isNull(instance)
                ? new DbUtil()
                : instance;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void main(String[] args) {
        Account account = new Account(1, "bogdan", "bodnar", "Lviv", "Male", "ebvegeg");
        selectById(1, Account.class);
    }

    public static int insert(Model model) {

        return 201;
    }

    public static <T extends Model> Model selectById(int id, Class<T> model) {

        String sql = generateSelectSql(id, model);

        Field[] fields = model.getDeclaredFields();
        Method[] methods = model.getMethods();

        for (Field field : fields) {
            for (Method method : methods) {

                if (method.getName().toLowerCase().contains("set") && field.getName().toLowerCase().contains(field.getName())){


                    method.setAccessible(true);
                    method.invoke()
                }
            }

        }


        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Model modelClass = model.getConstructor().newInstance();

               resultSet.getObject()


            }


        } catch (SQLException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T extends Model> String generateSelectSql(int id, Class<T> model) {
        return "SELECT * FROM " +
                model.getAnnotation(Table.class).name() +
                " WHERE id =" +
                id;
    }

    private static String getFieldsName(Model model) {
        return Arrays.stream(model.getClass().getDeclaredFields())
                .map(DbUtil::getFieldName)
                .collect(Collectors.joining(","));
    }

    private static String getFieldName(Field field) {
        return field.isAnnotationPresent(Name.class)
                ? field.getAnnotation(Name.class).name()
                : field.getName();
    }
}


