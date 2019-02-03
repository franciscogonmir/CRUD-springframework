package com.spring.jpa.controller.factura;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.models.entity.factura.Factura;
import com.spring.jpa.service.ClienteService;

@Controller
@RequestMapping("/factura")
public class FacturaController {

	@Autowired
	private ClienteService clienteSvc;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Cliente cliente = this.clienteSvc.findOne(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
		}else {
			Factura factura = new Factura();
			factura.setCliente(cliente);
			model.put("factura", factura);
			model.put("titulo", "Crear Factura");
		}
		return "factura/form";
	}
}
