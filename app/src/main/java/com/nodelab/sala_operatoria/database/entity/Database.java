package com.nodelab.sala_operatoria.database.entity;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.nodelab.sala_operatoria.Dao.TurnoDao;

@android.arch.persistence.room.Database(entities = {Turno.class},version=1)
public abstract class Database extends RoomDatabase {
    public abstract TurnoDao turnoDao();

    private static volatile Database INSTANCE;


    static public Database getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            Database.class, "database")
                            //.addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

   /* private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    //new PopulateDbAsync(INSTANCE).execute();
                }
            };
*/
  /*  private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TurnoDao mDao;

        PopulateDbAsync(Database db) {
            mDao = db.turnoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();

            Turno turno1= new Turno();
            turno1.setData("10/12/13");
            turno1.setNomeDottoreGiorno("Pippo");
            turno1.setNumeroDottoreGiorno("3323456755");
            turno1.setNomeDottoreNotte("Franco");
            turno1.setNumeroDottoreNotte("3278788372");
            mDao.insert(turno1);
            Turno turno2= new Turno();
            turno2.setData("10/12/13");
            turno2.setNomeDottoreGiorno("Pippo");
            turno2.setNumeroDottoreGiorno("3323456755");
            turno2.setNomeDottoreNotte("Franco");
            turno2.setNumeroDottoreNotte("3278788372");
            mDao.insert(turno2);
            return null;
        }
    }*/

}