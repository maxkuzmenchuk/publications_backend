package com.kuzmenchuk.publications.service;

import com.kuzmenchuk.publications.repository.PublicationRepository;
import com.kuzmenchuk.publications.repository.model.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

@Service
public class PublicationService {
    @Autowired
    private PublicationRepository publicationRepository;

    @Transactional
    public Publication findPublication(Long id) {
        return publicationRepository.findPublicationById(id);
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    @Transactional
    public void savePublication(Publication p, MultipartFile cover) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            InputStream is = null;
            try {
                is = new BufferedInputStream(cover.getInputStream());
                byte[] byteChunk = new byte[4096];
                int n;
                while ( (n = is.read(byteChunk)) > 0 ) {
                    baos.write(byteChunk, 0, n);
                }
                p.setImageName(cover.getOriginalFilename().split("\\.")[0]);
                p.setCover(baos.toByteArray());


            publicationRepository.save(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(Publication p) {
        publicationRepository.saveAndFlush(p);
    }

    public void delete(Publication p) {
        publicationRepository.delete(p);
    }
}
