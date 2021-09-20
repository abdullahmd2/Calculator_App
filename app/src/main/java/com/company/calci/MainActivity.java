package com.company.calci;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnAC, btnDel, btnDivide, btnMulti, btnPlus, btnMinus, btnEqual, btnDot;

    private TextView textViewResult, textViewHistory;

    private String number = null;

    double firstNumber = 0;
    double lastNumber = 0;

    String status = null;
    boolean operator = false;

    DecimalFormat myFormat = new DecimalFormat("######.######");

    String history, currentResult;

    boolean dot = true;

    boolean btnAcControl = true;

    boolean btnEqualsControl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);

        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);
        btnDivide = findViewById(R.id.btnDivide);
        btnMulti = findViewById(R.id.btnMulti);

        btnAC = findViewById(R.id.btnAC);
        btnDel = findViewById(R.id.btnDel);
        btnDot = findViewById(R.id.btnDot);
        btnEqual = findViewById(R.id.btnEqual);

        textViewResult = findViewById(R.id.textViewResult);
        textViewHistory = findViewById(R.id.textViewHistory);

        btn0.setOnClickListener(v -> numberClick("0"));
        btn1.setOnClickListener(v -> numberClick("1"));
        btn2.setOnClickListener(v -> numberClick("2"));
        btn3.setOnClickListener(v -> numberClick("3"));
        btn4.setOnClickListener(v -> numberClick("4"));
        btn5.setOnClickListener(v -> numberClick("5"));
        btn6.setOnClickListener(v -> numberClick("6"));
        btn7.setOnClickListener(v -> numberClick("7"));
        btn8.setOnClickListener(v -> numberClick("8"));
        btn9.setOnClickListener(v -> numberClick("9"));

        btnAC.setOnClickListener(v -> {
            status = null;
            number = null;
            textViewResult.setText("");
            textViewHistory.setText("");
            firstNumber = 0;
            lastNumber = 0;
            dot = true;
            btnAcControl = true;
        });
        btnDel.setOnClickListener(v -> {
            if (btnAcControl) {
                textViewResult.setText("");
            } else {
                number = number.substring(0, number.length() - 1);

                if (number.length() == 0)
                    btnDel.setClickable(false);
                else if (number.contains("."))
                    dot = false;
                else
                    dot = true;

                textViewResult.setText(number);
            }
        });

        btnPlus.setOnClickListener(v -> {
            history = textViewHistory.getText().toString();
            currentResult = textViewResult.getText().toString();
            textViewHistory.setText(history + currentResult + "+");

            if (operator) {
                if (status == "multiplication")
                    multiply();
                else if (status == "division")
                    divide();
                else if (status == "subtraction")
                    minus();
                else
                    plus();
            }
            status = "sum";
            operator = false;
            number = null;
        });
        btnMinus.setOnClickListener(v -> {
            history = textViewHistory.getText().toString();
            currentResult = textViewResult.getText().toString();
            textViewHistory.setText(history + currentResult + "-");

            if (operator) {
                if (status == "multiplication")
                    multiply();
                else if (status == "division")
                    divide();
                else if (status == "sum")
                    plus();
                else
                    minus();
            }
            status = "subtraction";
            operator = false;
            number = null;
        });
        btnMulti.setOnClickListener(v -> {
            history = textViewHistory.getText().toString();
            currentResult = textViewResult.getText().toString();
            textViewHistory.setText(history + currentResult + "x");

            if (operator) {
                if (status == "sum")
                    plus();
                else if (status == "division")
                    divide();
                else if (status == "subtraction")
                    minus();
                else
                    multiply();
            }
            status = "multiplication";
            operator = false;
            number = null;
        });
        btnDivide.setOnClickListener(v -> {
            history = textViewHistory.getText().toString();
            currentResult = textViewResult.getText().toString();
            textViewHistory.setText(history + currentResult + "/");

            if (operator) {
                if (status == "multiplication")
                    multiply();
                else if (status == "sum")
                    plus();
                else if (status == "subtraction")
                    minus();
                else
                    divide();
            }
            status = "division";
            operator = false;
            number = null;
        });

        btnEqual.setOnClickListener(v -> {
            if (operator) {
                if (status == "sum")
                    plus();
                else if (status == "subtraction")
                    minus();
                else if (status == "multiplication")
                    multiply();
                else if (status == "division")
                    divide();
                else
                    firstNumber = Double.parseDouble(textViewResult.getText().toString());
            }
            operator = false;
            btnEqualsControl = true;
        });
        btnDot.setOnClickListener(v -> {
            if (dot) {
                if (number == null)
                    number = "0.";
                else
                    number = number + ".";
            }

            textViewResult.setText(number);
            dot = false;
        });
    }

    public void numberClick(String view) {
        if (number == null) {
            number = view;
        } else if (btnEqualsControl) {
            firstNumber = 0;
            lastNumber = 0;
            number = view;
        } else {
            number = number + view;
        }

        textViewResult.setText(number);
        operator = true;
        btnAcControl = false;
        btnDel.setClickable(true);
        btnEqualsControl = false;
    }

    public void plus() {
        lastNumber = Double.parseDouble(textViewResult.getText().toString());
        firstNumber = firstNumber + lastNumber;
        textViewResult.setText(myFormat.format(firstNumber));
        dot = true;
    }

    public void minus() {
        if (firstNumber == 0) {
            firstNumber = Double.parseDouble(textViewResult.getText().toString());
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber - lastNumber;
        }
        textViewResult.setText(myFormat.format(firstNumber));
        dot = true;
    }

    public void multiply() {
        if (firstNumber == 0) {
            firstNumber = 1;
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber * lastNumber;
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber * lastNumber;
        }
        textViewResult.setText(myFormat.format(firstNumber));
        dot = true;
    }

    public void divide() {
        if (firstNumber == 0) {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = lastNumber / 1;
        } else {
            lastNumber = Double.parseDouble(textViewResult.getText().toString());
            firstNumber = firstNumber / lastNumber;
        }
        textViewResult.setText(myFormat.format(firstNumber));
        dot = true;
    }
}