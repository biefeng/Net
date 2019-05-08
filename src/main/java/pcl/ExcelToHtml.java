/*
package pcl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelToHtml{

	*/
/**
	 * 文件对象
	 *//*

	private File file = null;

	*/
/**
	 * 构造函数
	 * 
	 * @param file
	 *            要读取的excel文件对象
	 *//*

	public ExcelToHtml(File file) {

		this.file = file;
	}
	*/
/**
	 * 
	 * 方法说明：找出该sheet页内所有的合并单元格
	 * 
	 * @param sheet
	 * @param list
	 * 
	 *//*

	public void getCombineCell(HSSFSheet sheet, List<Region> list) {
		// 获得一个 sheet 中合并单元格的数量
		int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格加入list中
			Region re = sheet.getMergedRegionAt(i);
			list.add(re);
		}
	}

	*/
/**
	 * 
	 * 方法说明：构造
	 * <td>标签
	 * @param listCombineCell
	 * @param rowIndex
	 * @param cellIndex
	 * @param sb
	 * @param value
	 * 
	 *//*

	public void createTD(List<Region> listCombineCell, int rowIndex,
			int cellIndex, StringBuffer sb, String value) {

		if ("".equals(value)) {
			value = "&nbsp;";
		}
		// 标识是否该单元格是否在合并单元内
		boolean flag = false;
		for (Region r : listCombineCell) {
			// 记录该合并单元格的上下左右起点坐标
			int rowFrom = r.getRowFrom();
			int rowTo = r.getRowTo();
			int cellFrom = r.getColumnFrom();
			int cellTo = r.getColumnTo();
			// 只对左上角的第一个做colspan和rowspan属性
			if (rowIndex == rowFrom && cellIndex == cellFrom) {
				sb.append("<td");
				if (cellTo > cellFrom) {
					sb.append(" colspan=\"").append(cellTo - cellFrom + 1)
							.append("\"");
				}
				if (rowTo > rowFrom) {
					sb.append(" rowspan=\"").append(rowTo - rowFrom + 1)
							.append("\"");
				}
				sb.append(">").append(value).append("</td>");
				flag = true;
				break;
			}

			if (rowIndex >= rowFrom && rowIndex <= rowTo
					&& cellIndex >= cellFrom && cellIndex <= cellTo) {
				flag = true;
				break;
			}
		}
		// 不在合并单元格内的格子
		if (!flag) {
			sb.append("<td>").append(value).append("</td>");
		}

	}

	*/
/**
	 * 
	 * 方法说明：解析excel，生成html的table
	 * 
	 * @return
	 * @throws Exception
	 * 
	 *//*

	public String parseExcelToHtml() throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("<table width=\"100%\" border=\"1\">");
		// 存放该sheet页里面所有合并单元格
		List<Region> mergeRegionList = new ArrayList<Region>();

		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				file));
		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		HSSFSheet st = wb.getSheetAt(0);
		// 获取合并单元格列表
		getCombineCell(st, mergeRegionList);
		// 循环行
		for (int rowIndex = 0; rowIndex <= st.getLastRowNum(); rowIndex++) {
			HSSFRow row = st.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			sb.append("<tr>");
			// 循环列
			for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {

				String value = "";
				cell = row.getCell(columnIndex);
				if (cell != null) {
					// 注意：一定要设成这个，否则可能会出现乱码
					//((HSSFCell)cell).setEncoding(HSSFCell.ENCODING_UTF_16);
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_STRING:
						value = cell.getStringCellValue();
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							if (date != null) {
								value = new SimpleDateFormat("yyyy-MM-dd")
										.format(date);
							} else {
								value = "";
							}
						} else {
							value = new DecimalFormat("0.0").format(cell
									.getNumericCellValue());
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA:
						// 导入时如果为公式生成的数据则无值
						if (!cell.getStringCellValue().equals("")) {
							value = cell.getStringCellValue();
						} else {
							value = cell.getNumericCellValue() + "";
						}
						break;
					case HSSFCell.CELL_TYPE_BLANK:
						break;
					case HSSFCell.CELL_TYPE_ERROR:
						value = "";
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						value = (cell.getBooleanCellValue() ? "Y" : "N");
						break;
					default:
						value = "";
					}
					// 构造td标签，即每个单元格
					createTD(mergeRegionList, rowIndex, columnIndex, sb, value);
				}
			}

			sb.append("</tr> ");
		}

		in.close();
		sb.append("</table>");
		return sb.toString();
	}
	*/
/**
	 * 
	 * 方法说明：测试调用
	 *
	 * @param args
	 * @throws Exception 
	 *
	 *//*

	public static void main(String[] args) throws Exception {
		
		File file = new File("D:/TEST_1.xls");
		
		String result = new ExcelToHtml(file).parseExcelToHtml();

		System.out.println(result);
	}
}*/
