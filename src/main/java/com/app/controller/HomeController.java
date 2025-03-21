package com.app.controller;

import com.app.security.service.UserService;
import com.app.security.tableBDD.Note;
import com.app.security.tableBDD.User;
import com.app.security.service.NoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final NoteService noteService;
    private final UserService userService;

    public HomeController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }
    @GetMapping("/home")
    public String home(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";  // Rediriger vers la page de connexion si l'utilisateur n'est pas authentifié
        }

        String username = principal.getName();
        User user = userService.findByUsername(username);  // Récupérer l'utilisateur complet

        if (user == null) {
            return "redirect:/login";  // Rediriger si l'utilisateur n'est pas trouvé
        }

        // Récupérer les notes de l'utilisateur
        List<Note> notes = noteService.getNotesByUser(user);
        model.addAttribute("notes", notes);
        return "home";
    }

    @PostMapping("/home/add")
    public String addNote(
            @RequestParam String title,
            @RequestParam String content,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";  // Rediriger vers la page de connexion si l'utilisateur n'est pas authentifié
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);  // Récupérer l'utilisateur complet
        if (user == null) {
            return "redirect:/login";  // Rediriger si l'utilisateur n'est pas trouvé
        }
        noteService.createNote(title, content, user);
        redirectAttributes.addFlashAttribute("message", "Note ajoutée avec succès !");
        return "redirect:/home";
    }

    @GetMapping("/home/note/{id}")
    public String editNoteForm(@PathVariable Long id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";  // Rediriger vers la page de connexion si l'utilisateur n'est pas authentifié
        }

        // Récupérer la note par son ID
        Note note = noteService.getNoteById(id);

        if (note== null) {
            return "redirect:/home";  // Rediriger vers la page d'accueil si la note n'est pas trouvée
        }

        // Vérifier que l'utilisateur est bien le propriétaire de la note
        String username = principal.getName();
        if (!note.getUser().getUsername().equals(username)) {
            return "redirect:/home";  // Rediriger si l'utilisateur n'est pas autorisé à modifier cette note
        }

        // Ajouter la note au modèle
        model.addAttribute("note", note);
        return "note";  // Retourner la vue pour modifier la note
    }

    @GetMapping("/logout")
    public String logout(

    ) {
        return "redirect:/login";
    }
}