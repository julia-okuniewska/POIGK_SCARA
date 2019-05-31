import java.io.File;

import javax.media.j3d.*;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;

public class Scara {

	BranchGroup				root			= null;

	File					trzon, dolny, gorny, pionowy = null;

	Transform3D				pos0, pos1, pos2, pos3, pos4, pos5, pos6 = null;

	TransformGroup			tf0, tf1, tf2, tf3, tf4, tf5, tf6 = null;

	Sphere					obiekt1, obiekt2, obiekt3 = null;

	Appearance				wygladKuli		= null;

	Material				materialKuli	= null;

	float					xloc			= 0;
	float					rotGorny, rotDolny = 0;

	final static public int	GORNY_W_LEWO	= 0;
	final static public int	GORNY_W_PRAWO	= 1;
	final static public int	W_GORE			= 2;
	final static public int	W_DOL			= 3;
	final static public int	DOLNY_W_LEWO	= 4;
	final static public int	DOLNY_W_PRAWO	= 5;

	Scara(BranchGroup root) {
		this.root = root;
		utworzScara();
	}

	void utworzScara() {

		Cylinder podloga = new Cylinder(20f, 0.2f);
		Transform3D podlogaTransf = new Transform3D();
		podlogaTransf.setTranslation(new Vector3f(0.0f, -0.1f, 0.0f));
		TransformGroup podlogaTg = new TransformGroup(podlogaTransf);
		podlogaTg.addChild(podloga);
		 root.addChild(podlogaTg);

		trzon = new java.io.File("src/trzon.obj");
		dolny = new java.io.File("src/dolny.obj");
		gorny = new java.io.File("src/gorny.obj");
		pionowy = new java.io.File("src/pionowy.obj");

		pos0 = new Transform3D();
		pos1 = new Transform3D();
		pos2 = new Transform3D();
		pos3 = new Transform3D();

		pos0.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		pos0.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f));
		pos1.setTranslation(new Vector3f(0.0f, 0.25f, 0.0f));
		pos1.setRotation(new AxisAngle4f(0.0f, 0.0f, 1.0f, -(float) Math.PI / 2));
		pos2.setTranslation(new Vector3f(0.38f, 0.07f, 0.0f));
		pos3.setTranslation(new Vector3f(0.375f, 0.1f, 0.0f));
		pos3.setRotation(new AxisAngle4f(1.0f, 0.0f, 0.0f, -(float) Math.PI / 2));

		Transform3D tmp_rot = new Transform3D();

		tmp_rot.rotZ(Math.PI / 2);
		pos1.mul(tmp_rot);

		tmp_rot.rotY(Math.PI);
		pos3.mul(tmp_rot);

		tf0 = new TransformGroup(pos0);
		tf1 = new TransformGroup(pos1);
		tf2 = new TransformGroup(pos2);
		tf3 = new TransformGroup(pos3);

		tf0.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tf1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tf2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tf3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		dodajElement(trzon, tf0);
		dodajElement(dolny, tf1);
		dodajElement(gorny, tf2);
		dodajElement(pionowy, tf3);

		tf0.addChild(tf1);
		tf1.addChild(tf2);
		tf2.addChild(tf3);

		root.addChild(tf0);

		utworzSrodowisko();

	}

	void dodajElement(File file, TransformGroup tg) {

		ObjectFile loader = new ObjectFile();
		Scene s = null;

		try {
			s = loader.load(file.toURI().toURL());
		} catch (Exception e) {
			System.err.println("nie wczytalem elementu SCAR'y");
			System.exit(1);
		}

		tg.addChild(s.getSceneGroup());
		tg.setPickable(false);
	}

	void obrocScara(int kierunek) {

		float sign = 1.0f;
		Transform3D tmp_rot = new Transform3D();

		if (kierunek == GORNY_W_PRAWO) {
			Transform3D trObrot = new Transform3D();
			trObrot.set(new AxisAngle4f(0, 1, 0, (float) Math.PI / 12));

			rotGorny += Math.PI / 24;
			trObrot.rotY(rotGorny);

			trObrot.setTranslation(new Vector3f(0.38f, 0.07f, 0.0f));

			tf2.setTransform(trObrot);

		} else if (kierunek == GORNY_W_LEWO) {

			Transform3D trObrot = new Transform3D();
			trObrot.set(new AxisAngle4f(0, 1, 0, (float) Math.PI / 12));

			rotGorny -= Math.PI / 24;
			trObrot.rotY(rotGorny);

			trObrot.setTranslation(new Vector3f(0.38f, 0.07f, 0.0f));

			tf2.setTransform(trObrot);

		} else if (kierunek == DOLNY_W_PRAWO) {

			Transform3D trObrot = new Transform3D();
			trObrot.set(new AxisAngle4f(0, 1, 0, (float) Math.PI / 12));

			rotDolny += Math.PI / 24;
			trObrot.rotY(rotDolny);

			trObrot.setTranslation(new Vector3f(0.0f, 0.25f, 0.0f));

			tf1.setTransform(trObrot);

		} else if (kierunek == DOLNY_W_LEWO) {

			Transform3D trObrot = new Transform3D();
			trObrot.set(new AxisAngle4f(0, 1, 0, (float) Math.PI / 12));

			rotDolny -= Math.PI / 24;
			trObrot.rotY(rotDolny);

			trObrot.setTranslation(new Vector3f(0.0f, 0.25f, 0.0f));

			tf1.setTransform(trObrot);

		} else if (kierunek == W_GORE) {
			xloc += 0.01f;
			pos3.setTranslation(new Vector3f(0.375f,  0.1f+xloc, 0.0f));
			tf3.setTransform(pos3);

		} else if (kierunek == W_DOL) {
			xloc -= 0.01f;
			pos3.setTranslation(new Vector3f(0.375f,  0.1f + xloc, 0.0f));
			System.out.println(xloc);
			tf3.setTransform(pos3);
			
			
			

		}

	}

	void utworzSrodowisko() {

		materialKuli = new Material(new Color3f(0.0f, 0.0f, 0.0f), new Color3f(0.77f, 0.77f, 0.8f),
				new Color3f(0.2f, 0.0f, 0.0f), new Color3f(1.0f, 1.0f, 1.0f), 100.0f);

		wygladKuli = new Appearance();
		wygladKuli.setColoringAttributes(new ColoringAttributes(0.9f, 0.9f, 0.98f, ColoringAttributes.NICEST));
		wygladKuli.setMaterial(materialKuli);

		obiekt1 = new Sphere(0.05f, wygladKuli);

		pos4 = new Transform3D();

		pos4.setTranslation(new Vector3f(0.7f, 0.0f, 0.3f));


		tf4 = new TransformGroup(pos4);

		tf4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		tf4.addChild(obiekt1);
		
		obiekt2 = new Sphere(0.05f, wygladKuli);
		
		pos5 = new Transform3D();

		pos5.setTranslation(new Vector3f(0.9f, -0.1f, 0.17f));

		tf5 = new TransformGroup(pos5);

		tf5.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		tf5.addChild(obiekt2);
		
		obiekt3 = new Sphere(0.05f, wygladKuli);
		
		pos6 = new Transform3D();

		pos6.setTranslation(new Vector3f(0.6f, -0.2f, 0.45f));

		tf6 = new TransformGroup(pos6);

		tf6.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		tf6.addChild(obiekt3);

		root.addChild(tf4);
		root.addChild(tf5);
		root.addChild(tf6);
	}

}
