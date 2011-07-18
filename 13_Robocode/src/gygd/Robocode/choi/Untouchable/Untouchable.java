package gygd.Robocode.choi.Untouchable;

/**
 * 
 * Untouchable - a robot by Wonjohn Choi and Raafay Muzaffar
 * @author Wonjohn Choi, Raafay Muzaffar
 * @course ICS3U1
 * @date Jan. 19th, 2010
 */
 
import robocode.*;
import java.awt.Color;
 
public class Untouchable extends Robot
{
  //The following code starts the color array. The size of this array will be 4, since there will 4 different colors for the robot.
  Color getRandomColor[]=new Color[4];
//Main method. The program will run this each time for the robot, which means that the robot will be executing functions stated within the run routine each time.
//--------------------------------------------------------------------Main Method-------------------------------------------------------------------------------------
  public void run() 
  {
 
    //this will turn, and set the gun right 90 degrees. The reason for this is because our aim was to give the effect of moving the robot sideways, and this technique 
    //allows for that effect.
    this.turnGunRight(90);
 
    //for this method, the radar will also turn 90 degrees right. Reason-being was to move the radar with the gun, so that as soon as the robot detects an enemy, it can 
    //fire at it.
    this.turnRadarRight(90);
 
    //infinite loop. The condition in this while loop is true, which means that the code in the while loop will keep occurring over and over again
    while(true)
    { 
      for(int i=0;i<4;i++)//this for-loop will occur 4 times. The content in the loop will, therefore, also apply 4 times.
      {
        /*As the integer, i, is incrementing, the number of iterations is also increasing. "i" will be the position of the array, getRandomColor. Basically, what this
         *line of code will do is that the color will be creating a Color object in the getRandomColor array, based on the specified values for the HSB color model.
         *Each time, the float will be a random value, due to the Math.random method. Therefore, the color will be different each time.
         *The s and b components should be floating-point values between zero and one (numbers in the range 0.0-1.0). The h component can be any floating-point number.
         *The floor of this number is subtracted from it to create a fraction between 0 and 1. This fractional number is then multiplied by 360 to produce the hue angle in
         *the HSB color model.
         *h is the hue component
         *s is the saturation of the color
         *b is the bightness of the color
         */
 
        getRandomColor[i]=Color.getHSBColor((float)Math.random(),(float)Math.random(),(float)Math.random());
 
      }
 
      //This part will actually set the color of the robot's body, gun, radar, bullet, and scan arc in the same time. Each part of the robot will most likely be different.
      this.setColors(getRandomColor[0],getRandomColor[1],getRandomColor[2],Color.WHITE,getRandomColor[3]);
 
      for(int i=0;i<2;i++)
      {
        //The robot will turn the radar 360 degrees left (it will end up in the same position). The aim for this is to scan the entire environment.
        this.turnRadarLeft(360);
 
        //***This code will turn the robot right. This is corresponding to the other code marked ***. The reason for these corresponding codes is to avoid from stopping in 
        //front of the wall. Since the robot will constantly be moving sideways 350 units, it is possible that it may come acroos a wall, which may stop the robot from
        //proceeding.
        this.turnRight(20);
 
        //The robot will move 350 units ahead(this will actually give the effect of the robot moving sideways, since the gun is set 90 degrees to the right)
        this.ahead(250);
      }
 
      for(int i=0;i<2;i++)
      {
        //the radar left 360 degrees (it will end up in the same position). The aim for this is to scan the entire environment.
        this.turnRadarLeft(360);  
 
        //***this will turn the robot 10 degrees to the left. This is corresponding to the other code marked ***. The reason for these corresponding codes is to avoid from stopping in 
        //front of the wall. Since the robot will constantly be moving sideways 350 units, it is possible that it may come acroos a wall, which may stop the robot from
        //proceeding.
        this.turnLeft(10);
 
        //The robot will move back 350 units, which will give the effect of turning the robot sideways, since the gun is set 90 degrees to the right.
        this.back(250);
      }
 
    }
  }
 
  /**
   * onScannedRobot: What to do when you see another robot
   */
  public void onScannedRobot(ScannedRobotEvent e) 
  {
    //once an enemy is scanned, the robot will turn to the direction of the enemy. This method will make the robot surround the other enemy, since the robot will go
    // sideways, surrounding the enemy.
    this.turnRight(e.getBearing()-90);
 
    //this will set a variable, dist, as the distance between the robot and the enemy.
    double dist=e.getDistance();
 
    //if the current energy of the robot is the following, the functions will be executed.
    if(this.getEnergy()<5)
    {
      this.fire(0.1);//the robot will fire a power of 0.1, since the robot has less energy left.
    }
 
    //if the energy is not less than 5, and if the distance from the enemy is less than 200, the robot will fire a power of 3.
    else if(dist<200)
    {
      this.fire(3);//robot will fire a power of 3.
    }
 
    else if(dist<400)//also, if the distance between the enemy and the robot is less than 400, and the energy is not less than 5, the following will be done.
    {
      this.fire(2);// the robot will fire a power of 2.
    }
 
    else if(dist<600)//if the distance between the enemy and the robot is less than 600, and if the energy is not less than 5, the following will be done.
    {
      this.fire(1);//the robot will fire a power of 1.
    }
 
    else//if the robot agrees with none of the above conditions, the robot will fire a power of 0.1, since the robot will be too far
    {
      this.fire(0.1);// the robot will fire a power of 0.1
    }
  }
 
  /**
   * onHitByBullet: What to do when you're hit by a bullet
   */
  public void onHitRobot(HitRobotEvent e) 
  {  
    if (e.getBearing() >= 0)//if the bearing from the robot's direction to the enemy's is greater than or equal to 0, the robot will turn right.
    {
      turnRight(e.getBearing()-90);//if the robot is hit, it will turn right to the direction of the enemy who hit the robot. 
    }
    else//if the bearing from the enemy is not greater than or equal to 0, that means that the enemy is on the left side of the robot.
    {
      turnRight(-1*(e.getBearing()-90));//by multiplying the turning angle by negative 1, it will turn left, facing the enemy.
    }
 
    fire(3);//either way, at the end, the robot will fire a power of 3
  }
}
