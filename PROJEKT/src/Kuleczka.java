import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.media.j3d.*;
import javax.vecmath.*;

public class Kuleczka extends Applet implements ActionListener, KeyListener {

	
	private static final long serialVersionUID = 1L;
	private Button go = new Button("Go");
	private TransformGroup objTransformacja, objTransformacja2, objTransformacja3;
	private Transform3D trans3 = new Transform3D();
	private float height = 0.0f, sign = 1.0f, yloc3 = 0.05f;
	private Timer timer;

	public BranchGroup tworzenieGrafuSceny() {

		BranchGroup grafSceny = new BranchGroup();
		objTransformacja = new TransformGroup();
		objTransformacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		grafSceny.addChild(objTransformacja);

		objTransformacja2 = new TransformGroup();
		objTransformacja2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		grafSceny.addChild(objTransformacja2);

		objTransformacja3 = new TransformGroup();
		objTransformacja3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		grafSceny.addChild(objTransformacja3);

// setting parameters to ramie1
		Cylinder ramie1 = new Cylinder(0.5f, 0.25f);

		Appearance wygladRamie1 = new Appearance();
		wygladRamie1.setColoringAttributes(new ColoringAttributes(new Color3f(0.0f, 0.3f, 0.5f), 1));
		Material mat = new Material();
		mat.setAmbientColor(new Color3f(0.0f, 0.2f, 1.0f));
		wygladRamie1.setMaterial(mat);
		ramie1.setAppearance(wygladRamie1);

		objTransformacja = new TransformGroup();
		objTransformacja.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D pos1 = new Transform3D();
		pos1.setTranslation(new Vector3f(-0.4f, 0.0f, 0.0f));
		objTransformacja.setTransform(pos1);
		objTransformacja.addChild(ramie1);
		grafSceny.addChild(objTransformacja);

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Color3f light1Color = new Color3f(1.0f, 0.0f, 0.2f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		grafSceny.addChild(light1);
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.2f);
		AmbientLight light2 = new AmbientLight(ambientColor);
		light2.setInfluencingBounds(bounds);
		grafSceny.addChild(light2);

// setting parameters to ramie2
		Cylinder ramie2 = new Cylinder(0.5f, 0.25f);

		Appearance wygladRamie2 = new Appearance();
		wygladRamie2.setColoringAttributes(new ColoringAttributes(new Color3f(0.0f, 0.3f, 0.5f), 1));
		Material mat2 = new Material();
		mat2.setAmbientColor(new Color3f(0.0f, 0.2f, 1.0f));
		wygladRamie2.setMaterial(mat2);
		ramie2.setAppearance(wygladRamie2);

		objTransformacja2 = new TransformGroup();
		objTransformacja2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D pos2 = new Transform3D();
		pos2.setTranslation(new Vector3f(0.45f, 0.25f, 0.0f));
		objTransformacja2.setTransform(pos2);
		objTransformacja2.addChild(ramie2);
		grafSceny.addChild(objTransformacja2);

// setting parameters to ramie1
		Cylinder ramie3 = new Cylinder(0.05f, 1.0f);

		Appearance wygladRamie3 = new Appearance();
		wygladRamie3.setColoringAttributes(new ColoringAttributes(new Color3f(0.0f, 0.3f, 0.5f), 1));
		Material mat3 = new Material();
		mat3.setAmbientColor(new Color3f(0.0f, 0.2f, 1.0f));
		wygladRamie3.setMaterial(mat3);
		ramie1.setAppearance(wygladRamie3);

		objTransformacja3 = new TransformGroup();
		objTransformacja3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D pos3 = new Transform3D();
		pos3.setTranslation(new Vector3f(0.9f, 0.05f, 0.0f));
		objTransformacja3.setTransform(pos3);
		objTransformacja3.addChild(ramie3);
		grafSceny.addChild(objTransformacja3);

		return grafSceny;

	}

	public Kuleczka() {

		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c = new Canvas3D(config);
		add(BorderLayout.CENTER, c);

		c.addKeyListener(this);
		BranchGroup scene = tworzenieGrafuSceny();
		SimpleUniverse u = new SimpleUniverse();
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);

	}

	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			yloc3 += 0.1f;
			break;
		case KeyEvent.VK_DOWN:
			yloc3 -= 0.1f;
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void actionPerformed(ActionEvent e) {
	}

	public static void main(String[] args) {

		Kuleczka bb = new Kuleczka();
		//bb.addKeyListener(bb);
	//	MainFrame mf = new MainFrame(bb, 256, 256);

	}
}
