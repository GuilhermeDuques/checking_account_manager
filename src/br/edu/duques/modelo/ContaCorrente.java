package br.edu.duques.modelo;

import br.edu.duques.excessao.BancoException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class ContaCorrente {

    protected int numeroConta;
    protected double saldo;

    protected List<Operacao> operacoes = new ArrayList<>();

    public ContaCorrente(int numeroConta, double saldo) {
        this(numeroConta);
        realizarCredito(saldo, new Date());
    }

    public ContaCorrente(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public void adicionarOperacao(double valor, TipoOperacaoEnum tipoEnum) throws BancoException {
        
        adicionarOperacao(valor, tipoEnum, new Date());

    }

    public void adicionarOperacao(double valor, TipoOperacaoEnum tipoEnum, Date dataOperacao) throws BancoException {

        if (TipoOperacaoEnum.DEBITO == tipoEnum) {
            realizarDebito(valor, dataOperacao);

        } else {
            realizarCredito(valor, dataOperacao);
        }
    }

    protected abstract void realizarCredito(double valor, Date dataOperacao);

    protected abstract void realizarDebito(double valor, Date dataOperacao) throws BancoException;

    public List<Operacao> getOperacoes() {
        return operacoes;
    }

    public void setOperacoes(List<Operacao> operacoes) {
        this.operacoes = operacoes;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(int numeroConta) {
        this.numeroConta = numeroConta;
    }

    public double getSaldo() {
        return saldo;
    }
}
