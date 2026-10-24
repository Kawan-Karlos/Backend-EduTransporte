package com.example.edutransporte.controller;

import org.springframework.web.bind.annotation.RestController;

//Apenas pessoas com permssoes de adm podem utlizar essa classe
@RestController("feriados")
public class FeriadoController {

    //CRUD de feriados (GET, POST, DELETE, EDITAR)

    //Alunos e motoristas podem apenas ter acesso ao metodo GET.
}
