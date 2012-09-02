
import java.io.*;
import javax.swing.*;

public class TextAreaPrintStream extends PrintStream {

	//The JTextArea to which the output stream will be redirected.
	private JTextArea textArea;


	/**
	 * Method TextAreaPrintStream
	 * The constructor of the class.
	 * @param the JTextArea to wich the output stream will be redirected.
	 * @param a standard output stream (needed by super method)
	 **/
	public TextAreaPrintStream(JTextArea area, OutputStream out) {
		super(out);
		textArea = area;
	}

	//Method println
	public void println(String string) {
		textArea.append(string+"\n");
	}

	//Method print
	public void print(String string) {
		textArea.append(string);
	}
}
