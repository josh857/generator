package cn.fufeii.generator.service;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.domain.TableDO;
import cn.fufeii.generator.util.TypeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * JpaGenerator
 *
 * @author FuFei
 * @date 2021/7/3
 */
public class JpaGenerator extends AbstractGenerator {

    private final String tplPrefix = "template/jpa/";

    @Override
    protected void generateEntity(TableDO tableDO) {
        super.render(tplPrefix + "entity.btl", "entity", super.getEntityName(tableDO.getName())+"Po" + ".java", super.getEntityCommonVariableMap(true, tableDO));
    }

    @Override
    protected void generatePrimarykey(TableDO tableDO) {
        super.render(tplPrefix + "primarykey.btl", "primarykey", super.getEntityName(tableDO.getName())+"PoId" + ".java", super.getEntityCommonVariableMap(true, tableDO));
    }

    @Override
    protected void generateController(TableDO tableDO) {
        Map<String, Object> variableMap = new HashMap<>();
        super.setControllerVariableMap(variableMap, tableDO);
        super.render(tplPrefix + "controller.btl", "controller", super.getEntityName(tableDO.getName())+"Po" + "Controller.java", variableMap);
    }

    @Override
    protected void generateService(TableDO tableDO) {
        Map<String, Object> variableMap = new HashMap<>();
        super.setServiceVariableMap(variableMap, tableDO);
        if (ConfigHolder.getRuleService().getUseInterface()) {
            super.render(tplPrefix + "service.btl", "service", super.getEntityName(tableDO.getName())+"Po" + "Service.java", variableMap);
            super.render(tplPrefix + "serviceImpl.btl", "service/impl", super.getEntityName(tableDO.getName())+"Po" + "ServiceImpl.java", variableMap);
        } else {
            variableMap.put("existInterface", false);
            super.render(tplPrefix + "serviceOnly.btl", "service", super.getEntityName(tableDO.getName())+"Po" + "Service.java", variableMap);
        }
        super.render(tplPrefix + "crudService.btl", "service/crud", ConfigHolder.getRuleService().getCrudServicePrefix() + super.getEntityName(tableDO.getName()) + "Service.java", variableMap);
    }

    @Override
    protected void generateOrm(TableDO tableDO) {
        Map<String, Object> variableMap = new HashMap<>();
        super.setCommonVariable(variableMap, tableDO);
        variableMap.put("entityIdType", TypeUtil.getIdJavaType(tableDO));
        super.render(tplPrefix + "repository.btl", "repository", super.getEntityName(tableDO.getName())+"Po" + "Repository.java", variableMap);
    }

}
