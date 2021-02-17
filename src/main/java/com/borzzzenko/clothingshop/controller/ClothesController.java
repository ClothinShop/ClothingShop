package com.borzzzenko.clothingshop.controller;

import com.borzzzenko.clothingshop.model.Clothes;
import com.borzzzenko.clothingshop.model.ClothesCategory;
import com.borzzzenko.clothingshop.model.Color;
import com.borzzzenko.clothingshop.service.ClothesCategoryService;
import com.borzzzenko.clothingshop.service.ClothesService;
import com.borzzzenko.clothingshop.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClothesController {
    @Autowired
    private ClothesService clothesService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private ClothesCategoryService categoryService;

    @GetMapping("/")
    public String clothesList(Model model) {
        List<Clothes> clothesList = clothesService.findAll();

        model.addAttribute("clothes", clothesList);

        return "index";
    }

    @GetMapping("/product/{id}")
    public String clothesInfo(Model model, @PathVariable("id") Long id) {
        Clothes clothes = clothesService.findById(id);

        model.addAttribute("clothes", clothes);

        return "product";
    }

    @GetMapping("/admin")
    public String clothesListAdmin(Model model) {
        List<Clothes> clothesList = clothesService.findAll();

        model.addAttribute("clothes", clothesList);

        return "admin";
    }

    @GetMapping("/product/create")
    public String createClothesForm(Clothes clothes, Model model) {
        model.addAttribute("metaTitle", "Добавление одежды");

        List<Color> colors = colorService.findAll();
        model.addAttribute("colors", colors);

        List<ClothesCategory> categories = categoryService.findAll();
        model.addAttribute("categories", categories);

        return "create_product";
    }

    @PostMapping("/product/create")
    public String createClothesForm(Clothes clothes) {
        String path = clothes.getImagePath();
        path = "/img/" + path;
        clothes.setImagePath(path);

        clothesService.saveClothes(clothes);

        return "redirect:/admin";
    }
}
