package com.kuzmenchuk.publications.repository;

import com.kuzmenchuk.publications.repository.model.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Integer> {
    Publication findPublicationById(Integer id);
}
