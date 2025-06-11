package cn.cyanbukkit.example.cyanlib.mysql;

import java.util.HashMap;

/**
 * MySQL快捷语句枚举
 * <p>
 * 提供常用MySQL操作的预定义语句模板，简化SQL语句构建过程。
 * 注意：本类仅提供语句模板，实际数据库连接需要自行实现。
 *
 * <p><b>使用示例：</b>
 * <pre>
 * // 创建数据库
 * String createDb = MySQLFunction.CREATE_DB.sql.replace("?", "my_database");
 *
 * // 插入数据
 * HashMap<String, Object> data = new HashMap<>();
 * data.put("name", "test");
 * data.put("value", 123);
 * String insertSql = MySQLFunction.insert(data, "my_table");
 *
 * // 执行SQL 先准备后执行
 * statement.prepareStatement(insertSql).executeUpdate();
 * </pre>
 */
public enum MySQLFunction {

    /**
     * 显示所有数据库
     */
    SHOW_DB("SHOW DATABASES"),

    /**
     * 使用指定数据库
     */
    USE_DB("USE ?"),

    /**
     * 显示当前数据库的所有表
     */
    SHOW_TABLE("SHOW TABLES"),

    /**
     * 创建数据库
     */
    CREATE_DB("CREATE DATABASE ?"),

    /**
     * 如果不存在则创建数据库
     */
    IF_NOT_EXIST_CREATE_DB("CREATE DATABASE IF NOT EXISTS ?"),

    /**
     * 删除数据库
     */
    DELETE_DB("DROP DATABASE ?"),

    /**
     * 如果存在则删除数据库
     */
    IF_EXIST_DELETE_DB("DROP DATABASE IF EXISTS ?"),

    /**
     * 创建表前缀语句(需自行补充表结构定义)
     * <p>示例：CREATE_TABLE_PREFIX.sql + "my_table (id INT, name VARCHAR(20))"
     */
    CREATE_TABLE_PREFIX("CREATE TABLE IF NOT EXISTS "),

    /**
     * 查询表中所有数据
     */
    SELECT_ALL("SELECT * FROM ?"),

    /**
     * 条件查询数据
     * <p>支持运算符：=, !=, >, <, >=, <=, AND, OR, LIKE, IN, NOT等
     */
    FIND("SELECT * FROM ? WHERE ?"),

    /**
     * 更新数据语句模板
     * <p>需配合{@link #update(String, HashMap, String)}方法使用
     */
    UPDATE("UPDATE * SET %keyAndNewValue% WHERE %condition%"),

    /**
     * 删除符合条件的数据
     */
    DELETE_DATA("DELETE FROM %table% WHERE %condition%"),

    /**
     * 联合查询(已废弃，建议使用更明确的JOIN语句)
     * @deprecated 使用JOIN替代
     */
    @Deprecated
    UNION("SELECT * FROM %table1% WHERE %condition1% UNION SELECT * FROM %table2% WHERE %condition2% [ORDER BY column1, column2, ...];");

    /**
     * SQL语句模板
     */
    public final String sql;

    MySQLFunction(String sql) {
        this.sql = sql;
    }

    /**
     * 构建INSERT插入语句
     * <p>
     * 如需检测重复键，可在返回语句后追加：
     * <pre>ON DUPLICATE KEY UPDATE uuid = uuid, type = VALUES(type), ...</pre>
     *
     * @param keyAndValues 字段名和对应值的映射表
     * @param tableName 目标表名
     * @return 完整的INSERT语句
     *
     * <p><b>示例：</b>
     * <pre>
     * HashMap<String, Object> data = new HashMap<>();
     * data.put("name", "test");
     * data.put("value", 123);
     * String sql = MySQLFunction.insert(data, "my_table");
     * // 输出: INSERT INTO my_table (name,value) VALUES ('test','123')
     * </pre>
     */
    public static String insert(HashMap<String, Object> keyAndValues, String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(tableName).append(" (");
        keyAndValues.keySet().forEach(key -> sb.append(key).append(","));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        keyAndValues.values().forEach(value -> sb.append("'").append(value).append("',"));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * 构建条件查询语句
     *
     * @param tableName 目标表名
     * @param condition 查询条件表达式(如: "name = 'test' AND value > 100")
     * @return 完整的SELECT语句
     */
    public static String find(String tableName, String condition) {
        return FIND.sql.replace("?", tableName).replace("?", condition);
    }

    /**
     * 构建UPDATE更新语句
     *
     * @param tableName 目标表名
     * @param keyAndNewValue 要更新的字段和对应新值
     * @param condition 更新条件表达式
     * @return 完整的UPDATE语句
     */
    public static String update(String tableName, HashMap<String, Object> keyAndNewValue, String condition) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");
        keyAndNewValue.forEach((key, value) -> sb.append(key).append(" = '").append(value).append("',"));
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE ").append(condition);
        return sb.toString();
    }
}
