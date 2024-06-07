package com.fco.up.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Service
public class ImagemService {

    @Autowired
    private ImagemRepository imagemRepository;

    public ImagemEntity salvarImagem(MultipartFile file) throws IOException {

        ImagemEntity imagemEntity = new ImagemEntity();
        byte[] fileBytes = file.getBytes();

        imagemEntity.setFoto(Base64.getEncoder().encode(fileBytes));
        return imagemRepository.save(imagemEntity);
    }

    public byte[] buscarImagem(Long id) {
        Optional<ImagemEntity> imageEntityOptional = imagemRepository.findById(id);
        if (imageEntityOptional.isPresent()) {
            return Base64.getDecoder().decode(imageEntityOptional.get().getFoto());
        } else {
            throw new RuntimeException("Imagem não encontrada");
        }
    }

}
