package it.uniparthenope.myitalianfiscalcode

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.MultiAutoCompleteTextView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private val cities = arrayOf(
        "Napoli", "Avellino", "Salerno", "Caserta",
        "Benevento","Milano","Marano", "Nola"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, cities
        )

        findViewById<MultiAutoCompleteTextView>(R.id.mactvBirthPlace).also {
            it.setAdapter(adapter);
            it.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        };

        var btnSubmit = findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener() {
            /*
            var etName = findViewById<EditText>(R.id.etName)

            tvMessage.setText(etName.text)
             */
            var tvMessage = findViewById<TextView>(R.id.tvMessage)

            var etLastName = findViewById<TextView>(R.id.etLastName)
            var etFirstName = findViewById<TextView>(R.id.etFirstName)

            var rbgGender = findViewById<RadioGroup>(R.id.rgGender)

            var cvBirtDate = findViewById<CalendarView>(R.id.cvBirthDate)

            var mactvBirthPlace = findViewById<MultiAutoCompleteTextView>(R.id.mactvBirthPlace)

            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url = "http://193.205.230.3:13387/italian-fiscal-code"

            val jsonBody = JSONObject()
            jsonBody.put("lastname", etLastName.text)
            jsonBody.put("firstname", etFirstName.text)
            jsonBody.put("gender", "M")
            jsonBody.put("birthdate", cvBirtDate.date)
            jsonBody.put("birthplace", mactvBirthPlace.text)

            println(jsonBody.toString())

            val request: JsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                { response ->
                    tvMessage.text = response.getString("fiscalCode")
                },
                {
                    tvMessage.text = "xyz"
                    it.printStackTrace()
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers =
                        HashMap<String, String>()
                    headers["Content-Type"] = "application/json; charset=utf-8"
                    return headers
                }
            }


            // Add the request to the RequestQueue.
            queue.add(request)
        }
    }
}


