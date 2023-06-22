package org.geekhub.pavlo.repository;

import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.model.GoodsRemainsRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class ReportsRepository {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public ReportsRepository(NamedParameterJdbcTemplate namedJdbcTemplate) {
        this.namedJdbcTemplate = namedJdbcTemplate;
    }

    public List<GoodsRemainDto> goodsRemains(LocalDateTime dateRemains) {
        String sql = "" +
                "SELECT goods_id, g.name AS name,  sum(quantity) AS quantity " +
                "FROM ( " +
                "       SELECT goods_id, quantity " +
                "       FROM reg_goods_remains_total total " +
                "" +
                "       UNION ALL " +
                "" +
                "       SELECT goods_id, - quantity * plus_minus " +
                "       FROM reg_goods_remains moves " +
                "       WHERE doc_date > :docDate " +
                "   ) r " +
                "   LEFT JOIN goods g ON goods_id = g.id " +
                "GROUP BY goods_id, g.name " +
                "HAVING sum(quantity) <> 0 ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("docDate", dateRemains);

        List<GoodsRemainDto> res = namedJdbcTemplate.query(sql,
                parameters,
                (rs, rowNum) -> {
                    GoodsRemainDto gr = new GoodsRemainDto();
                    gr.setId(rs.getInt("goods_id"));
                    gr.setName(rs.getString("name"));
                    gr.setRemain(rs.getBigDecimal("quantity"));
                    return gr;
                });

        return res;
    }

    public BigDecimal goodsRemain(LocalDateTime dateRemains, int goodsId) {
        String sql = "" +
                "SELECT sum(quantity) AS quantity " +
                "FROM ( " +
                "       SELECT goods_id, quantity " +
                "       FROM reg_goods_remains_total total " +
                "       WHERE goods_id = :goods_id " +
                "" +
                "       UNION ALL " +
                "" +
                "       SELECT goods_id, - quantity * plus_minus " +
                "       FROM reg_goods_remains moves " +
                "       WHERE goods_id = :goods_id AND doc_date > :docDate) r ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("docDate", dateRemains)
                .addValue("goods_id", goodsId);

        BigDecimal res = namedJdbcTemplate.queryForObject(sql, parameters, BigDecimal.class);

        if (res == null) {
            res = BigDecimal.ZERO;
        }

        return res;
    }

    public List<GoodsRemainsRegister> goodsRemainsMovies(LocalDateTime dateFrom, LocalDateTime dateTo, int goodsId) {
        String sql = "" +
                "       SELECT plus_minus, doc_type, doc_num, doc_date, goods_id, quantity " +
                "       FROM reg_goods_remains " +
                "       WHERE doc_date >= :dateFrom AND doc_date <= :dateTo AND goods_id = :goodsId";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("dateFrom", dateFrom)
                .addValue("dateTo", dateTo)
                .addValue("goodsId", goodsId);

        List<GoodsRemainsRegister> res = namedJdbcTemplate.query(sql,
                parameters,
                (rec, rowNum) -> {
                    GoodsRemainsRegister gr = new GoodsRemainsRegister();
                    gr.setPlusMinus(rec.getInt("plus_minus"));
                    gr.setDocType(rec.getInt("doc_type"));
                    gr.setDocNum(rec.getInt("doc_num"));
                    gr.setDocDate(rec.getTimestamp("doc_date").toLocalDateTime());
                    gr.setGoodsId(rec.getInt("goods_id"));
                    gr.setQuantity(rec.getBigDecimal("quantity"));
                    return gr;
                });

        return res;
    }

    public List<GoodsRemainDto> sales(LocalDateTime dateFrom, LocalDateTime dateTo) {
        String sql = "" +
                "SELECT goods_id, g.name,  sum(quantity)  AS quantity " +
                "FROM sales_t st " +
                "   LEFT JOIN sales_h sh ON h_id = sh.id " +
                "   LEFT JOIN goods g ON goods_id = g.id " +
                "WHERE sh.doc_date >= :dateFrom AND sh.doc_date <= :dateTo " +
                "GROUP BY goods_id, g.name " +
                "ORDER BY g.name";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("dateFrom", dateFrom)
                .addValue("dateTo", dateTo);

        List<GoodsRemainDto> res = namedJdbcTemplate.query(sql,
                parameters, this::rowMapper);

        return res;
    }

    private GoodsRemainDto rowMapper(ResultSet rs, int rowNum) throws SQLException {
        GoodsRemainDto gr = new GoodsRemainDto();
        gr.setId(rs.getInt("goods_id"));
        gr.setName(rs.getString("name"));
        gr.setRemain(rs.getBigDecimal("quantity"));
        return gr;
    }
}
