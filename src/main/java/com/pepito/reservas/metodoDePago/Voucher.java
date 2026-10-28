package com.pepito.reservas.metodoDePago;

public class Voucher  extends Comprobante{
    private int nroId ;

    public Voucher (int nroId){
        super();
        this.nroId=nroId;
    }
    public int getNroId(){
        return nroId;
    }



}
