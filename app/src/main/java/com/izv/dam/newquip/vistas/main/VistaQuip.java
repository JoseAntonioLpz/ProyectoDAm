package com.izv.dam.newquip.vistas.main;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.dialogo.DialogoBorrar;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.vistas.notas.VistaNota;
import com.izv.dam.newquip.vistas.notas.VistaNotaLista;

import java.util.ArrayList;


public class VistaQuip extends AppCompatActivity implements ContratoMain.InterfaceVista , OnBorrarDialogListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recycler;
    private AdaptadorNota adaptador;
    private PresentadorQuip presentador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        presentador = new PresentadorQuip(this);

        recycler = (RecyclerView) findViewById(R.id.rvNotas);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new AdaptadorNota(null);
        recycler.setAdapter(adaptador);

        adaptador.setOnItemClickListener(new AdaptadorNota.OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                presentador.onEditNota(i);
            }
        });

        adaptador.setOnCheckBoxClickListener(new AdaptadorNota.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(int i, boolean value) {
                presentador.onUpdateRealizado(i, value);
            }
        });

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                presentador.onShowBorrarNota(swipedPosition);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recycler);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_nota);
        fab.setColorNormal(getResources().getColor(R.color.colorAccent));
        fab.setColorPressed(getResources().getColor(R.color.colorAccent));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.onAddNota(Nota.NOTA_SIMPLE);
                /*BottomSheetDialogFragment bsdFragment =
                        BottomSheetVistaQuip.newInstance();
                Toast.makeText(VistaQuip.this, "Visualizar notas", Toast.LENGTH_SHORT).show();
                bsdFragment.show(VistaQuip.this.getSupportFragmentManager(), "BSDialog");*/
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.add_lista);
        fab2.setColorNormal(getResources().getColor(R.color.colorAccent));
        fab2.setColorPressed(getResources().getColor(R.color.colorAccent));
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentador.onAddNota(Nota.NOTA_LISTA);
                Toast.makeText(VistaQuip.this, "add_lista", Toast.LENGTH_SHORT).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onPause() {
        presentador.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }



    @Override
    public void mostrarAgregarNota(int tipo) {
        Toast.makeText(VistaQuip.this, "add", Toast.LENGTH_SHORT).show();
        Intent i = null;
        if (tipo == Nota.NOTA_SIMPLE){
            i = new Intent(this, VistaNota.class);
        }else if(tipo == Nota.NOTA_LISTA){
            i = new Intent(this, VistaNotaLista.class);
        }
        startActivity(i);
    }

    @Override
    public void mostrarDatos(Cursor c1, Cursor c2) {
        adaptador.changeCursor(c1);
        adaptador.setCursorTareas(c2);
    }

    @Override
    public void mostrarEditarNota(Nota n) {
        Toast.makeText(VistaQuip.this, "edit", Toast.LENGTH_SHORT).show();
        Intent i = null;
        if (n.getTipo() == Nota.NOTA_SIMPLE){
            i = new Intent(this, VistaNota.class);
        }else if(n.getTipo() == Nota.NOTA_LISTA){
            i = new Intent(this, VistaNotaLista.class);
        }
        Bundle b = new Bundle();
        n.setTareas(adaptador.getCursorTareas());
        b.putParcelable("nota", n);
        i.putExtras(b);
        if(n.getTipo() == Nota.NOTA_LISTA)
            i.putParcelableArrayListExtra("tareas", (ArrayList<? extends Parcelable>) n.getTareas());
        startActivity(i);
    }

    @Override
    public void mostrarConfirmarBorrarNota(Nota n) {
        DialogoBorrar fragmentBorrar = DialogoBorrar.newInstance(n);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");

    }

    @Override
    public void onBorrarPossitiveButtonClick(Nota n) {
        presentador.onDeleteNota(n);
    }

    @Override
    public void onBorrarNegativeButtonClick() {
        presentador.onCancelDeleteNota();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_all) {
            Toast.makeText(VistaQuip.this, "Todas", Toast.LENGTH_SHORT).show();
            Cursor c = presentador.onChangeCursor(-1);
            adaptador.changeCursor(c);
        }else if (id == R.id.nav_notas) {
            Toast.makeText(VistaQuip.this, "Visualizar notas", Toast.LENGTH_SHORT).show();
            Cursor c = presentador.onChangeCursor(Nota.NOTA_SIMPLE);
            adaptador.changeCursor(c);
        } else if (id == R.id.nav_listas) {
            Toast.makeText(VistaQuip.this, "Visualizar listas", Toast.LENGTH_SHORT).show();
            Cursor c = presentador.onChangeCursor(Nota.NOTA_LISTA);
            adaptador.changeCursor(c);
        } else if (id == R.id.nav_recordatorios) {
            Toast.makeText(VistaQuip.this, "Recordatorios", Toast.LENGTH_SHORT).show();
            Cursor c = presentador.onChangeCursor(-1);
            adaptador.changeCursor(c);
        } else if (id == R.id.nav_completadas) {
            Toast.makeText(VistaQuip.this, "Completadas", Toast.LENGTH_SHORT).show();
            Cursor c = presentador.onChangeCursor(3);
            adaptador.changeCursor(c);
        } else if (id == R.id.nav_no_completadas) {
            Toast.makeText(VistaQuip.this, "No completadas", Toast.LENGTH_SHORT).show();
            Cursor c = presentador.onChangeCursor(4);
            adaptador.changeCursor(c);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}