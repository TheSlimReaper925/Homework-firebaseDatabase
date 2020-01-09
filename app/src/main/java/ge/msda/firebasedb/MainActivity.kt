package ge.msda.firebasedb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            val intent = Intent(this, InfoActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_main)

        loginBtn.setOnClickListener {

            val e = email.text.toString()
            val p = password.text.toString()

            if (TextUtils.isEmpty(e) || TextUtils.isEmpty(p)) {
                Toast.makeText(this, "Empty data!", Toast.LENGTH_LONG).show()
            } else {

                auth.signInWithEmailAndPassword(e, p).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, InfoActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error!", Toast.LENGTH_LONG).show()
                    }
                }

            }

        }

    }

}