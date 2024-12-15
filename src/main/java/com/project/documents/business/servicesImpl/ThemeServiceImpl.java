package com.project.documents.business.servicesImpl;

import com.project.documents.business.services.ThemeService;
import com.project.documents.dao.entities.Theme;
import com.project.documents.dao.repositories.ThemeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {

        private final ThemeRepository themeRepository;

        public ThemeServiceImpl(ThemeRepository themeRepository) {
            this.themeRepository = themeRepository;
        }
    @Override
        public List<Theme> getAllThemes() {
            return themeRepository.findAll();
        }
    @Override
        public Theme getThemeById(Long id) {
            return themeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Theme not found"));
        }
    @Override
        public Theme saveTheme(Theme theme) {
            return themeRepository.save(theme);
        }
    @Override
        public void deleteTheme(Long id) {
            themeRepository.deleteById(id);
        }
    }


