package com.nodelab.sala_operatoria.repository;

import android.app.Application;

import android.os.AsyncTask;


import com.nodelab.sala_operatoria.CalendarAdapter;
import com.nodelab.sala_operatoria.Dao.TurnoDao;
import com.nodelab.sala_operatoria.database.entity.Database;
import com.nodelab.sala_operatoria.database.entity.Turno;

import java.util.List;

public class TurnoRepository {

    private TurnoDao mTurnoDao;


    public TurnoRepository(Application application) {
        Database db = Database.getDatabase(application);
        mTurnoDao = db.turnoDao();
    }

    public Turno findByData(String data) {
        return mTurnoDao.findByData(data);
    }

    public List<Turno> getAll() {
        return mTurnoDao.getAll();
    }


    public void insert (Turno turno) {

        new insertAsyncTask(mTurnoDao,null).execute(turno);
    }

    public void upsert (Turno turno) {

        new insertAsyncTask(mTurnoDao,null).execute(turno);
    }

    public void insertAndUpdateAdapter(Turno turno, CalendarAdapter adapter) {
        new insertAsyncTask(mTurnoDao, adapter).execute(turno);
    }
    public Turno getLastInsertedTurno(){
        return mTurnoDao.getLastInsertedTurno();
    }

    public Turno findById(Long id) {
        return mTurnoDao.findById(id);
    }

    public List<Turno> findOverOggi(int uid) {
        return mTurnoDao.findOverOggi(uid);
    }

    private static class insertAsyncTask extends AsyncTask<Turno, Void, Void> {

        private TurnoDao mAsyncTaskDao;
        private CalendarAdapter adapter;

        insertAsyncTask(TurnoDao dao,CalendarAdapter adapter) {
            mAsyncTaskDao = dao;
            this.adapter=adapter;
        }

        @Override
        protected Void doInBackground(final Turno... params) {
           Long result= mAsyncTaskDao.insert(params[0]);
           if (result==-1) {
               mAsyncTaskDao.update(params[0]);
           } else {
               if (adapter!=null) {
                   adapter.addTurno(mAsyncTaskDao.findById(result));
                   adapter.updateLastDate();
               }
           }

            return null;

        }
    }



}
