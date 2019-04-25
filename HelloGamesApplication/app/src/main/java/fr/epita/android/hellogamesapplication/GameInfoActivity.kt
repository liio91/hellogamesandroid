package fr.epita.android.hellogamesapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_game_info.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class GameInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_info)

        // A List to store or objects
        val data = arrayListOf<GameObject>()
        // The base URL where the WebService is located
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"
        // Use GSON library to create our JSON parser
        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())
        // Create a Retrofit client object targeting the provided URL
        // and add a JSON converter (because we are expecting json responses)
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(jsonConverter)
            .build()

        val originIntent = intent

        val wsCallback: Callback<List<GameObject2>> = object : Callback<List<GameObject2>> {
            override fun onFailure(call: Call<List<GameObject2>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<GameObject2>>, response:
            Response<List<GameObject2>>
            ) {
                if (response.code() == 200) {
                    // We got our data !
                    val responseData = response.body()
                    if (responseData != null) {
                        Log.d("TAG", "WebService success : " + responseData.size)
                        // println("toto" + responseData[1].picture)
                        // Log.d("to", "ooo: " + )

                        //data.addAll(responseData)
                        //data.shuffle()

                        //button1.setImageResource()

                        // Use it in your activity
                        Glide
                            .with(this@GameInfoActivity)
                            //.load(responseData[1].picture)
                            .load(originIntent.getStringExtra("picture"))
                            .into(imageView2)

                        textView2.text = responseData[0].name
                        textView3.text = responseData[0].players.toString()
                    }
                }
            }
        }

        // retrieve the intent that caused the activity to open

        // extract data from the intent
        val id = originIntent.getIntExtra("id", 0)

        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WSInterface = retrofit.create(WSInterface::class.java)
        service.listToDos(id).enqueue(wsCallback)
    }
}

interface WSInterface {
    @GET("game/details")
    fun listToDos(@Query("game_id") game_id: Int): Call<List<GameObject2>>
}
