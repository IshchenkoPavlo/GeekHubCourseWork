package org.geekhub.pavlo.service;

import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.model.Goods;
import org.geekhub.pavlo.repository.GoodsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class GoodsService {
    private final Logger logger = LoggerFactory.getLogger(GoodsService.class);
    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsService(GoodsRepository goodsJdbcDao) {
        this.goodsRepository = goodsJdbcDao;
    }

    public List<Goods> getGoods(int parentId) {
        return goodsRepository.getGoods(parentId);
    }

    public List<GoodsRemainDto> getGoodsDto(int parentId) {
        return goodsRepository.getGoodsRemainDto(parentId);
    }

    public GoodsRemainDto getArticle(int id) {
        return goodsRepository.getArticle(id);
    }

    //Return list of errors (string and error text)
    public List<String> upload(MultipartFile file) {
        goodsRepository.uploadGoodsListStart();

        int portionCnt = 5000;
        List<String> res = new ArrayList<>();
        try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream());
             BufferedReader fileReader = new BufferedReader(inputStreamReader)) {
            HashMap<String, Object>[] goodsPortion = new HashMap[portionCnt];
            String s = null;
            int n = 0;
            do {
                try {
                    s = fileReader.readLine();
                } catch (IOException e) {
                    res.add(s + " - " + e.getMessage());
                    logger.error("Upload goods: " + s, e);
                }

                if (s == null) {
                    HashMap<String, Object>[] goodsPortionClipped = new HashMap[n];
                    System.arraycopy(goodsPortion, 0, goodsPortionClipped, 0, n);
                    goodsRepository.uploadGoodsList(goodsPortionClipped);
                    break;
                }

                if (goodsPortion[n] == null) {
                    goodsPortion[n] = new HashMap<>();
                }

                String err = readOneGoodsFromCSV(s, goodsPortion[n]);
                if (err != null) {
                    err = s + " - " + err;
                    res.add(err);
                    logger.debug(err);
                } else {
                    if (n == (portionCnt - 1)) {
                        goodsRepository.uploadGoodsList(goodsPortion);
                        n = 0;
                    } else {
                        n++;
                    }
                }
            } while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
        }

        goodsRepository.uploadGoodsListFinish();

        return res;
    }

    private String readOneGoodsFromCSV(String s, HashMap<String, Object> map) {
        String[] fields = s.split(";");

        if (fields.length < 6) {
            return "less than 6 fields";
        }

        map.put("isGroup", fields[0].equals("1"));

        try {
            map.put("id", Integer.valueOf(fields[1]));
        } catch (NumberFormatException e) {
            return "can't convert id to number";
        }

        map.put("name", fields[2]);

        if (fields[3].isEmpty()) {
            map.put("parentId", 0);
        } else {
            try {
                map.put("parentId", Integer.valueOf(fields[3]));
            } catch (NumberFormatException e) {
                return "can't convert parentId to number";
            }
        }

        if (fields[4].isEmpty()) {
            map.put("price", 0);
        } else {
            try {
                map.put("price", Float.valueOf(fields[4]));
            } catch (NumberFormatException e) {
                return "can't convert price to number";
            }
        }

        map.put("pricePurchase", 0);

        return null;
    }
}
