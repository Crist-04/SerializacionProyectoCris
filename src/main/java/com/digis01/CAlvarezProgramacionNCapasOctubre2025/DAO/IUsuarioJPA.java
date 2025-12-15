package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;

import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.UsuarioJPA;

public interface IUsuarioJPA {

    Result GetAll();

    Result Add(UsuarioJPA usuario);

    Result Delete(int idUsuario);

    Result GetById(int idUsuario);

    Result Update(UsuarioJPA usuario);
    
    Result Login(String username, String password);
    
    Result VerficarCuenta(int idUsuario);

}
