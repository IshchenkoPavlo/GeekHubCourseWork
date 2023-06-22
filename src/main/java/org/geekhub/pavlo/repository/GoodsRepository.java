package org.geekhub.pavlo.repository;

import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.model.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class GoodsRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<Goods> getGoods(int parentId) {
        String sql = "" +
                "SELECT id, is_group, name, price, price_purchase " +
                "FROM goods " +
                "WHERE parent_id = :parent_id " +
                "ORDER BY is_group DESC, name";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("parent_id", parentId);

        List<Goods> res = namedJdbcTemplate.query(sql, parameters, this::mapRow);

        for (Goods g : res) {
            g.setParentId(parentId);
        }
        return res;
    }

    private Goods mapRow(ResultSet rs, int rowNum) throws SQLException {
        Goods g = new Goods();
        g.setId(rs.getInt("id"));
        g.setIsGroup(rs.getBoolean("is_group"));
        g.setName(rs.getString("name"));
        g.setPrice(rs.getFloat("price"));
        g.setPricePurchase(rs.getFloat("price_purchase"));
        return g;
    }

    public List<GoodsRemainDto> getGoodsRemainDto(int parentId) {
        String sql = "" +
                "SELECT id, is_group, name, price, price_purchase, COALESCE(reg.quantity, 0)  AS quantity " +
                "FROM goods " +
                "   LEFT JOIN reg_goods_remains_total reg ON reg.goods_id = id " +
                "WHERE parent_id = :parent_id " +
                "ORDER BY is_group DESC, name";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("parent_id", parentId);

        List<GoodsRemainDto> res = namedJdbcTemplate.query(sql, parameters, this::mapRowsGoodsDto);

        return res;
    }

    private GoodsRemainDto mapRowsGoodsDto(ResultSet rs, int rowNum) throws SQLException {
        GoodsRemainDto g = new GoodsRemainDto();
        g.setId(rs.getInt("id"));
        g.setIsGroup(rs.getBoolean("is_group"));
        g.setName(rs.getString("name"));
        g.setPrice(rs.getFloat("price"));
        g.setPricePurchase(rs.getFloat("price_purchase"));
        g.setRemain(rs.getBigDecimal("quantity"));
        return g;
    }

    public GoodsRemainDto getArticle(int id) {
        String sql = "" +
                "SELECT id, is_group, name, price, price_purchase, COALESCE(reg.quantity, 0)  AS quantity " +
                "FROM goods " +
                "   LEFT JOIN reg_goods_remains_total reg ON reg.goods_id = id " +
                "WHERE id = :id ";

        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("id", id);

        GoodsRemainDto res = namedJdbcTemplate.queryForObject(sql, parameters, this::mapRowsGoodsDto);

        return res;

    }
    public void uploadGoodsListStart() {
        jdbcTemplate.update("DELETE FROM purchase_h");
        jdbcTemplate.update("DELETE FROM sales_h");
        jdbcTemplate.update("DELETE FROM reg_goods_remains");
        jdbcTemplate.update("DELETE FROM reg_goods_remains_total");

        jdbcTemplate.execute("DROP INDEX IF EXISTS goods_parent_id_index");

        jdbcTemplate.update("DELETE FROM goods");
    }

    public void uploadGoodsList(HashMap<String, ?>[] goodsList) {
        String sql = "" +
                "INSERT INTO goods (id, is_group, parent_id, name, price, price_purchase) " +
                "VALUES (:id, :isGroup, :parentId, :name, :price, :pricePurchase)";
        namedJdbcTemplate.batchUpdate(sql, goodsList);
    }

    public void uploadGoodsListFinish() {
        jdbcTemplate.execute("CREATE INDEX goods_parent_id_index ON goods (parent_id)");
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void setPricePurchase(int id, float newPricePurchase) {
        String sql = "" +
                "UPDATE goods SET " +
                "   price_purchase = :pricePurchase " +
                "WHERE id = :id ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("pricePurchase", newPricePurchase);

        namedJdbcTemplate.update(sql, parameters);
    }
}
