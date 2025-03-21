package com.app.controller;
import com.app.security.tableBDD.Note;
import com.app.security.repository.NoteRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @PostMapping("/notes/{id}/edit")
    public String updateNote(@PathVariable Long id,
                             @RequestParam String title,
                             @RequestParam String content,
                             Principal principal) {
        // Récupérer l'utilisateur actuel
        String username = principal.getName();

        // Récupérer la note depuis la base de données
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note non trouvée"));

        // Vérifier que l'utilisateur est autorisé à modifier la note
        if (!note.getUser().getUsername().equals(username)) {
           return "redirect:/login";  }

        // Mettre à jour la note
        note.setTitle(title);
        note.setContent(content);
        noteRepository.save(note);

        return "redirect:/notes/" + id; // Rediriger vers la page de la note mise à jour
    }
}