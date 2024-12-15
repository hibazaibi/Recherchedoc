package com.project.documents.business.services;

import com.project.documents.dao.entities.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File saveAttachment(MultipartFile file) throws Exception;

    File getAttachment(String fileId) throws Exception;

}
