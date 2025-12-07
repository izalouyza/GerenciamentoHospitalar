package br.com.hospital.interfaces;

public interface Gerenciavel<T> {
    void adicionar(T elemento) throws Exception;

    void listar() throws Exception;

    T buscar(String identificador) throws Exception;

    boolean editar(String identificador, T novoElemento) throws Exception;

    boolean remover(String identificador) throws Exception;
}
