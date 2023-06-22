package org.geekhub.pavlo.service;

import org.apache.poi.xwpf.usermodel.*;
import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportGoodsRemainsService {
    ReportsRepository reportsRepository;

    @Autowired
    public ReportGoodsRemainsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    public byte[] getReportWordFile(LocalDateTime dateRemains) throws IOException {
        byte[] res;
        try (XWPFDocument document = new XWPFDocument()) {

            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setFontSize(14);
            run.setText("Goods remains on the date " + DateTimeFormatter.ofPattern("dd.MM.yy").format(dateRemains));

            document.createParagraph();

            XWPFTable table = document.createTable(1, 4);
            table.setWidth("100%");

            XWPFTableRow tableRow = table.getRow(0);
            setColumnWidth(tableRow);

            setHeaderCellProperty(tableRow, 0, "N");
            setHeaderCellProperty(tableRow, 1, "Code");
            setHeaderCellProperty(tableRow, 2, "Goods name");
            setHeaderCellProperty(tableRow, 3, "Remain");

            List<GoodsRemainDto> records = reportsRepository.goodsRemains(dateRemains);
            DecimalFormat decimalFormatQuantity = new DecimalFormat("#,##0.000");
            for (int i = 0; i < records.size(); i++) {
                GoodsRemainDto record = records.get(i);

                tableRow = table.createRow();
                setColumnWidth(tableRow);
                setBodyCellProperty(tableRow, 0, Integer.toString(i + 1), ParagraphAlignment.CENTER);
                setBodyCellProperty(tableRow, 1, Integer.toString(record.getId()), ParagraphAlignment.CENTER);
                setBodyCellProperty(tableRow, 2, record.getName(), ParagraphAlignment.LEFT);
                setBodyCellProperty(tableRow, 3, decimalFormatQuantity.format(record.getRemain()), ParagraphAlignment.RIGHT);
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.write(outputStream);
            res = outputStream.toByteArray();
        }

        return res;
    }

    private void setHeaderCellProperty(XWPFTableRow tableRow, int colNum, String cellText) {
        XWPFTableCell cell = tableRow.getCell(colNum);
        cell.setText(cellText);
        cell.getParagraphs().get(0).setAlignment(ParagraphAlignment.CENTER);
        cell.getParagraphs().get(0).getRuns().get(0).setBold(true);
    }

    private void setBodyCellProperty(XWPFTableRow tableRow, int colNum, String cellText, ParagraphAlignment align) {
        XWPFTableCell cell = tableRow.getCell(colNum);
        cell.setText(cellText);
        cell.getParagraphs().get(0).setAlignment(align);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
    }

    private void setColumnWidth(XWPFTableRow tableRow) {
        tableRow.getCell(0).setWidth("10%");
        tableRow.getCell(1).setWidth("15%");
        tableRow.getCell(2).setWidth("60%");
        tableRow.getCell(3).setWidth("15%");
    }
}
