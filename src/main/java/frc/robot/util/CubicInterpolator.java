package frc.robot.util;

public class CubicInterpolator {

	private double courbure, deadzoneVitesse, deadzoneJoystick;

	/**
	 * 
	 * @param courbure Définit le niveau de courbure. 0 : DROIT => 1 : COURBE MAXIMALE
	 * @param deadzoneVitesse Définit le niveau minimal de vitesse. 
	 * @param deadzoneJoystick Définit le niveau minimal de poussée sur le joystick.
	 */
	public CubicInterpolator(double _courbure, double _deadzoneVitesse, double _deadzoneJoystick) {

		this.courbure = _courbure;
		this.deadzoneVitesse = _deadzoneVitesse;
		this.deadzoneJoystick = _deadzoneJoystick;

	}

	public double interpolate(double valeur) {

		if (valeur >= deadzoneJoystick) {

			return deadzoneVitesse + (1 - deadzoneVitesse) * (courbure* valeur * valeur * valeur + (1 - courbure) * valeur);

		} else if (valeur <= -deadzoneJoystick) {

			return -deadzoneVitesse + (1 - deadzoneVitesse) * (courbure* valeur * valeur * valeur + (1 - courbure) * valeur);

		} else {

			return interpolate(deadzoneJoystick) / deadzoneJoystick * valeur;

		}
	}

	public double getA() {
		return courbure;
	}

	public void setCourbure(double _courbure) {

		this.courbure = _courbure;
	}

	public double getDeadzoneVitesse() {
		return deadzoneVitesse;
	}

	public void setDeadzoneVitesse(double _deadzoneVitesse) {
		this.deadzoneVitesse = _deadzoneVitesse;
	}

	public double getDeadzoneJoystick() {
		return deadzoneJoystick;
	}

	public void setDeadzoneJoystick(double _deadzoneJoystick) {
		this.deadzoneJoystick = _deadzoneJoystick;
	}

}