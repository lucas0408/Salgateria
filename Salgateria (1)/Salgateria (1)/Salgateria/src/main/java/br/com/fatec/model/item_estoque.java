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
public class item_estoque {
    private Insumo insumo;
    private Item item;
    private float quantidade;

    public item_estoque(Insumo insumo, Item item, float quantidade) {
        this.insumo = insumo;
        this.item = item;
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.insumo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final item_estoque other = (item_estoque) obj;
        return Objects.equals(this.insumo, other.insumo);
    }
    
    

    public item_estoque() {
    }
    
    public Insumo getInsumo() {
        return insumo;
    }

    public void setInsumo(Insumo insumo) {
        this.insumo = insumo;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
