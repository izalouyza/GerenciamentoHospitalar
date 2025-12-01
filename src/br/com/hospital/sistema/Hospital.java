package br.com.hospital.sistema;

import br.com.hospital.entidades.Medico;
import br.com.hospital.entidades.Pessoa;
import br.com.hospital.interfaces.Gerenciavel;

import java.util.ArrayList;
import java.util.List;

public class Hospital implements Gerenciavel<Pessoa> {
    final private List<Pessoa> pessoasRegistradas;

    public Hospital (){
        this.pessoasRegistradas = new ArrayList<>();
    }

    public List<Pessoa> getPessoasRegistradas() {
        return pessoasRegistradas;
    }

    @Override
    public void adicionar(Pessoa elemento) {
        pessoasRegistradas.add(elemento);
    }

    @Override
    public void listar() {
        if (pessoasRegistradas.isEmpty()) {
            System.out.println("Nenhuma pessoa registrada, não é possível listar.");
            return;
        }
        for (Pessoa pessoaListar: pessoasRegistradas){
            pessoaListar.exibirInformacoes();
        }
    }

    @Override
    public Pessoa buscar(String identificador) {
        for (Pessoa pessoaBuscar : pessoasRegistradas) {
            if (pessoaBuscar.getCpf().equals(identificador)) {
                pessoaBuscar.exibirInformacoes();
                return pessoaBuscar;
            }
            if (pessoaBuscar instanceof Medico){
                if (((Medico) pessoaBuscar).getCrm().equals(identificador)){
                    pessoaBuscar.exibirInformacoes();
                    return pessoaBuscar;
                }
            }
        }
        return null;
    }

    @Override
    public boolean editar(String identificador, Pessoa novoElemento) {
        for (int i = 0; i < pessoasRegistradas.size(); i++) {
            if (pessoasRegistradas.get(i).getCpf().equals(identificador)) {
                pessoasRegistradas.set(i, novoElemento);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remover(String identificador) {
        return pessoasRegistradas.removeIf(pessoaRemovivel -> pessoaRemovivel.getCpf().equals(identificador));
    }
}
