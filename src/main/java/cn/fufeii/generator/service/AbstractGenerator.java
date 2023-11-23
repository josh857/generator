package cn.fufeii.generator.service;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.config.GeneratorConfig;
import cn.fufeii.generator.domain.ColumnDO;
import cn.fufeii.generator.domain.TableDO;
import cn.fufeii.generator.util.FilterUtil;
import cn.fufeii.generator.util.PathUtil;
import cn.fufeii.generator.util.TypeUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 抽象各层方法
 * 提供通用方法
 *
 * @author FuFei
 * @date 2021/6/26
 */
public abstract class AbstractGenerator implements Generator {

    private final GroupTemplate groupTemplate;

    public AbstractGenerator() {
        //初始化代码
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        Configuration cfg;
        try {
            cfg = Configuration.defaultConfiguration();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.groupTemplate = new GroupTemplate(resourceLoader, cfg);
    }

    protected List<ColumnDO> getColumnDOList(List<ColumnDO> columnDOList) {
        List<ColumnDO> returnList = new ArrayList<>(columnDOList.size());
        for (ColumnDO columnDO : columnDOList) {
            ColumnDO column = new ColumnDO();
            column.setJavaName(StrUtil.lowerFirst(StrUtil.toCamelCase(columnDO.getName())));
            column.setName(columnDO.getName());
            column.setComment(StrUtil.isBlank(columnDO.getComment()) ? columnDO.getName() : columnDO.getComment());
            String javaType = TypeUtil.getJavaType(columnDO.getType());
            column.setType(javaType);
            column.setPrimary(columnDO.getPrimary());
            returnList.add(column);
        }
        return returnList;
    }

    protected Set<String> getImportList(List<ColumnDO> columnDOList) {
        Set<String> returnSet = new HashSet<>();
        for (ColumnDO columnDO : columnDOList) {
            String javaType = TypeUtil.getJavaType(columnDO.getType());
            String anImport = TypeUtil.getImport(javaType);
            if (StrUtil.isNotBlank(anImport)) {
                returnSet.add(anImport);
            }
        }
        return returnSet;
    }

    /**
     * 获取驼峰命名下的实体名字
     */
    protected final String getEntityName(String originTableName) {
        // 原始数据库表明转为java实体名，例如 ab_cde_fgh AB_CDE_FGH ==> AbCdeFgh
        originTableName = originTableName.replaceFirst(ConfigHolder.getRuleEntity().getTablePrefix(), "").toLowerCase();
        return StrUtil.upperFirst(StrUtil.toCamelCase(originTableName));
    }

    /**
     * 获取请求路径前缀,使用-号连接
     */
    protected final String getRequestMappingPrefix(String entityName) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < entityName.length(); i++) {
            String c = String.valueOf(entityName.charAt(i));
            if (StrUtil.isUpperCase(c)) {
                c = StrUtil.DASHED + StrUtil.lowerFirst(c);
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /**
     * 创建/获取文件Writer
     */
    protected final BufferedWriter getFileWriter(String moduleName, String fileName) {
        return FileUtil.getWriter(FileUtil.file(ConfigHolder.getRuleCommon().getOutputPath() + moduleName + "\\" + fileName), StandardCharsets.UTF_8, false);
    }

    /**
     * 公共基础参数
     *
     * @param variableMap *
     * @param tableDO     *
     */
    protected final void setCommonVariable(Map<String, Object> variableMap, TableDO tableDO) {
        variableMap.put("author", ConfigHolder.getRuleCommon().getAuthor());
        variableMap.put("packagePath", ConfigHolder.getRuleCommon().getBasePackagePath());
        String entityName = this.getEntityName(tableDO.getName());
        variableMap.put("entityName", entityName);
        variableMap.put("lowerEntityName", StrUtil.lowerFirst(entityName));
        variableMap.put("tableComment", StrUtil.isBlank(tableDO.getComment()) ? tableDO.getName() : tableDO.getComment());
    }

    /**
     * 获取实体相关的所有变量
     *
     * @param needInheritBaseEntity *
     * @param tableDO               *
     */
    protected final Map<String, Object> getEntityCommonVariableMap(boolean needInheritBaseEntity, TableDO tableDO) {
        GeneratorConfig.Rule.Entity ruleEntity = ConfigHolder.getRuleEntity();
        // 过滤实体中的字段
        List<ColumnDO> columnDOList = tableDO.getColumnDOList();
        columnDOList = FilterUtil.filerColumn(columnDOList);
        // 继承父类也需要过滤字段，因为[filter]选项只有在正向过滤的时候，支持过滤字段
        // 如果是反向过滤或者[filter]只填写了表级别的话，则需要单独处理
        Boolean inheritBaseEntity = ruleEntity.getInheritBaseEntity();
        if (needInheritBaseEntity && inheritBaseEntity) {
            List<String> baseEntityFieldList = ruleEntity.getBaseEntityFieldList();
            columnDOList = columnDOList.stream()
                    .filter(it -> baseEntityFieldList.stream().noneMatch(it2 -> it2.equals(it.getName())))
                    .collect(Collectors.toList());
        }

        // 准备变量容器
        Map<String, Object> variableMap = CollUtil.newHashMap();

        // 实体类的字段名字段类型以及需要的导包
        List<ColumnDO> fieldList = this.getColumnDOList(columnDOList);
        Set<String> importList = this.getImportList(columnDOList);

        variableMap.put("fieldList", fieldList);
        variableMap.put("importList", importList);
        variableMap.put("useLombok", ruleEntity.getUseLombok());
        variableMap.put("tableName", tableDO.getName());
        variableMap.put("idName", ruleEntity.getIdName());
        variableMap.put("inheritBaseEntity", inheritBaseEntity);
        variableMap.put("utilPackagePath", PathUtil.parsePath(ConfigHolder.getRuleCommon().getUtilPackagePath()));

        // 乐观锁
        variableMap.put("useVersionLock", ruleEntity.getUesVersionLock());
        String versionLockField = ruleEntity.getVersionLockField();
        variableMap.put("versionLockField", versionLockField);
        boolean matchVersion = Convert.convert(new TypeReference<List<ColumnDO>>() {
        }, fieldList).stream().map(ColumnDO::getName).anyMatch(versionLockField::equals);
        variableMap.put("containsVersionLockField", matchVersion);

        // 审计
        variableMap.put("uesAudit", ruleEntity.getUesAudit());
        String auditCreateDateTimeField = ruleEntity.getAuditCreateDateTimeField();
        String auditUpdateDateTimeField = ruleEntity.getAuditUpdateDateTimeField();
        variableMap.put("auditCreateDateTimeField", auditCreateDateTimeField);
        variableMap.put("auditUpdateDateTimeField", auditUpdateDateTimeField);
        boolean matchAuditCreate = Convert.convert(new TypeReference<List<ColumnDO>>() {
        }, fieldList).stream().map(ColumnDO::getName).anyMatch(it -> it.equals(auditCreateDateTimeField));
        boolean matchAuditUpdate = Convert.convert(new TypeReference<List<ColumnDO>>() {
        }, fieldList).stream().map(ColumnDO::getName).anyMatch(it -> it.equals(auditUpdateDateTimeField));
        variableMap.put("containsAuditCreateDateTimeField", matchAuditCreate);
        variableMap.put("containsAuditUpdateDateTimeField", matchAuditUpdate);

        this.setCommonVariable(variableMap, tableDO);
        return variableMap;
    }

    protected final void render(String tplPath, String moduleName, String fileName, Map<String, Object> otherVariable) {
        Template template = groupTemplate.getTemplate(tplPath);
        if (CollUtil.isNotEmpty(otherVariable)) {
            template.binding(otherVariable);
        }
        template.renderTo(this.getFileWriter(moduleName, fileName));
    }

    protected void setControllerVariableMap(Map<String, Object> variableMap, TableDO tableDO) {
        variableMap.put("mergePageParamToReq", ConfigHolder.getRuleController().getMergePageParamToReq());
        variableMap.put("dtoUseSwagger", ConfigHolder.getRuleEntity().getDtoUseSwagger());
        variableMap.put("utilPackagePath", PathUtil.parsePath(ConfigHolder.getRuleCommon().getUtilPackagePath()));
        this.setCommonVariable(variableMap, tableDO);
        variableMap.put("entityIdType", TypeUtil.getIdJavaType(tableDO));
        variableMap.put("entityIdName", ConfigHolder.getRuleEntity().getIdName());
        this.getEntityName(tableDO.getName());
        variableMap.put("requestMappingPrefix", this.getRequestMappingPrefix(StrUtil.lowerFirst(this.getEntityName(tableDO.getName()))));
    }

    protected void setServiceVariableMap(Map<String, Object> variableMap, TableDO tableDO) {
        GeneratorConfig.Rule.Service ruleService = ConfigHolder.getRuleService();
        this.setCommonVariable(variableMap, tableDO);
        variableMap.put("entityIdType", TypeUtil.getIdJavaType(tableDO));
        variableMap.put("crudServicePrefix", ruleService.getCrudServicePrefix());
        variableMap.put("lowerCrudServicePrefix", StrUtil.lowerFirst(ruleService.getCrudServicePrefix()));
        variableMap.put("utilPackagePath", PathUtil.parsePath(ConfigHolder.getRuleCommon().getUtilPackagePath()));
        variableMap.put("exceptionPackagePath", PathUtil.parsePath(ConfigHolder.getRuleCommon().getExceptionPackagePath()));
    }


    protected void generateEntityModel(TableDO tableDO) {
        Map<String, Object> entityCommonVariableMap = this.getEntityCommonVariableMap(false, tableDO);
        entityCommonVariableMap.put("dtoUseSwagger", ConfigHolder.getRuleEntity().getDtoUseSwagger());
        entityCommonVariableMap.put("dtoUseComment", ConfigHolder.getRuleEntity().getDtoUseComment());
        entityCommonVariableMap.put("mergePageParamToReq", ConfigHolder.getRuleController().getMergePageParamToReq());
        entityCommonVariableMap.put("utilPackagePath", PathUtil.parsePath(ConfigHolder.getRuleCommon().getUtilPackagePath()));
        entityCommonVariableMap.put("inheritBasePageRequest", ConfigHolder.getRuleController().getInheritBasePageRequest());
        this.render("template/common/entityRequest.btl", "model/vo/request", this.getEntityName(tableDO.getName()) + "Request.java", entityCommonVariableMap);
        // 解决不使用lombok时，resp对象存在分页字段的getter和setter
        entityCommonVariableMap.put("mergePageParamToReq", false);
        this.render("template/common/entityResponse.btl", "model/vo/response", this.getEntityName(tableDO.getName()) + "Response.java", entityCommonVariableMap);
    }


    protected abstract void generateEntity(TableDO tableDO);

    protected abstract void generatePrimarykey(TableDO tableDO);
    protected abstract void generateController(TableDO tableDO);

    protected abstract void generateService(TableDO tableDO);

    protected abstract void generateOrm(TableDO tableDO);

    @Override
    public final void generate(TableDO tableDO) {
        if (ConfigHolder.getRuleEntity().getEnable()) {
            StaticLog.info("===================== start   entity   =====================");
            this.generateEntity(tableDO);
            this.generatePrimarykey(tableDO);
            StaticLog.info("===================== start   entityVO =====================");
            this.generateEntityModel(tableDO);
        }

        if (ConfigHolder.getRuleController().getEnable()) {
            StaticLog.info("===================== start controller =====================");
            this.generateController(tableDO);
        }

        if (ConfigHolder.getRuleService().getEnable()) {
            StaticLog.info("===================== start    service =====================");
            this.generateService(tableDO);
        }

        if (ConfigHolder.getRuleOrm().getEnable()) {
            StaticLog.info("===================== start        orm =====================");
            this.generateOrm(tableDO);
        }
    }


}