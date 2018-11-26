/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.triggers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * Add your docs here.
 */
public class AxisDownTrigger extends Trigger {
	private GenericHID joystick;
	private int axis;
	
	public AxisDownTrigger(GenericHID joystick, int axis) {
	
		this.joystick = joystick;
		this.axis = axis;
		
	}

    public boolean get() {
        return joystick.getRawAxis(axis) > 0.5;
    }
  }

