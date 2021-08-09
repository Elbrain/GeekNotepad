package uk.org.websolution.trader_org_tool.calculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import uk.org.websolution.trader_org_tool.R;


public class CalculatorFragment extends Fragment {
    private TextView calcText;
    private MaterialButton btnNumber1, btnNumber2, btnNumber3, btnNumber4, btnNumber5, btnNumber6, btnNumber7, btnNumber8, btnNumber9, btnNumber0;
    private MaterialButton btnAc, btnC, btnPlus, btnMinus, btnMult, btnDiv, btnDot, btnEqually;
    private Calculator calculator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calc, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        init();
    }

    private void init() {

        Toast notNumber = Toast.makeText(getView().getContext(), "Please enter the number", Toast.LENGTH_SHORT);
        calculator = new Calculator();
        calcText = getView().findViewById(R.id.calcView);

        btnNumber0 = getView().findViewById(R.id.button0);
        btnNumber0.setOnClickListener(v -> calcText.append(btnNumber0.getText()));

        btnNumber1 = getView().findViewById(R.id.button1);
        btnNumber1.setOnClickListener(v -> calcText.append(btnNumber1.getText()));

        btnNumber2 = getView().findViewById(R.id.button2);
        btnNumber2.setOnClickListener(v -> calcText.append(btnNumber2.getText()));

        btnNumber3 = getView().findViewById(R.id.button3);
        btnNumber3.setOnClickListener(v -> calcText.append(btnNumber3.getText()));

        btnNumber4 = getView().findViewById(R.id.button4);
        btnNumber4.setOnClickListener(v -> calcText.append(btnNumber4.getText()));

        btnNumber5 = getView().findViewById(R.id.button5);
        btnNumber5.setOnClickListener(v -> calcText.append(btnNumber5.getText()));

        btnNumber6 = getView().findViewById(R.id.button6);
        btnNumber6.setOnClickListener(v -> calcText.append(btnNumber6.getText()));

        btnNumber7 = getView().findViewById(R.id.button7);
        btnNumber7.setOnClickListener(v -> calcText.append(btnNumber7.getText()));

        btnNumber8 = getView().findViewById(R.id.button8);
        btnNumber8.setOnClickListener(v -> calcText.append(btnNumber8.getText()));

        btnNumber9 = getView().findViewById(R.id.button9);
        btnNumber9.setOnClickListener(v -> calcText.append(btnNumber9.getText()));

        btnC = getView().findViewById(R.id.buttonC);
        btnC.setOnClickListener(v -> calcText.setText(calculator.clearSymbol(calcText.getText().toString())));

        btnAc = getView().findViewById(R.id.buttonAc);
        btnAc.setOnClickListener(v -> calcText.setText(calculator.clearAll()));

        btnPlus = getView().findViewById(R.id.buttonPlus);
        btnPlus.setOnClickListener(v -> {
            if (calculator.checkSymbol(calcText.getText().toString())) {
                calcText.append(btnPlus.getText());
            } else {
                notNumber.show();
            }
        });

        btnMinus = getView().findViewById(R.id.buttonMinus);
        btnMinus.setOnClickListener(v -> {
            if (calculator.checkSymbol(calcText.getText().toString())) {
                calcText.append(btnMinus.getText());
            } else {
                notNumber.show();
            }
        });

        btnMult = getView().findViewById(R.id.buttonMult);
        btnMult.setOnClickListener(v -> {
            if (calculator.checkSymbol(calcText.getText().toString())) {
                calcText.append(btnMult.getText());
            } else {
                notNumber.show();
            }
        });

        btnDiv = getView().findViewById(R.id.buttonDiv);
        btnDiv.setOnClickListener(v -> {
            if (calculator.checkSymbol(calcText.getText().toString())) {
                calcText.append(btnDiv.getText());
            } else {
                notNumber.show();
            }
        });

        btnDot = getView().findViewById(R.id.buttonDot);
        btnDot.setOnClickListener(v -> {
            if (calculator.checkSymbol(calcText.getText().toString())) {
                calcText.append(btnDot.getText());
            } else {
                notNumber.show();
            }
        });

        btnEqually = getView().findViewById(R.id.buttonEqually);
        btnEqually.setOnClickListener(v -> {
            if (calculator.checkSymbol(calcText.getText().toString())) {
                calcText.setText(calculator.equally(calcText.getText().toString()));
            } else {
                notNumber.show();
            }
        });
    }
}
