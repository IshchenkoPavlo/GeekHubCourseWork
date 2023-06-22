package org.geekhub.pavlo.service;

import org.geekhub.pavlo.model.GoodsRemainsRegister;
import org.geekhub.pavlo.model.RegisterMovementTypes;
import org.geekhub.pavlo.model.SalesInvoice;
import org.geekhub.pavlo.model.SalesInvoiceRow;
import org.geekhub.pavlo.repository.RegisterGoodsRemainsRepository;
import org.geekhub.pavlo.repository.SalesInvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SalesInvoiceService {
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final RegisterGoodsRemainsRepository registerGoodsRemainsRepository;

    @Autowired
    public SalesInvoiceService(SalesInvoiceRepository salesInvoiceRepository, RegisterGoodsRemainsRepository registerGoodsRemainsRepository) {
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.registerGoodsRemainsRepository = registerGoodsRemainsRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(SalesInvoice doc) {
        checkRemains(doc);

        int docId = salesInvoiceRepository.getNewId();
        doc.setId(docId);

        saveRegister(doc);

        setTotal(doc);
        salesInvoiceRepository.add(doc);
    }

    private void setTotal(SalesInvoice doc) {
        BigDecimal total = new BigDecimal(0);
        for (SalesInvoiceRow r : doc.getRows()) {
            total = total.add(r.getAmount());
        }
        doc.setTotal(total);
    }

    private void checkRemains(SalesInvoice doc) {
        List<Integer> goods = doc.getRows().stream()
                .mapToInt(SalesInvoiceRow::getGoodsId)
                .boxed()
                .collect(Collectors.toList());

        Map<Integer, BigDecimal> goodsRemains = registerGoodsRemainsRepository.getRemains(goods);

        StringBuilder errText = new StringBuilder();
        for (int i = 0; i < doc.getRows().size(); i++) {
            SalesInvoiceRow docRow = doc.getRows().get(i);
            BigDecimal remain = goodsRemains.get(docRow.getGoodsId());

            if (remain == null) {
                remain = BigDecimal.ZERO;
            }

            if (remain.compareTo(docRow.getQuantity()) == -1) {
                errText.append(String.format("Line No. %d the balance %.3f is less than the quantity sold remain %.3f \n",
                        i,
                        remain,
                        docRow.getQuantity()));
            }
        }

        if (errText.length() > 0) {
            throw new IllegalArgumentException(errText.toString());
        }
    }

    private void saveRegister(SalesInvoice doc) {
        List<SalesInvoiceRow> rows = doc.getRows();

        List<GoodsRemainsRegister> movies = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            SalesInvoiceRow row = rows.get(i);
            GoodsRemainsRegister reg = new GoodsRemainsRegister(
                    RegisterMovementTypes.MINUS,
                    doc.getTypeId(),
                    doc.getId(),
                    doc.getDocDate().atStartOfDay(),
                    row.getGoodsId(),
                    row.getQuantity()
            );
            movies.add(reg);
        }

        registerGoodsRemainsRepository.add(movies);
    }

}
