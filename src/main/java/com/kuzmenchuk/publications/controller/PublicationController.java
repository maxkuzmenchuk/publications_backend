package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.Publication;
import com.kuzmenchuk.publications.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/publication")
public class PublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping("/show/{id}")
    public ModelAndView showPublication(@PathVariable("id") Integer id,
                                        Model model) {
        model.addAttribute("publication", publicationService.findPublication(id));

        return new ModelAndView("publication/viewPublication");
    }

    @GetMapping("/catalog")
    public List<Publication> showAllPublications () {
        return publicationService.getAllPublications();
    }

}
