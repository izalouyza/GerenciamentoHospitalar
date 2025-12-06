package br.com.hospital.sistema;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Pessoa;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private final List<Pessoa> pessoas = new ArrayList<>(); // lista de todas as pessoas (pacientes e médicos)
    private final List<Consulta> consultas = new ArrayList<>(); // lista de consultas

    // Adicionar pessoa
    public void adicionarPessoa(Pessoa p) {
        if (p == null) {
            Println("Pessoa inválida! Não é possível adicionar.");
            return;
        }

        if (!p.validar()) { // valida os dados da pessoa antes de adicionar
            Println("Pessoa contém dados inválidos. Cadastro não realizado.");
            return;
        }

        pessoas.add(p);
    }

    public List<Pessoa> getPessoas() {
        return pessoas; // retorna a lista de pessoas
    }

    // Verificar existência
    public boolean cpfExiste(String cpf) {
        if (cpf == null) return false;

        String cpfNumerico = cpf.replaceAll("\\D", ""); // remove caracteres não numéricos

        for (Pessoa p : pessoas) {
            String cpfPessoa = p.getCpf().replaceAll("\\D", "");
            if (cpfPessoa.equals(cpfNumerico)) {
                return true;
            }
        }

        return false;
    }

    public boolean crmExiste(String crm) {
        if (crm == null) return false;

        for (Pessoa p : pessoas) {
            if (p instanceof Medico) { // só médicos possuem CRM
                Medico m = (Medico) p;
                if (compararIdentificadores(m.getCrm(), crm)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Buscar pessoa
    public Pessoa buscarPessoa(String identificador) {
        if (identificador == null) return null;

        String idNumerico = identificador.replaceAll("\\D", "");

        for (Pessoa p : pessoas) {
            // comparar CPF
            String cpfPessoa = p.getCpf().replaceAll("\\D", "");
            if (cpfPessoa.equals(idNumerico)) {
                return p;
            }

            // comparar CRM se for médico
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (compararIdentificadores(m.getCrm(), identificador)) {
                    return m;
                }
            }
        }

        return null;
    }

    // Adicionar consulta
    public void adicionarConsulta(Consulta c) {
        if (c == null) {
            Println("Consulta inválida! Não foi adicionada.");
            return;
        }

        if (!c.validar()) { // valida dados da consulta
            Println("Consulta com dados inválidos! Não foi adicionada.");
            return;
        }

        consultas.add(c);
    }

    public List<Consulta> getConsultas() {
        return consultas; // retorna lista de consultas
    }

    // Buscar consulta
    public Consulta buscarConsulta(String id) {
        if (id == null) return null;

        for (Consulta c : consultas) {
            if (compararIdentificadores(c.getId(), id)) {
                return c;
            }
        }

        return null;
    }
}
