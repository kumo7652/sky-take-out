package com.sky.controller.user;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/addressBook")
@RequiredArgsConstructor
public class AddressBookController {
    private final AddressBookService addressBookService;

    /**
     * 查询当前登录用户的所有地址信息
     */
    @GetMapping("/list")
    public Result list() {
        log.info("用户：{}查询地址信息", BaseContext.getCurrentId());

        AddressBook addressBook = new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        List<AddressBook> list = addressBookService.list(addressBook);

        return Result.success(list);
    }

    /**
     * 新增地址
     * @param addressBook 地址信息
     */
    @PostMapping
    public Result save(@RequestBody AddressBook addressBook) {
        log.info("用户：{}添加地址：{}", BaseContext.getCurrentId(), addressBook);
        addressBookService.save(addressBook);

        return Result.success();
    }

    /**
     * 根据id查询地址
     * @param id 地址id
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("用户：{}查询地址,id：{}", BaseContext.getCurrentId(), id);
        AddressBook addressBook = addressBookService.getById(id);

        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     * @param addressBook 地址信息
     */
    @PutMapping
    public Result update(@RequestBody AddressBook addressBook) {
        log.info("用户：{}修改地址信息：{}", BaseContext.getCurrentId(), addressBook);
        addressBookService.update(addressBook);

        return Result.success();
    }

    /**
     * 设置默认地址
     * @param addressBook 地址信息
     */
    @PutMapping("/default")
    public Result setDefault(@RequestBody AddressBook addressBook) {
        addressBookService.setDefault(addressBook);

        return Result.success();
    }

    /**
     * 根据id删除地址
     * @param id 地址id
     */
    @DeleteMapping
    public Result deleteById(Long id) {
        addressBookService.deleteById(id);

        return Result.success();
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public Result getDefault() {
        AddressBook addressBook = new AddressBook();
        addressBook.setIsDefault(1);
        addressBook.setUserId(BaseContext.getCurrentId());

        List<AddressBook> list = addressBookService.list(addressBook);

        if (list != null && list.size() == 1) {
            return Result.success(list.get(0));
        }

        return Result.error("没有查询到默认地址");
    }

}
