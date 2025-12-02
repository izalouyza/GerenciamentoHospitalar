package br.com.hospital.gerenciadores;

import br.com.hospital.entidades.Consulta;
import br.com.hospital.interfaces.Gerenciavel;
import br.com.hospital.utilitarios.Utilitarios;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorConsulta implements Gerenciavel<Consulta> {

    private final List<Consulta> consultas = new ArrayList<>();

    // Adicionar
    @Override
    public void adicionar(Consulta consulta) {
        if (consulta == null) {
            System.out.println("Erro: Consulta nula.");
            return;
        }

        if (!consulta.validar()) {
            System.out.println("Erro ao adicionar consulta: " + consulta.getMensagemValidacao());
            return;
        }

        if (buscar(consulta.getIdentificador()) != null) {
            System.out.println("Erro: Já existe uma consulta com este ID.");
            return;
        }

        consultas.add(consulta);
        System.out.println("Consulta adicionada com sucesso!");
    }

    // Listar
    @Override
    public void listar() {
        if (Utilitarios.listaVazia(consultas)) {
            System.out.println("Nenhuma consulta cadastrada.");
            return;
        }

        for (Consulta c : consultas) {

            String descricao;
            if (c.getDescricao() == null) {
                descricao = "Não informada";
            } else {
                descricao = c.getDescricao();
            }

            System.out.printf("""
                ------------------------------
                Consulta ID: %s
                Paciente: %s
                Médico: %s
                Horário: %s
                Descrição: %s
                ------------------------------
                """,
                    c.getIdentificador(),
                    c.getPaciente().getNome(),
                    c.getMedico().getNome(),
                    c.getDataHora(),
                    descricao
            );
        }
    }

    // Buscar
    @Override
    public Consulta buscar(String identificador) {
        if (!Utilitarios.textoNaoVazio(identificador)) {
            return null;
        }

        for (Consulta c : consultas) {
            if (Utilitarios.compararIdentificadores(c.getIdentificador(), identificador)) {
                return c;
            }
        }
        return null;
    }

    // Editar
    public boolean editar(String id, Consulta novosDados) {
        Consulta antiga = buscar(id);

        if (antiga == null) {
            System.out.println("Consulta não encontrada.");
            return false;
        }

        if (novosDados == null) {
            System.out.println("Novos dados inválidos.");
            return false;
        }

        try {
            // Valida os novos dados antes de alterar a consulta
            if (!novosDados.validar()) {
                System.out.println("Falha na validação dos novos dados: " + novosDados.getMensagemValidacao());
                return false; // mantém a antiga
            }

            // Atualiza apenas os campos editáveis
            antiga.setDataHora(novosDados.getDataHora());
            antiga.setDescricao(novosDados.getDescricao());

            System.out.println("Consulta editada com sucesso!");
            return true;

        } catch (Exception e) {
            System.out.println("Erro ao editar a consulta: " + e.getMessage());
            return false; // mantém a antiga
        }
    }


    // Remover
    @Override
    public boolean remover(String identificador) {
        Consulta encontrada = buscar(identificador);

        if (encontrada == null) {
            System.out.println("Consulta não encontrada.");
            return false;
        }

        consultas.remove(encontrada);
        System.out.println("Consulta removida com sucesso!");
        return true;
    }

    // Getter da lista
    public List<Consulta> getConsultas() {
        return consultas;
    }
}
