package com.nodelab.sala_operatoria.Dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.nodelab.sala_operatoria.database.entity.Turno;

import java.util.List;



@Dao
public interface TurnoDao {

    @Query("SELECT * FROM turno")
     List<Turno> getAll();

    @Query("SELECT * FROM turno WHERE uid IN (:userIds)")
    List<Turno> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM turno WHERE data LIKE :data LIMIT 1")
    Turno findByData(String data);

    @Insert
    void insertAll(Turno... turno);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Long insert(Turno turno);

    @Update
    void update(Turno turno);




    @Delete
    void delete(Turno turno);

    @Query("DELETE FROM turno")
    void deleteAll();

    @Query("SELECT * FROM turno WHERE uid LIKE :id")
    Turno findById(Long id);

    @Query("SELECT * FROM turno ORDER BY uid DESC LIMIT 1")
    Turno getLastInsertedTurno();

    @Query("SELECT * FROM turno WHERE uid >= :uid")
    List<Turno> findOverOggi(int uid);
}
