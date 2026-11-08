package com.pepito.reservas.destino;

import java.util.ArrayList;
import java.util.List;

public class Destino extends LugarGeografico {

    ArrayList<LugarGeografico> destinos;

    public Destino(TipoGeografico tipo, String nombre) {
        super(tipo, nombre);
        this.destinos = new ArrayList<>(List.of());
    }

    public Destino(TipoGeografico tipo, String nombre, ArrayList<LugarGeografico> destinos) {
        super(tipo, nombre);
        this.destinos = new ArrayList<>(List.copyOf(destinos));
    }

    @Override
    public Boolean contiene(LugarGeografico otro) {
        return this == otro || this.destinos.stream().anyMatch(lugar-> lugar.contiene(otro));
    }

    @Override
    public Boolean puedeContener(LugarGeografico otro) {
        return this.tipo().mayorQue(otro.tipo());
    }

    public void agregarDestino(LugarGeografico lugar){

        if (contiene(lugar)){
            throw new DestinoException.DuplicidadDestino(lugar);
        }
        if(!puedeContener(lugar)){
            throw new DestinoException.IncompatibilidadDestino(this,lugar);
        }
            this.destinos.add(lugar);
    }

    @Override
    public void removerDestino(LugarGeografico lugar, Boolean override) {
        if (this.destinos.remove(lugar)) {
            return;
        }
        if (lugar == this || !this.contiene(lugar)) {
            throw new DestinoException.InexistenciaDestino(lugar);
        }
        if (!override) {
            throw new DestinoException.ViolacionRango(lugar);
        }
        this.destinos.stream()
                .filter(destino -> destino.contiene(lugar))
                .findFirst()
                .orElseThrow(() -> new DestinoException.InexistenciaDestino(lugar))
                .removerDestino(lugar, true);
    }
}