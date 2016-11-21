package com.izv.dam.newquip.contrato;

import android.database.Cursor;

import com.izv.dam.newquip.pojo.Nota;

public interface ContratoMain {

    interface InterfaceModelo {

        void close();

        long deleteNota(int position);

        long deleteNota(Nota n);

        Nota getNota(int position);

        void setCursor(Cursor c);

        interface OnDataLoadListener {
            void setCursor(Cursor c1, Cursor c2);
        }

        void loadData(OnDataLoadListener listener);

        void updateNota(Nota n);

        Cursor changeCursor(int tipo);
    }

    interface InterfacePresentador {

        void onAddNota(int tipo);

        void onDeleteNota(int position);

        void onDeleteNota(Nota n);

        void onCancelDeleteNota();

        void onEditNota(int position);

        void onEditNota(Nota n);

        void onPause();

        void onResume();

        void onShowBorrarNota(int position);

        Cursor onChangeCursor(int tipo);

        void onUpdateRealizado(int i, boolean isChecked);

        void onSetCursor(Cursor c);
    }

    interface InterfaceVista {

        void mostrarAgregarNota(int tipo);

        void mostrarDatos(Cursor c1, Cursor c2);

        void mostrarEditarNota(Nota n);

        void mostrarConfirmarBorrarNota(Nota n);

    }

}