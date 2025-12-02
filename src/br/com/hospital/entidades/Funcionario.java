package br.com.hospital.entidades;

public class Funcionario extends Pessoa {
    private String cargo;
    private String setor;

    public Funcionario(String id, String nome, String cpf, String telefone, String email,
                       String endereco, String senha, String nivelAcesso,
                       String cargo, String setor) {
        super(id, nome, cpf, telefone, email, endereco, senha, nivelAcesso);
        this.cargo = cargo;
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getSetor() {
        return setor;
    }
    public void setSetor(String setor) {
        this.setor = setor;
    }

    @Override
    public void exibirInformacoes() {
        super.exibirInformacoes();
        System.out.println("Cargo: " + cargo);
        System.out.println("Setor: " + setor);
    }
}
