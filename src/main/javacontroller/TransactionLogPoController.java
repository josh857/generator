package cn.fufeii.learn.test.mp.controller;

import cn.fufeii.learn.test.mp.util.CommonResult;
import cn.fufeii.learn.test.mp.util.PageResult;
import cn.fufeii.learn.test.mp.service.TransactionLogService;
import cn.fufeii.learn.test.mp.model.vo.request.TransactionLogRequest;
import cn.fufeii.learn.test.mp.model.vo.response.TransactionLogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

/**
 * TRANSACTION_LOG Controller
 *
 * @author josh
 */
@RestController
@RequestMapping("/transaction-log")
public class TransactionLogController {

    @Autowired
    private TransactionLogService transactionLogService;

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public PageResult<TransactionLogResponse> page(TransactionLogRequest request) {
        Page<TransactionLogResponse> pageResult = transactionLogService.page(request, PageRequest.of(request.getPage(), request.getSize(), Sort.by(Sort.Direction.DESC, "YES")));
        return PageResult.success(pageResult.getTotalElements(), pageResult.getContent());
    }

    /**
     * 查询详情
     */
    @GetMapping("/info/{id}")
    public CommonResult<TransactionLogResponse> info(@PathVariable Long id) {
        return CommonResult.success(transactionLogService.info(id));
    }

    /**
     * 创建
     */
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody TransactionLogRequest request) {
        transactionLogService.create(request);
        return CommonResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody TransactionLogRequest request) {
        transactionLogService.modify(request);
        return CommonResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        transactionLogService.remove(id);
        return CommonResult.success();
    }

}
