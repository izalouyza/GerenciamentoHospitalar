package br.com.hospital.interfaces;

public interface Gerenciavel<T> {

    void adicionar(T elemento);

    void listar();

    T buscar(String identificador);

    boolean editar(String identificador, T novoElemento);

    boolean remover(String identificador);
}