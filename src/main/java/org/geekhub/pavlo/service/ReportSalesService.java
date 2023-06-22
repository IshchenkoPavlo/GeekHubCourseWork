package org.geekhub.pavlo.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.geekhub.pavlo.dto.GoodsRemainDto;
import org.geekhub.pavlo.repository.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportSalesService {
    ReportsRepository reportsRepository;

    @Autowired
    public ReportSalesService(ReportsRepository reportsRepository) {
        this.reportsRepository = reportsRepository;
    }

    public byte[] getReportExcelFile(LocalDateTime dateFrom, LocalDateTime dateTo) throws IOException {
        List<GoodsRemainDto> records = reportsRepository.sales(dateFrom, dateTo);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Sheet1");

        sheet.setColumnWidth(0, 1000);
        sheet.setColumnWidth(1, 2000);
        sheet.setColumnWidth(2, 20000);
        sheet.setColumnWidth(3, 3000);

        String headerText = String.format("Sales for the period from %s to %s",
                DateTimeFormatter.ofPattern("dd.MM.yy").format(dateFrom),
                DateTimeFormatter.ofPattern("dd.MM.yy").format(dateTo));
        addHeader(workbook, sheet, headerText);

        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

        addTableHeader(workbook, sheet);

        CellStyle[] tableBodyStyles = createTableBodyStyles(workbook);
        for (int i = 0; i < records.size(); i++) {
            GoodsRemainDto rec = records.get(i);
            row = sheet.createRow(sheet.getLastRowNum() + 1);

            XSSFCell cell = setBodyCellProperty(row, 0, tableBodyStyles);
            cell.setCellValue(i + 1);

            cell = setBodyCellProperty(row, 1, tableBodyStyles);
            cell.setCellValue(rec.getId());

            cell = setBodyCellProperty(row, 2, tableBodyStyles);
            cell.setCellValue(rec.getName());

            cell = setBodyCellProperty(row, 3, tableBodyStyles);
            cell.setCellValue(rec.getRemain().doubleValue());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }

    private void addHeader(XSSFWorkbook workbook, XSSFSheet sheet, String headerText) {
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        CellStyle style = workbook.createCellStyle();
        style.setFont(font);

        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(headerText);
        cell.setCellStyle(style);
    }

    private void addTableHeader(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);

        Font font = workbook.createFont();
        font.setBold(true);

        CellStyle style = workbook.createCellStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        circleCell(style);

        String[] headers = {"N", "Code", "Goods name", "Sale amount"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private CellStyle[] createTableBodyStyles(XSSFWorkbook workbook) {
        CellStyle[] styles = new CellStyle[4];

        for (int i = 0; i < styles.length; i++) {
            styles[i] = workbook.createCellStyle();
            styles[i].setVerticalAlignment(VerticalAlignment.CENTER);
            circleCell(styles[i]);
        }

        styles[0].setAlignment(HorizontalAlignment.CENTER);
        styles[1].setAlignment(HorizontalAlignment.CENTER);
        styles[2].setAlignment(HorizontalAlignment.LEFT);
        styles[3].setAlignment(HorizontalAlignment.RIGHT);

        return styles;
    }

    private XSSFCell setBodyCellProperty(XSSFRow row, int colNum, CellStyle[] styles) {
        XSSFCell res = row.createCell(colNum);
        res.setCellStyle(styles[colNum]);
        return res;
    }

    private void circleCell(CellStyle style) {
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
    }
}
