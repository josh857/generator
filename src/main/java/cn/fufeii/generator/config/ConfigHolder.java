package cn.fufeii.generator.config;

/**
 * 配置持有者
 *
 * @author FuFei
 * @date 2021/7/2
 */
public final class ConfigHolder {

    private static GeneratorConfig.DataSource DATA_SOURCE;

    private static GeneratorConfig.Rule.Common RULE_COMMON;

    private static GeneratorConfig.Rule.Entity RULE_ENTITY;

    private static GeneratorConfig.Rule.Controller RULE_CONTROLLER;

    private static GeneratorConfig.Rule.Service RULE_SERVICE;

    private static GeneratorConfig.Rule.Orm RULE_ORM;

    public static void init(GeneratorConfig generatorConfig) {
        DATA_SOURCE = generatorConfig.getDataSource();
        RULE_COMMON = generatorConfig.getRule().getCommon();
        RULE_ENTITY = generatorConfig.getRule().getEntity();
        RULE_CONTROLLER = generatorConfig.getRule().getController();
        RULE_SERVICE = generatorConfig.getRule().getService();
        RULE_ORM = generatorConfig.getRule().getOrm();
    }

    public static GeneratorConfig.DataSource getDataSource() {
        return DATA_SOURCE;
    }

    public static GeneratorConfig.Rule.Common getRuleCommon() {
        return RULE_COMMON;
    }

    public static GeneratorConfig.Rule.Entity getRuleEntity() {
        return RULE_ENTITY;
    }

    public static GeneratorConfig.Rule.Controller getRuleController() {
        return RULE_CONTROLLER;
    }

    public static GeneratorConfig.Rule.Orm getRuleOrm() {
        return RULE_ORM;
    }

    public static GeneratorConfig.Rule.Service getRuleService() {
        return RULE_SERVICE;
    }

}
