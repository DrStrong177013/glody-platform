package com.glody.glody_platform.partner.controller;

import com.glody.glody_platform.partner.entity.PartnerCategory;
import com.glody.glody_platform.partner.service.PartnerCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class PartnerCategoryController {

    private final PartnerCategoryService categoryService;

    @GetMapping
    public List<PartnerCategory> getAllCategories() {
        return categoryService.getAllCategories();
    }
}
