package com.example.pruebasjson

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebasjson.Model.Circuito_Modelo
import com.example.pruebasjson.obtenerCircuitos.CircuitosAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.CyclicBarrier



val barrierAc3 =  CyclicBarrier(2);

class Actividad3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCircuitosCall("drivers.json")
        barrier.await()
        // Instance of users list using the data model class.
        val circuitos: ArrayList<Circuito_Modelo> = ArrayList()
        try {

            //val obj = JSONObject(getJSONFromAssets()!!)
            val obj = JSONObject(jsonTotal)
            val circuits0 = obj.getJSONObject("MRData")

            val circuits1 = circuits0.getJSONObject("CircuitTable")

            val listaCircuitos = circuits1.getJSONArray("Circuits")

//            println(listaCircuitos.length())
            for (i in 0 until listaCircuitos.length()) {
                val circuito = listaCircuitos.getJSONObject(i)
                //println(circuito)

                val id = circuito.getString("circuitId")
                val nombre = circuito.getString("circuitName")
                val localizacion = circuito.getJSONObject("Location")
                val ciudad = localizacion.getString("locality")
                val pais = localizacion.getString("country")

                // Now add all the variables to the data model class and the data model class to the array list.
                val userDetails =
                    Circuito_Modelo(nombre, pais, ciudad, "Aqui foto")

                circuitos.add(userDetails)
            }
        } catch (e: JSONException) {
            //exception
            e.printStackTrace()
        }

        val rvCircuito = findViewById<View>(R.id.rvCircuitos) as RecyclerView
        // Initialize contacts

        // Create adapter passing in the sample user data
        val adapter = CircuitoAdapter(this, circuitos)
        // Attach the adapter to the recyclerview to populate items
        rvCircuito.adapter = adapter
        // Set layout manager to position the items
        rvCircuito.layoutManager = LinearLayoutManager(this)
        // That's all!


        //println("-------------------------222")
    }

    private fun getCircuitosCall(busqueda: String) {
        println("Llego aqui")
        CoroutineScope(Dispatchers.IO).launch {

            val listResult = CircuitosAPI.retrofitService.getPhotos("https://ergast.com/api/f1/2010/drivers.json")
            jsonTotal = listResult
            barrier.await()

            println("------")
            print(listResult)

        }


    }
}