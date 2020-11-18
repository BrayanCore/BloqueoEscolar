package com.example.bloqueoescolar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bloqueoescolar.domain.struct.StructExam
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.select_grade.*

class SelectGrade : AppCompatActivity() {

    companion object {
        private const val TAG = "SelectGradeActivity"
    }

    val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    val databaseUser = FirebaseDatabase.getInstance().getReference("usuario").child(
        currentFirebaseUser!!.uid
    )
    var examsApproved = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_grade)

        examsApprovedOfUser()

        first_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 1)
            intent.putStringArrayListExtra("examsApproved", examsApproved)
            startActivity(intent)
        }

        second_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 2)
            intent.putStringArrayListExtra("examsApproved", examsApproved)
            startActivity(intent)
        }

        third_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 3)
            intent.putStringArrayListExtra("examsApproved", examsApproved)
            startActivity(intent)
        }

        fourth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 4)
            intent.putStringArrayListExtra("examsApproved", examsApproved)
            startActivity(intent)
        }

        fifth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 5)
            intent.putStringArrayListExtra("examsApproved", examsApproved)
            startActivity(intent)
        }

        sixth_grade.setOnClickListener {
            val intent = Intent(applicationContext, Exams::class.java)
            intent.putExtra("Grade", 6)
            intent.putStringArrayListExtra("examsApproved", examsApproved)
            startActivity(intent)
        }

    }

    fun examsApprovedOfUser(){

        databaseUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //Getting the string value of that node

                for (postSnapshot in dataSnapshot.child("IDs").children) {

                    var ID = postSnapshot.getValue(String::class.java)
                    //Toast.makeText(applicationContext,"$ID", Toast.LENGTH_LONG).show()
                    examsApproved.add(ID!!)
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }


        })

    }

}