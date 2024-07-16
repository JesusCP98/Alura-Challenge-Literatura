package com.alura.challenge_literatula.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
