package com.example.days_13

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    lateinit var db : DB
    lateinit var btn_date : Button
    lateinit var btn_save : Button
    lateinit var edt_title : EditText
    lateinit var edt_detail : EditText
    var selectDate = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DB(this)
        edt_title = findViewById(R.id.edt_title)
        edt_detail = findViewById(R.id.edt_detail)
        btn_date = findViewById(R.id.btn_date)
        btn_save = findViewById(R.id.btn_save)
        val deleteStatus = db.deleteNote(1)
        Log.d("delete Status",deleteStatus.toString())

        val updateStatus = db.updateNote(2,"Kahvaltı","Pazar günü Van Kahvaltısı")
       Log.d("update Status",updateStatus.toString())
        val calender = Calendar.getInstance()
        btn_date.setOnClickListener(View.OnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                    Log.d("i", i.toString()) // yıl
                    Log.d("i2", (i2+1).toString()) // ay
                    Log.d("i3", i3.toString()) // gün
                    var ay = "${i2 + 1}" //if (i2+1 < 10) "0${i2+1}" else "${i2+1}
                    if (i2+1<10){
                        ay ="0${i2 + 1}"
                    }
                    selectDate = "$i3.$ay.$i"
                },
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH),
            )
            datePickerDialog.show()
        })

        btn_save.setOnClickListener{
            if (selectDate != ""){
                var status = db.addNote(edt_title.text.toString(),edt_detail.text.toString(),selectDate)
                Log.d("status",status.toString())
                val ls = db.allNote()
                Log.d("ls",ls.toString())
                selectDate = ""
            }else{
                Toast.makeText(this,"Select Date",Toast.LENGTH_LONG).show()

            }

        }

    }
}