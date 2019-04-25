package fr.epita.android.hellogamesapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<View>(R.id.imageButton5)
        val button2 = findViewById<View>(R.id.imageButton6)
        val button3 = findViewById<View>(R.id.imageButton7)
        val button4 = findViewById<View>(R.id.imageButton8)

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



        val wsCallback: Callback<List<GameObject>> = object : Callback<List<GameObject>> {
            override fun onFailure(call: Call<List<GameObject>>, t: Throwable) {
                // Code here what happens if calling the WebService fails
                Log.w("TAG", "WebService call failed")
            }
            override fun onResponse(call: Call<List<GameObject>>, response:
            Response<List<GameObject>>
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
                            .with(this@MainActivity)
                            //.load(responseData[1].picture)
                            .load(responseData[1].picture)
                            .into(imageButton5)
                        // Use it in your activity
                        Glide
                            .with(this@MainActivity)
                            //.load(responseData[1].picture)
                            .load(responseData[2].picture)
                            .into(imageButton6)
                        // Use it in your activity
                        Glide
                            .with(this@MainActivity)
                            //.load(responseData[1].picture)
                            .load(responseData[3].picture)
                            .into(imageButton7)
                        // Use it in your activity
                        Glide
                            .with(this@MainActivity)
                            //.load(responseData[1].picture)
                            .load(responseData[4].picture)
                            .into(imageButton8)



                        button1.setOnClickListener()
                        {
                            // Create an explicit intent
                            val explicitIntent = Intent(this@MainActivity, GameInfoActivity::class.java)
                            // Insert extra data in the intent
                            val id = responseData[1].id
                            val picture = responseData[1].picture
                            explicitIntent.putExtra("id", id)
                            explicitIntent.putExtra("picture", picture)
                            // Start the other activity by sending the intent
                            startActivity(explicitIntent)
                        }

                        button2.setOnClickListener()
                        {
                            // Create an explicit intent
                            val explicitIntent = Intent(this@MainActivity, GameInfoActivity::class.java)
                            // Insert extra data in the intent
                            val id = responseData[2].id
                            val picture = responseData[2].picture
                            explicitIntent.putExtra("id", id)
                            explicitIntent.putExtra("picture", picture)
                            // Start the other activity by sending the intent
                            startActivity(explicitIntent)
                        }

                        button3.setOnClickListener()
                        {
                            // Create an explicit intent
                            val explicitIntent = Intent(this@MainActivity, GameInfoActivity::class.java)
                            // Insert extra data in the intent
                            val id = responseData[3].id
                            val picture = responseData[3].picture
                            explicitIntent.putExtra("id", id)
                            explicitIntent.putExtra("picture", picture)
                            // Start the other activity by sending the intent
                            startActivity(explicitIntent)
                        }

                        button4.setOnClickListener()
                        {
                            // Create an explicit intent
                            val explicitIntent = Intent(this@MainActivity, GameInfoActivity::class.java)
                            // Insert extra data in the intent
                            val id = responseData[4].id
                            val picture = responseData[4].picture
                            explicitIntent.putExtra("id", id)
                            explicitIntent.putExtra("picture", picture)
                            // Start the other activity by sending the intent
                            startActivity(explicitIntent)
                        }
                    }
                }
            }
        }

        // Use the client to create a service:
        // an object implementing the interface to the WebService
        val service: WSInterface2 = retrofit.create(WSInterface2::class.java)
        service.listToDos().enqueue(wsCallback)
    }
}

interface WSInterface2 {
    @GET("game/list")
    fun listToDos(): Call<List<GameObject>>
}