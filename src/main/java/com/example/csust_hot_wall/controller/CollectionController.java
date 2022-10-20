package com.example.csust_hot_wall.controller;

import com.example.csust_hot_wall.entity.Collection;
import com.example.csust_hot_wall.service.CollectionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收藏控制器
 */
@RestController
@RequestMapping("/collection")
public class CollectionController extends BaseController<Collection, CollectionService>{

}
