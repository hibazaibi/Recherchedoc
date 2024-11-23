package com.project.documents.services;

import com.project.documents.dao.entities.Theme;
import com.project.documents.dao.repositories.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

        private final ThemeRepository themeRepository;

        public ThemeService(ThemeRepository themeRepository) {
            this.themeRepository = themeRepository;
        }

        public List<Theme> getAllThemes() {
            return themeRepository.findAll();
        }

        public Theme getThemeById(Long id) {
            return themeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Theme not found"));
        }

        public Theme saveTheme(Theme theme) {
            return themeRepository.save(theme);
        }

        public void deleteTheme(Long id) {
            themeRepository.deleteById(id);
        }
    }


