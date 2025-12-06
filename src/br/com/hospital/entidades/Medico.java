package br.com.hospital.entidades;

import br.com.hospital.utilitarios.Utilitarios;

public class Medico extends Pessoa {
    private final String crm;
    private String especialidade;

    public Medico(String id, String nome, String cpf, String telefone, String email,
                  String endereco, String senha, String crm, String especialidade) {
        super(id, nome, cpf, telefone, email, endereco, senha, "MEDICO");
        this.crm = crm;
        this.especialidade = Utilitarios.normalizarTexto(especialidade);
    }

    public String getCrm() {
        return crm;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = Utilitarios.normalizarTexto(especialidade);
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        Utilitarios.println("CRM: " + crm);
        Utilitarios.println("Especialidade: " + especialidade);
    }
}
