package com.project.documents.business.services;

import com.project.documents.dao.entities.Theme;


import java.util.List;

public interface ThemeService {
    List<Theme> getAllThemes();
    Theme getThemeById(Long id);
    Theme saveTheme(Theme theme);
    void deleteTheme(Long id);
}


