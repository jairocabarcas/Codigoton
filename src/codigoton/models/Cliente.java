/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigoton.models;

import java.util.ArrayList;

/**
 *
 * @author Jairo
 */
public class Cliente implements Comparable<Cliente>{
    
    private int id;
    private String code;
    private int msle;
    private int type;
    private String location;
    private String company;
    private int encrypt;
    ArrayList<Cuenta> cuentas;
    private double totalBalance;

    public Cliente(int id, String code, int msle, int type, String location, String company, int encrypt) {
        this.id = id;
        this.code = code;
        this.msle = msle;
        this.type = type;
        this.location = location;
        this.company = company;
        this.encrypt = encrypt;
        cuentas= new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public int getMsle() {
        return msle;
    }

    public int getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getCompany() {
        return company;
    }

    public int getEncrypt() {
        return encrypt;
    }

    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
    public void calcularBalanceTotal(){
        totalBalance=0;
        if(cuentas.size()>0){
            for(int i=0;i<cuentas.size();i++){
                totalBalance=totalBalance+cuentas.get(i).getBalance();
            }
        }
    }

    @Override
    public int compareTo(Cliente c) {
        if(c.getTotalBalance()>totalBalance){
            return 1;
        }else
            if (c.getTotalBalance()<totalBalance){
                return -1;
            }else{
                return 0;
            }
    }
    
}
