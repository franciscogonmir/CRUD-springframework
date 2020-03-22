package com.spring.jpa.controller.factura;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.models.entity.Producto.Producto;
import com.spring.jpa.models.entity.factura.Factura;
import com.spring.jpa.models.entity.lineaFactura.LineaFactura;
import com.spring.jpa.service.ClienteService;

@Secured("ROLE_ADMIN")//a todos los metodos de la clase se le peromitira el acceso a los usuarios con role admin
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
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
	
	@GetMapping(value="/cargar-productos/{term}",produces="application/json")
	public @ResponseBody List<Producto> cargarProductos(@PathVariable(value ="term")String term){
		return clienteSvc.findByName(term);
	}
	
	@PostMapping("/form")
	public String guardarFactura(Factura factura,@RequestParam(name="item_id[]",required=false) Long[] itemId,@RequestParam(name="cantidad[]",required=false) int[] cantidad,RedirectAttributes flash,SessionStatus status) {

		for (int i = 0; i < itemId.length; i++) {
			Producto p = clienteSvc.findById(itemId[i]);
			LineaFactura linea = new LineaFactura();
			linea.setProducto(p);
			linea.setCantidad(cantidad[i]);
			factura.addLineaFactura(linea);;
		}
		clienteSvc.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con exito");
		return "redirect:/ver/"+factura.getCliente().getId();
		
	}
	
	@GetMapping("/verFactura/{id}")
	public String verFactura(@PathVariable(value="id")Long id,RedirectAttributes flash,Model model) {
		Factura fact = this.clienteSvc.fetchByIdWithClienteWhithLineaFacturaWithProducto(id);
		if(fact == null) {
			flash.addFlashAttribute("error","Factura no encontrada");
			return "redirect:/listar";
		}
		model.addAttribute("factura", fact);
		model.addAttribute("titulo", "factura -.".concat(fact.getDescripcion()));
		return "/factura/ver";
	}
	
	@GetMapping("/removeFactura/{id}")
	public String removeFactura(@PathVariable(value="id") Long id,RedirectAttributes flash) {
		Factura factura = this.clienteSvc.findFacturaById(id);
		this.clienteSvc.removeFactura(id);
		flash.addFlashAttribute("success", "factura eliminada con éxito");
		return "redirect:/ver/"+factura.getCliente().getId();
	}
}
