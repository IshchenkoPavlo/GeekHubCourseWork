package org.geekhub.pavlo.mapper;

import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.model.Goods;
import org.mapstruct.Mapper;

@Mapper
public interface GoodsMapper {
    GoodsRemainDto modelToDto(Goods goods);
    Goods dtoToModel(GoodsRemainDto goods);
}
