package telcos.proyectos.tomainventarios;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A login screen that offers login via user/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private View mProgressView;
    private View mLoginFormView;
    private EditText etUser;
    private EditText etPassword;

    Button ingresar;
    Button salir;
    //IP web service
    String IP = "http://172.16.11.180:";
    //String IP = "https://transportes.ws.telcosingenieria.com";
    //String PUERTO_HOST = "";
    //String IP = "http://190.85.119.251:";
    String PUERTO_HOST = "80";
    String CARPETA = "/Formulario_transportes/WebServices";
    //String PUERTO_HOST = "88";
    String LOGIN = IP + PUERTO_HOST + CARPETA + "/login.inc.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = (EditText) findViewById(R.id.user);
        etPassword = (EditText) findViewById(R.id.password);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView,int id,KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        ingresar = (Button) findViewById(R.id.ingresar);
        ingresar.setOnClickListener(this);

        salir = (Button) findViewById(R.id.cancelar);
        salir.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ingresar:

                Intent i = new Intent(LoginActivity.this,MenuActivity.class);
                startActivity(i);
                /*attemptLogin();
                mAuthTask = null;*/
                break;

            case R.id.cancelar:
                finish();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                System.exit(0);
                break;
        }
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        etUser.setError(null);
        etPassword.setError(null);

        // Store values at the time of the login attempt.
        String user = etUser.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Comprobar si el password ingresado no es nulo y es valido
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Comprobar si el campo para usuario esta vacio
        if (TextUtils.isEmpty(user)) {
            etUser.setError(getString(R.string.error_field_required));
            focusView = etUser;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError(getString(R.string.error_field_required));
            focusView = etPassword;
            cancel = true;
        }

        //Comprobar si hubo un fallo durante el ingreso de datos
        if (cancel) {
            // Enfocar el campo del error
            focusView.requestFocus();
        } else {
            // Cargar animacion con una barra de progreso
            showProgress(true);

            mAuthTask = new UserLoginTask();
            mAuthTask.execute(LOGIN,"1",
                    etUser.getText().toString(),
                    etPassword.getText().toString());
        }
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    public class UserLoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);

            if (s == "Usuario Existe") {
                Toast to = Toast.makeText(getApplicationContext(),"Bienvenido",Toast.LENGTH_LONG);
                to.show();
                showProgress(false);
         /*       Intent i = new Intent(LoginActivity.this,MainActivity.class);
                i.putExtra("User", etUser.getText().toString());
                startActivity(i);*/
                limpiarTx(etUser);
                limpiarTx(etPassword);
            } else if (s == "Usuario No existe") {
                Toast to = Toast.makeText(getApplicationContext(),"Usuario o clave incorrecto",Toast.LENGTH_LONG);
                to.show();
                showProgress(false);
            } else {
                Toast to = Toast.makeText(getApplicationContext(),"Verificar conexion",Toast.LENGTH_LONG);
                to.show();
                showProgress(false);
            }


        }

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null;
            String devuelve = "";
            if (params[1] == "1") {  //Ingresar Login

                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type","application/json");
                    urlConn.setRequestProperty("Accept","application/json");
                    urlConn.connect();

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("user",params[2]);
                    jsonParam.put("password",params[3]);


                    //Envio de parametros
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os,"UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();

                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                        }


                        JSONObject respuestaJSON = new JSONObject(result.toString());

                        int resultJSON = respuestaJSON.getInt("estado");


                        if (resultJSON == 1) {
                            devuelve = "Usuario Existe";
                        } else if (resultJSON == 2) {
                            devuelve = "Usuario No existe";
                        } else {
                            devuelve = "Error inesperado";
                        }
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            return devuelve;
        }
    }

    public void limpiarTx(EditText editText) {
        editText.setText("");
    }

}
