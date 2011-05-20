/** This class represents an instance of a project and contains
 *  all information needed to create the initialization file
 *  for call the evaluator.
 *  
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 */

package it.primelife.h234.project;

import it.primelife.h234.E09Config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/** This class represents an instance of a project and contains
 *  all information needed to create the initialization file
 *  for call the evaluator.
 */
public class project {
	/** This constructor defines a new project assigning a name and the
	 *  maximum number of attribute usable to define constraints and targets.
	 * 
	 * @param Name				the name of the project
	 * @param MaxNumAttributes	the maximum number of attributes
	 */
	public project(final String Name, final int MaxNumAttributes) {
		ProjectName			= Name;
		MaxNumberAttributes	= MaxNumAttributes;
	}
	
	/** This method returns true if the project is already saved.
	 * 
	 * @return true if the project is already saved
	 */
	public boolean isSaved() {
		return null != this.ProjectPath;
	}
	
	/** This method loads a project from the file used as input.
	 * 
	 * @param Path	the full path to the project to load
	 * 
	 * @return		the project loaded
	 * 
	 * @throws IOException
	 */
	public static project load(final String Path) throws IOException {
		File InFile = new File(Path);  
		Scanner In = new Scanner(InFile);
		
		project TmpProject 		= null;
		String TmpPrjName		= null;
		String TmpNumAttributes	= null;
		
		boolean areConstraints		= true;
		
		while ( In.hasNextLine() ) {
			try {
				if (null == TmpProject && null != TmpPrjName && null != TmpNumAttributes) {
					TmpProject = new project( TmpPrjName, Integer.parseInt(TmpNumAttributes) );
					TmpProject.ProjectPath = Path;
				}
				
				String Line = In.nextLine().trim();
				
				if ( !Line.isEmpty() ) {
					if ( Line.startsWith("# PROJECT NAME: ") ) {
						TmpPrjName = Line.substring( "# PROJECT NAME: ".length() ).trim();
					} else if ( Line.startsWith("# NUMBER OF ATTRIBUTES: ") ) {
						TmpNumAttributes = Line.substring( "# NUMBER OF ATTRIBUTES: ".length() ).trim();
					} else if ( '#' == Line.charAt(0) ) {
						; // comment (nothing to do)
					} else if ( Line.startsWith("- CONSTRAINTS") ) {
							areConstraints = true;
					} else if ( Line.startsWith("- TARGETS") ) {
							areConstraints = false;
					}else if ( '\n' != Line.charAt(0) ) {
						// data
						if (areConstraints) {
							TmpProject.addConstraint(Line);
						} else {
							int TabPos = Line.indexOf('\t');
							String CostStr 		= Line.substring(0, TabPos);
							String TargetStr	= Line.substring(TabPos + 1);
							
							target T = new target();
							T.set(TargetStr, Integer.parseInt(CostStr) );
							
							TmpProject.addTarget( T );
						}
					}
				}	
			} catch (Exception ex) {
				// nothing to do: skip the line
			}
		}
		
		In.close();
		
		return TmpProject;
	}
	
	/** This method saves the current project in the specified location.
	 * 
	 * @param Path	the full path where to save the project
	 * 
	 * @throws IOException
	 */
	public void save(final String Path) throws IOException {
		Writer Out = new BufferedWriter( new FileWriter(Path) );
		
		// comment with project name
		Out.write("# PROJECT NAME: " + ProjectName + "\n");
		Out.write("# NUMBER OF ATTRIBUTES: " + MaxNumberAttributes + "\n\n");
		
		// constraints
		Out.write("- CONSTRAINTS\n");
		for (String C : Constraints) {
			Out.write(C + "\n");
		}
		
		Out.write("\n");
		
		// targets
		Out.write("- TARGETS\n");
		for (target T : Targets) {
			Out.write( "" + T.getWeight() + "\t" + T.getAttributes() + "\n" );
		}
		
		Out.flush();
		Out.close();
		
		ProjectPath = Path;
	}
	
	/** This method saves the project in the location used the last time by
	 *  the method save(String).
	 *  
	 * @see #save(String)
	 * 
	 * @throws IOException
	 */
	public void save() throws IOException {
		if (null == ProjectPath) {
			throw new IOException("The project is not saved with Save As yet");
		}
		
		save(ProjectPath);
	}
	
	/** This method runs the experiments associated with the current project.
	 * 
	 * @param MinNumOfAttr	the minimum number of attribute to use in experiments
	 * @param MaxNumOfAttr	the maximum number of attribute to use in experiments
	 * 
	 * @return				a string representing the result of the experiments
	 * 
	 * @throws Exception
	 */
	public String run(final int MinNumOfAttr, final int MaxNumOfAttr, final boolean OnlyGreedy) throws Exception {
		String Result = "";
		
		try {			
			String Line = "";
			
			String OnlyGreedyStr = "";
			if (OnlyGreedy) {
				OnlyGreedyStr = " --only-greedy";
			}
			
			String ApplicationCmd = E09Config.getInstance().getApplicationCmd();
			
			String CmdLine = "./" + ApplicationCmd + " --min-num-of-attrs " + MinNumOfAttr +
							 " --max-num-of-attrs " + MaxNumOfAttr +
							 OnlyGreedyStr + " --experiment \"" + ProjectPath + "\""; 

			Process p = Runtime.getRuntime().exec(CmdLine);
			BufferedReader input = new BufferedReader( new InputStreamReader( p.getInputStream() ) );
			while ( (Line = input.readLine() ) != null ) {
				Result = Result + Line + "\n";
			}

			input.close();
		} catch (Exception ex) {
			throw new Exception("Problem during experiments running");
		}
		
		return Result;
	}
	
	/** This method returns the name of the project.
	 *
	 * @return	the project name
	 */
	public String getProjectName() {
		return ProjectName;
	}
	
	/** This method returns the maximum number of attributes.
	 * 
	 * @return	the maximum number of attributes
	 */
	public int getMaxNumberAttributes() {
		return MaxNumberAttributes;
	}
	
	/** This method returns the number of constraints.
	 * 
	 * @return	the number of constraints
	 */
	public int getNumberOfConstriants() {
		return Constraints.size();
	}
	
	/** This method adds a new constraint to the project.
	 * 
	 * @param C	the constraint to add
	 * 
	 * @return	true if the constraint has been added
	 */
	public boolean addConstraint(final String C) {
		if ( !isAlreadyInConstraints(C, Constraints) ) {
			Constraints.add(C);
			
			return true;
		}
		
		return false;
	}
	
	/**	This method removes the specified constraint from the project.
	 * 
	 * @param index	the index of the constraint to remove
	 */
	public void removeConstraint(final int index) {
		if ( Constraints.size() > index ) {
			Constraints.remove(index);
		}
	}
	
	/** This method removes all the constraints from the project.
	 */
	public void removeAllConstraints() {
		Constraints.clear();
	}
	
	/** This method modifies the specified constraint.
	 * 
	 * @param index	the index of the constraint to modify
	 * @param C		the new constraint value
	 * 
	 * @return		the old constraint
	 */
	public String modifyConstraint(final int index, final String C) {
		String OldConstraint = null;
		
		if ( Constraints.size() > index ) {
			OldConstraint = Constraints.get(index);
			Constraints.set(index, C);
		}
		
		return OldConstraint;
	}
	
	/** This method gets the constraint pointed from the index used as input.
	 * 
	 * @param index	the index of the constraint to get
	 * 
	 * @return		the constraint pointed from the input index
	 */
	public String getConstraint(final int index) {
		String Result = null;
		
		if ( Constraints.size() > index ) {
			Result = Constraints.get(index);
		}
		
		return Result;
	}
	
	/** This method returns the number of targets in the project.
	 * 
	 * @return	the number of targets in the project
	 */
	public int getNumberOfTargets() {
		return Targets.size();
	}
	
	/** This method adds the input target in the project.
	 * 
	 * @param T	the target to add
	 * 
	 * @return	true if the target has been added
	 * 
	 * @see target
	 */
	public boolean addTarget(final target T) {
		if ( !isAlreadyInTargets(T, Targets) ) {
			Targets.add(T);
			
			return true;
		}
		
		return false;
	}
	
	/** This method removes the specified target from the project.
	 * 
	 * @param index	the index of the target to remove
	 */
	public void removeTarget(final int index) {
		if ( Targets.size() > index ) {
			Targets.remove(index);
		}
	}
	
	/** This method removes all the targets from the project.
	 */
	public void removeAllTargets() {
		Targets.clear();
	}
	
	/** This method modify the specified target.
	 * 
	 * @param index	the index of the target to modify
	 * @param T		the new target
	 * 
	 * @return		the old target
	 * 
	 * @see target
	 */
	public target modifyTarget(final int index, final target T) {
		target OldTarget = null;
		
		if ( Targets.size() > index ) {
			OldTarget = Targets.get(index);
			Targets.set(index, T);
		}
		
		return OldTarget;
	}
	
	/** This method returns the target pointed from the input index.
	 * 
	 * @param index	the index of the target to retrieve
	 * 
	 * @return	the target to get
	 * 
	 * @see target
	 */
	public target getTarget(final int index) {
		target Result = null;
		
		if ( Targets.size() > index ) {
			Result = Targets.get(index);
		}
		
		return Result;
	}
	
	/** This method returns true if the constraint is already in the
	 *  constraints list.
	 *  
	 * @param C			the constraint to check
	 * @param Container	the list of the constraints
	 * 
	 * @return			true if the constraint is already in the list of constraints
	 */
	private boolean isAlreadyInConstraints(final String C, final List<String> Container) {
		for (String E : Container) {
			if ( E.equalsIgnoreCase(C) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/** This method returns true if the target is already in the
	 *  targets list.
	 *  
	 *  @param T			the target to check
	 *  @param Container	the list of targets
	 *  
	 *  @return				true if the target is already in the list of targets
	 *  
	 *  @see target
	 */
	private boolean isAlreadyInTargets(final target T, final List<target> Container) {
		for (target E : Container) {
			if ( E.getAttributes().equalsIgnoreCase( T.getAttributes() ) ) {
				return true;
			}
		}
		
		return false;
	}
	
	/** the project name property. */
	private String ProjectName			= "undefined";
	
	/** The project path property (where to save the project). */
	private String ProjectPath			= null;
	
	/** The maximum number of usable attribute for the project. */
	private int MaxNumberAttributes		= 10;
	
	/** The constraints container. */
	private List<String> Constraints	= new ArrayList<String>();
	
	/** The targets container. */
	private List<target> Targets		= new ArrayList<target>();
}
