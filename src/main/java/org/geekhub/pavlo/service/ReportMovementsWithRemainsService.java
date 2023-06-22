package org.geekhub.pavlo.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.geekhub.pavlo.DocsUtils;
import org.geekhub.pavlo.model.GoodsRemainsRegister;
import org.geekhub.pavlo.model.RegisterMovementTypes;
import org.geekhub.pavlo.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ReportMovementsWithRemainsService {
    ReportsRepository reportsRepository;

    @Autowired
    public ReportMovementsWithRemainsService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    public byte[] getReportPDFFile(LocalDateTime dateFrom, LocalDateTime dateTo, int goodsId) throws DocumentException {
        Document document = new Document();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);

        document.open();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        String headerText = String.format("Movement with remains from %s to %s",
                dateTimeFormatter.format(dateFrom),
                dateTimeFormatter.format(dateTo));
        document.add(new Paragraph(headerText));
        document.add(new Paragraph(" "));


        PdfPTable table = new PdfPTable(6);

        float[] columnWidths = {0.05f, 0.15f, 0.35f, 0.15f, 0.15f, 0.15f};
        table.setWidths(columnWidths);

        Stream.of("+/-", "Date", "Document", "Arrival", "Leaving", "Remain")
                .forEach((h) -> {
                    addCellToTable(table, h, Element.ALIGN_CENTER);
                });

        BigDecimal beginRemain = reportsRepository.goodsRemain(dateFrom, goodsId);
        PdfPCell cell = addCellToTable(table, "Start remain", Element.ALIGN_RIGHT, false);
        cell.setColspan(5);
        table.addCell(cell);
        addCellToTable(table, beginRemain.toString(), Element.ALIGN_RIGHT);

        BigDecimal arrival;
        BigDecimal leaving;
        BigDecimal arrivalTotal = BigDecimal.ZERO;
        BigDecimal leavingTotal = BigDecimal.ZERO;
        BigDecimal curentRemain = beginRemain;
        List<GoodsRemainsRegister> movies = reportsRepository.goodsRemainsMovies(dateFrom, dateTo, goodsId);
        for (GoodsRemainsRegister m : movies) {
            if (m.getPlusMinus() == RegisterMovementTypes.PLUS) {
                addCellToTable(table, "+", Element.ALIGN_CENTER);
                arrival = m.getQuantity();
                leaving = BigDecimal.ZERO;
                arrivalTotal = arrivalTotal.add(arrival);
                curentRemain = curentRemain.add(arrival);
            } else {
                addCellToTable(table, "-", Element.ALIGN_CENTER);
                arrival = BigDecimal.ZERO;
                leaving = m.getQuantity();
                leavingTotal = leavingTotal.add(leaving);
                curentRemain = curentRemain.subtract(leaving);
            }

            addCellToTable(table, dateTimeFormatter.format(m.getDocDate()), Element.ALIGN_CENTER);
            addCellToTable(table,
                    DocsUtils.docNameByTypeId(m.getDocType()) + " " + m.getDocNum(),
                    Element.ALIGN_LEFT);
            addCellToTable(table, arrival.toString(), Element.ALIGN_RIGHT);
            addCellToTable(table, leaving.toString(), Element.ALIGN_RIGHT);
            addCellToTable(table, curentRemain.toString(), Element.ALIGN_RIGHT);
        }

        cell = addCellToTable(table, "Total", Element.ALIGN_RIGHT, false);
        cell.setColspan(3);
        table.addCell(cell);
        addCellToTable(table, arrivalTotal.toString(), Element.ALIGN_RIGHT);
        addCellToTable(table, leavingTotal.toString(), Element.ALIGN_RIGHT);
        addCellToTable(table, curentRemain.toString(), Element.ALIGN_RIGHT);

        document.add(table);
        document.close();

        return outputStream.toByteArray();
    }

    private PdfPCell addCellToTable(PdfPTable table, String cellText, int horizontalAlignment, Boolean doAdd) {
        PdfPCell cell = new PdfPCell();
        cell.setBorderWidth(1);
        cell.setPhrase(new Phrase(cellText));
        cell.setHorizontalAlignment(horizontalAlignment);

        if (doAdd) {
            table.addCell(cell);
        }


        return cell;
    }

    private PdfPCell addCellToTable(PdfPTable table, String cellText, int horizontalAlignment) {
        return addCellToTable(table, cellText, horizontalAlignment, true);
    }
}
