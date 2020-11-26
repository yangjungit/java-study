package com.giovanny.study.poi;

import com.giovanny.study.constant.ExcelFormat;
import com.giovanny.study.entity.ExcelHeaderInfo;
import com.giovanny.study.entity.po.Uuu;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @packageName: com.giovanny.study.poi
 * @className: ExcelUtil
 * @description:
 * @author: YangJun
 * @date: 2020/5/7 15:39
 * @version: v1.0
 **/
@Slf4j
public class ExcelUtil {
    public static final int ROW_ACCESS_WINDOW_SIZE = 100;
    public static final int SHEET_MAX_ROW = 100000;

    private List list;
    private List<ExcelHeaderInfo> excelHeaderInfos;
    private Map<String, ExcelFormat> formatInfo;

    public ExcelUtil(List list, List<ExcelHeaderInfo> excelHeaderInfos, Map<String, ExcelFormat> formatInfo) {
        this.list = list;
        this.excelHeaderInfos = excelHeaderInfos;
        this.formatInfo = formatInfo;
    }

    public Workbook getWorkbook() {
        Workbook workbook = new SXSSFWorkbook(ROW_ACCESS_WINDOW_SIZE);
        //获取实体类的字段  就是excel的列
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        String[][] datas = transformData(fields);
        CellStyle style = setCellStyle(workbook);
        int pageRowNum = 0;
        Sheet sheet = null;

        long startTime = System.currentTimeMillis();
        log.info("开始处理excel文件。。。");

        for (int i = 0; i < datas.length; i++) {
            // 如果是每个sheet的首行
            if (i % SHEET_MAX_ROW == 0) {
                // 创建新的sheet
                sheet = createSheet(workbook, i);
                pageRowNum = 0; // 行号重置为0
                for (int j = 0; j < getHeaderRowNum(excelHeaderInfos); j++) {
                    sheet.createRow(pageRowNum++);
                }
                createHeader(sheet, style);
            }
            // 创建内容
            Row row = sheet.createRow(pageRowNum++);
            createContent(row, style, datas, i, fields);
        }
        log.info("处理文本耗时" + (System.currentTimeMillis() - startTime) + "ms");
        return workbook;
    }

    /**
     * 将原始数据转成二维数组
     *
     * @param fields
     * @return
     */
    private String[][] transformData(Field[] fields) {
        //行数
        int dataSize = this.list.size();
        String[][] datas = new String[dataSize][];
        // 获取报表的列数
        // 获取实体类的字段名称数组
        List<String> columnNames = this.getBeanProperty(fields);
        for (int i = 0; i < dataSize; i++) {
            datas[i] = new String[fields.length];
            for (int j = 0; j < fields.length; j++) {
                try {
                    /*
                      利用Java的反射可以这样拿到属性的值<pre>
                        Uuu uuu = new Uuu();
                        uuu.setId(1);
                        uuu.setName("msds");
                        uuu.setDescription("ds");
                        Field[] fields = uuu.getClass().getDeclaredFields();
                        Method getName = uuu.getClass().getMethod("getName");
                        String name = fields[0].getName();
                        Object invoke = getName.invoke(uuu);
                      有封装的有更简单的
                     */
                    // 赋值
                    datas[i][j] = BeanUtils.getProperty(list.get(i), columnNames.get(j));
                } catch (Exception e) {
                    log.error("获取对象属性值失败");
                    e.printStackTrace();
                }
            }
        }
        return datas;
    }

    /**
     * 获取实体类的字段名称数组
     *
     * @param fields 列   实体类的属性
     * @return 列
     */
    private List<String> getBeanProperty(Field[] fields) {
        List<String> columnNames = new ArrayList<>();
        for (Field field : fields) {
            String[] strings = field.toString().split("\\.");
            String columnName = strings[strings.length - 1];
            columnNames.add(columnName);
        }
        return columnNames;
    }

    /**
     * 设置样式
     *
     * @param workbook workbook
     * @return CellStyle
     */
    private CellStyle setCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        return style;
    }

    /**
     * 创建sheet
     *
     * @param workbook workbook
     * @param i        页
     * @return sheet
     */
    private Sheet createSheet(Workbook workbook, int i) {
        int sheetNum = i / SHEET_MAX_ROW;
        workbook.createSheet("sheet" + sheetNum);
        //动态指定当前的工作表
        return workbook.getSheetAt(sheetNum);
    }


    /**
     * 获取标题总行数
     *
     * @param headerInfos List<ExcelHeaderInfo>
     * @return
     */
    private int getHeaderRowNum(List<ExcelHeaderInfo> headerInfos) {
        int maxRowNum = 0;
        for (ExcelHeaderInfo excelHeaderInfo : headerInfos) {
            int tmpRowNum = excelHeaderInfo.getLastRow();
            if (tmpRowNum > maxRowNum) {
                maxRowNum = tmpRowNum;
            }
        }
        return maxRowNum + 1;
    }

    /**
     * 创建表头
     *
     * @param sheet sheet
     * @param style style
     */
    private void createHeader(Sheet sheet, CellStyle style) {
        for (ExcelHeaderInfo excelHeaderInfo : excelHeaderInfos) {
            int lastRow = excelHeaderInfo.getLastRow();
            int firstRow = excelHeaderInfo.getFirstRow();
            int lastCol = excelHeaderInfo.getLastCol();
            int firstCol = excelHeaderInfo.getFirstCol();

            // 行距或者列距大于0才进行单元格融合
            if ((lastRow - firstRow) != 0 || (lastCol - firstCol) != 0) {
                sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
            }
            // 获取当前表头的首行位置
            Row row = sheet.getRow(firstRow);
            // 在表头的首行与首列位置创建一个新的单元格
            Cell cell = row.createCell(firstCol);
            // 赋值单元格
            cell.setCellValue(excelHeaderInfo.getTitle());
            cell.setCellStyle(style);
            sheet.setColumnWidth(firstCol, sheet.getColumnWidth(firstCol) * 17 / 12);
        }
    }


    // 创建正文
    private void createContent(Row row, CellStyle style, String[][] content, int i, Field[] fields) {
        List<String> columnNames = getBeanProperty(fields);
        for (int j = 0; j < columnNames.size(); j++) {
            // 如果格式化Map为空，默认为字符串格式
            if (formatInfo == null) {
                row.createCell(j).setCellValue(content[i][j]);
                continue;
            }
            if (formatInfo.containsKey(columnNames.get(j))) {
                switch (formatInfo.get(columnNames.get(j)).getValue()) {
                    case "DOUBLE":
                        row.createCell(j).setCellValue(Double.parseDouble(content[i][j]));
                        break;
                    case "INTEGER":
                        log.info("content[i][j]={} ,columnNames.get(j) ={} ,i={},j={}", content[i][j], columnNames.get(j), i, j);
                        log.info("datas={}", content);
                        row.createCell(j).setCellValue(Integer.parseInt(content[i][j]));
                        break;
                    case "PERCENT":
                        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
                        Cell cell = row.createCell(j);
                        cell.setCellStyle(style);
                        cell.setCellValue(Double.parseDouble(content[i][j]));
                        break;
                    case "DATE":
                        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd HH:mm:ss"));
                        Cell cell1 = row.createCell(j);
                        cell1.setCellStyle(style);
                        row.createCell(j).setCellValue(this.parseDate(content[i][j]));
                }
            } else {
                row.createCell(j).setCellValue(content[i][j]);
            }
        }
    }

    /**
     * 字符串转日期
     *
     * @param strDate strDate
     * @return Date
     */
    private Date parseDate(String strDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(strDate);
        } catch (Exception e) {
            log.error("字符串转日期错误");
            e.printStackTrace();
        }
        return date;
    }


    public static void main(String[] args) throws Exception {
        Uuu uuu = new Uuu();
        uuu.setId(1);
        uuu.setName("msds");
        uuu.setDescription("ds");
        Field[] fields = uuu.getClass().getDeclaredFields();
        Method getName = uuu.getClass().getMethod("getName");
        String name = fields[0].getName();
        Object invoke = getName.invoke(uuu);

    }


    public void sendHttpResponse(HttpServletResponse response, String fileName, Workbook workbook) {
        try {
            fileName += System.currentTimeMillis() + ".xlsx";
            fileName = new String(fileName.getBytes(), "ISO8859-1");
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
