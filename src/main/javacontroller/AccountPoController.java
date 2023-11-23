package cn.fufeii.learn.test.mp.controller;

import cn.fufeii.learn.test.mp.util.CommonResult;
import cn.fufeii.learn.test.mp.util.PageResult;
import cn.fufeii.learn.test.mp.service.AccountService;
import cn.fufeii.learn.test.mp.model.vo.request.AccountRequest;
import cn.fufeii.learn.test.mp.model.vo.response.AccountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * ACCOUNT Controller
 *
 * @author josh
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public PageResult<AccountResponse> page(AccountRequest request) {
        Page<AccountResponse> pageResult = accountService.page(request, PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "YES")));
        return PageResult.success(pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 查询详情
     */
    @GetMapping("/info/{id}")
    public CommonResult<AccountResponse> info(@PathVariable Long id) {
        return CommonResult.success(accountService.info(id));
    }

    /**
     * 创建
     */
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody AccountRequest request) {
        accountService.create(request);
        return CommonResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody AccountRequest request) {
        accountService.modify(request);
        return CommonResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        accountService.remove(id);
        return CommonResult.success();
    }

}
