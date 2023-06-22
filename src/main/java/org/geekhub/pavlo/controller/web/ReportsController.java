package org.geekhub.pavlo.controller.web;

import com.itextpdf.text.DocumentException;
import org.geekhub.pavlo.service.ReportGoodsRemainsService;
import org.geekhub.pavlo.service.ReportMovementsWithRemainsService;
import org.geekhub.pavlo.service.ReportSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class ReportsController {
    final ReportGoodsRemainsService reportGoodsRemainsService;
    final ReportSalesService reportSalesService;
    final ReportMovementsWithRemainsService reportMovementsWithRemainsService;

    @Autowired
    public ReportsController(ReportGoodsRemainsService reportGoodsRemainsService,
                             ReportSalesService reportSalesService,
                             ReportMovementsWithRemainsService reportMovementsWithRemainsService) {
        this.reportGoodsRemainsService = reportGoodsRemainsService;
        this.reportSalesService = reportSalesService;
        this.reportMovementsWithRemainsService = reportMovementsWithRemainsService;
    }

    @GetMapping("/report-goods-remains")
    public String showReportRemainsPage(Model model) {
        model.addAttribute("currentDay", LocalDate.now());
        return "ReportGoodsRemain";
    }

    @GetMapping("/report-goods-remains/show")
    public ResponseEntity<byte[]> showReportRemains(@RequestParam("remainsDate") String repDateStr) throws IOException {
        LocalDateTime repDate = getLocalDateTimeFromStringPrm(repDateStr);

        byte[] fileContent = reportGoodsRemainsService.getReportWordFile(repDate);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
        headers.setContentDispositionFormData("inline", "Goods remains.docx");
        headers.setContentLength(fileContent.length);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/report-sales")
    public String showReportSalesPage(Model model) {
        model.addAttribute("currentDay", LocalDate.now());
        return "ReportSales";
    }

    @GetMapping("/report-sales/show")
    public ResponseEntity<byte[]> showReportSales(@RequestParam("dateFrom") String dateFromStr,
                                                  @RequestParam("dateTo") String dateToStr) throws IOException {
        LocalDateTime dateFrom = getLocalDateTimeFromStringPrm(dateFromStr);
        LocalDateTime dateTo = getLocalDateTimeFromStringPrm(dateToStr);

        byte[] fileContent = reportSalesService.getReportExcelFile(dateFrom, dateTo);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("inline", "Sales.xlsx");
        headers.setContentLength(fileContent.length);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    @GetMapping("/report-movements-with-remains")
    public String showReportMovementsWithRemainsPage(Model model) {
        model.addAttribute("currentDay", LocalDate.now());
        return "ReportMovementsWithRemains";
    }

    @GetMapping("/report-movements-with-remains/show")
    public ResponseEntity<byte[]> showReportMovementsWithRemains(@RequestParam("dateFrom") String dateFromStr,
                                                                 @RequestParam("dateTo") String dateToStr,
                                                                 @RequestParam(name = "goodsId", required = false) String goodsIdStr) throws DocumentException {
        if (dateFromStr == null || dateFromStr.isEmpty()) {
            throw new IllegalArgumentException("Date from must not be empty");
        }
        LocalDateTime dateFrom = getLocalDateTimeFromStringPrm(dateFromStr);

        if (dateToStr == null || dateToStr.isEmpty()) {
            throw new IllegalArgumentException("Date to must not be empty");
        }
        LocalDateTime dateTo = getLocalDateTimeFromStringPrm(dateToStr);

        if (goodsIdStr == null || goodsIdStr.isEmpty()) {
            throw new IllegalArgumentException("Goods must not be empty");
        }
        int goodsId = Integer.valueOf(goodsIdStr);

        byte[] fileContent = reportMovementsWithRemainsService.getReportPDFFile(dateFrom, dateTo, goodsId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.setContentDispositionFormData("inline", "Movements-with-remains.pdf");
        headers.setContentLength(fileContent.length);

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    private LocalDateTime getLocalDateTimeFromStringPrm(String dateStr) {
        return LocalDateTime.parse(dateStr + "T00:00:00");
    }

}
