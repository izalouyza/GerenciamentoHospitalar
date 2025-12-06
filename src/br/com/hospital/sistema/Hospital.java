package br.com.hospital.sistema;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private final List<Pessoa> pessoas = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();

    public void adicionarPessoa(Pessoa p) {
        if (p != null) {
            pessoas.add(p);
        }
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public Pessoa buscarPessoa(String identificador) {
        if (identificador == null) return null;

        String cpfNumerico = identificador.replaceAll("\\D", "");

        for (Pessoa p : pessoas) {

            // --- CPF ---
            String cpfPessoa = p.getCpf().replaceAll("\\D", "");
            if (cpfPessoa.equals(cpfNumerico)) {
                return p;
            }

            // --- CRM (apenas se for médico) ---
            if (p instanceof Medico medico) {
                if (medico.getCrm().equalsIgnoreCase(identificador)) {
                    return medico;
                }
            }
        }

        return null;
    }

    public void adicionarConsulta(Consulta c) {
        if (c != null) {
            consultas.add(c);
        }
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public Consulta buscarConsulta(String id) {
        if (id == null) return null;

        for (Consulta c : consultas) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }

        return null;
    }
}
