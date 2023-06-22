package org.geekhub.pavlo.service;

import org.geekhub.pavlo.model.GoodsRemainsRegister;
import org.geekhub.pavlo.model.PurchaseInvoice;
import org.geekhub.pavlo.model.PurchaseInvoiceRow;
import org.geekhub.pavlo.model.RegisterMovementTypes;
import org.geekhub.pavlo.repository.GoodsRepository;
import org.geekhub.pavlo.repository.PurchaseInvoiceRepository;
import org.geekhub.pavlo.repository.RegisterGoodsRemainsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseInvoiceService {
    private final PurchaseInvoiceRepository purchaseInvoiceRepository;
    private final GoodsRepository goodsRepository;
    private final RegisterGoodsRemainsRepository registerGoodsRemainsRepository;

    @Autowired
    public PurchaseInvoiceService(PurchaseInvoiceRepository purchaseInvoiceRepository,
                                  GoodsRepository goodsRepository,
                                  RegisterGoodsRemainsRepository registerGoodsRemainsRepository) {
        this.purchaseInvoiceRepository = purchaseInvoiceRepository;
        this.goodsRepository = goodsRepository;
        this.registerGoodsRemainsRepository = registerGoodsRemainsRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void add(PurchaseInvoice doc) {
        int docId = purchaseInvoiceRepository.getNewId();
        doc.setId(docId);

        saveRegister(doc);

        setTotal(doc);
        purchaseInvoiceRepository.add(doc);

        savePricePurchase(doc);
    }

    private void setTotal(PurchaseInvoice doc) {
        BigDecimal total = new BigDecimal(0);
        for (PurchaseInvoiceRow r : doc.getRows()) {
            total = total.add(r.getAmount());
        }
        doc.setTotal(total);
    }

    private void saveRegister(PurchaseInvoice doc) {
        List<PurchaseInvoiceRow> rows = doc.getRows();

        List<GoodsRemainsRegister> movies = new ArrayList<>(rows.size());
        for (int i = 0; i < rows.size(); i++) {
            PurchaseInvoiceRow row = rows.get(i);
            GoodsRemainsRegister reg = new GoodsRemainsRegister(
                    RegisterMovementTypes.PLUS,
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

    private void savePricePurchase(PurchaseInvoice doc) {
        for (PurchaseInvoiceRow r : doc.getRows()) {
            BigDecimal price = r.getPrice();
            if (price.compareTo(BigDecimal.ZERO) == 0) {
                continue;
            }

            goodsRepository.setPricePurchase(r.getGoodsId(), price.floatValue());
        }
    }
}
