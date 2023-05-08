package br.edu.duques.modelo;

import br.edu.duques.util.Util;
import java.util.Date;

public class Operacao {
    
    private Date data;
    private Double valor;
    private TipoOperacaoEnum tipo;

    public Operacao(Date data, Double valor, TipoOperacaoEnum tipo) {
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Operacao{" + "data=" + Util.formataData(data) + ", valor=" + valor + ", tipo=" + tipo + '}';
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date Data) {
        this.data = Data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double Valor) {
        this.valor = Valor;
    }

    public TipoOperacaoEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoOperacaoEnum tipo) {
        this.tipo = tipo;
    }
}
