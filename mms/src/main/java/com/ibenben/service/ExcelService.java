package com.ibenben.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import com.ibenben.domain.Product;
import com.ibenben.domain.ProductApply;
import com.ibenben.domain.ProductQc;
import com.ibenben.enumtuil.StatusEnum;
import com.ibenben.enumtuil.TemperaturePackageTypeEnum;
import com.ibenben.util.DateUtil;

@Service
public class ExcelService{

	public static final String TYPE_PRODUCT_RAWMATERIAL = "rawMaterial";
	public static final String TYPE_PRODUCT_SUPPLEISRAWMATERIAL = "suppliesRawMaterial";
	public static final String TYPE_PRODUCT_FINISHED ="finished";
	public static final String TYPE_PRODUCT_SUPPLIESFINISHED = "suppliesFinished";
	public static final String TYPE_PRODUCT_APPLY = "apply";
	public static final String TYPE_PRODUCT_QC = "qc";
	public static final String TYPE_PRODUCT_EFFICIENCY_COEFF_APPLY="efficiencyCoeffApply";
	public static final String TYPE_PRODUCT_EFFICIENCY_COEFF="TYPE_PRODUCT_EFFICIENCY_COEFF";
	public static final String TYPE_PRODUCT_EXCEPTION="TYPE_PRODUCT_EXCEPTION";
	public static final String UNSET_PRODUCT_EFFICIENCY_COEFF="UNSET_PRODUCT_EFFICIENCY_COEFF";
	
	@SuppressWarnings("unchecked")
	public void exportExcel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ParseException {
		String fileName = (String)model.get("fileName");
		String sheetName = (String)model.get("sheetName");
		String type = (String)model.get("type");
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(sheetName);
		List<String> headers = (List<String>)model.get("headers");
		List<?> results = (List<?>)model.get("list");
		
		Row headRow = sheet.createRow(0);
		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_JUSTIFY);
		headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = workbook.createFont();
		font.setBoldweight((short)14);
		font.setBold(true);
		headerStyle.setFont(font);
		int currentHeaderCell = 0;
		int currentRow = 0;
		for (String header : headers) {
			Cell cell = headRow.createCell(currentHeaderCell);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(header);
			cell.setCellStyle(headerStyle);
			currentHeaderCell++;
		}
		currentRow++;
		for(Object object: results){
            Row row = sheet.createRow(currentRow);
            createCellByType(type, row, object);
            currentRow++;
        }
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8")+".xls");
        OutputStream out = response.getOutputStream();
        workbook.write(out);
        out.flush();
        out.close();
        workbook.close();
	}
	
	@SuppressWarnings("unchecked")
	public void createCellByType(String Type, Row row, Object object) throws ParseException{
		switch (Type) {
		case TYPE_PRODUCT_RAWMATERIAL:
			createProductRawMaterialCell((Product)object, row);
			break;
		case TYPE_PRODUCT_SUPPLEISRAWMATERIAL:
			createProductSuppliesRawMaterialCell((Product)object,row);
			break;
		case TYPE_PRODUCT_FINISHED:
			createProductFinishedCell((Product)object, row);
			break;
		case TYPE_PRODUCT_SUPPLIESFINISHED:
			createProductSuppliesFinshedCell((Product)object, row);
			break;
		case TYPE_PRODUCT_APPLY:
			createProductApplyCell((ProductApply)object, row);
			break;
		case TYPE_PRODUCT_QC:
			createProductQcCell((ProductQc)object, row);
			break;
		case TYPE_PRODUCT_EFFICIENCY_COEFF_APPLY:
			createProductEfficiencyCoeffApplyCell((Map<String, Object>)object, row);
			break;
		case TYPE_PRODUCT_EFFICIENCY_COEFF:
			createProductEfficiencyCoeffHistoryCell((Map<String, Object>)object, row);
			break;
		case TYPE_PRODUCT_EXCEPTION:
			createProductExceptionEfficiencyCoeff((Map<String,Object>)object,row);
			break;
		case UNSET_PRODUCT_EFFICIENCY_COEFF:
			createUnsetProductEfficiencyCoeff((Map<String,Object>)object,row);
			break;
		default:
			break;
		}
	}

	private void createProductRawMaterialCell(Product product, Row row) throws ParseException{
		row.createCell(0).setCellValue(product.getPartyName());
		row.createCell(1).setCellValue(product.getProductId());
		row.createCell(2).setCellValue(product.getProductName());
		row.createCell(3).setCellValue(StatusEnum.getText(product.getStatus()));
		ProductQc qc = product.getProductQc();
		String qcCode = qc == null ? null : qc.getProductQcCode();
		String qcName = qc == null ? null : qc.getProductQcName();
		row.createCell(4).setCellValue(qcCode);
		row.createCell(5).setCellValue(qcName);
		String lastUpdatedTime = DateUtil.format(product.getLastUpdatedTime());
		row.createCell(6).setCellValue(lastUpdatedTime);
		row.createCell(7).setCellValue(product.getLastUpdatedUser());
		row.createCell(8).setCellValue(product.getNote());
	}
	
	private void createProductSuppliesRawMaterialCell(Product product, Row row) throws ParseException{
		row.createCell(0).setCellValue(product.getProductId());
		row.createCell(1).setCellValue(product.getProductName());
		row.createCell(2).setCellValue(StatusEnum.getText(product.getStatus()));
		row.createCell(3).setCellValue(product.getBarcode());
		row.createCell(4).setCellValue(product.getUnitCodeName());
		row.createCell(5).setCellValue(product.getCreatedUser());
		row.createCell(6).setCellValue(DateUtil.format(product.getCreatedTime()));
		row.createCell(7).setCellValue(product.getNote());
	}
	
	private void createProductFinishedCell(Product product, Row row) throws ParseException{
		row.createCell(0).setCellValue(product.getPartyName());
		row.createCell(2).setCellValue(product.getProductId());
		row.createCell(3).setCellValue(product.getProductName());
		row.createCell(4).setCellValue(StatusEnum.getText(product.getStatus()));
		row.createCell(5).setCellValue(product.getProductApplyCode());
		row.createCell(6).setCellValue(product.getProductApplyName());
		row.createCell(7).setCellValue(StatusEnum.getText(product.getApplyStatus()));
		row.createCell(8).setCellValue(DateUtil.format(product.getLastUpdatedTime()));
		row.createCell(9).setCellValue(product.getLastUpdatedUser());
		row.createCell(10).setCellValue(product.getNote());
	}
	
	private void createProductSuppliesFinshedCell(Product product, Row row) throws ParseException{
		row.createCell(0).setCellValue(product.getProductId());
		row.createCell(1).setCellValue(product.getProductName());
		row.createCell(2).setCellValue(product.getComponentDetail());
		row.createCell(3).setCellValue(StatusEnum.getText(product.getStatus()));
		row.createCell(4).setCellValue(TemperaturePackageTypeEnum.getText(product.getTemperaturePackageType()));
		row.createCell(5).setCellValue(product.getCreatedUser());
		row.createCell(6).setCellValue(DateUtil.format(product.getCreatedTime()));
		row.createCell(7).setCellValue(product.getNote());
	}
	
	private void createProductApplyCell(ProductApply apply, Row row) throws ParseException{
		row.createCell(0).setCellValue(apply.getPartyName());
		row.createCell(2).setCellValue(apply.getProductApplyCode());
		row.createCell(3).setCellValue(apply.getProductApplyName());
		row.createCell(4).setCellValue(StatusEnum.getText(apply.getApplyStatus()));
		row.createCell(5).setCellValue(DateUtil.format(apply.getLastUpdatedTime()));
		row.createCell(6).setCellValue(apply.getLastUpdatedUser());
		row.createCell(7).setCellValue(apply.getNote());
	}
	
	private void createProductQcCell(ProductQc qc, Row row) throws ParseException {
		row.createCell(0).setCellValue(qc.getPartyName());
		row.createCell(1).setCellValue(qc.getProductQcCode());
		row.createCell(2).setCellValue(qc.getProductQcName());
		row.createCell(3).setCellValue(StatusEnum.getText(qc.getStatus()));
		row.createCell(4).setCellValue(DateUtil.format(qc.getLastUpdatedTime()));
		row.createCell(5).setCellValue(qc.getLastUpdatedUser());
		row.createCell(6).setCellValue(qc.getNote());
	}
	private void createProductEfficiencyCoeffApplyCell(Map<String, Object> apply, Row row){
		row.createCell(0).setCellValue(apply.get("start_date")==null ? "" : apply.get("start_date").toString());
		row.createCell(1).setCellValue(apply.get("merchant_name") == null ? "" : apply.get("merchant_name").toString());
		row.createCell(2).setCellValue(apply.get("product_id") == null ? "" : apply.get("product_id").toString());
		row.createCell(3).setCellValue(apply.get("product_name") == null ? "": apply.get("product_name").toString());
		row.createCell(4).setCellValue(apply.get("facility_name") == null ? "" : apply.get("facility_name").toString());
		row.createCell(5).setCellValue(apply.get("temperature_package_type") == null ? "" : TemperaturePackageTypeEnum.getText(apply.get("temperature_package_type").toString()));
		row.createCell(6).setCellValue(apply.get("supplies_product_id") == null ? "" : apply.get("supplies_product_id").toString());
		row.createCell(7).setCellValue(apply.get("supplies_product_name") == null ? "" : apply.get("supplies_product_name").toString());
		row.createCell(8).setCellValue(apply.get("supplies_component_detail") == null ? "" : apply.get("supplies_component_detail").toString());
		row.createCell(9).setCellValue(apply.get("note") == null ? "" : apply.get("note").toString());
		//总系数
		row.createCell(10).setCellValue(apply.get("inventory_management_coff") == null ? 0 : Double.valueOf(apply.get("inventory_management_coff").toString())
				+ (apply.get("document_management_coff") == null ? 0 : Double.valueOf(apply.get("document_management_coff").toString()))
				+ (apply.get("circulation_processing_coff") == null ? 0 : Double.valueOf(apply.get("circulation_processing_coff").toString()))
				+ (apply.get("production_packaging_coff") == null ? 0 : Double.valueOf(apply.get("production_packaging_coff").toString()))
				+ (apply.get("distribution_shipping_coff") == null ? 0 : Double.valueOf(apply.get("distribution_shipping_coff").toString()))
				+ (apply.get("others_coff") == null ? 0 : Double.valueOf(apply.get("others_coff").toString()))
				);
		row.createCell(11).setCellValue(apply.get("inventory_management_coff") == null ? "" : apply.get("inventory_management_coff").toString());
		row.createCell(12).setCellValue(apply.get("document_management_coff") == null ? "" : apply.get("document_management_coff").toString());
		row.createCell(13).setCellValue(apply.get("circulation_processing_coff") == null ? "" : apply.get("circulation_processing_coff").toString());
		row.createCell(14).setCellValue(apply.get("production_packaging_coff") == null ? "" : apply.get("production_packaging_coff").toString());
		row.createCell(15).setCellValue(apply.get("distribution_shipping_coff") == null ? "" : apply.get("distribution_shipping_coff").toString());
		row.createCell(16).setCellValue(apply.get("others_coff") == null ? "" : apply.get("others_coff").toString());
		row.createCell(17).setCellValue(apply.get("status") == null ? "" : StatusEnum.getText(apply.get("status").toString()));
		row.createCell(18).setCellValue(apply.get("created_user") == null ? "" : apply.get("created_user").toString());
		row.createCell(19).setCellValue(apply.get("created_time") == null ? "" : apply.get("created_time").toString());
		row.createCell(20).setCellValue(apply.get("facility_note") == null ? "" : apply.get("facility_note").toString());
	}
	private void createProductEfficiencyCoeffHistoryCell(Map<String, Object> coff, Row row){
		row.createCell(0).setCellValue(coff.get("setting_time") == null? "" : coff.get("setting_time").toString());
		row.createCell(1).setCellValue(coff.get("start_date")==null ? "" : coff.get("start_date").toString());
		row.createCell(2).setCellValue(coff.get("end_date")==null ? "" : coff.get("end_date").toString());
		row.createCell(3).setCellValue(coff.get("merchant_name") == null ? "" : coff.get("merchant_name").toString());
		row.createCell(4).setCellValue(coff.get("product_id") == null ? "" : coff.get("product_id").toString());
		row.createCell(5).setCellValue(coff.get("product_name") == null ? "": coff.get("product_name").toString());
		row.createCell(6).setCellValue(coff.get("facility_name") == null ? "" : coff.get("facility_name").toString());
		row.createCell(7).setCellValue(coff.get("temperature_package_type") == null ? "" : TemperaturePackageTypeEnum.getText(coff.get("temperature_package_type").toString()));
		row.createCell(8).setCellValue(coff.get("supplies_product_id") == null ? "" : coff.get("supplies_product_id").toString());
		row.createCell(9).setCellValue(coff.get("supplies_product_name") == null ? "" : coff.get("supplies_product_name").toString());
		row.createCell(10).setCellValue(coff.get("supplies_component_detail") == null ? "" : coff.get("supplies_component_detail").toString());
		row.createCell(11).setCellValue(coff.get("note") == null ? "" : coff.get("note").toString());
		//总系数
		row.createCell(12).setCellValue(coff.get("inventory_management_coff") == null ? 0 : Double.valueOf(coff.get("inventory_management_coff").toString())
				+ (coff.get("document_management_coff") == null ? 0 : Double.valueOf(coff.get("document_management_coff").toString()))
				+ (coff.get("circulation_processing_coff") == null ? 0 : Double.valueOf(coff.get("circulation_processing_coff").toString()))
				+ (coff.get("production_packaging_coff") == null ? 0 : Double.valueOf(coff.get("production_packaging_coff").toString()))
				+ (coff.get("distribution_shipping_coff") == null ? 0 : Double.valueOf(coff.get("distribution_shipping_coff").toString()))
				+ (coff.get("others_coff") == null ? 0 : Double.valueOf(coff.get("others_coff").toString()))
				);
		row.createCell(13).setCellValue(coff.get("inventory_management_coff") == null ? "" : coff.get("inventory_management_coff").toString());
		row.createCell(14).setCellValue(coff.get("document_management_coff") == null ? "" : coff.get("document_management_coff").toString());
		row.createCell(15).setCellValue(coff.get("circulation_processing_coff") == null ? "" : coff.get("circulation_processing_coff").toString());
		row.createCell(16).setCellValue(coff.get("production_packaging_coff") == null ? "" : coff.get("production_packaging_coff").toString());
		row.createCell(17).setCellValue(coff.get("distribution_shipping_coff") == null ? "" : coff.get("distribution_shipping_coff").toString());
		row.createCell(18).setCellValue(coff.get("others_coff") == null ? "" : coff.get("others_coff").toString());
		row.createCell(19).setCellValue(coff.get("facility_note") == null ? "" : coff.get("facility_note").toString());
	}
	
	private void createProductExceptionEfficiencyCoeff(Map<String, Object> coff, Row row) {
		row.createCell(0).setCellValue(coff.get("setting_time") == null? "" : coff.get("setting_time").toString());
		row.createCell(1).setCellValue(coff.get("start_date")==null ? "" : coff.get("start_date").toString());
		row.createCell(2).setCellValue(coff.get("end_date")==null ? "" : coff.get("end_date").toString());
		row.createCell(3).setCellValue(coff.get("merchant_name") == null ? "" : coff.get("merchant_name").toString());
		row.createCell(4).setCellValue(coff.get("product_id") == null ? "" : coff.get("product_id").toString());
		row.createCell(5).setCellValue(coff.get("product_name") == null ? "": coff.get("product_name").toString());
		row.createCell(6).setCellValue(coff.get("facility_name") == null ? "" : coff.get("facility_name").toString());
		row.createCell(7).setCellValue(coff.get("temperature_package_type") == null ? "" : TemperaturePackageTypeEnum.getText(coff.get("temperature_package_type").toString()));
		row.createCell(8).setCellValue(coff.get("supplies_product_id") == null ? "" : coff.get("supplies_product_id").toString());
		row.createCell(9).setCellValue(coff.get("supplies_product_name") == null ? "" : coff.get("supplies_product_name").toString());
		row.createCell(10).setCellValue(coff.get("supplies_component_detail") == null ? "" : coff.get("supplies_component_detail").toString());
		row.createCell(11).setCellValue(coff.get("note") == null ? "" : coff.get("note").toString());
		//总系数
		row.createCell(12).setCellValue(coff.get("inventory_management_coff") == null ? 0 : Double.valueOf(coff.get("inventory_management_coff").toString())
				+ (coff.get("document_management_coff") == null ? 0 : Double.valueOf(coff.get("document_management_coff").toString()))
				+ (coff.get("circulation_processing_coff") == null ? 0 : Double.valueOf(coff.get("circulation_processing_coff").toString()))
				+ (coff.get("production_packaging_coff") == null ? 0 : Double.valueOf(coff.get("production_packaging_coff").toString()))
				+ (coff.get("distribution_shipping_coff") == null ? 0 : Double.valueOf(coff.get("distribution_shipping_coff").toString()))
				+ (coff.get("others_coff") == null ? 0 : Double.valueOf(coff.get("others_coff").toString()))
				);
		row.createCell(13).setCellValue(coff.get("inventory_management_coff") == null ? "" : coff.get("inventory_management_coff").toString());
		row.createCell(14).setCellValue(coff.get("document_management_coff") == null ? "" : coff.get("document_management_coff").toString());
		row.createCell(15).setCellValue(coff.get("circulation_processing_coff") == null ? "" : coff.get("circulation_processing_coff").toString());
		row.createCell(16).setCellValue(coff.get("production_packaging_coff") == null ? "" : coff.get("production_packaging_coff").toString());
		row.createCell(17).setCellValue(coff.get("distribution_shipping_coff") == null ? "" : coff.get("distribution_shipping_coff").toString());
		row.createCell(18).setCellValue(coff.get("others_coff") == null ? "" : coff.get("others_coff").toString());
		row.createCell(19).setCellValue(coff.get("facility_note") == null ? "" : coff.get("facility_note").toString());
	}
	
	private void createUnsetProductEfficiencyCoeff(Map<String,Object> coff,Row row){
		row.createCell(0).setCellValue(coff.get("merchant_name") == null ? "" : coff.get("merchant_name").toString());
		row.createCell(1).setCellValue(coff.get("product_id") == null ? "" : coff.get("product_id").toString());
		row.createCell(2).setCellValue(coff.get("product_name") == null ? "": coff.get("product_name").toString());
		row.createCell(3).setCellValue(coff.get("facility_name") == null ? "" : coff.get("facility_name").toString());
		row.createCell(4).setCellValue(coff.get("temperature_package_type") == null ? "" : TemperaturePackageTypeEnum.getText(coff.get("temperature_package_type").toString()));
		row.createCell(5).setCellValue(coff.get("supplies_product_id") == null ? "" : coff.get("supplies_product_id").toString());
		row.createCell(6).setCellValue(coff.get("supplies_product_name") == null ? "" : coff.get("supplies_product_name").toString());
		row.createCell(7).setCellValue(coff.get("supplies_component_detail") == null ? "" : coff.get("supplies_component_detail").toString());
		row.createCell(8).setCellValue(coff.get("note") == null ? "" : coff.get("note").toString());
		//总系数
		row.createCell(9).setCellValue(coff.get("inventory_management_coff") == null ? 0 : Double.valueOf(coff.get("inventory_management_coff").toString())
				+ (coff.get("document_management_coff") == null ? 0 : Double.valueOf(coff.get("document_management_coff").toString()))
				+ (coff.get("circulation_processing_coff") == null ? 0 : Double.valueOf(coff.get("circulation_processing_coff").toString()))
				+ (coff.get("production_packaging_coff") == null ? 0 : Double.valueOf(coff.get("production_packaging_coff").toString()))
				+ (coff.get("distribution_shipping_coff") == null ? 0 : Double.valueOf(coff.get("distribution_shipping_coff").toString()))
				+ (coff.get("others_coff") == null ? 0 : Double.valueOf(coff.get("others_coff").toString()))
				);
		row.createCell(10).setCellValue(coff.get("inventory_management_coff") == null ? "" : coff.get("inventory_management_coff").toString());
		row.createCell(11).setCellValue(coff.get("document_management_coff") == null ? "" : coff.get("document_management_coff").toString());
		row.createCell(12).setCellValue(coff.get("circulation_processing_coff") == null ? "" : coff.get("circulation_processing_coff").toString());
		row.createCell(13).setCellValue(coff.get("production_packaging_coff") == null ? "" : coff.get("production_packaging_coff").toString());
		row.createCell(14).setCellValue(coff.get("distribution_shipping_coff") == null ? "" : coff.get("distribution_shipping_coff").toString());
		row.createCell(15).setCellValue(coff.get("others_coff") == null ? "" : coff.get("others_coff").toString());
	}
}
