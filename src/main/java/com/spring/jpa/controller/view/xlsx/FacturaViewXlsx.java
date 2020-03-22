package com.spring.jpa.controller.view.xlsx;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.spring.jpa.models.entity.factura.Factura;
import com.spring.jpa.models.entity.lineaFactura.LineaFactura;

@Component("/factura/ver.xlsx")
public class FacturaViewXlsx extends AbstractXlsxView {

	private CellStyle theaderStyle;
	private CellStyle thbodyStyle;
	
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "atachment;filename=\"factura_view.xlsx\"");//definimos el nombre del fichero
		MessageSourceAccessor mensaje = getMessageSourceAccessor();
		Factura factura = (Factura) model.get("factura");
		//Sheet sheet = crearHoja(workbook,factura,mensaje);
		Sheet sheet = workbook.createSheet("Factura Spring");//creamos nuestra hoja de excel y le damos un nombre
		rellenarHoja(sheet,factura,mensaje);
		crearEstilos(workbook);
		AplicarEstilos(sheet);
		
		
		
	}
	
	private void AplicarEstilos(Sheet sheet) {
		sheet.getRow(9).getCell(0).setCellStyle(theaderStyle);
		sheet.getRow(9).getCell(1).setCellStyle(theaderStyle);	
		sheet.getRow(9).getCell(2).setCellStyle(theaderStyle);	
		sheet.getRow(9).getCell(3).setCellStyle(theaderStyle);
		aplicarStilosTabla(sheet);

	}

	private void crearEstilos(Workbook workbook) {

		 
		theaderStyle = workbook.createCellStyle();
		theaderStyle.setBorderBottom(BorderStyle.MEDIUM);
		theaderStyle.setBorderTop(BorderStyle.MEDIUM);
		theaderStyle.setBorderLeft(BorderStyle.MEDIUM);
		theaderStyle.setBorderRight(BorderStyle.MEDIUM);
		theaderStyle.setFillForegroundColor(IndexedColors.GOLD.index);
		theaderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		thbodyStyle = workbook.createCellStyle();
		thbodyStyle.setBorderBottom(BorderStyle.THIN);
		thbodyStyle.setBorderTop(BorderStyle.THIN);
		thbodyStyle.setBorderLeft(BorderStyle.THIN);
		thbodyStyle.setBorderRight(BorderStyle.THIN);
	}

	private void rellenarHoja(Sheet sheet, Factura factura, MessageSourceAccessor mensaje) {
	
		//A continuación rellenamos nuestra hoja de excel
				sheet.createRow(0).createCell(0).setCellValue(mensaje.getMessage("text.factura.ver.datos.cliente"));
				sheet.createRow(1).createCell(0).setCellValue(factura.getCliente().getNombre()+" "+factura.getCliente().getApellido());
				sheet.createRow(2).createCell(0).setCellValue(factura.getCliente().getEmail());
				
				sheet.createRow(4).createCell(0).setCellValue(mensaje.getMessage("text.factura.ver.datos.factura"));
				sheet.createRow(5).createCell(0).setCellValue(mensaje.getMessage("text.cliente.factura.folio")+": "+factura.getId());
				sheet.createRow(6).createCell(0).setCellValue(mensaje.getMessage("text.cliente.factura.descripcion")+": "+factura.getDescripcion());
				sheet.createRow(7).createCell(0).setCellValue(mensaje.getMessage("text.cliente.factura.fecha")+": "+factura.getCreateAt());
				
				rellenarLineas(factura,sheet,mensaje);

	}

	private void rellenarLineas(Factura factura, Sheet sheet, MessageSourceAccessor mensaje) {
		
		Row fila = sheet.createRow(9);
		fila.createCell(0).setCellValue(mensaje.getMessage("text.factura.form.item.nombre"));
		fila.createCell(1).setCellValue(mensaje.getMessage("text.factura.form.item.precio"));
		fila.createCell(2).setCellValue(mensaje.getMessage("text.factura.form.item.cantidad"));
		fila.createCell(3).setCellValue(mensaje.getMessage("text.factura.form.item.total"));
		int contador = 10;
		for(LineaFactura linea : factura.getLineaFactura()) {
			fila = sheet.createRow(contador);
			fila.createCell(0).setCellValue(linea.getProducto().getNombre());
			fila.createCell(1).setCellValue(linea.getProducto().getPrecio().toString());
			fila.createCell(2).setCellValue(linea.getCantidad());
			fila.createCell(3).setCellValue(linea.calcularTotal().toString());
			contador++;
		}
		fila = sheet.createRow(contador);
		fila.createCell(2).setCellValue(mensaje.getMessage("text.factura.form.total"));
		fila.createCell(3).setCellValue(factura.getTotal());
	}

	private void aplicarStilosTabla(Sheet sheet) {
		for(int i = 10;i < sheet.getLastRowNum();i++) {
			sheet.getRow(i).getCell(0).setCellStyle(thbodyStyle);
			sheet.getRow(i).getCell(1).setCellStyle(thbodyStyle);
			sheet.getRow(i).getCell(2).setCellStyle(thbodyStyle);
			sheet.getRow(i).getCell(3).setCellStyle(thbodyStyle);
		}		
	}


}
