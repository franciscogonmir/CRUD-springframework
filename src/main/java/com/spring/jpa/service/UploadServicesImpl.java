package com.spring.jpa.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServicesImpl implements UploadServices{

	private static final String DIR_NAME = "uploads";
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("pathFoto: "+pathFoto);
		Resource recurso = new UrlResource(pathFoto.toUri());
	return recurso;
	}

	@Override
	public String copy(MultipartFile file) {
		String uniqueFileName = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
		Path rootPath = getPath(uniqueFileName);
		log.info("rootPath: "+rootPath);
		try {
			Files.copy(file.getInputStream(), rootPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return uniqueFileName;
	}

	@Override
	public boolean delete(String filename) {
		Path pathFoto = getPath(filename);
		File archivo = pathFoto.toFile();
		if(archivo.exists() && archivo.canRead()){
			if(archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(getPath(DIR_NAME).toFile());
	}

	@Override
	public void init() throws IOException {
			Files.createDirectories(getPath(DIR_NAME));
	}
	public Path getPath(String filename) {
		return Paths.get(DIR_NAME).resolve(filename).toAbsolutePath();
	}

}
