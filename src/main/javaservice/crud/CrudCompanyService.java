package cn.fufeii.learn.test.mp.service.crud;

import cn.fufeii.learn.test.mp.entity.Company;
import cn.fufeii.learn.test.mp.repository.CompanyRepository;
import cn.fufeii.learn.test.mp.util.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * COMPANY
 * CRUD Company Service
 *
 * @author josh
 */
@Service
public class CrudCompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    /**
     * 列表查询
     */
    public List<Company> selectList(Specification<Company> spec) {
        return companyRepository.findAll(spec);
    }

    /**
     * 分页查询
     */
    public Page<Company> selectPage(Specification<Company> spec, Pageable pageable) {
        return companyRepository.findAll(spec, pageable);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<Company> selectByIdOptional(Long id) {
        return companyRepository.findById(id);
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public Company selectById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new BizException());
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<Company> selectOneOptional(Specification<Company> spec) {
        return companyRepository.findOne(spec);
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public Company selectOne(Specification<Company> spec) {
        return companyRepository.findOne(spec).orElseThrow(() -> new BizException());
    }

    /**
     * 统计个数
     */
    public long count(Specification<Company> spec) {
        return companyRepository.count(spec);
    }

    /**
     * 是否存在
     */
    public boolean exist(Specification<Company> spec) {
        return companyRepository.count(spec) > 0;
    }

    /**
     * 插入实体
     */
    public Company insert(Company entity) {
        return companyRepository.saveAndFlush(entity);
    }

    /**
     * 更新实体
     */
    public Company updateById(Company entity) {
        return companyRepository.saveAndFlush(entity);
    }

    /**
     * 删除实体
     */
    public void deleteById(Long id) {
        companyRepository.deleteById(id);
    }


    // ----------------------------------------------------------------------------------- //
    // -------------------------------- 下面是CRUD的扩展 ----------------------------------- //
    // ----------------------------------------------------------------------------------- //


}