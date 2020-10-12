package com.kuzmenchuk.publications.service;

import com.kuzmenchuk.publications.repository.PublicationRepository;
import com.kuzmenchuk.publications.repository.model.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    public Publication findPublication(Integer id) {
        return publicationRepository.findPublicationById(id);
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public void addNewPublication(Publication p) {
        Publication newPublication = new Publication();

        newPublication.setName(p.getName());
        newPublication.setPrice(p.getPrice());
        newPublication.setImageName(p.getImageName());
        newPublication.setDescription(p.getDescription());

        publicationRepository.saveAndFlush(newPublication);
    }

    public void update(Publication p) {
        publicationRepository.saveAndFlush(p);
    }

    public void delete(Publication p) {
        publicationRepository.delete(p);
    }
}
