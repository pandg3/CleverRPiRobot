package org.jointheleague.ecolban.cleverrobot;

/*********************************************************************************************
 * Vic's ultrasonic sensor running with Erik's Clever Robot for Pi
 * version 0.9, 170227
 **********************************************************************************************/
import java.io.IOException;
import java.util.Random;

import org.jointheleague.ecolban.rpirobot.IRobotAdapter;
import org.jointheleague.ecolban.rpirobot.IRobotInterface;
import org.jointheleague.ecolban.rpirobot.SerialConnection;
import org.jointheleague.ecolban.rpirobot.SimpleIRobot;

public class CleverRobot extends IRobotAdapter {
	Sonar sonar = new Sonar();
	private boolean tailLight;
	Camera cam;

	public CleverRobot(IRobotInterface iRobot) {
		super(iRobot);
	}

	public static void main(String[] args) throws Exception {
		System.out.println("Try event listner, rev Monday 2030");
		IRobotInterface base = new SimpleIRobot();
		CleverRobot rob = new CleverRobot(base);
		rob.setup();
		while (rob.loop()) {
		}
		rob.shutDown();

	}

	public boolean loop() throws Exception {
		readSensors(SENSORS_GROUP_ID100);
		Dragrace();
		//leds(139, 4, true);
		// Select Code
//		rightmaze();
		return true;

	}

	void rightmaze() throws InterruptedException, IOException {
		System.out.println(getWallSignal() + "");
		int Mlights[] = getLightBumps();
		drive(500, -220);
		if (Mlights[5] > 1 || Mlights[4] > 1 || Mlights[3] > 1) {
			driveDirect(-220, 500);
			Thread.sleep(3);

		}
	}

	private void setup() throws Exception {
		
		//drivePWM(255, 255); //13.37 seconds
		driveDirect(500, 500);
		//cam = new Camera(100, 100);
		// for(int i : cam.pixels){
		// System.out.println(i);
		// }
	}

	public void leftmaze() throws InterruptedException, IOException {
		System.out.println(getWallSignal() + "");
		int Mlights[] = getLightBumps();
		drive(500, 220);
		if (Mlights[0] > 1 || Mlights[1] > 1 || Mlights[2] > 1) {
			driveDirect(500, -220);
			Thread.sleep(3);

		}
	}

	public void Goldrush() throws InterruptedException, IOException {
		int Mlights[] = getLightBumps();
		System.out.println("Running Gold");
		if (getInfraredByte() == 248 || getInfraredByte() == 250) {
			// right infared
			driveDirect(-100, 100);
		} else if (getInfraredByte() == 248 || getInfraredByte() == 250) {
			driveDirect(100, -100);
		} else {
			int r = new Random().nextInt(2);

			if (r == 1) {
				drive(500, 220);
				if (Mlights[0] > 1 || Mlights[1] > 1 || Mlights[2] > 1) {
					driveDirect(500, -220);
					Thread.sleep(3);
				}
			} else {
				drive(500, -220);
				if (Mlights[5] > 1 || Mlights[4] > 1 || Mlights[3] > 1) {
					driveDirect(-220, 500);
					Thread.sleep(3);
				}
			}
			Thread.sleep(100);
		}
	}

	public void Dragrace() throws IOException, InterruptedException {
		
		int[] lights = getLightBumps();
		// Turn Left
		if (isBumpLeft() && isBumpRight()) {
			System.out.println("Turn around");
			driveDirect(-400, -400);
			Thread.sleep(1400);
			driveDirect(500, -500);
			Thread.sleep(900);
			drivePWM(255, 255);
		}

		// Turn Around

		else if (lights[5] > 1 || lights[4] > 1 || lights[3] > 1) {
			driveDirect(0, 500);

			System.out.println("Right");
			Thread.sleep(500);
			drivePWM(255, 255);
		} else if (lights[0] > 1 || lights[1] > 1 || lights[2] > 1) {
			driveDirect(500, 0);
			System.out.println("left");
			Thread.sleep(500);
			drivePWM(255, 255);
		} 
		else if (isBumpRight()) {
			driveDirect(-500, -500);
			Thread.sleep(300);
			driveDirect(100, 500);
			Thread.sleep(100);
			drivePWM(255, 255);
		} else if (isBumpLeft()) {
			driveDirect(-500, -500);
			Thread.sleep(300);
			driveDirect(500, 100);
			Thread.sleep(100);
			drivePWM(255, 255);
		}
	}

	private void shutDown() throws IOException {
		reset();
		stop();
		closeConnection();
	}
}
