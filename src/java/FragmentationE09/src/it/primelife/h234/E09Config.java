/** This class represents a tool to access to the application
 *  config file.
 *  
 *  version 1.0
 *
 *  UNIBG and UNIMI @2009
 */

package it.primelife.h234;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;

import nl.chess.it.util.config.Config;

/** This class extends the Config base class wrote by Guus Bosman adding support
 *  for specific application and the possibility to change the application
 *  preferences.
 */
public class E09Config extends Config {
	/** This enumeration summarizes the operative system supported by the application.
	 */
	public enum OSType {
		WIN,
		UNIXLIKE,
		OTHER
	}
	
    /**
     * The Name of the file we are looking for to read the configuration.
     */
    public static final String RESOURCE_NAME = "esorics09.properties";

    /** This method creates (or returns) a singleton instance of the Config tool.
     * 
     * @return the singleton instance of the Config tool
     */
    public static E09Config getInstance() {
    	if (null == E09ConfigInstance) {
    		E09ConfigInstance = new E09Config();
    	}
    	
    	return E09ConfigInstance;
    }
    
    /** This method returns the OS set in the preferences.
     * 
     * @return the OS set in the preferences.
     * 
     * @see OSType
     */
    public OSType getOS() {
    	return MyOSType;
    }
    
    /** This method returns the command to use to start the experiments.
     * 
     * @return the command to use to start the experiments
     */
    public String getApplicationCmd() {
        return ApplicationCmd;
    }
    
    /** This method sets the OS and the command to use to start the experiments
     *  in the application preferences.
     *  
     * @param OS	the current Operative System 
     * @param Cmd	the command to use to start the experiments
     * 
     * @return		true if all has be gone right, otherwise false
     */
    public boolean setOSAndApplication(final OSType OS, final String Cmd) {
    	MyOSType = OS;
    	
    	if (OSType.WIN == OS) {
    		ApplicationCmd = "esorics09.exe";
    	}
    	
    	if (OSType.UNIXLIKE == OS) {
    		ApplicationCmd = "esorics09";
    	}
    	
    	if (OSType.OTHER == OS) {
    		ApplicationCmd = Cmd;
    	}
    	
    	return save();
    }
    
    /** This constructor creates a new E09Config object.
     */
    private E09Config() {
        super(RESOURCE_NAME);
        
        String OS = getString("property.os");
    	
    	if ( OS.equalsIgnoreCase("win") ) {
    		MyOSType = OSType.WIN;
    	}
    	
    	if ( OS.equalsIgnoreCase("unixlike") ) {
    		MyOSType = OSType.UNIXLIKE;
    	}
    	
    	if ( OS.equalsIgnoreCase("other") ) {
    		MyOSType = OSType.OTHER;
    	} 
    	
    	ApplicationCmd = getString("property.cppapp");
    }
    
    /** This method try to save updated preferences into the preferences file.
     * 
     * @return true if all has be gone right, otherwise false
     */
    private boolean save() {
    	try {
			Writer Out = new BufferedWriter( new FileWriter( "./" + RESOURCE_NAME ) );
			
			Out.write( "#os can assume the following values: win, unixlike, other\n" );
			
			String OST = "other";
			if (OSType.WIN == MyOSType) {
				OST = "win";
			} else if (OSType.UNIXLIKE == MyOSType) {
				OST = "unixlike";
			}
			Out.write( "property.os=" + OST + "\n" );
			
			Out.write( "property.cppapp=" + ApplicationCmd.trim() + "\n" );
			
			Out.flush();
			Out.close();
		} catch (Exception ex) {
			return false;
		}
		
		return true;
    }
    
    /** The singleton instance of the Config tool. */
    private static E09Config E09ConfigInstance = null;
    
    /** The current OS Type saved in the preferences. */
    private OSType MyOSType			= OSType.WIN;
    
    /** The current command, used to start the experiments, saved in the preferences. */ 
    private String ApplicationCmd	= "esorics09.exe";
}
