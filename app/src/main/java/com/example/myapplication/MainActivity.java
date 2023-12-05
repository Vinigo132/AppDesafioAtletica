package com.example.myapplication;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.myapplication.controller.UsuarioDAO;
import com.example.myapplication.model.MembroAtletica;

import com.example.myapplication.view.MenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {


    private TextInputLayout emailLogin;
    private TextInputLayout senhaLogin;
    private TextView esqueceuSenha;
    private TextInputLayout emailCadastro;
    private TextInputLayout senhaCadastro;
    private TextInputLayout confirmarSenhaCadastro;
    private TextInputLayout nome;
    private Animation fadeInAnimation;
    private Animation fadeOutAnimation;
    private EditText emailLoginEditText;
    private EditText senhaLoginEditText;
    private EditText nomeCadastroEditText;
    private EditText emailCadastroEditText;
    private EditText senhaCadastroEditText;
    private EditText ConferirSenhaEditText, emailEsqueceuSenha;
    private Button btnContinuar;
    private UsuarioDAO usuarioDAO;
    private Dialog popUp;
    private Button enviar,cancelar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        initializeAnimations();
        usuarioDAO = new UsuarioDAO();

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.dismiss();
            }
        });
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEsqueceuSenha.getText().toString();
                if (email.equals(""))
                    Toast.makeText(MainActivity.this, "Informe um email!", Toast.LENGTH_LONG).show();
                else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(MainActivity.this,
                                                "Um e-mail de redefinição de senha foi enviado para " +
                                                        "o seu endereço de e-mail.",
                                                Toast.LENGTH_LONG).show();
                                        popUp.dismiss();
                                        emailEsqueceuSenha.setText("");
                                    } else {
                                        Toast.makeText(MainActivity.this,
                                                "Falha ao enviar e-mail de redefinição de senha. " +
                                                        "Verifique se o endereço de e-mail é válido.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUp.show();
            }
        });


        MaterialButtonToggleGroup materialButtonToggleGroup = findViewById(R.id.btnsToggle);

        materialButtonToggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {

            if (isChecked) {
                if (checkedId == R.id.btnLogin) {
                    setVisibilityWithAnimation(View.VISIBLE, View.GONE);
                } else if (checkedId == R.id.btnCadastrar) {
                    setVisibilityWithAnimation(View.GONE, View.VISIBLE);
                }
            }
        });


    }

    public void ClickContinuar(View view) {
        if(btnContinuar.getText().toString().equals("Continuar")){

            emailLoginEditText = findViewById(R.id.emailLoginEditText);
            senhaLoginEditText = findViewById(R.id.senhaLoginEditText);
            String email = emailLoginEditText.getText().toString();
            String senha = senhaLoginEditText.getText().toString();

            if(email.isEmpty() || senha.isEmpty()){
                showToast("Preencha todos os campos!");
            }else {
                // Chame o método fazerLogin da classe UsuarioDAO
                usuarioDAO.fazerLogin(email, senha, (success, errorMessage) -> {
                    if (success) {
                        showToast("Login bem-sucedido");
                        startMenuActivity();
                    } else {
                        showToast(errorMessage);
                    }
                });
            }

        }else {
            MembroAtletica membro = new MembroAtletica();
            nomeCadastroEditText = findViewById(R.id.nomeCadastroEditText);
            emailCadastroEditText = findViewById(R.id.emailCadastroEditText);
            senhaCadastroEditText = findViewById(R.id.senhaCadastroEditText);
            ConferirSenhaEditText = findViewById(R.id.ConferirSenhaEditText);
            membro.setNome(nomeCadastroEditText.getText().toString());
            membro.setEmail(emailCadastroEditText.getText().toString());
            membro.setSenha(senhaCadastroEditText.getText().toString());
            String conferirSenha = ConferirSenhaEditText.getText().toString();

            if (membro.getNome().isEmpty() || membro.getEmail().isEmpty() || membro.getSenha().isEmpty() || conferirSenha.isEmpty()) {
                showToast("Preencha todos os campos!");
            } else {
                    usuarioDAO.Cadastrar(membro, conferirSenha, (success, errorMessage) -> {
                        if (success) {
                            showToast("Cadastro bem-sucedido");
                            startMenuActivity();
                        } else {
                            showToast(errorMessage);
                        }
                    });

            }
        }
    }

    @SuppressLint("ResourceType")
    private void initializeViews() {
        //       -------------- login------------------
        emailLogin = findViewById(R.id.emailLogin);
        senhaLogin = findViewById(R.id.senhaLogin);
        esqueceuSenha = findViewById(R.id.esqueceuSenha);
        //       -------------- Cadastro------------------
        emailCadastro = findViewById(R.id.emailCadastro);
        senhaCadastro = findViewById(R.id.senhaCadastro);
        confirmarSenhaCadastro = findViewById(R.id.confirmarSenhaCadastro);
        nome = findViewById(R.id.nomeCadastro);

        btnContinuar = findViewById(R.id.btnContinuar);

        // ----------------- popUp --------------
        popUp = new Dialog(this);
        popUp.setContentView(R.layout.popup_esqueceu_senha);
        popUp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        popUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        enviar = popUp.findViewById(R.id.btnEnviar);
        cancelar = popUp.findViewById(R.id.btnCancelar);
        emailEsqueceuSenha = popUp.findViewById(R.id.emailRecuperarSenhaEditText);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initializeAnimations() {
        fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
    }

    private void setVisibilityWithAnimation(int loginVisibility, int cadastroVisibility) {
        if (loginVisibility == View.VISIBLE) {
            emailLogin.startAnimation(fadeInAnimation);
            senhaLogin.startAnimation(fadeInAnimation);
            esqueceuSenha.startAnimation(fadeInAnimation);
            btnContinuar.setText("Continuar");
        } else {
            emailLogin.startAnimation(fadeOutAnimation);
            senhaLogin.startAnimation(fadeOutAnimation);
            esqueceuSenha.startAnimation(fadeOutAnimation);
        }

        if (cadastroVisibility == View.VISIBLE) {
            emailCadastro.startAnimation(fadeInAnimation);
            senhaCadastro.startAnimation(fadeInAnimation);
            confirmarSenhaCadastro.startAnimation(fadeInAnimation);
            nome.startAnimation(fadeInAnimation);
            btnContinuar.setText("Cadastrar");
        } else {
            emailCadastro.startAnimation(fadeOutAnimation);
            senhaCadastro.startAnimation(fadeOutAnimation);
            confirmarSenhaCadastro.startAnimation(fadeOutAnimation);
            nome.startAnimation(fadeOutAnimation);
        }

        emailLogin.setVisibility(loginVisibility);
        senhaLogin.setVisibility(loginVisibility);
        esqueceuSenha.setVisibility(loginVisibility);
        emailCadastro.setVisibility(cadastroVisibility);
        senhaCadastro.setVisibility(cadastroVisibility);
        confirmarSenhaCadastro.setVisibility(cadastroVisibility);
        nome.setVisibility(cadastroVisibility);
    }

    private void startMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}