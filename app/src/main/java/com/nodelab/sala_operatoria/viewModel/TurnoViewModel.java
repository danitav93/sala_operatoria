package com.nodelab.sala_operatoria.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.nodelab.sala_operatoria.CalendarAdapter;
import com.nodelab.sala_operatoria.database.entity.Turno;
import com.nodelab.sala_operatoria.repository.TurnoRepository;

import java.util.List;

public class TurnoViewModel extends AndroidViewModel {

    private TurnoRepository mRepository;



    public TurnoViewModel(@NonNull Application application) {
        super(application);
        mRepository= new TurnoRepository(application);
    }


    public List<Turno> getAll() {
        return mRepository.getAll();
    }

    public void insert(Turno turno) { mRepository.insert(turno); }

    public void update(Turno turno) { mRepository.insert(turno); }

    public void upsert(Turno turno) {
        mRepository.insert(turno);
    }

    public Turno getLastInsertedTurno() {
      return   mRepository.getLastInsertedTurno();
    }

    public void upsertAndUpdateTurniAdapter(Turno turno, CalendarAdapter adapter) {
        mRepository.insertAndUpdateAdapter(turno,adapter);
    }


    public Turno findById(Long id) {
      return  mRepository.findById(id);
    }

    public Turno getTurnoByData(String data) {
        return mRepository.findByData(data);
    }

    public List<Turno> getTurniOverOggi(int uid) {
        return mRepository.findOverOggi(uid);
    }
}
