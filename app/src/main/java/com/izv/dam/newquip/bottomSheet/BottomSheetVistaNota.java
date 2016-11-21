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

public class BottomSheetVistaNota extends BottomSheetDialogFragment {

    public static BottomSheetVistaNota newInstance() {
        return new BottomSheetVistaNota();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bs_vista_nota, container, false);
        return v;
    }
}
