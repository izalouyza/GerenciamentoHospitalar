package br.com.hospital.sistema;

import br.com.hospital.entidades.*;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.List;

public class Hospital {
    private final List<Pessoa> pessoas = new ArrayList<>();
    private final List<Consulta> consultas = new ArrayList<>();

    // PESSOAS
    public void adicionarPessoa(Pessoa p) {
        if (p == null) return;
        pessoas.add(p);
    }

    public void listarPessoas() {
        if (Utilitarios.listaVazia(pessoas)) {
            Utilitarios.println("Nenhuma pessoa cadastrada.");
            return;
        }
        for (Pessoa p : pessoas) p.exibirInformacoes();
    }

    // busca por CPF (paciente/funcionario) e por CRM (médico)
    public Pessoa buscarPessoa(String identificador) {
        if (!Utilitarios.textoNaoVazio(identificador)) return null;
        String clean = identificador.replaceAll("\\D", "");
        for (Pessoa p : pessoas) {
            if (p.getCpf().replaceAll("\\D", "").equals(clean)) return p;
            if (p instanceof Medico) {
                Medico m = (Medico) p;
                if (m.getCrm().equalsIgnoreCase(identificador)) return m;
            }
        }
        return null;
    }

    public boolean editarPessoa(String cpfAntigo, Pessoa nova) {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).getCpf().equals(cpfAntigo)) {
                pessoas.set(i, nova);
                return true;
            }
        }
        return false;
    }

    public boolean removerPessoa(String cpf) {
        return pessoas.removeIf(p -> p.getCpf().equals(cpf));
    }

    // CONSULTAS
    public void adicionarConsulta(Consulta c) {
        if (c == null) return;
        consultas.add(c);
    }

    public List<Consulta> getConsultas() { return consultas; }

    public Consulta buscarConsulta(String id) {
        if (!Utilitarios.textoNaoVazio(id)) return null;
        for (Consulta c : consultas) if (c.getId().equalsIgnoreCase(id)) return c;
        return null;
    }

    public boolean editarConsulta(String id, Consulta nova) {
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getId().equals(id)) {
                consultas.set(i, nova);
                return true;
            }
        }
        return false;
    }

    public boolean removerConsulta(String id) {
        return consultas.removeIf(c -> c.getId().equalsIgnoreCase(id));
    }

    public void listarConsultas() {
        if (Utilitarios.listaVazia(consultas)) {
            Utilitarios.println("Nenhuma consulta marcada.");
            return;
        }
        for (Consulta c : consultas) c.exibirResumo();
    }
}
