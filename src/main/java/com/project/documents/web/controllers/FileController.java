package com.project.documents.web.controllers;

import com.project.documents.dao.entities.File;
import com.project.documents.business.servicesImpl.FileServiceImpl;
import com.project.documents.business.services.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RequestMapping("/file")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileServiceImpl attachmentService;

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        File attachment = null;
        String downloadURl = "";
        attachment = attachmentService.saveAttachment(file);
        downloadURl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/file/download/")
                .path(attachment.getId())
                .toUriString();

        return new ResponseData(attachment.getFileName(),
                downloadURl,
                file.getContentType(),
                file.getSize());
    }

    @GetMapping("/download/{fileId}")

    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) throws Exception {
        File attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return  ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName()
                                + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
    @DeleteMapping("/delete/{fileId}")
    public ResponseData deleteFile(@PathVariable String fileId) throws Exception {
        attachmentService.deleteAttachment(fileId);
        return new ResponseData("File deleted successfully", "", "", 0);
    }
    @GetMapping("/attachments/{fileId}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileId) throws Exception {
        File attachment = attachmentService.getAttachment(fileId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(attachment.getFileType()));
        headers.setContentLength(attachment.getData().length);
        return new ResponseEntity<>(attachment.getData(), headers, HttpStatus.OK);
    }



}

