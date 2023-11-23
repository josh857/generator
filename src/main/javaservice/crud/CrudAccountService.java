package cn.fufeii.learn.test.mp.service.crud;

import cn.fufeii.learn.test.mp.entity.Account;
import cn.fufeii.learn.test.mp.repository.AccountRepository;
import cn.fufeii.learn.test.mp.util.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ACCOUNT
 * CRUD Account Service
 *
 * @author josh
 */
@Service
public class CrudAccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * 列表查询
     */
    public List<Account> selectList(Specification<Account> spec) {
        return accountRepository.findAll(spec);
    }

    /**
     * 分页查询
     */
    public Page<Account> selectPage(Specification<Account> spec, Pageable pageable) {
        return accountRepository.findAll(spec, pageable);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<Account> selectByIdOptional(Long id) {
        return accountRepository.findById(id);
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public Account selectById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new BizException());
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<Account> selectOneOptional(Specification<Account> spec) {
        return accountRepository.findOne(spec);
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public Account selectOne(Specification<Account> spec) {
        return accountRepository.findOne(spec).orElseThrow(() -> new BizException());
    }

    /**
     * 统计个数
     */
    public long count(Specification<Account> spec) {
        return accountRepository.count(spec);
    }

    /**
     * 是否存在
     */
    public boolean exist(Specification<Account> spec) {
        return accountRepository.count(spec) > 0;
    }

    /**
     * 插入实体
     */
    public Account insert(Account entity) {
        return accountRepository.saveAndFlush(entity);
    }

    /**
     * 更新实体
     */
    public Account updateById(Account entity) {
        return accountRepository.saveAndFlush(entity);
    }

    /**
     * 删除实体
     */
    public void deleteById(Long id) {
        accountRepository.deleteById(id);
    }


    // ----------------------------------------------------------------------------------- //
    // -------------------------------- 下面是CRUD的扩展 ----------------------------------- //
    // ----------------------------------------------------------------------------------- //


}