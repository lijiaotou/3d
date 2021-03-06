package cs4620.ray1.shader;


import java.util.List;

import cs4620.ray1.IntersectionRecord;
import cs4620.ray1.Light;
import cs4620.ray1.Ray;
import cs4620.ray1.Scene;
import egl.math.Color;
import egl.math.Colord;
 
import egl.math.Vector3d;


/**
 * A Lambertian material scatters light equally in all directions. BRDF value is
 * a constant
 *
 * @author ags
 */
public class Lambertian extends Shader {

	/** The color of the surface. */
	protected final Colord diffuseColor = new Colord(Color.White);
	public void setDiffuseColor(Colord inDiffuseColor) { diffuseColor.set(inDiffuseColor); }
	public Colord getDiffuseColor() {return new Colord(diffuseColor);}
	public double kd = 0.5;

	public Lambertian() { }
	
	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return "lambertian: " + diffuseColor;
	}

	/**
	 * Evaluate the intensity for a given intersection using the Lambert shading model.
	 * 
	 * @param outIntensity The color returned towards the source of the incoming ray.
	 * @param scene The scene in which the surface exists.
	 * @param ray The ray which intersected the surface.
	 * @param record The intersection record of where the ray intersected the surface.
	 * @param depth The recursion depth.
	 */
	@Override
	public void shade(Colord outIntensity, Scene scene, Ray ray, IntersectionRecord record) {
		// TODO#A2: Fill in this function.
		// 1) Loop through each light in the scene.
		// 2) If the intersection point is shadowed, skip the calculation for the light.
		//	  See Shader.java for a useful shadowing function.
		// 3) Compute the incoming direction by subtracting
		//    the intersection point from the light's position.
		// 4) Compute the color of the point using the Lambert shading model. Add this value
		//    to the output.
		outIntensity.add(diffuseColor);
		Vector3d l = new Vector3d();
		double  r = 1;
		double nl = 0;
		double k = 0;
		Vector3d I = new Vector3d();
		 
	 	 
		List<Light> lights = scene.getLights();
		for(Light light : lights)                              
		{           
			l.set(light.position).sub(record.location);
			r = l.dot(l);
			I.set(light.intensity);
			I.div(r);
			l.normalize();
			
			//Ld = kd (I/r2)max(0, n �� l)
			nl = record.normal.dot(l);
			k = kd * Math.max(0, nl); 
			
			outIntensity.addMultiple(k, I);
		}
	}

}  
