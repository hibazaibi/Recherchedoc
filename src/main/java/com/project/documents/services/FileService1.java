package com.project.documents.services;

import com.project.documents.dao.entities.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService1 {
    File saveAttachment(MultipartFile file) throws Exception;

    File getAttachment(String fileId) throws Exception;

}
