package com.bpmncompiler.bpmn_compiler.api;

import com.bpmncompiler.bpmn_compiler.core.compiler.CompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController // 1. Je suis une API (Je parle JSON/Texte, pas HTML)
@RequestMapping("/api/compiler") // 2. Mon adresse de base
@RequiredArgsConstructor
public class CompilerController {

    private final CompilerService compilerService;

    // 3. Endpoint POST : On envoie des données (le fichier)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> compile(
            @RequestParam("file") MultipartFile file, // Le fichier BPMN
            @RequestParam(value = "language", defaultValue = "JAVA") String language // Le langage cible (Optionnel,
                                                                                     // défaut JAVA)
    ) {
        try {
            // 4. On passe la patate chaude au Service (Le Cerveau)
            String generatedCode = compilerService.compile(file.getInputStream(), language);

            // 5. Succès : On renvoie le code avec un statut 200 OK
            return ResponseEntity.ok(generatedCode);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erreur de lecture du fichier : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            // Erreur client (ex: langage non supporté) -> 400 Bad Request
            return ResponseEntity.badRequest().body("Requête invalide : " + e.getMessage());
        } catch (Exception e) {
            // Erreur inconnue -> 500 Internal Server Error
            return ResponseEntity.internalServerError().body("Erreur de compilation : " + e.getMessage());
        }
    }
}
