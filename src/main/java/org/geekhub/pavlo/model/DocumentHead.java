package org.geekhub.pavlo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class   DocumentHead <T extends DocumentRow> {
    private int typeId;
    private int id;
    private LocalDate docDate;
    private final List<T> rows = new ArrayList<>();

    public DocumentHead() {
    }

    public int getTypeId() {
        return typeId;
    }

    protected void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDocDate() {
        return docDate;
    }

    public void setDocDate(LocalDate docDate) {
        this.docDate = docDate;
    }

    public List<T> getRows() {
        return rows;
    }
}
