import javax.media.j3d.BranchGroup;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;

public class Scara {

	BranchGroup				root			= null;

	Cylinder				trzon, dolny, gorny, pionowy = null;

	Transform3D				pos0, pos1, pos2, pos3 = null;

	TransformGroup			tf0, tf1, tf2, tf3 = null;
	
	float xloc = 0;
	
//	RotationInterpolator    obracacz

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

		trzon = new Cylinder(0.05f, 0.4f);
		dolny = new Cylinder(0.05f, 0.3f);
		gorny = new Cylinder(0.05f, 0.2f);
		pionowy = new Cylinder(0.01f, 0.4f);

		pos0 = new Transform3D();
		pos1 = new Transform3D();
		pos2 = new Transform3D();
		pos3 = new Transform3D();
		
		
		
		pos0.setTranslation(new Vector3f(-0.2f, -0.2f, 0.0f));
		pos1.setTranslation(new Vector3f(0.15f, 0.25f, 0.0f));
		pos2.setTranslation(new Vector3f(0.08f, -0.2f, 0.0f));
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

		tf0.addChild(trzon);
		tf1.addChild(dolny);
		tf2.addChild(gorny);
		tf3.addChild(pionowy);

		
		tf0.addChild(tf1);
		tf1.addChild(tf2);
		tf2.addChild(tf3);
		
		root.addChild(tf0);
	}

	void obrocScara(int kierunek) {

		float sign = 1.0f;
		Transform3D tmp_rot = new Transform3D();

		if (kierunek == DOLNY_W_PRAWO) {

			
			System.out.println("obracan dolny w prawo");

		} else if (kierunek == DOLNY_W_LEWO) {
			System.out.println("obracam dolny w lewo");

		} else if (kierunek == GORNY_W_PRAWO) {
			System.out.println("obracam gorny w prawo");
			
			AxisAngle4f osObrotu = new AxisAngle4f(1, 0, 0, (float)Math.PI);			
			pos2 = new Transform3D();
			pos2.set(osObrotu);			
			tmp_rot.rotY(Math.PI / 12);			
			pos2.setTranslation(new Vector3d(0,0.1,0));			
			pos2.mul(tmp_rot);
			tf2.setTransform(pos2);

		} else if (kierunek == GORNY_W_LEWO) {
			System.out.println("obracam  gorny w lewo");

		} else if (kierunek == W_GORE) {
			xloc+=0.01f;
			System.out.println("obracam w gore");
			pos3.setTranslation(new Vector3f(xloc,0.0f,0.0f));
			tf3.setTransform(pos3);
			

		} else if (kierunek == W_DOL) {
			xloc-=0.01f;
			System.out.println("obracam w gore");
			pos3.setTranslation(new Vector3f(xloc,0.0f,0.0f));
			tf3.setTransform(pos3);
			
		
		}

	}

}
