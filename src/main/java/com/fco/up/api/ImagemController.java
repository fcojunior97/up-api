package com.fco.up.api;

import com.fco.up.domain.ImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/imagens")
public class ImagemController {

    @Autowired
    private ImagemService imagemService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            imagemService.salvarImagem(file);
            return new ResponseEntity<>("A foto foi enviada com sucesso", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Falha no envio da imagem", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        try {
            byte[] imageBytes = imagemService.buscarImagem(id);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=foto.jpg");
            headers.add("Content-Type", "image/jpeg");
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
