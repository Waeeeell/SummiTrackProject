package com.example.myapplication

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ActivitatService {

    // GET - Obtenir totes les activitats
    @GET("api/GetAll/allActivitats")
    suspend fun getAllActivitats(): Response<List<Activitat>>

    // GET - Obtenir una activitat per ID
    @GET("api/GetActivitat/{id}")
    suspend fun getActivitatById(@Path("id") id: Long): Response<Activitat>

    // POST - Crear una nova activitat
    @POST("api/PostActivitat/activitat")
    suspend fun createActivitat(@Body activitat: Activitat): Response<Activitat>

    // PUT - Actualitzar una activitat existent
    @PUT("api/PutActivitat/{id}")
    suspend fun updateActivitat(@Path("id") id: Long, @Body activitat: Activitat): Response<Activitat>

    // DELETE - Eliminar una activitat
    @DELETE("api/DeleteActivitat/{id}")
    suspend fun deleteActivitat(@Path("id") id: Long): Response<Void>

    // GET - Filtrar activitats per distància mínima
    @GET("api/GetActivitats/filtre")
    suspend fun filtrarPerDistancia(@Query("distanciaMin") distanciaMin: Int): Response<List<Activitat>>
}
