package cn.cyanbukkit.example.cyanlib.mysql;

import java.util.HashMap;

/**
 * 快捷MySQL语句 内置所有MYSQL 的语句格式
 * 链接还得靠自己哦我只负责语句
 */
public enum MySQLFunction {

    SHOW_DB("SHOW DATABASES"),
    USE_DB("USE %database%"),
    SHOW_TABLE("SHOW TABLES"),
    CREATE_DB("CREATE DATABASE %database%"),
    IF_NOT_EXIST_CREATE_DB("CREATE DATABASE IF NOT EXISTS %database%"),
    DELETE_DB("DROP DATABASE %database%"),
    IF_EXIST_DELETE_DB("DROP DATABASE IF EXISTS %database%"),
    /**
     * 默认不存在就创 其他情况自己拼
     */
    CREATE_TABLE_PREFIX("CREATE TABLE IF NOT EXISTS "),
    /**
     * 下面是查询
     */
    SELECT_ALL("SELECT * FROM %table%"),
    /**
     * condition 限制表达式
     * Where  支持 = != > < >= <=
     * AND  组合
     * LIKE 模糊
     * IN   数组   IN ('US', 'CA', 'MX');
     * NOT
     */
    FIND("SELECT * FROM %table% WHERE %condition%"),
    UPDATE("UPDATE * SET %keyAndNewValue% WHERE %condition%"),
    /**
     * 删除符合条件的那一行数据
     */
    DELETE_DATA("DELETE FROM %table% WHERE %condition%"),
    @Deprecated
    UNION("SELECT * FROM %table1% WHERE %condition1% UNION SELECT * FROM %table2% WHERE %condition2% [ORDER BY column1, column2, ...];")
    ;
    public final String sql;
    MySQLFunction(String sql) {
        this.sql = sql;
    }

    /**
     * 如果是检测是否已经存在 就在后面 加ON DUPLICATE KEY UPDATE uuid = uuid  type = VALUES(type),
     *                     world = VALUES(world),
     *                     x = VALUES(x),
     *                     y = VALUES(y),
     *                     z = VALUES(z)
     * uuid 是唯一键 可以根据自己的表进行填写
     * @param keyAndValues 关键词与默认值
     * @param tableName 表名
     * @return 插入语句
     */
    public String insert(HashMap<String, Object> keyAndValues, String tableName) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO " + tableName + " (");
        for (String key : keyAndValues.keySet()) {
            sb.append(key).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") VALUES (");
        for (Object value : keyAndValues.values()) {
            sb.append("'").append(value).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * 查询
     * @param tableName 表名
     * @param condition 条件 比如 uuid = '123'
     * @return 查询语句
     */
    public static String find(String tableName, String condition) {
        return FIND.sql.replace("%table%", tableName).replace("%condition%", condition);
    }

    /**
     * 更新
     * @param tableName 表名
     * @param keyAndNewValue key 是关键词 value 是值
     * @param condition 条件 比如 uuid = '123'
     * @return 更新语句
     */
    public static String update(String tableName, HashMap<String, Object> keyAndNewValue, String condition) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName).append(" SET ");
        for (String key : keyAndNewValue.keySet()) {
            sb.append(key).append(" = '").append(keyAndNewValue.get(key)).append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE ").append(condition);
        return sb.toString();
    }


}
