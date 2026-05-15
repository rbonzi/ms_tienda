package com.tienda.tienda.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

public class DataInitializer {
    @Slf4j
    @Component
    @RequiredArgsConstructor
    public class DataInitializer implements CommandLineRunner {
       // private final MembresiaRepository membresiaRepository;
       // private final UsuarioRepository usuarioRepository;

        @Override
        public void run(String... args){
           // if(usuarioRepository.count() > 0){
           //     log.info("Datos cargados");
           //     return;
           // }

            log.info("No hay datos guardados, creando datos");

            // Solo para pruebas

        }
    }
}
