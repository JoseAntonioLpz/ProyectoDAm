package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.Nota;

public interface ContratoNotaLista {

    interface InterfaceModelo {
        void saveNota(Nota n);

        void removeTarea(long id);
    }

    interface InterfacePresentador {
        void onResume(Nota n);

        void onPause(Nota n);

        void onRemoveTarea(long id);
    }

    interface InterfaceVista {
        void showNota(Nota n);
    }

}