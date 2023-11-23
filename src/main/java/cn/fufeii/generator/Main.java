package cn.fufeii.generator;

import cn.fufeii.generator.config.ConfigHolder;
import cn.fufeii.generator.config.GeneratorConfig;
import cn.fufeii.generator.config.Mode;
import cn.fufeii.generator.domain.TableDO;
import cn.fufeii.generator.service.Generator;
import cn.fufeii.generator.service.JpaGenerator;
import cn.fufeii.generator.service.MybatisPlusGenerator;
import cn.fufeii.generator.util.DatabaseUtil;
import cn.fufeii.generator.util.FilterUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.yaml.snakeyaml.Yaml;

import java.util.Arrays;
import java.util.List;

/**
 * 启动类
 *
 * @author FuFei
 */
public class Main {

    private static final String GENERATOR_CONFIG_LOCATION = "classpath:/generator.yaml";

    private static final String CMD_PARAM_NAME = "generator.config.location=";

    private static void initProperties(final String location) {
        // 读取配置并保存设置到内存
        GeneratorConfig config = new Yaml().loadAs(ResourceUtil.getStream(location), GeneratorConfig.class);
        StaticLog.info("当前配置为：{}", config);
        ConfigHolder.init(config);
    }

    private static Generator obtainGenerator() {
        Mode mode = Mode.getByName(ConfigHolder.getRuleCommon().getMode());
        StaticLog.info("当前代码模式为：" + mode);
        if (Mode.MYBATIS_PLUS == mode) {
            return new MybatisPlusGenerator();
        }
        if (Mode.JPA == mode) {
            return new JpaGenerator();
        }
        throw new IllegalArgumentException("generator.rule.common.mode参数配置错误");
    }

    public static void main(String[] args) {
        String location = GENERATOR_CONFIG_LOCATION;
        // 尝试从命令行中获取配置
        if (ArrayUtil.isNotEmpty(args)) {
            String cmdParam = Arrays.stream(args).filter(it -> it.startsWith(CMD_PARAM_NAME)).map(it -> it.replaceFirst(CMD_PARAM_NAME, StrUtil.EMPTY)).findAny().orElse(null);
            if (StrUtil.isNotBlank(cmdParam)) {
                location = cmdParam;
            }
        }
        initProperties(location);
        Generator generator = obtainGenerator();
        List<TableDO> tableDOList = DatabaseUtil.selectTables();
        if (CollUtil.isEmpty(tableDOList)) {
            StaticLog.warn("数据库中没有可用的表");
            return;
        }
        // 过滤不需要的表，并开始生成需要的表
        for (TableDO tableDO : FilterUtil.filerTable(tableDOList)) {
            StaticLog.info(">>>>>>>>>>>>>>>>>>>>>>>>>> start generate, the table is [{}] >>>>>>>>>>>>>>>>>>>>>>>>>>", tableDO.getName());
            generator.generate(tableDO);
            StaticLog.info("<<<<<<<<<<<<<<<<<<<<<<<<<< end   generate, the table is [{}] <<<<<<<<<<<<<<<<<<<<<<<<<<\n", tableDO.getName());
        }
    }


}
