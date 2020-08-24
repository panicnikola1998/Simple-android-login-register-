package cm.com.example.feature

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cm.com.example.R
import cm.com.example.databinding.ActivityMainBinding
import cm.com.example.ext.getUserPrefObj
import cm.com.example.ext.saveUserPrefObj
import cm.com.example.util.Utils
import cm.com.example.util.dialog.ClickDialogListener
import cm.com.example.util.dialog.Dialog
import cm.com.example.vo.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initView()
        initGoogleSignIn()
    }

    private fun initView(){
        user = this.getUserPrefObj()
        Utils.setImageFromUrl(this, user?.photo ?: "", binding.avatar)
        binding.txtName.text = user?.name
        binding.txtMail.text = user?.email
        binding.ctlLogout.setOnClickListener(this)
    }

    private fun initGoogleSignIn() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ctlLogout -> {
                Dialog().showLogoutDialog(this, object : ClickDialogListener.Yes {
                    override fun onCLickYes() {
                        logout()
                    }
                })
            }
        }
    }

    private fun logout() {
        mGoogleSignInClient.signOut().addOnCompleteListener(this) {
            this.saveUserPrefObj(null)
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

}
