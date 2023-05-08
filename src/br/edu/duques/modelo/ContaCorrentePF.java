package br.edu.duques.modelo;

import br.edu.duques.excessao.BancoException;
import java.util.Date;

public class ContaCorrentePF extends ContaCorrente {

    private double chequeEspecial, chequeEspecialAtual;

    public ContaCorrentePF(int numeroConta, double saldo, double chequeEspecial) {
        super(numeroConta, saldo);
        this.chequeEspecial = chequeEspecial;
        this.chequeEspecialAtual = chequeEspecial;
    }

    public ContaCorrentePF(int numeroConta, double chequeEspecial) {
        super(numeroConta);
        this.chequeEspecial = chequeEspecial;
        this.chequeEspecialAtual = chequeEspecial;
    }

    public double getChequeEspecial() {
        return chequeEspecial;
    }

    public void setChequeEspecial(double chequeEspecial) {
        this.chequeEspecial = chequeEspecial;
    }

    @Override
    protected void realizarCredito(double valor, Date dataOperacao) {
        Operacao operacao = new Operacao(dataOperacao, valor, TipoOperacaoEnum.CREDITO);
        operacoes.add(operacao);

        if (chequeEspecial == chequeEspecialAtual) {
            saldo += valor;
        } else {
            double valorUltilizado = chequeEspecial - chequeEspecialAtual;
            if (valorUltilizado >= valor) {
                chequeEspecialAtual += valor;
            } else {
                saldo += (valor - valorUltilizado);
                chequeEspecialAtual = chequeEspecial;
            }
        }
    }

    @Override
    public String toString() {
        return "ContaCorrentePF{" + "chequeEspecial=" + chequeEspecial + ", chequeEspecialAtual=" + chequeEspecialAtual + ", saldo="+saldo+'}';
    }

   


    @Override
    protected void realizarDebito(double valor, Date dataOperacao) throws BancoException {
        if (valor > saldo + chequeEspecialAtual) {
            throw new BancoException("Saldo insuficiente para realizar a operação.");
        } else {
            Operacao operacao = new Operacao(dataOperacao, valor, TipoOperacaoEnum.DEBITO);
            operacoes.add(operacao);
            if (saldo >= valor) {
                saldo -= valor;
            } else {
                chequeEspecialAtual -= (valor - saldo);
                saldo = 0;
            }
        }
    }

    public double getChequeEspecialAtual() {
        return chequeEspecialAtual;
    }
}
