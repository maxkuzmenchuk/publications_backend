package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.Publication;
import com.kuzmenchuk.publications.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
@CrossOrigin(origins = "http://localhost:8080")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping("/{id}")
    public Publication getImageForOrderItem(@PathVariable("id") Long id) {
        return publicationService.findPublication(id);
    }

    @GetMapping("/all")
    public List<Publication> showAllPublications() {
        return publicationService.getAllPublications();
    }

}
