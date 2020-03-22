package com.spring.jpa.controller.view.Csv;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.spring.jpa.models.entity.Cliente;

@Component("lista.csv")
public class ClientesCsvView extends AbstractView {

	
	
	
	public ClientesCsvView() {
		setContentType("text/csv");
	}

	//sobreescribimos para indicar que es un contenido descargable
	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//modificamos cabecera de respuesta http
		response.setHeader("Content-Disposition", "atachment;filename=\"Cliente.csv\"");
		response.setContentType(this.getContentType());
		
		Page<Cliente> clientes = (Page<Cliente>) model.get("Clientes");
		
		String[] header = {"id","nombre","email","creationAt"};
		
		ICsvBeanWriter beanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		
		beanWriter.writeHeader(header);
		
		for (Cliente cliente : clientes) {
			beanWriter.write(cliente, header);
		}
		
		beanWriter.close();
	}

}
