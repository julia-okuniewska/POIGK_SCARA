import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.behaviors.vp.ViewPlatformBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Okno extends Applet implements KeyListener, MouseListener, MouseMotionListener, ActionListener {

	private static final long	serialVersionUID	= 1L;

	SimpleUniverse				universe			= null;
	public BranchGroup			root				= null;
	Canvas3D					canvas				= null;
	OrbitBehavior				orbita				= null;
	BoundingSphere				bounds				= null;
	Point3d						boundsHome			= null;

	GraphicsConfiguration		config				= null;

	DirectionalLight			swiatlo1			= null;

	JPanel						aboveCanvas			= null;
	JButton						lewo				= null;
	JButton						prawo				= null;
	JButton						gora				= null;
	JButton						dol					= null;
	JButton						home				= null;

	Scara						scara				= null;

	public Okno() {

		setLayout(new BorderLayout());
		config = SimpleUniverse.getPreferredConfiguration();
		bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		canvas = new Canvas3D(config);
		canvas.addKeyListener(this);
		canvas.addMouseListener(this);
		canvas.addMouseMotionListener(this);

		utworzGUI();

		add("North", aboveCanvas);
		add("Center", canvas);
		add("South", new Label("Sterowanie?"));

		root = new BranchGroup();
		universe = new SimpleUniverse(canvas);
		orbita = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
		orbita.setSchedulingBounds(bounds);

		Transform3D homeTransform = new Transform3D();
		homeTransform.setTranslation(new Vector3f(0.0f, 0.0f, 3.0f));

		utworzOswietlenie();
		scara = new Scara(root);

		universe.getViewingPlatform().setNominalViewingTransform();
		universe.getViewingPlatform().setViewPlatformBehavior(orbita);
		universe.getViewingPlatform().getViewPlatformBehavior().setHomeTransform(homeTransform);

		universe.addBranchGraph(root);
	}

	void utworzOswietlenie() {

		Color3f swiatlo1_kolor = new Color3f(1.8f, 0.1f, 0.1f);
		Vector3f swiatlo1_kierunek = new Vector3f(5.0f, -5.0f, -12.0f);

		swiatlo1 = new DirectionalLight(swiatlo1_kolor, swiatlo1_kierunek);
		swiatlo1.setInfluencingBounds(bounds);
		root.addChild(swiatlo1);
	}

	@Override
	public void keyPressed(KeyEvent e) {

		int keyID = e.getKeyCode();

		if (keyID == KeyEvent.VK_UP) {
			scara.obrocScara(Scara.W_GORE);
		} else if (keyID == KeyEvent.VK_DOWN) {
			scara.obrocScara(Scara.W_DOL);
		} else if (keyID == KeyEvent.VK_LEFT) {
			scara.obrocScara(Scara.DOLNY_W_LEWO);
		} else if (keyID == KeyEvent.VK_RIGHT) {
			scara.obrocScara(Scara.DOLNY_W_PRAWO);
		} else if (keyID == KeyEvent.VK_A) {
			scara.obrocScara(Scara.GORNY_W_LEWO);
		} else if (keyID == KeyEvent.VK_D) {
			scara.obrocScara(Scara.GORNY_W_PRAWO);
		} else if (keyID == KeyEvent.VK_SPACE) {
			System.out.println("z³ap");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	void utworzGUI() {

		aboveCanvas = new JPanel();

		gora = new JButton("gora");
		dol = new JButton("dol");
		lewo = new JButton("lewo");
		prawo = new JButton("prawo");
		home = new JButton("home");

		gora.addActionListener(this);
		dol.addActionListener(this);
		lewo.addActionListener(this);
		prawo.addActionListener(this);
		home.addActionListener(this);

		aboveCanvas.add(gora);
		aboveCanvas.add(dol);
		aboveCanvas.add(lewo);
		aboveCanvas.add(prawo);
		aboveCanvas.add(home);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object source = e.getSource();

		// TODO obarcanie œrodowiska przyciskami

		if (source == gora) {
			System.out.println("w gore");
		} else if (source == dol) {
			System.out.println("w dol");
		} else if (source == lewo) {
			System.out.println("w lewo");

		} else if (source == prawo) {
			System.out.println("w prawo");
		} else if (source == home) {
			orbita.goHome();
		}

	}

}
