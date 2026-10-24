package com.example.edutransporte.controller;


import org.springframework.web.bind.annotation.RestController;

//Apenas pessoas com permssoes de adm podem utlizar essa classe
@RestController("/convites")
public class ConviteController {

    //gerar código de convite para dar acesso administrativo a outros usuarios, o codigo deve expirar 2 horas depois caso não seja usado

}
