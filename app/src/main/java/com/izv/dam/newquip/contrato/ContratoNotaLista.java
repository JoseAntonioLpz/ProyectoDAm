package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.pojo.Tarea;

public interface ContratoNotaLista {

    interface InterfaceModelo {
        void close();

        Nota getNota(long id);

        long saveNota(Nota n);

        void removeTarea(Tarea t);

        interface OnDataLoadListener {
            void setNota(Nota n);
        }
    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveNota(Nota n);

        void onAddTarea(Nota n);

        void onRemoveTarea(Tarea t);
    }

    interface InterfaceVista {

        void mostrarNota(Nota n);

    }

}