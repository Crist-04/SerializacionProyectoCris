package com.digis01.CAlvarezProgramacionNCapasOctubre2025.JPA;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USUARIO")
public class UsuarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    public int IdUsuario;

    @Column(name = "nombre")
    public String Nombre;

    @Column(name = "username")
    public String UserName;

    @Column(name = "apellidopaterno")
    public String ApellidoPaterno;

    @Column(name = "apellidomaterno")
    public String ApellidoMaterno;

    @Column(name = "email")
    public String Email;

    @Column(name = "password")
    public String Password;
    
    @Column(name = "estatus")
    public Integer Estatus;
    
    @Column(name = "isverified")
    public Integer IsVerified;

    @Column(name = "fechanacimiento")
    public Date FechaNacimiento;

    @Column(name = "sexo")
    public String Sexo;

    @Column(name = "telefono")
    public String Telefono;

    @Column(name = "celular")
    public String Celular;

    @Column(name = "curp")
    public String CURP;

    @Lob
    @Column(name = "imagen", columnDefinition = "CLOB")
    @JsonProperty("imagen")
    public String Imagen;

    @ManyToOne
    @JoinColumn(name = "idrol")
    private RolJPA rol;

    @OneToMany(mappedBy = "UsuarioJPA", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    public List<DireccionJPA> direccionesJPA = new ArrayList<>();

    public RolJPA getRol() {
        return rol;
    }

public void setRol(RolJPA rol) {
    this.rol = rol;
}

public List<DireccionJPA> getDireccionesJPA() {
    return direccionesJPA;
}

public void setDireccionesJPA(List<DireccionJPA> direccionesJPA) {
    this.direccionesJPA = direccionesJPA;
}

@JsonIgnore
    public int getIdUsuario() {
        return IdUsuario;
    }

    @JsonIgnore
    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    @JsonIgnore
    public String getNombre() {
        return Nombre;
    }

    @JsonIgnore
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getUsername() {
        return UserName;
    }

    @JsonIgnore
    public void setUsername(String UserName) {
        this.UserName = UserName;
    }

    @JsonIgnore
    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }

    @JsonIgnore
    public void setApellidoPaterno(String ApellidoPaterno) {
        this.ApellidoPaterno = ApellidoPaterno;
    }

    @JsonIgnore
    public String getApellidoMaterno() {
        return ApellidoMaterno;
    }

    @JsonIgnore
    public void setApellidoMaterno(String ApellidoMaterno) {
        this.ApellidoMaterno = ApellidoMaterno;
    }

    @JsonIgnore
    public String getEmail() {
        return Email;
    }

    @JsonIgnore
    public void setEmail(String Email) {
        this.Email = Email;
    }

    @JsonIgnore
    public String getPassword() {
        return Password;
    }

    @JsonIgnore
    public void setPassword(String Password) {
        this.Password = Password;
    }

    @JsonIgnore
    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    @JsonIgnore
    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }

    @JsonIgnore
    public String getSexo() {
        return Sexo;
    }

    @JsonIgnore
    public void setSexo(String Sexo) {
        this.Sexo = Sexo;
    }

    @JsonIgnore
    public String getTelefono() {
        return Telefono;
    }

    @JsonIgnore
    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    @JsonIgnore
    public String getCelular() {
        return Celular;
    }

    @JsonIgnore
    public void setCelular(String Celular) {
        this.Celular = Celular;
    }

    @JsonIgnore
    public String getCURP() {
        return CURP;
    }

    @JsonIgnore
    public void setCURP(String CURP) {
        this.CURP = CURP;
    }

    @JsonIgnore
    public String getImagen() {
        return Imagen;
    }

    @JsonIgnore
    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    @JsonIgnore
    public String getUserName() {
        return UserName;
    }

    @JsonIgnore
    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    @JsonIgnore
    public Integer getEstatus() {
        return Estatus;
    }

    @JsonIgnore
    public void setEstatus(Integer Estatus) {
        this.Estatus = Estatus;
    }

    @JsonIgnore
    public Integer getIsVerified() {
        return IsVerified;
    }

    @JsonIgnore
    public void setIsVerified(Integer IsVerified) {
        this.IsVerified = IsVerified;
    }
    
    
    
}
