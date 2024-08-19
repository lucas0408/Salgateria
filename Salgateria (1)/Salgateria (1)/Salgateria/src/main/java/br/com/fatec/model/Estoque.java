/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.time.LocalDate;
import java.sql.Date;
import java.text.DecimalFormat;


/**
 *
 * @author lucas
 */
public class Estoque {
    private int id;
    private Insumo insumo;
    private Date data_validade;
    private float quantidade;
    private float valor;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Date getData_validade() {
        return data_validade;
    }

    public void setData_validade(Date data_validade) {
        this.data_validade = data_validade;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Estoque(Insumo insumo, Date data_validade, float quantidade, float valor) {
        this.insumo = insumo;
        this.data_validade = data_validade;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Estoque() {
    }
    
        
    DecimalFormat df = new DecimalFormat("0.0");
    
    DecimalFormat pf = new DecimalFormat("0.00");
    
    DecimalFormat tf = new DecimalFormat("0.000");
    
    public void adicionar(float quantidade, float valor){
        if(df.format(valor).equals(df.format(getValor()))){
            valor = valor + (getQuantidade() * valor)/quantidade;
        }else{
             valor = valor + getValor();
        }
        this.quantidade = quantidade + getQuantidade();
        this.valor = Float.parseFloat(pf.format(valor).replace(",", "."));
    }
    
    public boolean retirar(float quantidade, float valor){
        System.out.println("\n" + valor + "\n" + getValor());
        if(quantidade < getQuantidade() || valor < getValor()){
            return true;
        }else{
            if(df.format(valor).equals(df.format(getValor()))){
                valor = valor - ((getQuantidade() * valor)/quantidade);
            }else{
                valor = valor - getValor();
            }
            this.valor = Float.parseFloat(pf.format(valor).replace(",", "."));
            this.quantidade = Float.parseFloat(tf.format(quantidade - getQuantidade()).replace(",", "."));
            return false;
        }
    }
}
