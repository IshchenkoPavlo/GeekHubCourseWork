package org.geekhub.pavlo.repository;

import org.geekhub.pavlo.model.PurchaseInvoice;
import org.geekhub.pavlo.model.PurchaseInvoiceRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PurchaseInvoiceRepository {
    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    public PurchaseInvoiceRepository() {

    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(PurchaseInvoice doc) {
        addHead(doc);
        addRows(doc);
    }

    private void addHead(PurchaseInvoice doc) {
        String sql = "" +
                "INSERT INTO purchase_h (id, doc_date, comment, total) " +
                "VALUES (:id, :doc_date, :comment, :total)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", doc.getId())
                .addValue("doc_date", doc.getDocDate())
                .addValue("comment", doc.getComment())
                .addValue("total", doc.getTotal());
        namedJdbcTemplate.update(sql, parameters);
    }

    private void addRows(PurchaseInvoice doc) {
        List<PurchaseInvoiceRow> rows = doc.getRows();

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
                "INSERT INTO purchase_t (h_id, row_num, goods_id, quantity, price, amount) " +
                "VALUES (:h_id, :row_num, :goods_id, :quantity, :price, :amount)";

        namedJdbcTemplate.batchUpdate(sql, lst);
    }

    public int getNewId() {
        String sql = "SELECT nextval('purchase_h_id_seq')";
        int res = jdbcTemplate.queryForObject(sql, Integer.class);

        return res;
    }
}
