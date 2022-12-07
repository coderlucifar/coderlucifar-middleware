package com.coderlucifar.middleware.whitelist.test.controller;

import com.coderlucifar.middleware.whitelist.annotation.DoWhiteList;
import com.coderlucifar.middleware.whitelist.test.entity.Stock;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {

    @DoWhiteList(key = "userId", returnJson = "{\"msg\": \"您不是白名单用户，没有访问权限\"}")
    @GetMapping("/stock/queryStock")
    public Stock getStockInfoByUserId(@RequestParam("userId") String userId) {
        return new Stock("stock info");
    }

}
