package com.project.documents.business.servicesImpl;

import com.project.documents.business.services.FileService;
import com.project.documents.dao.entities.File;
import com.project.documents.dao.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileServiceImpl implements FileService {
    private FileRepository attachmentRepository;

    public FileServiceImpl(FileRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public File saveAttachment(MultipartFile file) throws Exception {
        if (file == null) {
            return null;
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if(fileName.contains("..")) {
                throw  new Exception("Filename contains invalid path sequence "
                        + fileName);
            }

            File attachment
                    = new File(fileName,
                    file.getContentType(),
                    file.getBytes());
            return attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new Exception("Could not save File: " + fileName);
        }
    }

    @Override
    public File getAttachment(String fileId) throws Exception {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(
                        () -> new Exception("File not found with Id: " + fileId));
    }
    public void deleteAttachment(String fileId) throws Exception {
        File attachment = attachmentRepository.findById(fileId)
                .orElseThrow(() -> new Exception("File not found with Id: " + fileId));
        attachmentRepository.delete(attachment);
    }

}