package org.geekhub.pavlo.repository;

import org.geekhub.pavlo.model.GoodsRemainsRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RegisterGoodsRemainsRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(Map<String, Object>[] lst) {

        String sql = "" +
                "MERGE INTO reg_goods_remains_total t " +
                "USING(VALUES(:goods_id, :quantity * :plus_minus)) AS p (goods_id, quantity) " +
                "    ON t.goods_id = p.goods_id " +
                "WHEN NOT MATCHED THEN " +
                "    INSERT (goods_id, quantity) VALUES(p.goods_id, p.quantity) " +
                "WHEN MATCHED AND t.quantity = -p.quantity THEN " +
                "    DELETE " +
                "WHEN MATCHED THEN " +
                "    UPDATE SET quantity = t.quantity + p.quantity";

        namedJdbcTemplate.batchUpdate(sql, lst);

        sql = "" +
                "INSERT INTO reg_goods_remains (plus_minus, doc_type, doc_num, doc_date, goods_id, quantity) " +
                "VALUES (:plus_minus, :doc_type, :doc_num, :doc_date, :goods_id, :quantity)";
        namedJdbcTemplate.batchUpdate(sql, lst);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(List<GoodsRemainsRegister> lst) {
        lst.sort((GoodsRemainsRegister r1, GoodsRemainsRegister r2) -> {
            return r1.getGoodsId() - r2.getGoodsId();
        });

        Map<String, Object>[] movies = new Map[lst.size()];

        for (int i = 0; i < lst.size(); i++) {
            GoodsRemainsRegister reg = lst.get(i);
            movies[i] = new HashMap<>();
            movies[i].put("plus_minus", reg.getPlusMinus().getValue());
            movies[i].put("doc_type", reg.getDocType());
            movies[i].put("doc_num", reg.getDocNum());
            movies[i].put("doc_date", reg.getDocDate());
            movies[i].put("goods_id", reg.getGoodsId());
            movies[i].put("quantity", reg.getQuantity());
        }

        add(movies);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Map<Integer, BigDecimal> getRemains(List<Integer> lst) {
        Map<Integer, BigDecimal> res = new HashMap<>();

        String sql = "" +
                "SELECT goods_id, quantity " +
                "FROM reg_goods_remains_total " +
                "WHERE goods_id IN (:goods_id_lst) ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("goods_id_lst", lst);

        namedJdbcTemplate.query(sql,
                parameters,
                (ResultSet rs) -> {
                    res.put(rs.getInt("goods_id"), rs.getBigDecimal("quantity"));
                });

        return res;
    }
}
