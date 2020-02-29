package cursor.jdbc.util;

import cursor.jdbc.annotation.Name;
import cursor.jdbc.annotation.Table;
import cursor.jdbc.model.Model;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class DbUtil {

    private static DbUtil instance;
    private static final String URL = "jdbc:mysql://localhost:3306/dev_profiles_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Dream1990";

    public static DbUtil getInstance() {
        return Objects.isNull(instance)
                ? new DbUtil()
                : instance;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static <T extends Model> Model findById(int id, Class<T> model) {

        var findById = "SELECT * FROM "
                + getTableName(model)
                + " WHERE id = ?";

        Model modelInstance = null;

        try (PreparedStatement statement = getConnection().prepareStatement(findById)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            modelInstance = createAndGetModel(model, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modelInstance;
    }

    public static <T extends Model> List<Model> findAll(Class<T> model) {

        var findAll = "SELECT * FROM " + getTableName(model);
        List<Model> models = new ArrayList<>();

        try (PreparedStatement statement = getConnection().prepareStatement(findAll)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                models.add(createAndGetModel(model, resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return models;
    }

    public static void insert(Model model) {
        var insert = "INSERT INTO " + getTableName(model.getClass())
                + "(" + getFieldsName(model) + ")"
                + " VALUES ("
                + getDataForInsert(model)
                + ")";
        try (PreparedStatement statement = getConnection().prepareStatement(insert)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(int id, Model model) {
        var update = updateSQLGenerator(model);

        try (PreparedStatement statement = getConnection().prepareStatement(update)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Model> void delete(int id, Class<T> tableName) {

        var delete = "DELETE FROM "
                + getTableName(tableName)
                + " WHERE id = ?;";

        try (PreparedStatement statement = getConnection().prepareStatement(delete)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SneakyThrows
    private static <T extends Model> Model createAndGetModel(Class<T> model, ResultSet resultSet) {

        resultSet.next();
        Model modelClass = model.getConstructor().newInstance();
        Field[] fields = model.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(modelClass, resultSet.getObject(getFieldName(field)));
            field.setAccessible(false);
        }
        return modelClass;
    }

    private static <T extends Model> String getTableName(Class<T> model) {
        return model.getAnnotation(Table.class).name();
    }

    private static String getFieldsName(Model model) {
        return Arrays.stream(model.getClass().getDeclaredFields())
                .map(DbUtil::getFieldName)
                .filter(fieldName -> !fieldName.equals("id"))
                .collect(Collectors.joining(","));
    }

    private static String getFieldName(Field field) {
        return field.isAnnotationPresent(Name.class)
                ? field.getAnnotation(Name.class).name()
                : field.getName();
    }

    private static String getDataForInsert(Model model) {
        return Arrays.stream(model.getClass().getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .filter(field -> !field.getName().equals("id"))
                .map(field -> getFieldValue(field, model))
                .map(value -> "'" + value + "'")
                .collect(Collectors.joining(","));
    }

    private static String getFieldValue(Field field, Model model) {
        try {
            field.setAccessible(true);
            var value = field.get(model).toString();
            field.setAccessible(false);
            return value;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String updateSQLGenerator(Model model) {

        Field[] fields = model.getClass().getDeclaredFields();
        List<String> data = new ArrayList<>();
        for (Field field : fields) {
            data.add(getFieldName(field) + " = '" + getFieldValue(field, model) + "'");
        }
        return "UPDATE " + getTableName(model.getClass())
                + " SET "
                + String.join(",", data)
                + "WHERE id = ?";
    }
}


