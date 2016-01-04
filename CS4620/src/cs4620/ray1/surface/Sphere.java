package cs4620.ray1.surface;

import cs4620.ray1.IntersectionRecord;
import cs4620.ray1.Ray;
import egl.math.Vector3d;

/**
 * Represents a sphere as a center and a radius.
 *
 * @author ags
 */
public class Sphere extends Surface {
  
  /** The center of the sphere. */
  protected final Vector3d center = new Vector3d();
  public void setCenter(Vector3d center) { this.center.set(center); }
  
  /** The radius of the sphere. */
  protected double radius = 1.0;
  public void setRadius(double radius) { this.radius = radius; }
  
  protected final double M_2PI = 2*Math.PI;
  
  public Sphere() { }
  
  /**
   * Tests this surface for intersection with ray. If an intersection is found
   * record is filled out with the information about the intersection and the
   * method returns true. It returns false otherwise and the information in
   * outRecord is not modified.
   *
   * @param outRecord the output IntersectionRecord
   * @param ray the ray to intersect
   * @return true if the surface intersects the ray
   */
  public boolean intersect(IntersectionRecord outRecord, Ray rayIn) {
    // TODO#A2: fill in this function.
	  double tm = 0;
	  double lp = 0;
	  Vector3d vectorP = new Vector3d();
	  vectorP.set(center).sub(rayIn.origin);
	  
	  tm = vectorP.dot(rayIn.direction);
	  lp = vectorP.dot(vectorP);
	  double lmexp2 = lp - (tm * tm);
	  double deltTexp2 = radius * radius - lmexp2;
	  if ( deltTexp2 >= 0)
	  {
		  double  deltT = Math.sqrt(deltTexp2);
		  double t0 = tm - deltT;
		  outRecord.location.set(rayIn.origin).addMultiple(t0, rayIn.direction);
		  outRecord.normal.set(outRecord.location).sub(center).normalize();
		
		  return true;
	  }

    return false;
  }
  
  /**
   * @see Object#toString()
   */
  public String toString() {
    return "sphere " + center + " " + radius + " " + shader + " end";
  }

}