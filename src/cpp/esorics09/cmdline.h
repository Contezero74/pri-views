/** This utility module defines the class used to manage the
 *  command line parsing.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 */

#if !defined	CMDLINE_H__
#define			CMDLINE_H__

#include <cstddef>
#include <iostream>
#include <memory>
#include <string>

using namespace std;

#include <boost/program_options.hpp>
using namespace boost::program_options;

/** This singleton class represents the default way to parse
 *  the command line arguments. It uses Boost library to enhance
 *  the parsing capabilities.
 */
class cmd_line {
public:
	/** This static method returns an instance of the cmd_line class.
	 *
	 *  @return	a singleton instance of cmd_line class.
	 */
	static cmd_line&	getInstance();

	/** Class destructor: it cleans up Boost references. */
	~cmd_line();

	/** This method parses the input command line and instanciates
	 *  all the properties of the class.
	 *
	 *  @param argc	the standard C++ argc argument of the main function:
	 *				it represents the number of arguments on the command line
	 *  @param argv	the standard C++ argv argument of the main function:
	 *				it represents an array of "string" (in C faction) representing
	 *				the command line arguments
	 *
	 *  @return		true if all is correct
	 */
	bool parseCommandLine(int argc, char *argv[]);
	
	/** Thie method resets to default values the class properties. */
	void reset();

	/** This method returns true if the command line asks for showing the
	 *  help.
	 *
	 *  @return	true if the help has to be showed
	 */
	bool isHelpToShow() const;

	/** This method returns true if the command line ask for running
	 *	hard-coded test. This functionality is used for debuging purposes.
	 *
	 *  @return	true if test has to be run
	 */
	bool isTest2Run() const;

	/** This method returns true if the command line ask for running
	 *	predefined problem.
	 *
	 *  @return	true if predefined input has to be used
	 */
	bool isExp2Run() const;

	/** This method returns true if the command line ask for running
	 *	random generated input for the problem.
	 *
	 *  @return	true if random generated input has to be used
	 */
	bool isRandomExp2Run() const;

	/** This method returns the minimum number of attributes has to be
	 *  used in experiment. The value is retrieved from the command line.
	 *
	 *	@return	the minimum number of attributes has to be used in experiment
	 */
	int getMinNumberOfAttributes() const;

	/** This method returns the maximum number of attributes has to be
	 *  used in experiment. The value is retrieved from the command line.
	 *
	 *	@return	the maximum number of attributes has to be used in experiment
	 */
	int getMaxNumberOfAttributes() const;

	/** This method returns true if only the greedy algorithm has to run, otherwise
	 *  if both exaustive and greedy algoritms have to run returns false.
	 *
	 *  @return true if only the greedy algorithm has to run
	 */
	bool isOnlyGreedy() const;

	/** This method returns the path of the experiment file to load and run.
	 *
	 *  @return	the path of the experiment
	 */
	string getExperimentPath() const;

	/** This method prints out in specified stream the program help.
	 *
	 *  @param OS	the output stream where to print out
	 */
	void printHelp(ostream &OS) const;

	/** This method prints out in specified stream the problems
	 *  found during command line parsing.
	 *
	 *  @param OS	the output stream where to print out
	 */
	void printCommandLineProblems(ostream &OS);


private:
	/** This private default constructor avoids to instanciated
	 *  this class without using the singleton getInstance()
	 *  method.
	 *	This constructor initializes the class itself.
	 */
	cmd_line();

	/** The pointer to the singleton instance of this class. */
	static auto_ptr<cmd_line>	MyInstance;

	/** This property is true if the help has to be showed. */
	bool IsHelpToShow;

	/** This property is true if the test has to be run. */
	bool IsTest2Run;

	/** This property is true if the the predefined problem has to be run. */
	bool IsExp2Run;

	/** This property is true if the random problem has to be run */
	bool IsRandomExp2Run;

	/** This property stores the minimum number of attributes to use during the
	 *  experiment.
	 */
	int MinNumberOfAttributes;

	/** This property stores the maximum number of attributes to use during the
	 *  experiment.
	 */
	int MaxNumberOfAttributes;

	/** If this property is set to true, only the greedy algorithm runs, otherwise
	 *  both exaustive and greedy algorithms run.
	 */
	bool OnlyGreedy;

	/** This property stores the path of the experient to run. */
	string ExperimentPath;


	vector<string> CommandLineProblems;

	// boost library related properties
	options_description	*Desc;
	variables_map *VM;
};

#endif //		CMDLINE_H__
