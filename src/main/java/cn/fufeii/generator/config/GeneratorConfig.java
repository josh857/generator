package cn.fufeii.generator.config;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 生成器生成设置类
 *
 * @author FuFei
 * @date 2021/6/25
 */
@Data
public class GeneratorConfig {

    /**
     * 数据源配置
     */
    private DataSource dataSource = new DataSource();

    /**
     * 代码生成规则
     */
    private Rule rule = new Rule();

    @Data
    public static class DataSource {

        /**
         * jdbc的链接地址
         */
        private String url;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;


        /**
         * 驱动
         */
        private String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    }

    @Data
    public static class Rule {

        /**
         * 公共配置
         */
        private Common common = new Common();

        /**
         * entity设置
         */
        private Entity entity = new Entity();

        /**
         * entity设置
         */
        private Controller controller = new Controller();

        /**
         * service设置
         */
        private Service service = new Service();

        /**
         * orm设置
         */
        private Orm orm = new Orm();


        @Data
        public static class Common {

            /**
             * 生成代码模式 [mybatis-plus] [mybatis] [jpa]
             * 只能选择上面三种
             */
            private String mode = "";

            /**
             * 生成的代码文件输出存放路径
             */
            private String outputPath = "";

            /**
             * 作者
             */
            private String author = "generator";

            /**
             * 基础包路径
             */
            private String basePackagePath = "";

            /**
             * 工具包路径
             */
            private String utilPackagePath = "";

            /**
             * 工具包路径
             */
            private String exceptionPackagePath = "";

        }

        @Data
        public static class Entity {

            /**
             * 为true则生成entity层
             */
            private Boolean enable = true;

            /**
             * 过滤标志，true-忽略，false-包含
             */
            private Boolean filterFlag = true;

            /**
             * 过滤内容
             */
            private List<String> filterInclude = Collections.emptyList();

            /**
             * 为true则使用lombok简化代码
             */
            private Boolean useLombok = true;

            /**
             * 表前缀
             */
            private String tablePrefix = "";

            /**
             * id字段的具体名字
             */
            private String idName = "id";

            /**
             * 为true则会将tinyint(1)转为Boolean，否则将转为Integer
             */
            private Boolean tinyint1ToBool = false;

            /**
             * 实体字段datetime映射为LocalDateTime，默认false
             */
            private Boolean datetimeToLdt = false;

            /**
             * 实体使用乐观锁注解(默认字段为version)，默认true
             */
            private Boolean uesVersionLock = true;

            /**
             * uesVersionLock为true是生效，指定数据库中乐观锁的字段名
             */
            private String versionLockField = "version";

            /**
             * 实体使用审计
             */
            private Boolean uesAudit = true;

            /**
             * 当uesAudit为true时生效，指定数据库中创建日期时间的字段名
             */
            private String auditCreateDateTimeField = "create_date_time";

            /**
             * 当uesAudit为true时生效，指定数据库中修改日期时间的字段名
             */
            private String auditUpdateDateTimeField = "update_date_time";

            /**
             * 生成的DTO是否使用swagger的注解
             */
            private Boolean dtoUseSwagger = true;

            /**
             * 生成的DTO是否加上注释
             */
            private Boolean dtoUseComment = false;

            /**
             * 为true时所有实体类将继承基础实体类
             */
            private Boolean inheritBaseEntity = false;

            /**
             * 填写BaseEntity中的字段，这些字段将会在Entity中忽略
             */
            private List<String> baseEntityFieldList = Collections.emptyList();

        }

        @Data
        public static class Controller {

            /**
             * 为true则生成service层
             */
            private Boolean enable = true;

            /**
             * 将分页参数合并到请求参数中
             */
            private Boolean mergePageParamToReq = true;

            /**
             * 为true时Request类将继承基础分页类（前提为Controller.mergePageParamToReq生效）
             */
            private Boolean inheritBasePageRequest = true;

        }

        @Data
        public static class Service {

            /**
             * 为true则生成service层
             */
            private Boolean enable = true;

            /**
             * 为true则会创建service接口和serviceImpl，否则直接创建Service类
             */
            private Boolean useInterface = true;

            /**
             * 生成CRUD实体类的前缀，默认生成CrudXxxService
             */
            private String crudServicePrefix = "Crud";

        }

        @Data
        public static class Orm {

            /**
             * 为true则生成orm层
             */
            private Boolean enable = true;

            /**
             * 为true则会在接口层加上@Mapper注解
             */
            private Boolean useMapperAnnotation = true;

            /**
             * 在mybatis或者mybatis-plus环境下生效，true生成XML文件（mp不会生成curd代码，mybatis会生成curd代码）
             */
            private Boolean useXmlMapper = true;

        }

    }

}
