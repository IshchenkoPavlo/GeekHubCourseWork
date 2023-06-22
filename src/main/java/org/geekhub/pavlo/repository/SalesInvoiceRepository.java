package org.geekhub.pavlo.repository;

import org.geekhub.pavlo.model.SalesInvoice;
import org.geekhub.pavlo.model.SalesInvoiceRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SalesInvoiceRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public SalesInvoiceRepository() {
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(SalesInvoice doc) {
        addHead(doc);
        addRows(doc);
    }

    private int addHead(SalesInvoice doc) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "" +
                "INSERT INTO sales_h (id, doc_date, total) " +
                "VALUES (:id, :doc_date, :total)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", doc.getId())
                .addValue("doc_date", doc.getDocDate())
                .addValue("total", doc.getTotal());
        namedJdbcTemplate.update(sql, parameters, keyHolder);

        return (Integer) keyHolder.getKeys().get("id");
    }

    private void addRows(SalesInvoice doc) {
        List<SalesInvoiceRow> rows = doc.getRows();

        Map<String, Object>[] lst = new Map[rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            lst[i] = new HashMap<>();
            lst[i].put("h_id", doc.getId());
            lst[i].put("row_num", i);
            lst[i].put("goods_id", rows.get(i).getGoodsId());
            lst[i].put("quantity", rows.get(i).getQuantity());
            lst[i].put("price", rows.get(i).getPrice());
            lst[i].put("amount", rows.get(i).getPrice());
        }

        String sql = "" +
                "INSERT INTO sales_t (h_id, row_num, goods_id, quantity, price, amount) " +
                "VALUES (:h_id, :row_num, :goods_id, :quantity, :price, :amount)";

        namedJdbcTemplate.batchUpdate(sql, lst);
    }

    public int getNewId() {
        String sql = "SELECT nextval('sales_h_id_seq')";
        int res = jdbcTemplate.queryForObject(sql, Integer.class);
        return res;
    }
}
