package br.edu.duques.util;

import br.edu.duques.excessao.BancoException;
import br.edu.duques.modelo.Operacao;
import br.edu.duques.modelo.Pessoa;
import br.edu.duques.modelo.PessoaFisica;
import br.edu.duques.modelo.PessoaJuridica;
import br.edu.duques.modelo.TipoOperacaoEnum;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

public class ArquivoUtil {

    private String nomeArq;
    private Scanner entrada;
    private Formatter saida;

    public ArquivoUtil(String nomeArq) throws BancoException {
        this.nomeArq = nomeArq;

        try {
            entrada = new Scanner(new File(nomeArq));
        } catch (FileNotFoundException ex) {
            throw new BancoException("Erro: abertura do arquivo");
        }
    }

    public List<Pessoa> lerArquivo() throws BancoException {
        List<Pessoa> pessoas = new ArrayList<>();
        String linha;
        String[] campos;

        Pessoa pessoaAtual = null;
        while (entrada.hasNext()) {
            linha = entrada.nextLine();
            campos = linha.split(";");
            if (campos[0].startsWith("1")) {                
                pessoaAtual = Util.criarPessoa(campos[1], campos[2], Integer.parseInt(campos[0]), 0, Double.parseDouble(campos[4]));
                pessoas.add(pessoaAtual);
            } else if (campos[0].startsWith("2")) {
                pessoaAtual = Util.criarPessoa(campos[1], campos[2], Integer.parseInt(campos[0]), 0);
                pessoas.add(pessoaAtual);
            } else if (campos[0].startsWith("3")) {
                Date dataOperacao = Util.formataData(campos[2]);
                double valor = Double.parseDouble(campos[3]);
                TipoOperacaoEnum tipoOperacao = TipoOperacaoEnum.valueOf(campos[1]);                    
                pessoaAtual.getContaCorrente().adicionarOperacao(valor, tipoOperacao, dataOperacao);
            }
        }
        return pessoas;
    }

    private void abrirGravacao() throws BancoException {

        try {
            saida = new Formatter(nomeArq);
        } catch (FileNotFoundException ex) {
            throw new BancoException("Erro: arquivo não encontrado");
        }
    }

    public void gravarPessoas(List<Pessoa> pessoas) throws BancoException {
        if (saida == null) {
            abrirGravacao();
        }
        for (Pessoa p : pessoas) {
            if (String.valueOf(p.getContaCorrente().getNumeroConta()).startsWith("1")) {
                PessoaFisica pessoa = (PessoaFisica) p;
                saida.format("%s;%s;%s;%s;%s\n", pessoa.getContaCorrente().getNumeroConta(), pessoa.getNome(), pessoa.getCpf(), pessoa.getContaCorrente().getSaldo(), pessoa.getContaCorrente().getChequeEspecial());
            } else if (String.valueOf(p.getContaCorrente().getNumeroConta()).startsWith("2")) {
                PessoaJuridica pessoa = (PessoaJuridica) p;
                saida.format("%s;%s;%s;%s\n", pessoa.getContaCorrente().getNumeroConta(), pessoa.getNome(), pessoa.getCnpj(), pessoa.getContaCorrente().getSaldo());
            }
            for (Operacao operacao: p.getContaCorrente().getOperacoes()){                
                saida.format("3;%s;%s;%s;%s\n", operacao.getTipo(), Util.formataData(operacao.getData()), operacao.getValor(), p.getContaCorrente().getNumeroConta());                
            }
        }
        fecharArquivo();
    }

    private void fecharArquivo() {

        if (saida != null) {
            saida.close();
        }
        if (entrada != null) {
            entrada.close();
        }
    }
}
