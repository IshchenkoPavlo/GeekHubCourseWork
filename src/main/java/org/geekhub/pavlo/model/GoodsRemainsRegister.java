package org.geekhub.pavlo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GoodsRemainsRegister {
    private RegisterMovementTypes plusMinus;
    private int docType;
    private int docNum;
    private LocalDateTime docDate;
    private int  goodsId;
    private BigDecimal quantity;

    public GoodsRemainsRegister() {
    }

    public GoodsRemainsRegister(RegisterMovementTypes plusMinus, int docType, int docNum, LocalDateTime docDate, int goodsId, BigDecimal quantity) {
        this.plusMinus = plusMinus;
        this.docType = docType;
        this.docNum = docNum;
        this.docDate = docDate;
        this.goodsId = goodsId;
        this.quantity = quantity;
    }

    public RegisterMovementTypes getPlusMinus() {
        return plusMinus;
    }

    public void setPlusMinus(RegisterMovementTypes plusMinus) {
        this.plusMinus = plusMinus;
    }

    public void setPlusMinus(int plusMinus) {
        switch (plusMinus) {
            case 1:
                this.plusMinus = RegisterMovementTypes.PLUS;
                break;
            case -1:
                this.plusMinus = RegisterMovementTypes.MINUS;
                break;
            default:
                throw new IllegalArgumentException("Unknown RegisterMovementTypes value " + plusMinus);
        }
    }

    public int getDocType() {
        return docType;
    }

    public void setDocType(int docType) {
        this.docType = docType;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public LocalDateTime getDocDate() {
        return this.docDate;
    }

    public void setDocDate(LocalDateTime docDate) {
        this.docDate = docDate;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}
