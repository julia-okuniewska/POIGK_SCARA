import com.sun.j3d.utils.applet.MainFrame;

public class Applikacja {

	static Okno okno = null;

	void Aplikacja() {
	}

	public static void main(String[] args) {

		okno = new Okno();
		new MainFrame(okno, 600, 400);

	}
}
