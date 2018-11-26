package frc.robot.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class POVTrigger extends Trigger {
	
	public enum Arrow {
		
		UP(315, 0, 45),
		DOWN(135, 180, 225);
		
		private int angle1;
		private int angle2;
		private int angle3;
		
		
		private Arrow(int angle1, int angle2, int angle3) {
			this.angle1 = angle1;
			this.angle2 = angle2;
			this.angle3 = angle3;
		}
		
		private boolean isWithinRange(int pov) {
			return pov == angle1 || pov == angle2 || pov == angle3;
		}
		
		
	}
	
	
	private GenericHID joystick;
	private Arrow arrow;
	
	public POVTrigger(GenericHID joystick, Arrow arrow) {
		this.arrow = arrow;
		this.joystick = joystick;
	}

    public boolean get() {
        return arrow.isWithinRange(joystick.getPOV());
    }
}
