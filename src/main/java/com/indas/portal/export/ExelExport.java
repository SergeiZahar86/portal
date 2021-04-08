package com.indas.portal.export;

import com.indas.portal.entities.Part;
import com.indas.portal.repositories.PartRepository;
import com.indas.portal.service.Maper;
import com.indas.portal.service.dto.PartDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

@Component
public class ExelExport {

    public void exportPart(PartDto partDto, HttpServletResponse response){
        ExelExportPart exportPart = new ExelExportPart(partDto);
        try {
            exportPart.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void writeIntoExcel(HttpServletResponse response) throws FileNotFoundException, IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Birthdays");

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);

        // Мы запишем имя и дату в два столбца
        // имя будет String, а дата рождения --- Date,
        // формата dd.mm.yyyy
        Cell name = row.createCell(0);
        name.setCellValue("John");

        Cell birthdate = row.createCell(1);

        DataFormat format = book.createDataFormat();
        CellStyle dateStyle = book.createCellStyle();
        dateStyle.setDataFormat(format.getFormat("dd.mm.yyyy"));
        birthdate.setCellStyle(dateStyle);


        // Нумерация лет начинается с 1900-го
        birthdate.setCellValue(new Date(110, 10, 10));

        // Меняем размер столбца
        sheet.autoSizeColumn(1);

        ServletOutputStream stream = response.getOutputStream();
        book.write(stream);
        book.close();
        stream.close();
    }

}
