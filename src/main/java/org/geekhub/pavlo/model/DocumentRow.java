package org.geekhub.pavlo.model;

public class DocumentRow{
private int headId;
private int rowNum;

public DocumentRow(){
        }

public int getGoodsId(){
        return headId;
        }

public void setGoodsId(int goodsId){
        this.headId=goodsId;
        }

public int getRowNum(){
        return rowNum;
        }

public void setRowNum(int rowNum){
        this.rowNum=rowNum;
        }
        }