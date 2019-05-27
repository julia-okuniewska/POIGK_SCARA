import java.io.File;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.Cylinder;

public class Scara {

	BranchGroup				root			= null;

	File					trzon, dolny, gorny, pionowy = null;

	Transform3D				pos0, pos1, pos2, pos3 = null;

	TransformGroup			tf0, tf1, tf2, tf3 = null;

	RotationInterpolator	obracacz		= null;

	BoundingSphere			bounds			= null;

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

		trzon = new java.io.File("src/trzon.obj");
		dolny = new java.io.File("src/dolny.obj");
		gorny = new java.io.File("src/gorny.obj");

		pos0 = new Transform3D();
		pos1 = new Transform3D();
		pos2 = new Transform3D();
		pos3 = new Transform3D();

		pos0.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		pos0.setRotation(new AxisAngle4f(0.0f, 0.0f, 0.0f, 0.0f));
		pos1.setTranslation(new Vector3f(0.0f, 0.25f, 0.0f));
		pos1.setRotation(new AxisAngle4f(0.0f, 0.0f, 1.0f, -(float) Math.PI / 2));
		pos2.setTranslation(new Vector3f(0.38f, 0.07f, 0.0f));
		pos3.setTranslation(new Vector3f(-0.1f, -0.08f, 0.0f));

		Transform3D tmp_rot = new Transform3D();

		tmp_rot.rotZ(Math.PI / 2);

		pos1.mul(tmp_rot);
		pos3.mul(tmp_rot);

		tf0 = new TransformGroup(pos0);
		tf1 = new TransformGroup(pos1);
		tf2 = new TransformGroup(pos2);
		tf3 = new TransformGroup(pos3);

		tf0.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tf1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tf2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tf3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		bounds = new BoundingSphere();
		Alpha alpha_animacja = new Alpha(-1, 5000);
		obracacz = new RotationInterpolator(alpha_animacja, tf2);
		obracacz.setSchedulingBounds(bounds);

		dodajElement(trzon, tf0);
		dodajElement(dolny, tf1);
		dodajElement(gorny, tf2);

		tf0.addChild(tf1);
		tf1.addChild(tf2);
		tf2.addChild(tf3);

		root.addChild(tf0);

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
			System.out.println("obracam w gore");
			pos3.setTranslation(new Vector3f(xloc, 0.0f, 0.0f));
			tf3.setTransform(pos3);

		} else if (kierunek == W_DOL) {
			xloc -= 0.01f;
			System.out.println("obracam w gore");
			pos3.setTranslation(new Vector3f(xloc, 0.0f, 0.0f));
			tf3.setTransform(pos3);

		}

	}

}
