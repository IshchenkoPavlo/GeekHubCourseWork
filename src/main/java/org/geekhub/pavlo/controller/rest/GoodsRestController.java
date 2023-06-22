package org.geekhub.pavlo.controller.rest;

import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class GoodsRestController {
    private final GoodsService goodsService;

    @Autowired
    public GoodsRestController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }


    @GetMapping("/api/v1/goods")
    public List<GoodsRemainDto> getGoodsList(@RequestParam("parentId") int parentId) {
        return goodsService.getGoodsDto(parentId);
    }

    @GetMapping("/api/v1/article")
    public GoodsRemainDto getArticle(@RequestParam("id") int id) {
        return goodsService.getArticle(id);
    }

    @PostMapping(value = "/goods/upload-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(@RequestParam("userfile") MultipartFile file) {
        List<String> res = goodsService.upload(file);

        if (res.size() > 0) {
            throw new RuntimeException(res.stream().reduce("Errors occurred during download:", (a, b) -> a + "\n" + b));
        }
    }
}
