package com.kuzmenchuk.publications.controller;

import com.kuzmenchuk.publications.repository.model.Publication;
import com.kuzmenchuk.publications.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/admin/publication")
@CrossOrigin(origins = "http://localhost:8080")
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
                                          @RequestParam("file") MultipartFile file,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("publication/newPublication");
        } else {
            String uploadFolder = System.getProperty("user.dir") + "/src/main/resources/static/images/";
            if (file.isEmpty()) {
                return new ModelAndView("publication/newPublication");
            }
            try {
                byte[]bytes = file.getBytes();
                Path path = Paths.get(uploadFolder + file.getOriginalFilename());
                Files.write(path, bytes);

                p.setImageName(file.getOriginalFilename());
                publicationService.addNewPublication(p);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
