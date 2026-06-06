package org.cug.geodt.weibo.geomesa.controller;

import org.cug.geodt.weibo.geomesa.service.GeoMesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @className: GeoMesaController
 * @author: caiyixun
 * @description: TODO
 * @date: 2024/8/15 14:58
 * @version: 1.0
 */
@RestController
@RequestMapping("/geomesa")
public class GeoMesaController {

    @Autowired
    private GeoMesaService geoMesaService;


    @PostMapping("/create-schema")
    public String createSchema(String typeName, String schema) {
        try {
            geoMesaService.createSchema(typeName, schema);
            return "Schema created";
        } catch (IOException e) {
            return "Failed to create schema: " + e.getMessage();
        }
    }

    @DeleteMapping("/delete-schema")
    public String deleteSchema(String typeName) {
        try {
            geoMesaService.deleteSchema(typeName);
            return "Schema deleted";
        } catch (IOException e) {
            return "Failed to delete schema: " + e.getMessage();
        }
    }

}
