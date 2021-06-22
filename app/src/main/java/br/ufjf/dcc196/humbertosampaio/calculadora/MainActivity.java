package br.ufjf.dcc196.humbertosampaio.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText _txtNumero1;
    private EditText _txtNumero2;

    private ToggleButton _tglSoma;
    private ToggleButton _tglSubtracao;
    private ToggleButton _tglMultiplicacao;
    private ToggleButton _tglDivisao;
    private CompoundButton _botaoAtivo;

    private TextView _lblResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        atribuirCamposPrivados();
        adicionarEventosAosBotoes();
        adicionarEventosAosCamposDeTexto();
    }

    public void calcular(Button button) {
        Double numero1 = obterTextoComoDouble(_txtNumero1);
        Double numero2 = obterTextoComoDouble(_txtNumero2);
        double resultado = 0.0;
        String erro = "";

        ToggleButton botao = (ToggleButton)button;

        switch (botao.getId()) {
            case R.id.tglSoma:
                resultado = numero1 + numero2;
                break;
            case R.id.tglSubtracao:
                resultado = numero1 - numero2;
                break;
            case R.id.tglMultiplicacao:
                resultado = numero1 * numero2;
                break;
            case R.id.tglDivisao:
                if (numero2 == 0.0)
                    erro = "Erro! Divisão por 0.";
                else
                    resultado = numero1 / numero2;
                break;
        }

        if (erro.isEmpty()) {
            _lblResultado.setText(new DecimalFormat("#.######").format(resultado));
            return;
        }

        _lblResultado.setText(erro);
    }

    private void atribuirCamposPrivados() {
        _txtNumero1 = findViewById(R.id.txtNumero1);
        _txtNumero2 = findViewById(R.id.txtNumero2);

        _tglSoma = findViewById(R.id.tglSoma);
        _tglSubtracao = findViewById(R.id.tglSubtracao);
        _tglMultiplicacao = findViewById(R.id.tglMultiplicacao);
        _tglDivisao = findViewById(R.id.tglDivisao);

        _lblResultado = findViewById(R.id.lblResultado);
    }

    private void adicionarEventosAosBotoes() {
        CompoundButton.OnCheckedChangeListener ouvinte = (buttonView, isChecked) -> {
            if (isChecked) {
                desmarcarBotaoAtual();
                calcular(buttonView);
                _botaoAtivo = buttonView;
                return;
            }

            _botaoAtivo = null;
            _lblResultado.setText("");
        };

        _tglSoma.setOnCheckedChangeListener(ouvinte);
        _tglSubtracao.setOnCheckedChangeListener(ouvinte);
        _tglMultiplicacao.setOnCheckedChangeListener(ouvinte);
        _tglDivisao.setOnCheckedChangeListener(ouvinte);
    }

    private void adicionarEventosAosCamposDeTexto() {
        TextWatcher textWatcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (_botaoAtivo != null)
                    calcular(_botaoAtivo);
            }
        };

        _txtNumero1.addTextChangedListener(textWatcher);
        _txtNumero2.addTextChangedListener(textWatcher);
    }

    private void desmarcarBotaoAtual() {
        if (_botaoAtivo != null) {
            _botaoAtivo.setChecked(false);
        }
    }

    private Double obterTextoComoDouble(EditText editText) {
        double text = 0.0;

        try {
            text = Double.parseDouble(editText.getText().toString());
        } catch (Exception e) {

        }

        return text;
    }
}