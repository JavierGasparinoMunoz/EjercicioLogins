package utils;

import javafx.beans.property.StringProperty;

public class Persona {
    private String contrasenia, nombre, apellido, correo, ciclo, conocimientosPrevios;
    private Integer idPersona;

    public Persona(String contrasenia, String nombre, String apellido, String correo, String ciclo, String conocimientosPrevios) {
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.ciclo = ciclo;
        this.conocimientosPrevios = conocimientosPrevios;
    }

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public Persona(String nombre, String apellido, String correo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
    }

    public Persona(String contrasenia, String nombre, String apellido, String correo, String ciclo, String conocimientosPrevios, Integer idPersona) {
        this.contrasenia = contrasenia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.ciclo = ciclo;
        this.conocimientosPrevios = conocimientosPrevios;
        this.idPersona = idPersona;
    }

    public Persona() {

    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }

    public String getConocimientosPrevios() {
        return conocimientosPrevios;
    }

    public void setConocimientosPrevios(String conocimientosPrevios) {
        this.conocimientosPrevios = conocimientosPrevios;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "contrasenia='" + contrasenia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", ciclo='" + ciclo + '\'' +
                ", conocimientosPrevios='" + conocimientosPrevios + '\'' +
                '}';
    }
}