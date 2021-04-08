package com.indas.portal.export;

import com.indas.portal.service.dto.CarDto;
import com.indas.portal.service.dto.PartDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.Arrays.asList;

public class ExelExportPart {

    private Workbook workbook;
    private Sheet sheet;
    private PartDto partDto;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    private String NAME_SHEET = "Партия";
    private short FONT_SCALAR = 20;
    private final int ROUND_MODE = ROUND_HALF_UP;

    private final List<String> titles = asList(
            "пп №",
            "Номер вагона",
            "Время взвешивания",
            "Тара, т",
            "Тара НСИ, т",
            "Дельта Тара, т",
            "Брутто, т",
            "Нетто, т",
            "+/- к гр/под,",
            "Левая тележка, т",
            "Правая тележка, т",
            "тел.1 - тел.2,",
            "Груз-сть",
            "Зона",
            "Грузоотправитель",
            "Грузополучатель",
            "Вид материала",
            "Итог атт.",
            "Причина не аттестации"
    );

    public ExelExportPart(PartDto partDto) {
        this.partDto = partDto;
        workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(NAME_SHEET);
    }

    private void writePartHeader() {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        short fontSize = (short) (14 * FONT_SCALAR);
        font.setFontHeight(fontSize);
        style.setFont(font);

        Row row = null;

        row = sheet.createRow(0);
        createCell(row, 0, "Время начала аттестации:", style);
        createCell(row, 1, dateFormat.format(new Date(partDto.startTimeUts)), style);

        row = sheet.createRow(1);
        createCell(row, 0, "Время окончания:", style);
        createCell(row, 1, dateFormat.format(new Date(partDto.endTimeUts)), style);

        row = sheet.createRow(2);
        createCell(row, 0, "Продолжительность", style);
        LocalDateTime end = LocalDateTime.ofInstant(new Date(partDto.endTimeUts).toInstant(), ZoneId.systemDefault());
        LocalDateTime start = LocalDateTime.ofInstant(new Date(partDto.startTimeUts).toInstant(), ZoneId.systemDefault());
        LocalDateTime result = minus(end,start);
        createCell(row, 1, result.getHour()+":"+result.getMinute()+"(ч:мин)", style);

        row = sheet.createRow(3);
        createCell(row, 0, "Аттестующий", style);
        createCell(row, 1, partDto.oper, style);
    }

    public LocalDateTime minus(LocalDateTime firstDate, LocalDateTime secondDate) {
        return firstDate
                .minusYears(secondDate.getYear())
                .minusMonths(secondDate.getMonthValue())
                .minusDays(secondDate.getDayOfMonth())
                .minusHours(secondDate.getHour())
                .minusMinutes(secondDate.getMinute())
                .minusSeconds(secondDate.getSecond());
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Float) {
            cell.setCellValue((Float) value);
        } else if (value instanceof BigDecimal) {
            cell.setCellValue(value.toString());
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeCarHeader() {

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        short fontSize = (short) (14 * FONT_SCALAR);
        font.setFontHeight(fontSize);
        style.setFont(font);

        Row row = sheet.createRow(5);

        for (int i = 0; titles.size() > i; i++){
            createCell(row, i, titles.get(i), style);
        }
    }


    private BigDecimal getBigDecimal(Float f) {
        if (f == null) {
            f = 0.0f;
        }
        return BigDecimal.valueOf(f).setScale(2, ROUND_MODE);
    }

    private void writeCars() {
        int rowCount = 6;

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        short fontSize = (short) (12 * FONT_SCALAR);
        font.setFontHeight(fontSize);
        style.setFont(font);

        for (CarDto car : partDto.cars) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            BigDecimal brutto = getBigDecimal(car.brutto);
            BigDecimal taraE = getBigDecimal(car.taraE);
            BigDecimal netto = brutto.subtract(taraE);
            BigDecimal tara = getBigDecimal(car.tara);
            BigDecimal taraDelta = taraE.subtract(tara);
            BigDecimal leftTruck = getBigDecimal(car.leftTruck);
            BigDecimal rightTruck = getBigDecimal(car.rightTruck);
            BigDecimal subtractLeftRight = leftTruck.subtract(rightTruck);
            BigDecimal maxWheight = getBigDecimal(car.maxWheight);
            BigDecimal grPod = netto.subtract(maxWheight);


            createCell(row, columnCount++, car.id, style);
            createCell(row, columnCount++, car.num, style);
            createCell(row, columnCount++, dateFormat.format(new Date(car.weighingTimeUts)), style);
            createCell(row, columnCount++, tara, style);
            createCell(row, columnCount++, taraE, style);
            createCell(row, columnCount++, taraDelta, style);
            createCell(row, columnCount++, brutto, style);
            createCell(row, columnCount++, netto, style);
            createCell(row, columnCount++, grPod, style);
            createCell(row, columnCount++, leftTruck, style);
            createCell(row, columnCount++, rightTruck, style);
            createCell(row, columnCount++, subtractLeftRight, style);
            createCell(row, columnCount++, maxWheight, style);
            createCell(row, columnCount++, car.zoneE, style);
            createCell(row, columnCount++, car.shipper != null ? car.shipper.name : "", style);
            createCell(row, columnCount++, car.consignee != null ? car.consignee.name : "", style);
            createCell(row, columnCount++, car.mat != null ? car.mat.name : "", style);
            createCell(row, columnCount++, car.attCode, style);
            createCell(row, columnCount++, car.cause != null ? car.cause.name : "", style);
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writePartHeader();
        writeCarHeader();
        writeCars();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }

}
