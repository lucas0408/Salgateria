/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

import java.util.Objects;

/**
 *
 * @author lucas
 */
public class Insumo {
    private int id;
    private String nome;
    private String tipo;
    private float estoque_minimo;
    
    @Override
    public String toString() {
        return getNome();
    }

    public Insumo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Insumo(String nome, String tipo, float estoque_minimo) {
        this.nome = nome;
        this.tipo = tipo;
        this.estoque_minimo = estoque_minimo;
    }
    
    
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getEstoque_minimo() {
        return estoque_minimo;
    }

    public void setEstoque_minimo(float estoque_minimo) {
        this.estoque_minimo = estoque_minimo;
    }
    
    
    
}
