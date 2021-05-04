package br.com.unipac.doar_me.model.repository

import android.content.ContentValues
import android.content.Context
import br.com.unipac.doar_me.infra.DatabaseHandle
import br.com.unipac.doar_me.model.entity.Trabalho

import kotlin.collections.ArrayList

class TrabalhoRepository(ctx: Context) {

    val db = DatabaseHandle(ctx)

    fun add(trab: Trabalho): Boolean {
        val db = db._writableDatabase()
        val values = ContentValues()
        values.put(TITULO, trab.titulo)
        values.put(DESCRICAO, trab.descricao)
        values.put(NOTA, trab.nota)
        val _success = db.insert(TABLE_NAME, null, values)
        return (("$_success").toInt() != -1)
    }

    fun getById(_id: Int): Trabalho {
        val trabalho= Trabalho()
        val db = db._readableDatabase()
        val selectQuery =
            "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        trabalho.id = cursor.getInt(cursor.getColumnIndex(ID))
        trabalho.titulo = cursor.getString(cursor.getColumnIndex(TITULO))
        trabalho.descricao = cursor.getString(cursor.getColumnIndex(DESCRICAO))
        trabalho.nota = cursor.getDouble(cursor.getColumnIndex(NOTA))
        cursor.close()
        return trabalho
    }

    fun getAll(): ArrayList<Trabalho> {
        val trabList = ArrayList<Trabalho>()
        val db = db._readableDatabase()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val trabalho= Trabalho()
                    trabalho.id = cursor.getInt(cursor.getColumnIndex(ID))
                    trabalho.titulo = cursor.getString(cursor.getColumnIndex(TITULO))
                    trabalho.descricao = cursor.getString(cursor.getColumnIndex(DESCRICAO))
                    trabalho.nota = cursor.getDouble(cursor.getColumnIndex(NOTA))
                    trabList.add(trabalho)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return trabList
    }

    fun update(trabalho: Trabalho): Boolean {
        val db = db._writableDatabase()
        val values = ContentValues().apply {
            put(TITULO, trabalho.titulo)
            put(DESCRICAO, trabalho.descricao)
            put(NOTA, trabalho.nota)
        }
        val _success = db.update(
            TABLE_NAME,
            values,
            ID + "=?",
            arrayOf(trabalho.id.toString())
        ).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    fun delete(_id: Int): Boolean {
        val db = db._writableDatabase()
        val _success = db.delete(
            TABLE_NAME,
            ID + "=?",
            arrayOf(_id.toString())
        ).toLong()
        return ("$_success").toInt() != -1
    }

    fun deleteAll(): Boolean {
        val db = db._writableDatabase()
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }

    companion object {
        private val TABLE_NAME = "tb_trab"
        private val ID = "Id"
        private val TITULO = "Titulo"
        private val DESCRICAO = "Descricao"
        private val NOTA = "Nota"
        val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($ID INTEGER PRIMARY KEY, $TITULO TEXT, $DESCRICAO TEXT, $NOTA DOUBLE(2,0));"
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}