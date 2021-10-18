package com.bug.design.pattern.solidprinciple;


class Document {
	public String name;
}

//We want this document to be printed, scan, fax.
interface MultiFunctionPrinter {
	void print();
	void scan();
	void fax();
}

//Suppose if we have a multiFunction device which supports all these above functionalities like print, scan, fax
class MultiFunctionDevice implements MultiFunctionPrinter{

	@Override
	public void print() {
		// TODO Auto-generated method stub
	}

	@Override
	public void scan() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fax() {
		// TODO Auto-generated method stub
	}
}

/**
 * Till this point we are good, suppose we have a old fashioned printer, which can only print the document.
 * Then implementing the MultiFunctionPrinter is pointless, because we are allowing the client to perform on 
 * the scan and fax.
 */
class OldFashionedDevice implements MultiFunctionPrinter{
	@Override
	public void print() {
		// TODO Auto-generated method stub
	}

	@Override
	public void scan() {
		/**
		 * Not required
		 */
		// TODO Auto-generated method stub
	}

	@Override
	public void fax() {
		/**
		 * Not required
		 */
		// TODO Auto-generated method stub
	}
}

/**
 * To overcome the issue. We have ISP principle, which states that the interface should have only the required 
 * functionalities which the client require.
 * To achieve this we have to segregate the interface like below
 */
interface Printer{
	void print();
}

//Now the old fashioned printer can use this interface and achieve the print mechanism
class OldFashionedPrinter implements Printer{

	@Override
	public void print() {
		// TODO Auto-generated method stub
	}
}

//Like the Printer interface we can segregate the scan and fax mechanism.
interface Scan{
	void scan();
}

//Created a multiFunctionMechanism which extends both Printer and Scan interface and can have fax mechanism inside it.
interface MultiFunctionMechanism extends Printer, Scan{
	void fax();
}

public class InterfaceSegregationPrinciple implements MultiFunctionMechanism{

	@Override
	public void print() {
		// TODO Auto-generated method stub
	}

	@Override
	public void scan() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fax() {
		// TODO Auto-generated method stub
	}
}
