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

    public PresentadorNotaLista(ContratoNotaLista.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new ModeloNotaLista((Context) vista);
    }

    @Override
    public void onResume(Nota n) {
        this.vista.showNota(n);
    }

    @Override
    public void onPause(Nota n) {
        this.modelo.saveNota(n);
    }

    @Override
    public void onRemoveTarea(long id) {
        this.modelo.removeTarea(id);
    }
}
