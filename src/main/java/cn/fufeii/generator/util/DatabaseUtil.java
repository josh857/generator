package cn.fufeii.generator.util;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.config.GeneratorConfig;
import cn.fufeii.generator.config.GeneratorConfig.DataSource;
import cn.fufeii.generator.domain.ColumnDO;
import cn.fufeii.generator.domain.TableDO;
import jdk.swing.interop.SwingInterOpUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库工具类
 *
 * @author FuFei
 */
public final class DatabaseUtil {

  private static final String
      SELECT_COLUMN = // "SELECT COLUMN_NAME, COLUMN_COMMENT, COLUMN_TYPE FROM
                      // INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND TABLE_SCHEMA = ? ORDER
                      // BY ORDINAL_POSITION";
      "SELECT\n"
              + "    c.name AS COLUMN_NAME,\n"
              + "    ep.value AS COLUMN_COMMENT,\n"
              + "    ty.name AS COLUMN_TYPE,\n"
              + "   CASE WHEN ic.column_id IS NOT NULL THEN 'YES' ELSE 'NO' END AS IS_PRIMARY_KEY\n"
              + "FROM\n"
              + "    sys.columns c\n"
              + "INNER JOIN\n"
              + "    sys.tables t ON c.object_id = t.object_id\n"
              + "INNER JOIN\n"
              + "    sys.types ty ON c.system_type_id = ty.system_type_id\n"
              + "LEFT JOIN\n"
              + "    sys.extended_properties ep ON ep.major_id = c.object_id AND ep.minor_id = c.column_id AND ep.name = 'MS_Description'\n"
              + "LEFT JOIN\n"
              + "    sys.indexes i ON i.object_id = t.object_id AND i.is_primary_key = 1\n"
              + "LEFT JOIN\n"
              + "    sys.index_columns ic ON ic.object_id = i.object_id AND ic.column_id = c.column_id\n"
              + "WHERE\n" +
              "ty.name <> 'sysname'" +
              "and"
              +" t.name = ?";

    private static final String SELECT_TABLE = //"SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.`TABLES` WHERE TABLE_SCHEMA = ?";
                                                "SELECT \n" +
                                                        "    t.name AS TABLE_NAME,\n" +
                                                        "    ep.value AS TABLE_COMMENT\n" +
                                                        "FROM \n" +
                                                        "    sys.tables t\n" +
                                                        "LEFT JOIN \n" +
                                                        "    sys.extended_properties ep ON ep.major_id = t.object_id AND ep.minor_id = 0 AND ep.name = 'MS_Description'";
    private static DataSource DATASOURCE;


    private static DataSource getDataSource() {
        if (DATASOURCE != null) {
            return DATASOURCE;
        }
        synchronized (DatabaseUtil.class) {
            if (DATASOURCE != null) {
                return DATASOURCE;
            }
            DATASOURCE = ConfigHolder.getDataSource();
            return DATASOURCE;
        }
    }


    /**
     * 查询某个数据库连接的所有表
     */
    public static List<TableDO> selectTables() {
        DataSource dataSource = getDataSource();
        List<TableDO> tables = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_TABLE);
        ) {
            //获取数据库名称
            String dbName = getDbName(dataSource);
            //构造查询语句
//            preparedStatement.setString(1, dbName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TableDO tableDO = new TableDO(resultSet.getString("TABLE_NAME"), resultSet.getString("TABLE_COMMENT"));
                tableDO.setColumnDOList(selectColumn(tableDO.getName()));
                tables.add(tableDO);
            }
            return tables;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 查询某个表的所有字段
     */
    public static List<ColumnDO> selectColumn(String tableName) {
        GeneratorConfig.DataSource dataSource = getDataSource();
        ArrayList<ColumnDO> columnInfoList = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_COLUMN);
        ) {
//            String dbName = getDbName(dataSource);
            preparedStatement.setString(1, tableName);
//            preparedStatement.setString(2, dbName);
            //执行查询
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                columnInfoList.add(new ColumnDO(resultSet.getString("COLUMN_NAME"), resultSet.getString("COLUMN_TYPE"), resultSet.getString("COLUMN_COMMENT"), resultSet.getString("IS_PRIMARY_KEY")));
            }
      System.out.println(columnInfoList);
            return columnInfoList;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 获取数据库名称
     */
    private static String getDbName(GeneratorConfig.DataSource dataSource) {
        return "fubon";
    }

}
