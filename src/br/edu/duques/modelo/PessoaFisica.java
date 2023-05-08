package br.edu.duques.modelo;

public class PessoaFisica extends Pessoa {

    private String cpf;

    public PessoaFisica(String nome, String CPF, ContaCorrentePF contaCorrente) {
        super(nome, contaCorrente);
        this.cpf = CPF;
    }

    @Override
    public ContaCorrentePF getContaCorrente() {
        return (ContaCorrentePF) contaCorrente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String CPF) {
        this.cpf = CPF;
    }

}
