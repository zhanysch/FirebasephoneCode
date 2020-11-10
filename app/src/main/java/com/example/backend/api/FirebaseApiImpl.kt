package com.example.backend.api

import androidx.lifecycle.MutableLiveData
import com.example.backend.model.NewsItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


interface FirebaseApi  {
    fun loadData() : MutableLiveData<ArrayList<NewsItem>>

}

class FirebaseApiImpl: FirebaseApi {

    override fun loadData(): MutableLiveData<ArrayList<NewsItem>> {
        val data = MutableLiveData<ArrayList<NewsItem>>()
        val database = Firebase.database
        val ref = database.getReferenceFromUrl("https://first-backend-firebase-2fbe5.firebaseio.com/")
        val list : ArrayList<NewsItem>?  = arrayListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children){
                    val point = postSnapshot.getValue(NewsItem:: class.java)
                    point?.let { list?.add(it) }
                }
                data.postValue(list) // data отправь значение list в mainactivity
            }
            override fun onCancelled(error: DatabaseError) {
                data.postValue(null)
            }
        })
        return data
    }
}