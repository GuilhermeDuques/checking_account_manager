package br.edu.duques.main;

import br.edu.duques.excessao.BancoException;
import br.edu.duques.modelo.ContaCorrentePF;
import br.edu.duques.modelo.Operacao;
import br.edu.duques.modelo.Pessoa;
import br.edu.duques.modelo.TipoOperacaoEnum;
import br.edu.duques.util.ArquivoUtil;
import br.edu.duques.util.Util;
import java.util.ArrayList;
import java.util.List;

public class Conta {

    private ArquivoUtil arquivoUtil;

    private List<Pessoa> pessoas = new ArrayList<>();

    public static void main(String[] args) throws BancoException {
        new Conta().run();
    }

    public void run() throws BancoException {
        arquivoUtil = new ArquivoUtil("C:\\Users\\Duques\\Documents\\NetBeansProjects\\ContaCorrente\\src\\arquivo\\Contas.txt");
        pessoas = arquivoUtil.lerArquivo();

        int opcao = 0;
        while (opcao != 6) {
            opcao = menu();
            try {
                acoes(opcao);
            } catch (BancoException be) {
                System.err.println(be.getMessage());
            }
        }

    }

    private int menu() {
        int opcao = 0;

        do {
            System.out.printf("\n[1] Inclusão de conta pessoa fisica.\n"
                    + "[2] Inclusão de conta pessoa juridica.\n"
                    + "[3] Alteração de saldo.\n"
                    + "[4] Exclusão de conta.\n"
                    + "[5] Relatórios gerenciais.\n"
                    + "[6] Sair.\n\n");
            opcao = Util.lerInteiro("Entre com sua opção: ");
            if ((opcao < 1) || (opcao > 6)) {
                System.err.println("Opção inválida");
            }
        } while ((opcao < 1) || (opcao > 6));
        return opcao;
    }

    private void acoes(int opcao) throws BancoException {

        switch (opcao) {
            case 1:
                inclusaoPF();
                break;
            case 2:
                inclusaoPJ();
                break;
            case 3:
                alteracaoConta();
                break;
            case 4:
                exclusaoConta();
                break;
            case 5:
                int menuRel = 0;
                while (menuRel != 6) {
                    menuRel = relatorios();
                    acoesRelatorio(menuRel);
                }
                break;
            default:
                sair();
                break;
        }
    }

    private void sair() throws BancoException {
        arquivoUtil.gravarPessoas(pessoas);
        System.out.println("Tchau e volte sempre!");
    }

    private void validarExistencia(int numeroConta) throws BancoException {
        if (obterContaCorrente(numeroConta) == null) {
            throw new BancoException("Conta não existe.");
        }
    }

    private Pessoa obterContaCorrente(int contaCorrente) {
        if (pessoas != null) {
            for (Pessoa p : pessoas) {
                if (p.getContaCorrente().getNumeroConta() == contaCorrente) {
                    return p;
                }
            }
        }
        return null;
    }

    private void inclusaoPF() throws BancoException {

        String nome = Util.lerString("Informe o nome do cliente: ");
        int conta = Util.lerInteiro("Informe o número da conta: ");
        validarConta(conta);
        String documento = Util.lerString("Informe o número do cpf: ");
        double saldo = Util.lerDouble("Informe o saldo do cliente: ");
        double chequeEspecial = Util.lerDouble("Informe o valor do cheque especial: ");
        pessoas.add(Util.criarPessoa(nome, documento, conta, saldo, chequeEspecial));
    }

    private void inclusaoPJ() throws BancoException {

        String nome = Util.lerString("Informe o nome da empresa: ");
        int conta = Util.lerInteiro("Informe o número da conta da empresa: ");
        validarConta(conta);
        String documento = Util.lerString("Informe o número do cnpj: ");
        double saldo = Util.lerDouble("Informe o saldo da empresa: ");
        pessoas.add(Util.criarPessoa(nome, documento, conta, saldo));
    }

    private void alteracaoConta() throws BancoException {

        int conta = Util.lerInteiro("Informe o número da conta: ");
        validarExistencia(conta);
        Pessoa p = obterContaCorrente(conta);
        String tipoOperacao = Util.lerString("Informe o tipo de operacao:(D)-Debito ou (C)-Credito ");
        double valor = Util.lerDouble("Informe o valor: ");
        TipoOperacaoEnum tipoEnum = "D".equalsIgnoreCase(tipoOperacao) ? TipoOperacaoEnum.DEBITO : TipoOperacaoEnum.CREDITO;
//                ifternario
        p.getContaCorrente().adicionarOperacao(valor, tipoEnum);
    }

    private void exclusaoConta() throws BancoException {

        int conta = Util.lerInteiro("Informe o número da conta que deseja excluir: ");
        validarExistencia(conta);
        Pessoa exclusao = obterContaCorrente(conta);
        pessoas.remove(exclusao);
        System.out.println("Conta removida com sucesso");
    }

    private int relatorios() throws BancoException {
        int opcao1 = 0;

        do {
            System.out.printf("\n[1] listar clientes com saldo negativo.\n"
                    + "[2] listar os clientes que tem saldo acima de um determinado valor.\n"
                    + "[3] listar todas as contas PF.\n"
                    + "[4] listar todas as contas PJ.\n"
                    + "[5] listar as operações realizadas em uma determinada conta.\n"
                    + "[6] Voltar ao menu anterior.\n\n");
            opcao1 = Util.lerInteiro("Entre com sua opção: ");
            if (opcao1 < 1 || opcao1 > 6) {
                System.err.println("Opção inválida");
            }
        } while (opcao1 < 1 || opcao1 > 6);
        return opcao1;

    }

    private void acoesRelatorio(int opcao1) throws BancoException {

        switch (opcao1) {
            case 1:
                listarClienteSaldoNegativo();
                break;
            case 2:
                listarClienteComSaldoMaiorQue();
                break;
            case 3:
                listarContasPF();
                break;
            case 4:
                listarContasPJ();
                break;
            case 5:
                listarOperacoes();
                break;
        }
    }

    private void listarClienteSaldoNegativo() {
        StringBuilder relatorio = new StringBuilder("########## Clientes com saldo negativo ##########\n");
        for (Pessoa p : pessoas) {
            if (String.valueOf(p.getContaCorrente().getNumeroConta()).startsWith("1")) {
                ContaCorrentePF ccpf = (ContaCorrentePF) p.getContaCorrente();
                if (ccpf.getChequeEspecial() != ccpf.getChequeEspecialAtual()) {
                    relatorio.append(p + "\n");
                }
            }
        }
        relatorio.append("#################################################");
        System.out.println(relatorio.toString());

    }

    private void listarClienteComSaldoMaiorQue() {
        double valor = Util.lerDouble("Informe o valor: ");
        StringBuilder relatorio = new StringBuilder("########## Clientes com saldo maior que " + valor + " ##########\n");
        for (Pessoa p : pessoas) {
            if (p.getContaCorrente().getSaldo() >= valor) {
                relatorio.append(p + "\n");
            }
        }
        relatorio.append("#################################################");
        System.out.println(relatorio.toString());
    }

    private void listarContasPF() {
        StringBuilder relatorio = new StringBuilder("########## Clientes com contas PF ##########\n");
        for (Pessoa p : pessoas) {
            if (String.valueOf(p.getContaCorrente().getNumeroConta()).startsWith("1")) {
                relatorio.append(p + "\n");
            }
        }
        relatorio.append("#################################################");
        System.out.println(relatorio.toString());
    }

    private void listarContasPJ() {
        StringBuilder relatorio = new StringBuilder("########## Clientes com contas PJ ##########\n");
        for (Pessoa p : pessoas) {
            if (String.valueOf(p.getContaCorrente().getNumeroConta()).startsWith("2")) {
                relatorio.append(p + "\n");
            }
        }
        relatorio.append("#################################################");
        System.out.println(relatorio.toString());
    }

    private void listarOperacoes() throws BancoException {
        int conta = Util.lerInteiro("Informe o número da conta: ");
        validarExistencia(conta);
        Pessoa pessoa = obterContaCorrente(conta);
        StringBuilder relatorio = new StringBuilder("########## Lista das Operações ##########\n");
        relatorio.append(pessoa + "\n");
        for (Operacao o : pessoa.getContaCorrente().getOperacoes()) {
            relatorio.append(o.toString() + "\n");
        }
        relatorio.append("#################################################");
        System.out.println(relatorio.toString());
    }

    private void validarConta(int numeroConta) throws BancoException {
        if (obterContaCorrente(numeroConta) != null) {
            throw new BancoException("Conta ja cadastrada");
        }
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
}
