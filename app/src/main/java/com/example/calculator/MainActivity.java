package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.*;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputLayout result_txt_view = null;

    public void setText(View v) {
        result_txt_view.setError(null);
        String currentText = result_txt_view.getEditText().getText().toString();

        if (currentText.length() == 1 && currentText.equals("0")) {
            result_txt_view.getEditText().setText(((Button) v).getText());
        } else if (currentText.contains("=")) {
            String newText = (String) ((Button) v).getText();
            if (newText.matches("\\d+(?:\\.\\d+)?")) {
                result_txt_view.getEditText().setText(((Button) v).getText());
            } else {
                result_txt_view.getEditText().setText(currentText.replace("=", "") + ((Button) v).getText());
            }
        } else {
            result_txt_view.getEditText().setText(currentText + ((Button) v).getText());
        }
    }

    public void clearText(View v) {
        result_txt_view.setError(null);
        result_txt_view.getEditText().setText("0");
    }

    public void deleteSingleText(View v) {
        result_txt_view.setError(null);
        String currentText = result_txt_view.getEditText().getText().toString();
        if (currentText.length() <= 1) {
            result_txt_view.getEditText().setText("0");
        } else {
            String original_txt = result_txt_view.getEditText().getText().toString();
            result_txt_view.getEditText().setText(original_txt.substring(0, original_txt.length() - 1));
        }
    }

    public void calculateResult(View v) {
        result_txt_view.setError(null);
        String currentText = result_txt_view.getEditText().getText().toString();
        if (currentText.equals("")) {
            result_txt_view.getEditText().setText("0");
        } else if (currentText.contains("=")) {
            ;
        } else {
            try {
                MathEval math = new MathEval();
                double result = math.evaluate(result_txt_view.getEditText().getText().toString());
                if (Double.toString(result).equals("Infinity")) {
                    result_txt_view.setError("Division by zero not allowed");
                    result_txt_view.getEditText().setText("0");
                } else if (result % 1 == 0) {
                    result_txt_view.getEditText().setText("=" + (int) result);
                } else {
                    result_txt_view.getEditText().setText("=" + result);
                }
            } catch (Exception e) {
                result_txt_view.setError("Invalid expression");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result_txt_view = findViewById(R.id.result);
    }

}