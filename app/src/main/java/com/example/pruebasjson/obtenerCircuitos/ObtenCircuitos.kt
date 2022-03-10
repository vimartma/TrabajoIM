package com.example.pruebasjson.obtenerCircuitos

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private const val BASE_URL = "https://ergast.com/api/f1/2010/"


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface ObtenerCircuitos {
    @GET
    suspend fun getPhotos(@Url url:String): String
}
object CircuitosAPI {
    val retrofitService : ObtenerCircuitos by lazy {
        retrofit.create(ObtenerCircuitos::class.java) }
}
