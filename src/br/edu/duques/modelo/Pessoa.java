package br.edu.duques.modelo;

public abstract class Pessoa {

    protected String nome;
    protected ContaCorrente contaCorrente;
    
    public Pessoa(String nome,ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
        this.nome = nome;
    }

    public ContaCorrente getContaCorrente() {
        return contaCorrente;
    }

    public void setContaCorrente(ContaCorrente contaCorrente) {
        this.contaCorrente = contaCorrente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Pessoa{" + "nome=" + nome + ", contaCorrente=" + contaCorrente + '}';
    }
    
    
}
