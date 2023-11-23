package cn.fufeii.generator.service;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.domain.TableDO;
import cn.fufeii.generator.util.FilterUtil;
import cn.fufeii.generator.util.TypeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * MybatisPlusGenerator
 *
 * @author FuFei
 * @date 2021/6/26
 */
public class MybatisPlusGenerator extends AbstractGenerator {

    private final String tplPrefix = "template/mybatis-plus/";

    @Override
    protected void generateEntity(TableDO tableDO) {
        super.render(tplPrefix + "entity.btl", "entity", super.getEntityName(tableDO.getName()) + ".java", super.getEntityCommonVariableMap(true, tableDO));
    }

    @Override
    protected void generatePrimarykey(TableDO tableDO) {

    }


    @Override
    protected void generateController(TableDO tableDO) {
        Map<String, Object> variableMap = new HashMap<>();
        super.setControllerVariableMap(variableMap, tableDO);
        super.render(tplPrefix + "controller.btl", "controller", super.getEntityName(tableDO.getName()) + "Controller.java", variableMap);
    }

    @Override
    protected void generateService(TableDO tableDO) {
        Map<String, Object> variableMap = new HashMap<>();
        super.setServiceVariableMap(variableMap, tableDO);
        if (ConfigHolder.getRuleService().getUseInterface()) {
            super.render(tplPrefix + "service.btl", "service", super.getEntityName(tableDO.getName()) + "Service.java", variableMap);
            super.render(tplPrefix + "serviceImpl.btl", "service/impl", super.getEntityName(tableDO.getName()) + "ServiceImpl.java", variableMap);
        } else {
            variableMap.put("existInterface", false);
            super.render(tplPrefix + "serviceOnly.btl", "service", super.getEntityName(tableDO.getName()) + "Service.java", variableMap);
        }
        super.render(tplPrefix + "crudService.btl", "service/crud", ConfigHolder.getRuleService().getCrudServicePrefix() + super.getEntityName(tableDO.getName()) + "Service.java", variableMap);
    }

    @Override
    protected void generateOrm(TableDO tableDO) {
        Map<String, Object> variableMap = new HashMap<>();
        super.setCommonVariable(variableMap, tableDO);
        variableMap.put("entityIdType", TypeUtil.getIdJavaType(tableDO));
        variableMap.put("useMapperAnnotation", ConfigHolder.getRuleOrm().getUseMapperAnnotation());
        super.render(tplPrefix + "dao.btl", "dao", super.getEntityName(tableDO.getName()) + "Dao.java", variableMap);
        if (ConfigHolder.getRuleOrm().getUseXmlMapper()) {
            variableMap.put("entityIdName", ConfigHolder.getRuleEntity().getIdName());
            variableMap.put("columnList", super.getColumnDOList(FilterUtil.filerColumn(tableDO.getColumnDOList())));
            super.render(tplPrefix + "xml.btl", "xml", super.getEntityName(tableDO.getName()) + "Mapper.xml", variableMap);
        }
    }
}
