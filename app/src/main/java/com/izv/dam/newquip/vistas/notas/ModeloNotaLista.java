package com.izv.dam.newquip.vistas.notas;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoNotaLista;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.pojo.Tarea;

/**
 * Created by dam on 25/10/2016.
 */

public class ModeloNotaLista implements ContratoNotaLista.InterfaceModelo  {

    //private GestionNota gn = null;
    //private GestionTareas gt = null;
    private ContentResolver cr;

    public ModeloNotaLista(Context c){
        //gn = new GestionNota(c);
        //gt = new GestionTareas(c);
        cr = c.getContentResolver();
    }

    @Override
    public void close() {
        //gt.close();
        //gn.close();
    }

    @Override
    public Nota getNota(long id) {
        Log.v("ModeloNotaLista", "getNota id: " + id);
        /*Nota n = gn.get(id);
        n.setTareas(gt.getByNota(id));
        return n;*/
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, id);
        Cursor c = cr.query(
                uri,
                ContratoBaseDatos.TablaNota.PROJECTION_ALL,
                "",
                new String[]{},
                ContratoBaseDatos.TablaNota.SORT_ORDER_DEFAULT
        );
        Nota n = Nota.getNota(c);
        String where = ContratoBaseDatos.TablaTareas.ID_NOTA + " =? ";
        String[] argumentos = new String[]{String.valueOf(n.getId())};
        c = cr.query(
                ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA,
                ContratoBaseDatos.TablaTareas.PROJECTION_ALL,
                where,
                argumentos,
                ContratoBaseDatos.TablaTareas.SORT_ORDER_DEFAULT
        );
        Log.v("ModeloNotaLista", "getNota tareas: " + c.getCount());
        n.setTareas(c);
        return n;
    }


    @Override
    public long saveNota(Nota n) {
        Log.v("ModeloNotaLista", "saveNota id: " + n.getId());
        long r = 0;
        if(n.getId() == 0) {
            r = this.insertNota(n);
        } else {
            this.updateNota(n);
        }
        return r;
    }

    private long deleteNota(Nota n) {
        Log.v("ModeloNotaLista", "deleteNota id: " + n.getId());
        /*for (Tarea t: n.getTareas()){
            gt.delete(t);
        }
        return gn.delete(n);*/
        for (Tarea t: n.getTareas()){
            Uri u = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, t.getId());
            cr.delete(
                    u,
                    "",
                    new String[]{}
            );
        }
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, n.getId());
        return cr.delete(
                uri,
                "",
                new String[]{}
        );

    }

    private long insertNota(Nota n) {
        Log.v("ModeloNotaLista", "insertNota id: " + n.getId());
        /*if(n.getTitulo().trim().compareTo("")==0 && n.getTareas().size() == 0) {
            return 0;
        }
        for (Tarea t: n.getTareas()) {
            if(t.getId() == 0){
                t.setIdNota(n.getId());
                gt.insert(t);
            }else{
                gt.update(t);
            }
        }
        return gn.insert(n);*/
        if(n.getTitulo().trim().compareTo("") == 0) {
            this.deleteNota(n);
            for (Tarea t : n.getTareas()) {
                Uri u = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, t.getId());
                cr.delete(
                        u,
                        "",
                        new String[]{}
                );
            }
            return 0;
        }
        for (Tarea t: n.getTareas()){
            t.setId(ContentUris.parseId(cr.insert(
                    ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA,
                    t.getContentValues()
            )));
        }
        return ContentUris.parseId(cr.insert(
                ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA,
                n.getContentValues(false)
        ));
    }

    private long updateNota(Nota n) {
        Log.v("ModeloNotaLista", "updateNota id: " + n.getId());
        /*if(n.getTitulo().trim().compareTo("")==0) {
            this.deleteNota(n);
            gn.delete(n);
            return 0;
        }
        for (Tarea t : n.getTareas()){
            if(t.getId() == 0){
                t.setIdNota(n.getId());
                gt.insert(t);
            }else{
                gt.update(t);
            }
        }
        return gn.update(n);*/
        Uri uri = ContentUris.withAppendedId(ContratoBaseDatos.TablaNota.CONTENT_URI_NOTA, n.getId());
        if(n.getTitulo().trim().compareTo("") == 0) {
            this.deleteNota(n);
            for (Tarea t : n.getTareas()) {
                Uri u = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, t.getId());
                cr.delete(
                        u,
                        "",
                        new String[]{}
                );
            }
            return 0;
        }
        for (Tarea t : n.getTareas()){
            if(t.getId() == 0){
                t.setId(ContentUris.parseId(cr.insert(
                        ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA,
                        t.getContentValues()
                )));
            }else{
                Uri u = ContentUris.withAppendedId(ContratoBaseDatos.TablaTareas.CONTENT_URI_TAREA, t.getId());
                cr.update(
                        u,
                        t.getContentValues(),
                        "",
                        new String[]{}
                );
            }
        }
        return cr.update(
                uri,
                n.getContentValues(true),
                "",
                new String[]{}
        );
    }
}
