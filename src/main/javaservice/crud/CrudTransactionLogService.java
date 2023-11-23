package cn.fufeii.learn.test.mp.service.crud;

import cn.fufeii.learn.test.mp.entity.TransactionLog;
import cn.fufeii.learn.test.mp.repository.TransactionLogRepository;
import cn.fufeii.learn.test.mp.util.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * TRANSACTION_LOG
 * CRUD TransactionLog Service
 *
 * @author josh
 */
@Service
public class CrudTransactionLogService {

    @Autowired
    private TransactionLogRepository transactionLogRepository;

    /**
     * 列表查询
     */
    public List<TransactionLog> selectList(Specification<TransactionLog> spec) {
        return transactionLogRepository.findAll(spec);
    }

    /**
     * 分页查询
     */
    public Page<TransactionLog> selectPage(Specification<TransactionLog> spec, Pageable pageable) {
        return transactionLogRepository.findAll(spec, pageable);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<TransactionLog> selectByIdOptional(Long id) {
        return transactionLogRepository.findById(id);
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public TransactionLog selectById(Long id) {
        return transactionLogRepository.findById(id).orElseThrow(() -> new BizException());
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<TransactionLog> selectOneOptional(Specification<TransactionLog> spec) {
        return transactionLogRepository.findOne(spec);
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public TransactionLog selectOne(Specification<TransactionLog> spec) {
        return transactionLogRepository.findOne(spec).orElseThrow(() -> new BizException());
    }

    /**
     * 统计个数
     */
    public long count(Specification<TransactionLog> spec) {
        return transactionLogRepository.count(spec);
    }

    /**
     * 是否存在
     */
    public boolean exist(Specification<TransactionLog> spec) {
        return transactionLogRepository.count(spec) > 0;
    }

    /**
     * 插入实体
     */
    public TransactionLog insert(TransactionLog entity) {
        return transactionLogRepository.saveAndFlush(entity);
    }

    /**
     * 更新实体
     */
    public TransactionLog updateById(TransactionLog entity) {
        return transactionLogRepository.saveAndFlush(entity);
    }

    /**
     * 删除实体
     */
    public void deleteById(Long id) {
        transactionLogRepository.deleteById(id);
    }


    // ----------------------------------------------------------------------------------- //
    // -------------------------------- 下面是CRUD的扩展 ----------------------------------- //
    // ----------------------------------------------------------------------------------- //


}