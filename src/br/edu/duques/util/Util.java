package br.edu.duques.util;

import br.edu.duques.excessao.BancoException;
import br.edu.duques.modelo.ContaCorrentePF;
import br.edu.duques.modelo.ContaCorrentePJ;
import br.edu.duques.modelo.Pessoa;
import br.edu.duques.modelo.PessoaFisica;
import br.edu.duques.modelo.PessoaJuridica;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Util {

    private static String FORMATO_DATA = "dd/MM/yyyy HH:mm";
    private static SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA);

    public static String formataData(Date data) {

        return sdf.format(data);
    }

    public static Date formataData(String data) throws BancoException {

        try {
            return sdf.parse(data);
        } catch (ParseException ex) {
            throw new BancoException("Data inválida");
        }
    }

    public static String lerString(String msg) {
        Scanner entrada = new Scanner(System.in);
        System.out.println(msg);

        String nome = entrada.nextLine();
        return nome;
    }

    public static int lerInteiro(String msg) {
        Scanner entrada = new Scanner(System.in);
        int num;
        System.out.println(msg);
        num = entrada.nextInt();
        return num;
    }

    public static double lerDouble(String msg) {
        Scanner entrada = new Scanner(System.in);
        double num;
        System.out.println(msg);

        num = entrada.nextDouble();
        return num;
    }

    public static Pessoa criarPessoa(String nome, String documento, int numeroDaConta, double saldo, double chequeEspecial) throws BancoException {
        if (nome == null || nome.split(" ").length == 1) {
            throw new BancoException("Nome inválido");
        } else if (String.valueOf(numeroDaConta).startsWith("1")) {
            return criarPessoaFisica(nome, documento, numeroDaConta, saldo, chequeEspecial);
        } else {
            throw new BancoException("Número da conta inválido!");
        }
    }

    public static Pessoa criarPessoa(String nome, String documento, int conta, double saldo) throws BancoException {
        if (nome == null || nome.split(" ").length == 1) {
            throw new BancoException("Nome invalido");
        } else if (String.valueOf(conta).startsWith("2")) {
            return criarPessoaJuridica(nome, documento, conta, saldo);
        } else {
            throw new BancoException("Número da conta inválido!");
        }
    }

    private static PessoaFisica criarPessoaFisica(String nome, String documento, int numeroDaConta, double saldo, double chequeEspecial) {

        PessoaFisica pessoaFisica = null;
        if (saldo > 0) {
            pessoaFisica = new PessoaFisica(nome, documento, new ContaCorrentePF(numeroDaConta, saldo, chequeEspecial));
        } else {
            pessoaFisica = new PessoaFisica(nome, documento, new ContaCorrentePF(numeroDaConta, chequeEspecial));
        }
        return pessoaFisica;
    }

    private static PessoaJuridica criarPessoaJuridica(String nome, String documento, int numeroDaConta, double saldo) {
        PessoaJuridica pessoaJuridica = null;
        if (saldo > 0) {
            pessoaJuridica = new PessoaJuridica(nome, documento, new ContaCorrentePJ(numeroDaConta, saldo));
        } else {
            pessoaJuridica = new PessoaJuridica(nome, documento, new ContaCorrentePJ(numeroDaConta));
        }
        return pessoaJuridica;
    }
}
