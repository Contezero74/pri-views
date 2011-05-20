/** This class represents a target: i.e., the set of attributes
 *  representing it and the associated weight.
 *  
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 */

package it.primelife.h234.project;

/** This class represents an instance of a target (i.e.,
 *  the set of attributes representing it and the
 *  associated weight).
 */
public class target {
	/** Default constructor for the target class. */
	public target() { /* nothing to do */ }
	
	/** This method sets the attributes and the weight of the target.
	 * 
	 * @param Attributes	the set of attributes representing the target
	 * @param Weight		the weight associated with the target
	 */
	public void set(final String Attributes, final int Weight) {
		TargetAttributes	= Attributes;
		TargetWeight		= Weight;
	}
	
	/** This method returns the set of attributes representing the target.
	 * 
	 * @return the set of attributes representing the target
	 */
	public String getAttributes() {
		return TargetAttributes;
	}
	
	/** This method returns the weight associated with the target.
	 * 
	 * @return the weight associated with the target
	 */
	public int getWeight() {
		return TargetWeight;
	}
	
	/** This method returns a string representing both the weight associated
	 *  with the target and the the string of bit representing it.
	 *  
	 * 	@return string representing both the weight associated with the
	 * 			target and the the string of bit representing it
	 */
	public String toString() {
		return "" + TargetWeight + "\t" + TargetAttributes;
	}
	
	/** This property stores the string of bit representing the target. */
	private String TargetAttributes = new String();
	
	/** This property represents the weight of the target. */
	private int TargetWeight;
}
