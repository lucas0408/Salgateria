/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.model;

/**
 *
 * @author lucas
 */
public class Item_pedido {
    private Pedido pedido;
    private Item item;
    private int quantidade;

    public Item_pedido() {
    }

    public Item_pedido(Pedido pedido, Item item, int quantidade) {
        this.pedido = pedido;
        this.item = item;
        this.quantidade = quantidade;
    }
    
    
    
    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
}
