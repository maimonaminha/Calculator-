
package com.example.calculatorv1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton button_c, button_openBracket, button_closeBracket;
    MaterialButton button_divide, button_multiply, button_plus, button_minus, button_equals;
    MaterialButton button_0, button_1, button_2, button_3, button_4, button_5, button_6, button_7, button_8, button_9;
    MaterialButton button_ac, button_dot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_box);
        solutionTv = findViewById(R.id.solution_box);


        button_c = assignId(R.id.button_c);
        button_openBracket = assignId(R.id.button_openBracket);
        button_closeBracket = assignId(R.id.button_closeBracket);
        button_divide = assignId(R.id.button_divide);
        button_multiply = assignId(R.id.button_multiply);
        button_plus = assignId(R.id.button_add);
        button_minus = assignId(R.id.button_minus);
        button_equals = assignId(R.id.button_equal);
        button_0 = assignId(R.id.button_0);
        button_1 = assignId(R.id.button_1);
        button_2 = assignId(R.id.button_2);
        button_3 = assignId(R.id.button_3);
        button_4 = assignId(R.id.button_4);
        button_5 = assignId(R.id.button_5);
        button_6 = assignId(R.id.button_6);
        button_7 = assignId(R.id.button_7);
        button_8 = assignId(R.id.button_8);
        button_9 = assignId(R.id.button_9);
        button_ac = assignId(R.id.button_ac);
        button_dot = assignId(R.id.button_dot);
    }


    MaterialButton assignId(int id) {
        MaterialButton btn = findViewById(id);
        btn.setOnClickListener(this);
        return btn;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("ac")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        if (buttonText.equals("C")) {
            // Check if dataToCalculate is not empty
            if (!dataToCalculate.isEmpty()) {
                // Remove the last character
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            }
            // If dataToCalculate is empty after backspace, set resultTv to "0"
            if (dataToCalculate.isEmpty()) {
                resultTv.setText("0");
            }
        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }

        solutionTv.setText(dataToCalculate);

        String finalResult = getResult(dataToCalculate);

        if (!finalResult.equals("Err") && !dataToCalculate.isEmpty()) {
            resultTv.setText(finalResult);
        }
    }





    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}
