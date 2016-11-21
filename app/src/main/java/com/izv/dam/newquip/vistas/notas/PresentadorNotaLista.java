package com.izv.dam.newquip.vistas.notas;

import android.content.Context;

import com.izv.dam.newquip.contrato.ContratoNotaLista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by dam on 25/10/2016.
 */

public class PresentadorNotaLista implements ContratoNotaLista.InterfacePresentador {

    private ContratoNotaLista.InterfaceModelo modelo;
    private ContratoNotaLista.InterfaceVista vista;
    private ContratoNotaLista.InterfaceModelo.OnDataLoadListener oyente;

    public PresentadorNotaLista(ContratoNotaLista.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new ModeloNotaLista((Context) vista);
        oyente = new ContratoNotaLista.InterfaceModelo.OnDataLoadListener() {
            @Override
            public void setNota(Nota n) {
                PresentadorNotaLista.this.vista.mostrarNota(n);
            }
        };
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public long onSaveNota(Nota n) {
        return modelo.saveNota(n);
    }

    @Override
    public void onAddTarea(Nota n) {
        this.modelo.saveNota(n);
        this.vista.mostrarNota(n);
    }
}
