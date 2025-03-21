package com.app.security.repository;

import com.app.security.tableBDD.Note;
import com.app.security.tableBDD.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUser(User user); // Trouver les notes d'un utilisateur
}