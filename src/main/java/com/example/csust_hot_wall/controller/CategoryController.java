package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Category;
import com.example.csust_hot_wall.service.CategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类别控制器
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController<Category, CategoryService>{

}
