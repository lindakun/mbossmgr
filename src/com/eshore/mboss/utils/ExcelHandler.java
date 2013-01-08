package com.eshore.mboss.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.jfinal.plugin.activerecord.Model;

/**
 * 导入导出Excel
 * 
 * @author LinDK
 * 
 */
public class ExcelHandler {
	public static Logger logger = LogManager.getLogger(ExcelHandler.class
			.getName());

	/**
	 * 导出数据为XLS格式
	 * 
	 * @param fileName
	 *            文件的名称，可以设为绝对路径，也可以设为相对路径
	 * @param content
	 *            数据的内容
	 * @param title
	 *            标题
	 */
	@SuppressWarnings({ "rawtypes" })
	public static boolean exportExcel(String fileName, List modelList,
			Map<String, String> titles) {
		WritableWorkbook wwb = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fileName);
			wwb = Workbook.createWorkbook(fos);
			WritableSheet ws = wwb.createSheet("temp", 10); // 创建一个工作表

			// 设置单元格的文字格式
			WritableFont wf = new WritableFont(WritableFont.ARIAL, 12,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLUE);
			WritableCellFormat wcf = new WritableCellFormat(wf);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			wcf.setAlignment(Alignment.CENTRE);
			ws.setRowView(1, 500);

			// 遍历每一行记录
			for (int i = 0; i < modelList.size(); i++) {
				Model model = (Model) modelList.get(i);
				int j = 0;
				// 添加标题列
				if (i == 0) {
					for (Map.Entry<String, String> entry : titles.entrySet()) {
						ws.addCell(new Label(j, i, entry.getValue(), wcf));
						j++;
					}
					wcf = new WritableCellFormat();
					j = 0;
				}
				// 添加数据列
				for (Map.Entry<String, String> entry : titles.entrySet()) {
					ws.addCell(new Label(j, i + 1, String.valueOf(model.get(
							entry.getKey(), "")), wcf));
					j++;
				}
			}
			wwb.write();
			return true;
		} catch (IOException e) {
			logger.error("IO错误", e);
			return false;
		} catch (RowsExceededException e) {
			logger.error("行溢出错误", e);
			return false;
		} catch (WriteException e) {
			logger.error("写入错误", e);
			return false;
		} catch (IllegalArgumentException e) {
			logger.error("参数错误", e);
			return false;
		} finally {
			try {
				if (wwb != null)
					wwb.close();
				if (fos != null)
					fos.close();
			} catch (WriteException e) {
				logger.error("关闭Workbook或者FileOutputStream错误", e);
			} catch (IOException e) {
				logger.error("关闭Workbook或者FileOutputStream错误", e);
			}
		}
	}

	/** */
	/**
	 * 从Excel文件里读取数据保存到List里
	 * 
	 * @param fileName
	 *            Excel文件的名称
	 * @return List对象,里面包含从Excel文件里获取到的数据
	 */
	@SuppressWarnings("rawtypes")
	public static List<Model> importExcel(String fileName) {
		List<Model> v = new ArrayList<Model>();
		Workbook book = null;
		try {
			book = Workbook.getWorkbook(new File(fileName));
			Sheet sheet = book.getSheet(0); // 获得第一个工作表对象
			int rows = sheet.getRows();

			for (int i = 0; i < rows; i++) {
				Cell[] cell = sheet.getRow(i);
				if (cell.length == 0)
					continue;

				// Active active = new Active();
				// active.set(Active.ACITVE_NAME, sheet.getCell(1, i)
				// .getContents());
				// p.setNickname(sheet.getCell(2, i).getContents());
				// p.setPower(sheet.getCell(3, i).getContents());
				// p.setWit(sheet.getCell(4, i).getContents());
				// p.setPolity(sheet.getCell(5, i).getContents());
				// p.setCharm(sheet.getCell(6, i).getContents());
				// p.setStory(sheet.getCell(7, i).getContents());
				// v.add(active);
			}

			book.close();
		} catch (IOException e) {
			logger.error("IO错误", e);
		} catch (BiffException e) {
			logger.error("Excel读取错误", e);
		} catch (IndexOutOfBoundsException e) {
			logger.error("索引越界错误", e);
		} finally {
			if (book != null)
				book.close();
		}
		return v;
	}
}
