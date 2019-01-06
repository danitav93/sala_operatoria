package com.nodelab.sala_operatoria;





import android.app.Dialog;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nodelab.sala_operatoria.database.entity.Turno;
import com.nodelab.sala_operatoria.viewModel.TurnoViewModel;


public class SaveTurnoDialog extends DialogFragment {



    public static final String TAG = "example_dialog";

    private static Turno turno;

    private static TurnoViewModel turnoViewModel;

    private  MainActivity mainActivity;

    public TextView data;
    private TextView dottoreGiorno;
    private TextView numeroDottoreGiorno;
    private TextView dottoreNotte;
    private TextView numeroDottoreNotte;
    private ImageView plus;
    private View constraintLayout3;
    private View constraintLayout2;

   // private Toolbar toolbar;



    public static SaveTurnoDialog display(FragmentManager fragmentManager, Turno turnoInput,  TurnoViewModel turnoViewModelInput,MainActivity mainActivity) {

        turno=turnoInput;

        SaveTurnoDialog exampleDialog = new SaveTurnoDialog();

        exampleDialog.show(fragmentManager, TAG);

        turnoViewModel=turnoViewModelInput;

        exampleDialog.mainActivity=mainActivity;

        return exampleDialog;



    }



    @Override
    public void onCreate(Bundle savedInstanceState) {

        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);

        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);



        View view = inflater.inflate(R.layout.dialog_save_turno, container, false);

        data=view.findViewById(R.id.data);
        dottoreGiorno=view.findViewById(R.id.edit_nome_giorno);
        numeroDottoreGiorno=view.findViewById(R.id.edit_numero_giorno);
        dottoreNotte=view.findViewById(R.id.edit_nome_notte);
        numeroDottoreNotte=view.findViewById(R.id.edit_numero_notte);
        Button salva=view.findViewById(R.id.save_button);
        salva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turno.setData(data.getText().toString());
                turno.setNomeDottoreGiorno(dottoreGiorno.getText().toString());
                turno.setNumeroDottoreGiorno(numeroDottoreGiorno.getText().toString());
                turno.setNomeDottoreNotte(dottoreNotte.getText().toString());
                turno.setNumeroDottoreNotte(numeroDottoreNotte.getText().toString());
                turnoViewModel.upsertAndUpdateTurniAdapter(turno,mainActivity.getAdapter());
                mainActivity.getAdapter().notifyDataSetChanged();
                if (mainActivity.getTurnoDiOggi()==null) {
                    mainActivity.setTurnoDiOggi(turno);
                }
                dismiss();
            }
        });
        data.setText(turno.getData());
        dottoreGiorno.setText(turno.getNomeDottoreGiorno());
        numeroDottoreGiorno.setText(turno.getNumeroDottoreGiorno());
        numeroDottoreNotte.setText(turno.getNumeroDottoreNotte());
        dottoreNotte.setText(turno.getNomeDottoreNotte());
        //toolbar = view.findViewById(R.id.toolbar);



        return view;

    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

       /* toolbar.setNavigationOnClickListener(v -> dismiss());

        toolbar.setTitle("Some Title");

        toolbar.inflateMenu(R.menu.example_dialog);

        toolbar.setOnMenuItemClickListener(item -> {

            dismiss();

            return true;

        });*/

    }

    @Override
    public void onStart() {

        super.onStart();

        Dialog dialog = getDialog();

        if (dialog != null) {

            int width = ViewGroup.LayoutParams.MATCH_PARENT;

            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            dialog.getWindow().setLayout(width, height);


            dialog.getWindow().setWindowAnimations(R.style.AppTheme_Slide);

        }

    }

}