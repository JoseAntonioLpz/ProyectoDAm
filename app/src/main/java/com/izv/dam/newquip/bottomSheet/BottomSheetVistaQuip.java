package com.izv.dam.newquip.bottomSheet;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izv.dam.newquip.R;

/**
 * Created by JoseAntonio on 13/10/2016.
 */

public class BottomSheetVistaQuip extends BottomSheetDialogFragment {

    public static BottomSheetVistaQuip newInstance() {
        return new BottomSheetVistaQuip();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bs_vista_quip, container, false);
        return v;
    }
}
