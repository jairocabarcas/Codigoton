/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton.models;

/**
 *
 * @author Jairo
 */
public class Cuenta {
    
    private int id;
    private int client_id;
    private double balance; 

    public Cuenta(int id, int client_id, double balance) {
        this.id = id;
        this.client_id = client_id;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public int getClient_id() {
        return client_id;
    }

    public double getBalance() {
        return balance;
    }
    
    
    
}
