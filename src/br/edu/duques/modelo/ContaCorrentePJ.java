package br.edu.duques.modelo;

import br.edu.duques.excessao.BancoException;
import java.util.Date;
public class ContaCorrentePJ extends ContaCorrente {

    public ContaCorrentePJ(int numeroConta, double saldo) {
        super(numeroConta, saldo);
    }

    public ContaCorrentePJ(int numeroConta) {
        super(numeroConta);
    }

    @Override
    protected void realizarDebito(double valor, Date dataOperacao) throws BancoException {
        if (saldo < valor) {
            throw new BancoException("Saldo insuficiente para realizar a operação.");
        } else {
            Operacao operacao = new Operacao(dataOperacao, valor, TipoOperacaoEnum.DEBITO);
            operacoes.add(operacao);
            saldo -= valor;
        }
    }

    @Override
    protected void realizarCredito(double valor, Date dataOperacao) {
        Operacao operacao = new Operacao(dataOperacao, valor, TipoOperacaoEnum.CREDITO);
        operacoes.add(operacao);
        saldo += valor;
    }

    @Override
    public String toString() {
        return "ContaCorrentePJ{" + numeroConta + " - " + saldo + " }";
    }
}
