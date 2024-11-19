package com.project.documents.dao.repositories;

import com.project.documents.dao.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
