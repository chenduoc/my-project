package com.chen.my_project.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 * Excel工具类
 * 
 * @author ChenDuochuang
 * @date 2018年11月7日
 */
public final class ExcelUtil {

    /** 日志 */
    private static final Logger logger = LogManager.getLogger(ExcelUtil.class);

    private ExcelUtil() {
    }

    /**
     * 读入Excel文件获取Workbook对象
     * @param multipartFile
     * @return
     * @author ChenDC
     */
    public static Workbook readExcel(MultipartFile multipartFile) {
        Workbook workbook = null;
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        try {
            if (ExcelConstants.SUFFIX_XLS.equals(suffix)) {
                workbook = new HSSFWorkbook(multipartFile.getInputStream());
            } else if (ExcelConstants.SUFFIX_XLSX.equals(suffix)) {
                workbook = new XSSFWorkbook(multipartFile.getInputStream());
            }
        } catch (Exception e) {
            logger.error("读取文件：{}出错，错误信息：", fileName, e);
            return null;
        }

        return workbook;
    }

    /**
     * 从workbook中读取信息
     * @param sheet
     * @return
     * @author ChenDC
     */
    public static void getValueFromExcel(Workbook workbook) {
        logger.info("开始解析Excel文件");
        Sheet sheet = workbook.getSheetAt(0); // 获取第一个sheet
        Row row = null;
        int rowNum = sheet.getLastRowNum();

        // 从第1行开始
        for (int i = 0; i <= rowNum; i++) {
            row = sheet.getRow(i);
            if (null != row) {
                // 跳过空行
                if (isCellEmpty(row.getCell(0))) {
                    continue;
                }

                // 取第1列的值
                String value = getCellValue(row.getCell(0));
                logger.info("++value:" + value);
            }
        }
    }

    /**
     * 生成Excel
     * @param workbook
     * @param list
     * @author ChenDuochuang
     */
    public static void generateExcelData(Workbook workbook, List<String> list) {
        // 取得sheet页
        Sheet sheet = workbook.getSheetAt(0);
        // 内容单元格样式
        CellStyle conetentCellStyle = buildCellStyle(workbook);

        int contentStartRow = 1;
        for (String value : list) {
            Row contentRow = sheet.createRow(contentStartRow++);
            ExcelUtil.createHSSFCell(contentRow, 0, value, conetentCellStyle);
        }
    }

    /**
     * 转成一个下载的响应体
     * @param fileName
     * @param response
     * @author ChenDC
     * @throws UnsupportedEncodingException
     */
    public static void convertDownloadResponse(String fileName, HttpServletResponse response)
            throws UnsupportedEncodingException {
        response.setContentType("application/octet-stream");
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=40");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8").concat(".xls"));
    }

    /**
     * 下载Excel
     * @param workbook
     * @param output
     * @throws Exception
     * @author ChenDC
     */
    public static void downloadExcel(Workbook workbook, OutputStream output) throws Exception {
        workbook.write(output);
        output.flush();
        output.close();
        workbook.close();
    }

    /**
     * 将Excel文件输出到本地文件
     * @param workbook
     * @throws IOException
     * @author ChenDuochuang
     */
    public static void outputExcelFile(Workbook workbook) throws IOException {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());// 设置日期格式
        String format = df.format(new Date());
        String excelFilePath = "F:/Excel_" + format + ExcelConstants.SUFFIX_XLS;
        logger.info("写入EXCEL：{}", excelFilePath);
        FileOutputStream fileOut = new FileOutputStream(excelFilePath);
        workbook.write(fileOut);
        fileOut.close();
    }

    /**
     * 获取类对应的配置文件流
     * @param clzz
     * @return
     * @author ChenDC
     */
    public static InputStream getResourceAsStream(Class<?> clzz) {
        return clzz.getResourceAsStream("/excel/T-BOX信息导出模板.xls");
    }

    /**
     * 创建单元格
     * 
     * @param row 单元格所在的行
     * @param index 单元格序号
     * @param value 单元格内容
     * @param style 单元格样式
     * @return
     */
    private static Cell createHSSFCell(Row row, int index, String value, CellStyle style) {
        Cell cell = row.createCell(index, CellType.STRING);
        cell.setCellValue(value);
        cell.setCellStyle(style);

        return cell;
    }

    /**
     * 创建Excel标准内容格式
     * @param workbook
     * @return
     * @author ChenDC
     */
    private static CellStyle buildCellStyle(Workbook workbook) {
        // 内容单元格样式
        CellStyle contentCellStyle = workbook.createCellStyle();
        contentCellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
        contentCellStyle.setBorderLeft(BorderStyle.THIN); // 左边框
        contentCellStyle.setBorderTop(BorderStyle.THIN); // 上边框
        contentCellStyle.setBorderRight(BorderStyle.THIN); // 右边框
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直居中

        return contentCellStyle;
    }

    /**
     * 获取cell中的值
     * @param cell
     * @return
     * @author ChenDC
     */
    private static String getCellValue(Cell cell) {
        if (null != cell) {
            if (CellType.NUMERIC.equals(cell.getCellTypeEnum())) {
                cell.setCellType(CellType.STRING);
            }
            return cell.getStringCellValue();
        }
        logger.error("未解析到cell");
        return null;
    }

    /**
     * 验证Cell是否为空
     * 
     * <pre>
     * true：有为空的cell
     * false：没有为空的cell
     * 
     * <pre>
     * @param cells
     * @return
     * @author ChenDC
     */
    private static boolean isCellEmpty(Cell... cells) {
        for (Cell cell : cells) {
            if (null == cell) {
                return true;
            }
        }
        return false;
    }
}
