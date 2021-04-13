package br.com.unipac.doar_me.model.repository

import android.content.ContentValues
import android.content.Context
import br.com.unipac.doar_me.infra.DatabaseHandle
import br.com.unipac.doar_me.model.entity.Disciplina

class DisciplinaRepository(ctx: Context) {

    val db = DatabaseHandle(ctx)

    fun add(disciplina: Disciplina): Boolean {
        val db = db._writableDatabase()
        val values = ContentValues()
        values.put(NAME, disciplina.name)
        val _success = db.insert(TABLE_NAME, null, values)
        return (("$_success").toInt() != -1)
    }

    fun getById(_id: Int): Disciplina {
        val disciplina = Disciplina()
        val db = db._readableDatabase()
        val selectQuery =
            "SELECT * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        cursor?.moveToFirst()
        disciplina.id = cursor.getInt(cursor.getColumnIndex(ID))
        disciplina.name = cursor.getString(cursor.getColumnIndex(NAME))
        cursor.close()
        return disciplina
    }

    fun getAll(): ArrayList<Disciplina> {
        val disciplinaList = ArrayList<Disciplina>()
        val db = db._readableDatabase()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val disciplina = Disciplina()
                    disciplina.id = cursor.getInt(cursor.getColumnIndex(ID))
                    disciplina.name = cursor.getString(cursor.getColumnIndex(NAME))
                    disciplinaList.add(disciplina)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return disciplinaList
    }

    fun update(disciplina: Disciplina): Boolean {
        val db = db._writableDatabase()
        val values = ContentValues().apply {
            put(NAME, disciplina.name)
        }
        val _success = db.update(
            TABLE_NAME,
            values,
            ID + "=?",
            arrayOf(disciplina.id.toString())
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
        private val TABLE_NAME = "tb_disciplina"
        private val ID = "Id"
        private val NAME = "Nome"
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT);"
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}