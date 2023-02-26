package org.SSAFP.UniCode.controller.filedownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/files")
@RestController
public class FileDownloadController {

	@Value("${file.path.upload-files}")
	String filePath;

	// folder :db에 저장된 save_folder 
	// name : db에 저장된 save_file
    @GetMapping("/{folder}/{name}")
    public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable("name") String name, @PathVariable("folder") String folder) throws IOException {
    	Path path = Paths.get(filePath + "/" + folder + "/" + name);
		String contentType = Files.probeContentType(path);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(name, StandardCharsets.UTF_8).build());
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		InputStreamResource resource =  new InputStreamResource(Files.newInputStream(path));
		return new ResponseEntity<InputStreamResource>(resource, headers, HttpStatus.OK);
    }
}
