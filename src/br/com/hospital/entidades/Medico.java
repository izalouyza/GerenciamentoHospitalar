package br.com.hospital.entidades;

public class Medico extends Pessoa {
    private final String crm;
    private String especialidade;

    public Medico(String id, String nome, String cpf, String telefone, String email,
                  String endereco, String senha, String crm, String especialidade) {
        super(id, nome, cpf, telefone, email, endereco, senha, "MEDICO");
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }
    public String getEspecialidade() {
        return especialidade;
    }
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("CRM: " + crm);
        System.out.println("Especialidade: " + especialidade);
    }
}
