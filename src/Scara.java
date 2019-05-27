import javax.media.j3d.BranchGroup;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cylinder;

public class Scara {

	BranchGroup				root				= null;

	Cylinder				trzon				= null;
	Cylinder				ramie1				= null;
	Cylinder				ramie2				= null;

	Transform3D				trzonTrans			= null;
	Transform3D				ramie1Trans			= null;
	Transform3D				ramie2Trans			= null;

	TransformGroup			trzonTransGroup		= null;
	TransformGroup			ramie1TransGroup	= null;
	TransformGroup			ramie2TransGroup	= null;

	final static public int	GORNY_W_LEWO		= 0;
	final static public int	GORNY_W_PRAWO		= 1;
	final static public int	W_GORE				= 2;
	final static public int	W_DOL				= 3;
	final static public int	DOLNY_W_LEWO		= 4;
	final static public int	DOLNY_W_PRAWO		= 5;

	Scara(BranchGroup root) {
		this.root = root;
		utworzScara();
	}

	void utworzScara() {

		trzon = new Cylinder(0.05f, 0.4f);
		ramie1 = new Cylinder(0.05f, 0.3f);
		ramie2 = new Cylinder(0.05f, 0.2f);

		trzonTrans = new Transform3D();
		ramie1Trans = new Transform3D();
		ramie2Trans = new Transform3D();

		Vector3f vPrzesunieciaRamie1 = new Vector3f(0.0f, 0.4f, 0.0f);
		ramie1Trans.setTranslation(vPrzesunieciaRamie1);

		Vector3f vPrzesunieciaRamie2 = new Vector3f(0.3f, 0.4f, 0.0f);
		ramie2Trans.setTranslation(vPrzesunieciaRamie2);

		Transform3D tmp_rot = new Transform3D();

		tmp_rot.rotZ(Math.PI / 2);

		ramie1Trans.mul(tmp_rot);
		ramie2Trans.mul(tmp_rot);

		trzonTransGroup = new TransformGroup(trzonTrans);
		ramie1TransGroup = new TransformGroup(ramie1Trans);
		ramie2TransGroup = new TransformGroup(ramie2Trans);

		trzonTransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		ramie1TransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		ramie2TransGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		trzonTransGroup.addChild(trzon);
		ramie1TransGroup.addChild(ramie1);
		ramie2TransGroup.addChild(ramie2);

		root.addChild(trzonTransGroup);
		root.addChild(ramie1TransGroup);
		root.addChild(ramie2TransGroup);
	}

	void obrocScara(int kierunek) {

		float sign = 1.0f;
		Transform3D tmp_rot = new Transform3D();

		if (kierunek == DOLNY_W_PRAWO) {

			tmp_rot.rotX(Math.PI / 12);
			ramie2Trans.mul(tmp_rot);
			ramie2TransGroup.setTransform(ramie2Trans);
			System.out.println("obracan dolny w prawo");

		} else if (kierunek == DOLNY_W_LEWO) {
			System.out.println("obracam dolny w lewo");

		} else if (kierunek == GORNY_W_PRAWO) {
			System.out.println("obracam gorny w prawo");

		} else if (kierunek == GORNY_W_LEWO) {
			System.out.println("obracam  gorny w lewo");

		} else if (kierunek == W_GORE) {
			System.out.println("obracam w gore");

		} else if (kierunek == W_DOL) {
			System.out.println("obracam w dol");
		}

	}

}
