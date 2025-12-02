package br.com.hospital.entidades;

public class Paciente extends Pessoa {
    private int idade;
    private String historicoClinico;

    public Paciente(String id, String nome, String cpf, String telefone, String email,
                    String endereco, String senha, int idade, String historicoClinico) {
        super(id, nome, cpf, telefone, email, endereco, senha, "PACIENTE");
        this.idade = idade;
        this.historicoClinico = historicoClinico;
    }

    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getHistoricoClinico() {
        return historicoClinico;
    }
    public void setHistoricoClinico(String historicoClinico) {
        this.historicoClinico = historicoClinico;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Idade: " + idade);
        System.out.println("Histórico Clínico: " + historicoClinico);
    }
}
