package br.edu.duques.modelo;

public class PessoaJuridica extends Pessoa {

    private String cnpj;

    public PessoaJuridica(String nome, String cnpj, ContaCorrentePJ contaCorrente) {
        super(nome, contaCorrente);
        this.cnpj = cnpj;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

}
