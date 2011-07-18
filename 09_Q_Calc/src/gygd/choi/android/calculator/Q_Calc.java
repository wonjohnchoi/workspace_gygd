package gygd.choi.android.calculator;

import gygd.choi.android.calculator.R.id;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 
 * A class that uses both 'InfixEquation' and 'PostfixCalculator' classes to
 * make an calculation application using Android libraries. This program runs
 * fine on Android machine.
 * 
 * @author Wonjohn Choi
 * 
 */
public class Q_Calc extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Button btn = (Button) super.findViewById(id.click);
		
		btn.setOnClickListener(new View.OnClickListener() {

			EditText formula = (EditText) findViewById(id.input); // formula given by user
			TextView result = (TextView) findViewById(id.output); // result that will be shown to user

			PostfixCalculator pf = new PostfixCalculator();
			InfixEquation ifx = new InfixEquation();

			@Override
			public void onClick(View view) {

				if (formula.getText().toString().equals("")) {
					result.setText(R.string.noInput);
				} else {
					try {
						ifx.addInput(formula.getText().toString());
						pf.addInput(ifx.toPostfix());
						
						result.setText("Postfix Equation: "+pf.toString());
						result.append(String.format("\nResult: %.9f", pf.evaluate()));
					}

					catch (Exception e) {
						result.setText(R.string.wrongInput);
					}
				}
			}
		});

	}
}