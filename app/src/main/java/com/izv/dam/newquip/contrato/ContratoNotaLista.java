package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.Nota;

public interface ContratoNotaLista {

    interface InterfaceModelo {
        void close();

        Nota getNota(long id);

        long saveNota(Nota n);

        interface OnDataLoadListener {
            void setNota(Nota n);
        }
    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveNota(Nota n);

        void onAddTarea(Nota n);

    }

    interface InterfaceVista {

        void mostrarNota(Nota n);

    }

}