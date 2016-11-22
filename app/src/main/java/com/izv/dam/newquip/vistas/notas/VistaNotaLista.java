package com.izv.dam.newquip.vistas.notas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorTarea;
import com.izv.dam.newquip.contrato.ContratoNotaLista;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.pojo.Tarea;

import java.util.Date;
import java.util.List;

/**
 * Created by dam on 25/10/2016.
 */

public class VistaNotaLista extends AppCompatActivity implements ContratoNotaLista.InterfaceVista {

    private PresentadorNotaLista presentador;
    private Nota nota = new Nota();
    private EditText etTitulo;
    private RecyclerView rvTareas;
    private AdaptadorTarea adaptador;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_lista);

        presentador = new PresentadorNotaLista(this);
        etTitulo = (EditText) findViewById(R.id.etTitulo);

        if (savedInstanceState != null) {
            nota = savedInstanceState.getParcelable("nota");
            nota.setTareas((List) savedInstanceState.getParcelableArrayList("tareas"));
        } else {
            Bundle b = getIntent().getExtras();
            if(b != null ) {
                nota = b.getParcelable("nota");
                nota.setTareas((List) b.getParcelableArrayList("tareas"));
            }else {
                nota.setFecha(new Date(System.currentTimeMillis()));
            }
        }
        System.out.println("Received nota: \n\t" + nota);
        rvTareas = (RecyclerView) findViewById(R.id.rvTareas);
        rvTareas.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorTarea(nota.getTareas());
        adaptador.setOnCheckBoxClickListener(new AdaptadorTarea.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(int i, boolean value) {
                nota.getTareas().get(i).setRealizado(value);
            }
        });
        adaptador.setOnCaretUpdateListener(new AdaptadorTarea.OnCaretUpdateListener() {
            @Override
            public void onCaretUpdate(int i, String text) {
                nota.getTareas().get(i).setTarea(text);
            }
        });
        rvTareas.setAdapter(adaptador);
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                presentador.onRemoveTarea(nota.getTareas().get(swipedPosition));
                nota.removeTarea(swipedPosition);
                adaptador.changeList(nota.getTareas());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTareas);

        mostrarNota(nota);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnFlotNotList);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nota.addTarea(new Tarea());
                presentador.onAddTarea(nota);
            }
        });
    }

    @Override
    public void mostrarNota(Nota n) {
        etTitulo.setText(n.getTitulo());
        adaptador.changeList(n.getTareas());
    }

    @Override
    protected void onPause() {
        saveNota();
        presentador.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("nota", nota);
    }

    private void saveNota() {
        nota.setTitulo(etTitulo.getText().toString());
        nota.setTipo(Nota.NOTA_LISTA);
        long r = presentador.onSaveNota(nota);
        if(r > 0 & nota.getId() == 0){
            nota.setId(r);
        }
        for (Tarea t :
                nota.getTareas()) {
            System.out.println(t);
        }
    }
}
