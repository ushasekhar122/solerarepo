package createcalculator.main;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import createcalculator.exception.EquationInCompleteException;
import createcalculator.exception.InvalidOperatorException;
import createcalculator.exception.InvalidValueException;
import createcalculator.runtimepoly.Arithmetic;
import createcalculator.runtimepoly.ArithmeticImpl;
import createcalculator.runtimepoly.ScientificOp;
import createcalculator.runtimepoly.TrignometricOp;

public class CalculatorMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Eneter Expression:");
		StringBuilder inputExpression = new StringBuilder();
		// input process.
		while (true) {
			System.out.println("pls enter 'return' word for submit the input..");
			String expression = scanner.nextLine();
			if (expression.endsWith("return")) {
				inputExpression
						.append(new StringBuilder(expression).delete(expression.length() - 6, expression.length()));
				break;
			}
			inputExpression.append(expression);
		}
		// final Expression.
		String expression = inputExpression.toString().trim();
		
		try {
			boolean matches = String.valueOf(expression.charAt(expression.length() - 1)).matches("[a-z|A-Z|+|-|*|/]");
			if (matches) {
				throw new EquationInCompleteException(
						"pls do not expression ends with  Characters(A-Z) or Operators(+, -, *, /) ");
			}

			String[] split = expression.split("[+|*|/|-]");
			double finalResult = 0.0;
			StringBuffer bufferForOperation = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String s = split[i];
				double resultsOfArtAndSciOperations = 0.0;
				if (!(s.matches("[0-9]+"))) {
					if (!s.endsWith(")")) {
						throw new InvalidValueException("Invalid method Syntax :" + s);
					}
					Double valueForArtAndSciOperations = Double
							.parseDouble(s.substring(s.indexOf("(") + 1, s.indexOf(")")));
					String methodName = s.substring(0, s.indexOf("("));
					TrignometricOp trignometircOp = Arrays.stream(TrignometricOp.values())
							.filter(val -> val.name().equals(methodName)).findFirst().orElse(null);
					if (Objects.nonNull(trignometircOp)) {
						resultsOfArtAndSciOperations = applyTrignometricOperation(methodName,
								valueForArtAndSciOperations);
					} else {
						ScientificOp scintificOp = Arrays.stream(ScientificOp.values())
								.filter(val -> val.name().equals(methodName)).findFirst().orElse(null);
						if (Objects.nonNull(scintificOp)) {
							resultsOfArtAndSciOperations = applySciOperation(methodName, valueForArtAndSciOperations);
						} else {
							throw new IllegalArgumentException("Invalid Operation :" + methodName);
						}
					}
				} else {
					resultsOfArtAndSciOperations = Double.parseDouble(s);
				}
				if (i == 0) {
					finalResult = resultsOfArtAndSciOperations;
					StringBuffer stringBuffer = new StringBuffer(expression);
					bufferForOperation = stringBuffer.delete(0, s.length());
				}
				if (i > 0) {
					char artOpn = bufferForOperation.charAt(0);
					finalResult = appyArithOperations(finalResult, resultsOfArtAndSciOperations, artOpn);
					bufferForOperation = bufferForOperation.delete(0, s.length() + 1);
				}
			}

			System.out.println("final Result : " + finalResult);

		} catch (EquationInCompleteException | InvalidOperatorException | InvalidValueException
				| IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}

	}

	public static double applyTrignometricOperation(String operation, Double value) throws InvalidOperatorException {
		TrignometricOp key = TrignometricOp.valueOf(operation);
		double result;
		switch (key) {
		case sin: {
			result = Math.round(Math.sin(value));
			break;
		}
		case cos: {
			result = Math.round(Math.cos(value));
			break;
		}
		case tan: {
			result = Math.round(Math.tan(value));
			break;
		}
		case sec: {
			double cos = Math.cos(value);
			result = Math.round(1 / cos);
			break;
		}
		default:
			throw new InvalidOperatorException("Invalid Trignometirc Opetation : " + key);
		}
		return result;

	}

	public static double applySciOperation(String operation, Double value) throws InvalidOperatorException {
		ScientificOp key = ScientificOp.valueOf(operation);
		double result;
		switch (key) {
		case abs: {
			result = Math.round(Math.abs(value));
			break;
		}
		case sqrt: {
			result = Math.round(Math.sqrt(value));
			break;
		}
		default:
			throw new InvalidOperatorException("Invalid Scintific Opetation : " + key);
		}
		return result;

	}

	public static double appyArithOperations(double first, double second, char key) throws InvalidOperatorException {

		Arithmetic ar = new ArithmeticImpl();
		double result;
		switch (key) {
		case '+': {
			result = ar.add(first, second);
			break;
		}
		case '-': {
			result = ar.sub(first, second);
			break;
		}
		case '*': {
			result = ar.mul(first, second);
			break;
		}
		case '/': {
			result = ar.div(first, second);
			break;
		}
		default:
			throw new InvalidOperatorException("Invalid Arithmatic Opetation : " + key);
		}
		return result;

	}

}