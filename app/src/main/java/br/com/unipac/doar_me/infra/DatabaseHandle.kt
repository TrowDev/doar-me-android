package br.com.unipac.doar_me.infra

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable
import br.com.unipac.doar_me.model.repository.DisciplinaRepository
import br.com.unipac.doar_me.model.repository.PessoaRepository
import br.com.unipac.doar_me.model.repository.TrabalhoRepository

class DatabaseHandle(ctx: Context) : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(PessoaRepository.CREATE_TABLE)
        db?.execSQL(DisciplinaRepository.CREATE_TABLE)
        db?.execSQL(TrabalhoRepository.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(PessoaRepository.DROP_TABLE)
        db?.execSQL(DisciplinaRepository.DROP_TABLE)
        db?.execSQL(TrabalhoRepository.DROP_TABLE)
        onCreate(db)
    }

    fun _writableDatabase(): SQLiteDatabase {
        return this.writableDatabase
    }

    fun _readableDatabase(): SQLiteDatabase {
        return this.readableDatabase
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "doarme2.db"
    }
}