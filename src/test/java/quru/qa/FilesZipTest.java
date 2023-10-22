package quru.qa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.apache.commons.jexl2.parser.ParserConstants.or;

public class FilesZipTest {

    private static final String[][] FILE_CSV = new String[][]{
            {"Тип топлива", "Сейчас"},
            {"Дизель", "56.85 ₽"},
            {"Бензин АИ-92", "47.10 ₽"},
            {"Бензин АИ-95", "50.52 ₽"},
            {"Бензин АИ-98", "60.59 ₽"}
    };
    private static final String FILE_XLSX = "Тестовый 1";
    public ClassLoader classLoader = FilesZipTest.class.getClassLoader();

    @DisplayName("Чтение и проверка сожержимого pdf файла")
    @Test
    void pdfFileTest() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("ZIP.zip")) {
            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().contains("pdf")) {
                    PDF pdf = new PDF(zip);
                    Assertions.assertTrue(pdf.text.contains("Справочная информация"));
                    Assertions.assertTrue(pdf.title.contains("Краткое справочное руководство"));
                    Assertions.assertTrue(pdf.author.contains("Nuance Communications, Inc."));
                }
            }
        }
    }

    @DisplayName("Чтение и проверка сожержимого csv файла")
    @Test
    void csvFileTes() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("ZIP.zip")) {
            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().contains("csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zip));
                    List<String[]> csv = csvReader.readAll();
                    Assertions.assertArrayEquals(FILE_CSV, csv.toArray());
                }
            }
        }
    }

    @DisplayName("Чтение и проверка сожержимого xlsx файла")
    @Test
    void xlsxFileTes2() throws Exception {
        try (InputStream is = classLoader.getResourceAsStream("ZIP.zip")) {
            ZipInputStream zip = new ZipInputStream(is);
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.getName().contains("xlsx")) {
                    XLS xls = new XLS(zip);
                    Assertions.assertEquals(FILE_XLSX,
                            xls.excel.getSheetAt(0)
                                    .getRow(0)
                                    .getCell(1)
                                    .getStringCellValue());
                }
            }
        }
    }

}