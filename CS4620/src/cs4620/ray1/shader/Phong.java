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
 * A Phong material.
 *
 * @author ags, pramook
 */
public class Phong extends Shader {

	/** The color of the diffuse reflection. */
	protected final Colord diffuseColor = new Colord(Color.White);
	public void setDiffuseColor(Colord diffuseColor) { this.diffuseColor.set(diffuseColor); }
	public Colord getDiffuseColor() {return new Colord(diffuseColor);}

	/** The color of the specular reflection. */
	protected final Colord specularColor = new Colord(Color.White);
	public void setSpecularColor(Colord specularColor) { this.specularColor.set(specularColor); }
	public Colord getSpecularColor() {return new Colord(specularColor);}

	/** The exponent controlling the sharpness of the specular reflection. */
	protected double exponent = 1.0;
	public void setExponent(double exponent) { this.exponent = exponent; }
	public double getExponent() {return exponent;}
	
	public double kd = 15;
	public double ks = 300;
	public double ka = 0.05;
	
	public Phong() { }

	/**
	 * @see Object#toString()
	 */
	public String toString() {    
		return "phong " + diffuseColor + " " + specularColor + " " + exponent + " end";
	}

	/**
	 * Evaluate the intensity for a given intersection using the Phong shading model.
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
		// 4) Compute the color of the point using the Phong shading model. Add this value
		//    to the output.
		//outIntensity.add(diffuseColor);
		 
 
		List<Light> lights = scene.getLights();
		for(Light light : lights)                              
		{     
			Vector3d l = new Vector3d();
			double  r = 1;
			double nl = 0;
			double diffuse = 0;
			double cosa = 0;
			double specular = 0;
			Vector3d I = new Vector3d();
			Vector3d h = new Vector3d();
			
			//diffuse shading
			//Ld = kd (I/r2)max(0, n ¡¤ l)
			l.set(light.position).sub(record.location);
			r = l.dot(l);
			I.set(diffuseColor);
			I.div(r);
			
			l.normalize();

			nl = record.normal.dot(l);
			diffuse = kd * Math.max(0, nl); 
			outIntensity.addMultiple(diffuse, I);
			
			//Specular shading(Blinn-Phong)
			//Ls = ks (I/r2)max(0, cosa)p
			h.sub(ray.direction).add(l);
			h.normalize();
			I.set(specularColor);
			I.div(r*r);
			cosa = Math.pow(record.normal.dot(h), exponent);
			specular = ks * Math.max(0, cosa);
			outIntensity.addMultiple(specular, I);

			//Ambient shading
			I.set(diffuseColor);
			outIntensity.addMultiple(ka, I);
			
		}
		
	}

}
