package com.project.documents.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
