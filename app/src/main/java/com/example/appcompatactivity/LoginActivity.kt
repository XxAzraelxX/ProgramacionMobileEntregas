package com.example.appcompatactivity
import com.google.android.gms.common.SignInButton

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseUser
import com.google.android.gms.tasks.Task
import android.util.Log

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var rememberCheckBox: CheckBox
    private lateinit var googleSignInButton: SignInButton // Cambiado a SignInButton

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val PREFS_NAME = "login_prefs"
    private val KEY_EMAIL = "email"
    private val KEY_REMEMBER = "remember"

    private val RC_SIGN_IN = 9001 // Código para manejar el resultado de Google Sign-In

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()

        // Inicializar vistas
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)
        rememberCheckBox = findViewById(R.id.rememberCheckBox)
        googleSignInButton = findViewById(R.id.sign_in_button) // Asegúrate de usar SignInButton aquí

        val sharedPrefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        // Cargar datos guardados (si los hay)
        val savedEmail = sharedPrefs.getString(KEY_EMAIL, "")
        val isRemembered = sharedPrefs.getBoolean(KEY_REMEMBER, false)

        // Deshabilitar botón por defecto
        loginButton.isEnabled = false

        // Validación de campos en tiempo real
        val textWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = emailEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                loginButton.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        emailEditText.setText(savedEmail)
        rememberCheckBox.isChecked = isRemembered

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        // Configurar Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))  // Aquí referenciamos el webClientId
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        // Acción del botón de Google Sign-In
        googleSignInButton.setOnClickListener {
            signInWithGoogle()
        }

        // Acción del botón de login tradicional
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val remember = rememberCheckBox.isChecked

            // Validación simple
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validación simulada (usuario ficticio)
            if (email != "usuario@demo.com" || password != "1234") {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Guardar en SharedPreferences si "Recordarme" está activado
            val editor = sharedPrefs.edit()
            if (remember) {
                editor.putString(KEY_EMAIL, email)
                editor.putBoolean(KEY_REMEMBER, true)
            } else {
                editor.remove(KEY_EMAIL)
                editor.putBoolean(KEY_REMEMBER, false)
            }
            editor.apply()

            // Aquí puedes navegar a la siguiente pantalla
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
            finish()
        }
    }

    // Método para manejar el inicio de sesión con Google
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    // Manejar el resultado del intento de inicio de sesión con Google
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Verificar si es el resultado del intento de inicio de sesión con Google
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Intentar obtener la cuenta de Google
                val account = task.getResult(ApiException::class.java)

                // Obtener el idToken
                val idToken = account.idToken
                Log.d("LoginActivity", "idToken: $idToken")  // Verifica que el idToken no sea null

                if (idToken != null) {
                    // Si el idToken no es null, continúa con la autenticación de Firebase
                    firebaseAuthWithGoogle(idToken)
                } else {
                    // Si el idToken es null, muestra un mensaje de error
                    Log.w("LoginActivity", "idToken es null")
                    Toast.makeText(this, "Error al obtener el idToken", Toast.LENGTH_SHORT).show()
                }

            } catch (e: ApiException) {
                Log.w("LoginActivity", "Google sign in failed", e)
                Toast.makeText(this, "Error en la autenticación con Google", Toast.LENGTH_SHORT).show()
            }
        }
    }



    // Autenticación con Firebase usando el idToken de Google
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Toast.makeText(this, "Bienvenido ${user?.displayName}", Toast.LENGTH_LONG).show()

                    // Si la autenticación es exitosa, navega a la siguiente actividad (MainActivity)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Cierra la actividad de login para no permitir volver atrás
                } else {
                    Toast.makeText(this, "Falló la autenticación", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
