package com.izv.dam.newquip.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Tarea;

import java.util.List;

/**
 * Created by Pablo on 17/10/2016.
 */

public class AdaptadorTarea extends RecyclerView.Adapter<AdaptadorTarea.ViewHolderTarea>{
    private List<Tarea> list;
    //private OnItemClickListener listener;
    private OnCaretUpdateListener caretListener;
    private OnCheckBoxClickListener checkBoxListener;

    public interface OnCaretUpdateListener {
        void onCaretUpdate(int i, String text);
    }

    public void setOnCaretUpdateListener(OnCaretUpdateListener listener){
        this.caretListener = listener;
    }

    public interface OnCheckBoxClickListener {
        void onCheckBoxClick(int i, boolean value);
    }

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener listener){
        this.checkBoxListener = listener;
    }

    /*public interface OnItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }*/



    public static class ViewHolderTarea extends RecyclerView.ViewHolder {
        public CheckBox cbRealizado;
        public EditText etTarea;

        public ViewHolderTarea(View itemView) {
            super(itemView);
            cbRealizado = (CheckBox) itemView.findViewById(R.id.cbRealizado);
            etTarea = (EditText) itemView.findViewById(R.id.etTarea);
        }

        public void bind(final int i, final OnCheckBoxClickListener checkBoxClickListener,final OnCaretUpdateListener caretListener){
            cbRealizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    checkBoxClickListener.onCheckBoxClick(i, b);
                }
            });
            etTarea.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    caretListener.onCaretUpdate(i, etTarea.getText().toString());
                }
            });
        }

        /*public void bind(final int i, final OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(i);
                }
            });
        }*/
    }

    public AdaptadorTarea(List<Tarea> list){
        this.list = list;
    }


    @Override
    public ViewHolderTarea onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_tarea, parent, false);
        return new ViewHolderTarea(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderTarea holder, int position) {
        Tarea tarea = list.get(position);
        holder.cbRealizado.setChecked(tarea.isRealizado());
        holder.etTarea.setText(tarea.getTarea());
        holder.bind(position, checkBoxListener, caretListener);
    }

    @Override
    public int getItemCount() {
        if(list.isEmpty()){
            return 0;
        }
        return list.size();
    }

    public void changeList(List<Tarea> list) {
        if (list != null) {
            this.list = list;
            notifyDataSetChanged();
        }
    }
}
