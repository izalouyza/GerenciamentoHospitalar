package br.com.hospital.sistema;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.List;

public class Hospital {

    private final List<Pessoa> pessoas = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();

    //Adicionar pessoa
    public void adicionarPessoa(Pessoa p) {
        if (p == null) {
            Utilitarios.println("Pessoa inválida! Não é possível adicionar.");
            return;
        }

        if (!p.validar()) {
            Utilitarios.println("Pessoa contém dados inválidos. Cadastro não realizado.");
            return;
        }

        pessoas.add(p);
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    //Verificar se o CPF existe
    public boolean cpfExiste(String cpf) {
        if (cpf == null) return false;

        String cpfNumerico = cpf.replaceAll("\\D", "");

        for (Pessoa p : pessoas) {
            String cpfPessoa = p.getCpf().replaceAll("\\D", "");
            if (cpfPessoa.equals(cpfNumerico)) {
                return true;
            }
        }

        return false;
    }

    //Verificar se o CRM existe
    public boolean crmExiste(String crm) {
        if (crm == null) return false;

        for (Pessoa p : pessoas) {
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (Utilitarios.compararIdentificadores(m.getCrm(), crm)) {
                    return true;
                }
            }
        }

        return false;
    }

    //Buscar pessoa (CPF ou CRM)
    public Pessoa buscarPessoa(String identificador) {
        if (identificador == null) return null;

        String idNumerico = identificador.replaceAll("\\D", "");

        for (Pessoa p : pessoas) {

            // Comparar CPF
            String cpfPessoa = p.getCpf().replaceAll("\\D", "");
            if (cpfPessoa.equals(idNumerico)) {
                return p;
            }

            // Comparar CRM (se for médico)
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (Utilitarios.compararIdentificadores(m.getCrm(), identificador)) {
                    return m;
                }
            }
        }

        return null;
    }

    //Adicionar consulta
    public void adicionarConsulta(Consulta c) {
        if (c == null) {
            Utilitarios.println("Consulta inválida! Não foi adicionada.");
            return;
        }

        if (!c.validar()) {
            Utilitarios.println("Consulta com dados inválidos! Não foi adicionada.");
            return;
        }

        consultas.add(c);
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    //Buscar consulta por ID
    public Consulta buscarConsulta(String id) {
        if (id == null) return null;

        for (Consulta c : consultas) {
            if (Utilitarios.compararIdentificadores(c.getId(), id)) {
                return c;
            }
        }

        return null;
    }
}
