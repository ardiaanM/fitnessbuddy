//MEALSAIMENU, more known now as "Healthy-AI" Refactor?

package com.example.fitnessbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fitnessbuddy.databinding.ActivityMainBinding
import com.example.fitnessbuddy.databinding.ActivityMealsAimenuBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MealsAIMenu : AppCompatActivity() {
    private val client = OkHttpClient()
    private val answerList = mutableListOf<String>() // List to store the answers
    private lateinit var txtResponse: TextView
    private lateinit var binding: ActivityMealsAimenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsAimenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        txtResponse = findViewById<TextView>(R.id.txtResponse)
        val btnClear = findViewById<Button>(R.id.btnClear)

        btnSubmit.setOnClickListener {
            val question = etQuestion.text.toString().trim()
            Toast.makeText(this, "Generating Answer..", Toast.LENGTH_SHORT).show()
            if (question.isNotEmpty()) {
                getResponse(question) { response ->
                    runOnUiThread {
                        answerList.add(response) // Add the response to the list
                        updateAnswerText() // Update the text view with all the answers
                        etQuestion.text.clear() // Clear the EditText
                    }
                }
            }
        }

        btnClear.setOnClickListener {
            clearAnswers()
            Toast.makeText(this, "Answers cleared!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateAnswerText() {
        // Build the combined text of all the answers in the list
        val combinedText = answerList.joinToString("\n\n")
        txtResponse.text = combinedText
    }

    private fun clearAnswers() {
        answerList.clear()
        txtResponse.text = ""
    }

    fun getResponse(question:String, callback: (String) -> Unit){
        val apiKey = "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"
        val url = "https://api.openai.com/v1/completions"

        val requestBody = """
            {
            "model": "text-davinci-003",
            "prompt": "$question",
            "max_tokens": 500,
            "temperature": 0
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(requestBody.toRequestBody("application/json".toMediaTypeOrNull()))
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
               Log.e("error", "API failed",e)
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                if (body != null) {
                    Log.v("data",body)
                }
                else{
                    Log.v("data", "empty")
                }
                var jsonObject = JSONObject(body)
                val jsonArray: JSONArray=jsonObject.getJSONArray("choices")
                val textResult = jsonArray.getJSONObject(0).getString("text")
                callback(textResult)
            }
        })
    }

}