package com.izv.dam.newquip.vistas.main;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Nota;

public class ModeloQuip implements ContratoMain.InterfaceModelo {

    //TODO los cursores fallan cuando se filtra, lleva el control de los 2
    private ContentResolver cr;
    private Cursor cursor;

    public ModeloQuip(Context c) {
        cr = c.getContentResolver();
    }

    @Override
    public void close() {
        cursor.close();
    }

    @Override
    public long deleteNota(Nota n) {
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,n.getId());
        if (n.getTipo() == Nota.NOTA_LISTA) {
            String where = ContratoBaseDatos.TablaTareas.ID_NOTA + " =? ";
            String[] argumentos = new String[]{String.valueOf(n.getId())};
            cr.delete(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, where, argumentos);
        }
        return cr.delete(uri,"",null);
    }

    @Override
    public long deleteNota(int position) {
        cursor.moveToPosition(position);
        Nota n = Nota.getNota(cursor);
        return this.deleteNota(n);
    }

    @Override
    public Nota getNota(int position) {
        cursor.moveToPosition(position);
        Nota n = Nota.getNota(cursor);
        n.setId(cursor.getLong(0));
        Log.v("ModeloQuip", "getNota id: " + n.getId());
        return n;
    }

    @Override
    public void setCursor(Cursor c){
        this.cursor = c;
    }

    @Override
    public void loadData(OnDataLoadListener listener) {
        Log.v("ModeloQuip", "loadData");
        cursor = cr.query(
                ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                null,
                null,
                null,
                "2, 1"// ordena por el segundo campo, y en caso de empate por el primero
        );

        Cursor cursor2 = cr.query(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA,
                null,
                null,
                null,
                null
        );
        listener.setCursor(cursor, cursor2);
    }

    @Override
    public void updateNota(Nota n) {
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,n.getId());
        cr.update(
                uri,
                n.getContentValues(true),
                null,
                null
        );
        Log.v("ModeloQuip", "updateNota() nota: " + n);
    }

    @Override
    public Cursor changeCursor(int tipo){
        Log.v("ModeloQuip", "changeCursor");
        Uri uri = ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA;
        Cursor c = cr.query(uri,null,null,null,null);
        if(tipo == Nota.NOTA_SIMPLE){
            c = cr.query(uri,null, ContratoBaseDatos.TablaNota.TIPO + " = " + Nota.NOTA_SIMPLE, null ,ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT);
        }else if(tipo == Nota.NOTA_LISTA){
            c =  cr.query(uri,null, ContratoBaseDatos.TablaNota.TIPO + " = " + Nota.NOTA_LISTA, null ,ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT);
        }else if(tipo == 3){
            c = cr.query(uri,null,ContratoBaseDatos.TablaNota.REALIZADO + " = " + 1,null,ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT);
        }else if(tipo == 4){
            c = cr.query(uri,null,ContratoBaseDatos.TablaNota.REALIZADO + " = " + 0,null,ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT);
        }else if(tipo == -1){
            c = cr.query(uri,null,null,null,null);
        }
        cursor = c;
        return c;
    }
}