package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.Publication;
import com.kuzmenchuk.publications.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/publication")
public class AdminPublicationController {
    @Autowired
    private PublicationService publicationService;

    @GetMapping("/show-all")
    public ModelAndView showPublications(Model model) {
        model.addAttribute("publications", publicationService.getAllPublications());

        return new ModelAndView("publication/listPublications");
    }

    @GetMapping("/add-publication")
    public ModelAndView addNewPublication(Model model) {
        model.addAttribute("newPublication", new Publication());

        return new ModelAndView("publication/newPublication");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPublication(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("editPublication", publicationService.findPublication(id));

        return new ModelAndView("publication/editPublication");
    }

    @PostMapping("/new-publication")
    public ModelAndView addNewPublication(@ModelAttribute("newPublication") @Valid Publication p,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("publication/newPublication");
        } else {
            publicationService.addNewPublication(p);

            return new ModelAndView("redirect:/admin/publication/show-all");
        }
    }

    @PostMapping("update/{id}")
    public ModelAndView updatePublication(@PathVariable("id") Integer id,
                                          @ModelAttribute("editPublication") @Valid Publication p) {
        Publication publicationToUpd = publicationService.findPublication(id);

        publicationToUpd.setName(p.getName());
        publicationToUpd.setPrice(p.getPrice());
        publicationToUpd.setImageName(p.getImageName());
        publicationToUpd.setDescription(p.getDescription());

        publicationService.update(publicationToUpd);

        return new ModelAndView("redirect:/admin/publication/show-all");
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deletePublication(@PathVariable("id") Integer id) {
        Publication publication = publicationService.findPublication(id);

        publicationService.delete(publication);

        return new ModelAndView("redirect:/admin/publication/show-all");
    }
}
