package cn.fufeii.generator.service;

import cn.fufeii.generator.domain.TableDO;

/**
 * 代码生成API
 *
 * @author FuFei
 * @date 2021/6/26
 */
public interface Generator {

    /**
     * 执行生成所有代码
     */
    void generate(TableDO tableDO);

}
