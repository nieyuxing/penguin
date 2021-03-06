package com.qexz.util;

import com.qexz.model.Question;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class QuestionExcelReader {


    private static Logger logger = Logger.getLogger(QuestionExcelReader.class.getName()); // 日志打印类

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 根据文件后缀名类型获取对应的工作簿对象
     * @param inputStream 读取文件的输入流
     * @param fileType 文件后缀名类型（xls或xlsx）
     * @return 包含文件数据的工作簿对象
     * @throws IOException
     */
    public static Workbook getWorkbook(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    /**
     * 读取Excel文件内容
     * @param fileName 要读取的Excel文件所在路径
     * @return 读取结果列表，读取失败时返回null
     */
    public static List<Question> readExcel(String fileName) throws Exception {
        Workbook workbook = null;
        FileInputStream inputStream = null;
        try {
            // 获取Excel后缀名
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            // 获取Excel文件
            File excelFile = new File(fileName);
            if (!excelFile.exists()) {
                logger.info("指定的Excel文件不存在！");
                return null;
            }

            // 获取Excel工作簿
            inputStream = new FileInputStream(excelFile);
            workbook = getWorkbook(inputStream, fileType);

            // 读取excel中的数据
            List<Question> resultDataList = parseExcel(workbook);

            return resultDataList;
        } catch (Exception e) {
            logger.info("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage());
            throw new Exception("解析Excel失败，文件名：" + fileName + " 错误信息：" + e.getMessage()) ;
        } finally {
            try {
                if (null != workbook) {
                    workbook.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.info("关闭数据流出错！错误信息：" + e.getMessage());
                return null;
            }
        }
    }

    /**
     * 解析Excel数据
     * @param workbook Excel工作簿对象
     * @return 解析结果
     */
    private static List<Question> parseExcel(Workbook workbook) {
        List<Question> resultDataList = new ArrayList<>();
        // 解析sheet
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            Sheet sheet = workbook.getSheetAt(sheetNum);

            // 校验sheet是否合法
            if (sheet == null) {
                continue;
            }

            // 获取第一行数据
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            if (null == firstRow) {
                logger.info("解析Excel失败，在第一行没有读取到任何数据！");
            }

            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum ; //+1可以忽略第一行 不加就从第0行开始
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (null == row) {
                    continue;
                }

                Question resultData = convertRowToData(row);
                if (null == resultData) {
                    logger.info("第 " + row.getRowNum() + "行数据不合法，已忽略！");
                    continue;
                }
                resultDataList.add(resultData);
            }
        }

        return resultDataList;
    }

    /**
     * 将单元格内容转换为字符串
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if(cell==null){
            return null;
        }
        String returnValue = null;
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                Double doubleValue = cell.getNumericCellValue();

                // 格式化科学计数法，取一位整数
                DecimalFormat df = new DecimalFormat("0");
                returnValue = df.format(doubleValue);
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                break;
            case FORMULA:   // 公式
                returnValue = cell.getCellFormula();
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }


    private static Question convertRowToData(Row row) {
        Question resultData = new Question();
        Cell cell;
        int cellNum = 0;
        cell = row.getCell(cellNum++);

        String title=convertCellValueToString(cell); //获取第一格内容
        resultData.setTitle(title);
        // 获取密码
        cell = row.getCell(cellNum++); //下一列
        String content=convertCellValueToString(cell); //获取第二格内容
        resultData.setContent(content);
        //获取费用
        cell = row.getCell(cellNum++); //下一列
        String question_type = convertCellValueToString(cell); //获取第一格内容

        switch (question_type){
            case "单选": resultData.setQuestionType(0);
                break;
            case "多选": resultData.setQuestionType(1);
                break;
            case "问答": resultData.setQuestionType(2);
                break;
            case "编程": resultData.setQuestionType(3);
                break;
            default:
                break;
        }
        cell = row.getCell(cellNum++); //下一列
        String A=convertCellValueToString(cell); //获取第二格内容
        resultData.setOptionA(A);

        cell = row.getCell(cellNum++); //下一列
        String B=convertCellValueToString(cell); //获取第二格内容
        resultData.setOptionB(B);

        cell = row.getCell(cellNum++); //下一列
        String C=convertCellValueToString(cell); //获取第二格内容
        resultData.setOptionC(C);

        cell = row.getCell(cellNum++); //下一列
        String D=convertCellValueToString(cell); //获取第二格内容
        resultData.setOptionD(D);

        cell = row.getCell(cellNum++); //下一列
        String answer=convertCellValueToString(cell); //获取第二格内容
        resultData.setAnswer(answer);

        cell = row.getCell(cellNum++); //下一列
        String parse=convertCellValueToString(cell); //获取第二格内容
        resultData.setParse(parse);

        cell = row.getCell(cellNum++); //下一列
        String diff=convertCellValueToString(cell); //获取第二格内容
        resultData.setDifficulty(Integer.parseInt(diff));

        cell = row.getCell(cellNum++); //下一列
        String tips=convertCellValueToString(cell); //获取第二格内容
        resultData.setTips(tips);

        resultData.setContestId(0);
        return resultData;
    }


}
