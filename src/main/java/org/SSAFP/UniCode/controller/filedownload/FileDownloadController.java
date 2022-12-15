package org.SSAFP.UniCode.controller.filedownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<InputStreamResource> getTermsConditions(@PathVariable("name") String name, @PathVariable("folder") String folder) throws FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + name);
        File file = new File(filePath+ File.separator + folder + File.separator + name);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
