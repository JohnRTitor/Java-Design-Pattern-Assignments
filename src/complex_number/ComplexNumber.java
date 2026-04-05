package complex_number;

class ComplexNumber {

	private int real;
	private int imaginary;

	// Default Constructor
	ComplexNumber() {
		this.real = 0;
		this.imaginary = 0;
	}

	// Parameterized Constructor
	ComplexNumber(int real, int imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	// Overloaded add(int, int)
	void add(int real, int img) {
		this.real += real;
		this.imaginary += img;
	}

	// Overloaded add(ComplexNumber)
	void add(ComplexNumber cn) {
		this.real += cn.real;
		this.imaginary += cn.imaginary;
	}

	@Override
	public String toString() {
		if (imaginary >= 0)
			return real + " + " + imaginary + " i";
		else
			return real + " - " + (-imaginary) + " i";
	}
}
