package br.com.hospital.sistema;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Pessoa;
import static br.com.hospital.utilitarios.Utilitarios.*;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private final List<Pessoa> pessoas = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();

    // --------------------------------------------------------
    //  Pessoas
    // --------------------------------------------------------

    public void adicionarPessoa(Pessoa p) {
        if (p == null) {
            Println("Pessoa inválida! Não é possível adicionar.");
            return;
        }

        if (!p.validar()) {
            Println("Pessoa contém dados inválidos. Cadastro não realizado.");
            return;
        }

        pessoas.add(p);
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public boolean cpfExiste(String cpf) {
        if (cpf == null) return false;

        String cpfNumerico = cpf.replaceAll("\\D", "");

        for (Pessoa p : pessoas) {
            if (p.getCpf().replaceAll("\\D", "").equals(cpfNumerico)) {
                return true;
            }
        }
        return false;
    }

    public Pessoa buscarPessoa(String identificador) {
        if (identificador == null) return null;

        String idNumerico = identificador.replaceAll("\\D", "");

        for (Pessoa p : pessoas) {

            // Verificar CPF
            if (p.getCpf().replaceAll("\\D", "").equals(idNumerico)) {
                return p;
            }

            // Verificar CRM caso seja médico
            if (p instanceof Medico medico) {
                if (compararIdentificadores(medico.getCrm(), identificador)) {
                    return medico;
                }
            }
        }

        return null;
    }

    // --------------------------------------------------------
    //  Consultas
    // --------------------------------------------------------

    public void adicionarConsulta(Consulta c) {
        if (c == null) {
            Println("Consulta inválida! Não foi adicionada.");
            return;
        }

        if (!c.validar()) {
            Println("Consulta com dados inválidos! Não foi adicionada.");
            return;
        }

        consultas.add(c);
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

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