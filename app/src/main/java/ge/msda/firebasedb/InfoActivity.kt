package ge.msda.firebasedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_info.*
import com.bumptech.glide.Glide
import android.widget.LinearLayout
import android.widget.ImageView



class InfoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        init()

        saveBtn.setOnClickListener {

            val n: String = inputFullName.text.toString()
            val p: String = inputPhone.text.toString()
            val a: String = addressInput.text.toString()
            val l: String = imageInput.text.toString()

            if (TextUtils.isEmpty(n)) {
                Toast.makeText(this, "Empty name!", Toast.LENGTH_LONG).show()
            } else {
                contactInfo(n, p, a, l)
            }

        }

    }

    private fun init() {

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("UserInfo")

        addUserInfoChangeListener()

    }

    private fun contactInfo(name: String, phone: String?, address: String?, link: String?) {
        val userInfo = UserInfo(name, phone, address, link)
        db.child(auth.currentUser?.uid!!).setValue(userInfo)
    }

    private fun addUserInfoChangeListener() {

        db.child(auth.currentUser?.uid!!)
            .addValueEventListener(object : ValueEventListener {

                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(snap: DataSnapshot) {

                    val userInfo: UserInfo = snap.getValue(UserInfo::class.java) ?: return

                    showFullName.text = userInfo.name
                    showPhone.text = userInfo.mobile ?: ""
                    showAddress.text = userInfo.address ?: ""

                    Glide.with(this@InfoActivity)
                        .load(userInfo.link)
                        .into(imageView2)


                    inputFullName.setText("")
                    inputPhone.setText("")
                    addressInput.setText("")
                    imageInput.setText("")

                }

            })

    }

}