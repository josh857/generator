package cn.fufeii.learn.test.mp.controller;

import cn.fufeii.learn.test.mp.util.CommonResult;
import cn.fufeii.learn.test.mp.util.PageResult;
import cn.fufeii.learn.test.mp.service.CompanyService;
import cn.fufeii.learn.test.mp.model.vo.request.CompanyRequest;
import cn.fufeii.learn.test.mp.model.vo.response.CompanyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * COMPANY Controller
 *
 * @author josh
 */
@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public PageResult<CompanyResponse> page(CompanyRequest request) {
        Page<CompanyResponse> pageResult = companyService.page(request, PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "YES")));
        return PageResult.success(pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 查询详情
     */
    @GetMapping("/info/{id}")
    public CommonResult<CompanyResponse> info(@PathVariable Long id) {
        return CommonResult.success(companyService.info(id));
    }

    /**
     * 创建
     */
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody CompanyRequest request) {
        companyService.create(request);
        return CommonResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody CompanyRequest request) {
        companyService.modify(request);
        return CommonResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        companyService.remove(id);
        return CommonResult.success();
    }

}
