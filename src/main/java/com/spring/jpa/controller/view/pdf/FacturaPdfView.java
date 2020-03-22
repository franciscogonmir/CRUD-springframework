package com.spring.jpa.controller.view.pdf;

import java.awt.Color;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.spring.jpa.models.entity.factura.Factura;
import com.spring.jpa.models.entity.lineaFactura.LineaFactura;
//hay que indicar la vista que se va a tranformar a pdf
@Component("/factura/ver")
public class FacturaPdfView extends AbstractPdfView {
	
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private LocaleResolver localeResolver;
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Locale locale= localeResolver.resolveLocale(request);
		//getMessageResourceAccessor se hereda de la clase AbstractPdfView
		MessageSourceAccessor mensaje = getMessageSourceAccessor();//otra forma para acceder a los  textos de los archivos de traducciones
		//obtenemos la factura del modelo
		Factura factura = (Factura) model.get("factura");
		
		//creamos una tabla para la vista pdf
		PdfPTable tabla = new PdfPTable(1);//indicamos una columna para la tabla
		tabla.setSpacingAfter(20);
		PdfPCell celda = null;
		
		celda=new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.cliente",null, locale)));
		celda.setBackgroundColor(new Color(184, 218, 255));
		celda.setPadding(8f);
		tabla.addCell(celda);
		tabla.addCell(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		tabla.addCell(factura.getCliente().getEmail());
		
		PdfPTable tabla2 = new PdfPTable(1);
		tabla2.setSpacingAfter(20);
		celda = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.ver.datos.factura",null, locale)));
		celda.setBackgroundColor(new Color(195, 230, 203));
		celda.setPadding(8f);
		tabla2.addCell(celda);
		tabla2.addCell( messageSource.getMessage("text.cliente.factura.folio",null, locale) + factura.getId());
		tabla2.addCell(messageSource.getMessage("text.cliente.factura.descripcion",null, locale)+factura.getDescripcion());
		tabla2.addCell(messageSource.getMessage("text.cliente.factura.fecha",null, locale)+factura.getCreateAt());
		
		PdfPTable table3 = new PdfPTable(4);
		table3.setWidths(new float[] {3.5f, 1, 1, 1});
		table3.addCell(mensaje.getMessage("text.factura.form.item.nombre"));
		table3.addCell(messageSource.getMessage("text.factura.form.item.precio",null, locale));
		table3.addCell(messageSource.getMessage("text.factura.form.item.cantidad",null, locale));
		table3.addCell(messageSource.getMessage("text.factura.form.item.total",null, locale));
		
		for (LineaFactura linea : factura.getLineaFactura()) {
			table3.addCell(linea.getProducto().getNombre());
			table3.addCell(linea.getProducto().getPrecio().toString());
			celda = new PdfPCell(new Phrase(linea.getCantidad().toString()));
			celda.setHorizontalAlignment(new PdfPCell().ALIGN_CENTER);
			table3.addCell(celda);
			
			table3.addCell(linea.calcularTotal().toString());
		}
		
		celda = new PdfPCell(new Phrase(messageSource.getMessage("text.factura.form.total", null, locale)));
		celda.setColspan(3);
		celda.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		table3.addCell(celda);
		table3.addCell(factura.getTotal().toString());
		

		
		//anadimos las tablas al documento
		document.add(tabla);
		document.add(tabla2);
		document.add(table3);
	}

}
