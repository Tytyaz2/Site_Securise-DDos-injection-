package com.app.security.service;

import com.app.security.tableBDD.Note;
import com.app.security.tableBDD.User;
import com.app.security.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByUser(User user) {
        return noteRepository.findByUser(user);
    }

    public Note createNote(String title, String content, User user) {
        if (user == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(user); // Associez l'utilisateur à la note
        return noteRepository.save(note);
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElse(null);
    }
}