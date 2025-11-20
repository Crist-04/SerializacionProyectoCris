package com.digis01.CAlvarezProgramacionNCapasOctubre2025.DAO;


import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.DireccionJPA;
import com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA.Result;

public interface IDireccionJPA {

    Result UpdateDireccion(DireccionJPA direccion, int idUsuario);
    Result AddDireccion(DireccionJPA direccion, int idUsuario);
    Result DeleteDireccion(int idDireccion);

}
