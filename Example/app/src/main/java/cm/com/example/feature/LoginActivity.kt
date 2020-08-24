package cm.com.example.feature

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cm.com.example.R
import cm.com.example.databinding.ActivityLoginBinding
import cm.com.example.ext.getUserPrefObj
import cm.com.example.ext.saveUserPrefObj
import cm.com.example.vo.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val GOOGLE_SIGN_IN_REQUEST_CODE = 1
    }

    private lateinit var binding: ActivityLoginBinding
    private var account: GoogleSignInAccount? = null
    private lateinit var gso: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var mDatabase: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        init()
        initGoogleSignIn()
    }

    override fun onStart() {
        super.onStart()
        val user = this.getUserPrefObj()
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun init(){
        mDatabase = FirebaseDatabase.getInstance().reference
        binding.btnGg.setOnClickListener(this)
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
            R.id.btn_gg -> {
                signInGoogle()
            }
        }
    }

    private fun signInGoogle() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            account = completedTask.getResult(ApiException::class.java)
            saveData(account)
        } catch (e: ApiException) {
            Log.w("Gray", "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun saveData(account: GoogleSignInAccount?) {
        val user = User(account?.photoUrl.toString(), account?.displayName, account?.email)

        mDatabase?.child("users")?.child(account?.id ?: return)?.setValue(user)

        this.saveUserPrefObj(user)

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
