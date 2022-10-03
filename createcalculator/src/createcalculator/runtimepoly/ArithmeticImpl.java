package createcalculator.runtimepoly;

import createcalculator.exception.ZeroDivisionException;

public class ArithmeticImpl extends Arithmetic{

	@Override
	public double add(double a, double b) {
		
		return a+b ;
	}

	@Override
	public double sub(double a, double b) {
		
		return a-b;
	}

	@Override
	public double mul(double a, double b) {
		
		return a*b;
	}

	@Override
	public double div(double a, double b) {
		try {
			if(b == 0.0) {
				throw new ZeroDivisionException("pls choose another value for '0'");
			}

		}catch (ZeroDivisionException e) {
			System.out.println(e.getMessage());
		}
		return a/b;
	}

}