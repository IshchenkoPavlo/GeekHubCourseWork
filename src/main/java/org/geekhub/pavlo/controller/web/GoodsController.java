package org.geekhub.pavlo.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GoodsController {
    @Autowired
    public GoodsController() {
    }

    @GetMapping("/goods")
    public String goodsPage() {
        return "Goods";
    }

    @GetMapping("/upload-goods")
    public String uploadGoodsPage() {
        return "UploadGoods";
    }
}
