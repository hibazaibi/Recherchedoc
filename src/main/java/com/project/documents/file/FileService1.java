package com.project.documents.file;

import org.springframework.web.multipart.MultipartFile;

public interface FileService1 {
    File saveAttachment(MultipartFile file) throws Exception;

    File getAttachment(String fileId) throws Exception;

}
