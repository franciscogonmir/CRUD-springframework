package com.spring.jpa.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.naming.spi.DirStateFactory.Result;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.jpa.dao.IClienteDao;
import com.spring.jpa.models.entity.Cliente;
import com.spring.jpa.service.ClienteService;
import com.spring.jpa.service.UploadServices;
import com.spring.jpa.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	@Autowired
	private ClienteService clienteSvc;
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadServices uploadSvc;
	
	@GetMapping(value="/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable(value="filename") String filename){
		/*Path pathFoto = Paths.get("uploads").resolve(filename).toAbsolutePath();
		log.info("pathFoto: "+pathFoto);
		Resource recurso = null;
		try {
			recurso = new UrlResource(pathFoto.toUri());
			if(!recurso.exists() && !recurso.isReadable()) {
				throw new RuntimeException();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Resource recurso = null;
		try {
			recurso = uploadSvc.load(filename);
			if(!recurso.exists() && !recurso.isReadable()) {
				throw new RuntimeException();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=/"+recurso.getFilename()+"/").body(recurso);
	}
	@GetMapping(value="ver/{id}")
	public String ver(@PathVariable(value="id") Long id,Map<String,Object> model,RedirectAttributes flash) {
		Cliente cliente = clienteSvc.fetchById(id);
		if(cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle del cliente "+cliente.getNombre());
		return "ver";
	}
	
	
	@RequestMapping(value="listar",method=RequestMethod.GET)
	public String listar(@RequestParam(name="page",defaultValue = "0") int page,Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente>clientes = clienteSvc.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		model.addAttribute("page",pageRender );
		model.addAttribute("titulo","Bienvenido a mi vista");
		model.addAttribute("Clientes",clientes);
		return "lista";
	}
	
	@GetMapping(value="form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("titulo", "Formulario de spring");
		model.put("cliente", cliente);
		return "form";
	}
	
	@RequestMapping(value="form/{id}")
	public String editar(@PathVariable(value="id") Long id,RedirectAttributes flash,Model model) {
		Cliente cliente = null;
		if(id != null && id > 0) {
			cliente = clienteSvc.findOne(id);
			if(cliente.getFoto() != null) {
				if(cliente.getFoto() != null) {
					/*Path pathFoto = Paths.get("uploads").resolve(clienteSvc.findOne(id).getFoto()).toAbsolutePath();
					File archivo = pathFoto.toFile();
					if(archivo.exists() && archivo.canRead()){
						if(archivo.delete()) {*/
							if(uploadSvc.delete(cliente.getFoto())){
							flash.addFlashAttribute("info", "la imagen "+cliente.getFoto()+" ha sido eliminado correctamente");
							}
							//	}
					//}
				}
			}
		}else {
			flash.addFlashAttribute("error", "Error al editar al cliente");
			return  "redirect:listar";
		}
		model.addAttribute("cliente",cliente);
		return "form";
	}
	
	@RequestMapping(value="form", method=RequestMethod.POST)
	public String guardar(@RequestParam("file") MultipartFile foto,@Valid Cliente cliente, BindingResult result,RedirectAttributes flash,SessionStatus status) {
		if(result.hasErrors()) {
			return "form";
		}
		if(!foto.isEmpty()) {
			/*Path rutaDirectorio = Paths.get("src//main//resources//static//upload");
			String rootDirectory = rutaDirectorio.toFile().getAbsolutePath();*/
			//String uniqueFileName = UUID.randomUUID().toString()+"_"+foto.getOriginalFilename();
			//Path rootPath = Paths.get("uploads").resolve(uniqueFileName);
			//Path rootAbsolutePath = rootPath.toAbsolutePath();
			//log.info("rootPath: "+rootPath);
			//log.info("absolutePath: "+rootAbsolutePath);
			//try {
				/*byte[] imagenBytes = foto.getBytes();
				Path rutaCompleta = Paths.get(rootDirectory + "//"+foto.getOriginalFilename());
				Files.write(rutaCompleta, imagenBytes);*/
				//Files.copy(foto.getInputStream(), rootAbsolutePath);
				String uniqueFileName = uploadSvc.copy(foto);
				flash.addFlashAttribute("info", "Imagen "+uniqueFileName+" creada correctamente");
				cliente.setFoto(uniqueFileName);
			/*} catch (IOException e) {
				e.printStackTrace();
			}*/
		}

		String mensaje = (cliente.getId() != null)?"Cliente editado con éxito":"Cliente creado con éxito";
		flash.addFlashAttribute("success", mensaje);
		clienteSvc.save(cliente);
		status.setComplete();
		return "redirect:listar";
	}

	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") Long id,RedirectAttributes flash) {
		flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		
		//Path pathFoto = Paths.get("uploads").resolve(clienteSvc.findOne(id).getFoto()).toAbsolutePath();
		//File archivo = pathFoto.toFile();
			if(uploadSvc.delete(clienteSvc.findOne(id).getFoto())) {
				flash.addFlashAttribute("info", "la imagen "+clienteSvc.findOne(id).getFoto()+" ha sido eliminado correctamente");
			}
		//}
		
		clienteSvc.remove(id);
		return "redirect:/listar";
	}
}
