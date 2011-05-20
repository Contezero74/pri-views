/** This utility module defines the class used to manage the
 *  command line parsing.
 *
 *  version 1.5
 *
 *  UNIBG and UNIMI @2009
 *
 *  Implementation file
 */

#include "cmdline.h"

#include "config.h"

// public methods

/** This static method returns an instance of the cmd_line class.
 *
 *  @return	a singleton instance of cmd_line class.
 */
cmd_line& cmd_line::getInstance() {
	if ( NULL == MyInstance.get() ) {
		MyInstance = auto_ptr<cmd_line>(new cmd_line() );
	}

	return *MyInstance;
}

/** Class destructor: it cleans up Boost references. */
cmd_line::~cmd_line() {
	delete VM;
	delete Desc;
}

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
bool cmd_line::parseCommandLine(int argc, char *argv[]) {
	store( parse_command_line(argc, argv, *Desc), *VM);
	notify(*VM);

	bool isParseOk		= false;
	bool isSomeError	= false;

	try {
		if ( VM->count("help") ) {
			IsHelpToShow	= true;
			isParseOk		= true;
		}

		if ( VM->count("run-test") ) {
			IsTest2Run		= true;
			isParseOk		= true;
		}

		if ( VM->count("run-exp") ) {
			IsExp2Run		= true;
			isParseOk		= true;
		}

		if ( VM->count("random-exp") ) {
			IsRandomExp2Run	= true;
			isParseOk		= true;
		}

		if ( VM->count("min-num-of-attrs") ) {
			isParseOk		= true;
			
			if ( 4 > MinNumberOfAttributes) {
				MinNumberOfAttributes = 4;
			}
		}

		if ( VM->count("max-num-of-attrs") ) {
			isParseOk		= true;
			
			if ( MAX_NUMBER_ATTRIBUTES < MaxNumberOfAttributes) {
				MaxNumberOfAttributes = MAX_NUMBER_ATTRIBUTES;
			}

			if (0 > MaxNumberOfAttributes) { MaxNumberOfAttributes = MinNumberOfAttributes*2; }
		}

		if ( VM->count("only-greedy") ) {
			OnlyGreedy		= true;
		}

		if ( VM->count("experiment") ) {
			isParseOk		= true;
		}

	} catch(exception e) {
		isSomeError = true;

		CommandLineProblems.push_back("a generic (not expected) error happened");

		cerr << "exception catched in config::parseCommandLine method" << endl << e.what() << endl;
	}

	return isParseOk && !isSomeError;
}

/** Thie method resets to default values the class properties. */
void cmd_line::reset() {
	IsHelpToShow			= false;
	IsTest2Run				= false;
	IsExp2Run				= false;
	MinNumberOfAttributes	= 4;
	MaxNumberOfAttributes	= 8;
	OnlyGreedy				= false;
	ExperimentPath			= "";

	CommandLineProblems.clear();
}

/** This method returns true if the command line asks for showing the
 *  help.
 *
 *  @return	true if the help has to be showed
 */
bool cmd_line::isHelpToShow() const { return IsHelpToShow; }

/** This method returns true if the command line ask for running
 *	hard-coded test. This functionality is used for debuging purposes.
 *
 *  @return	true if test has to be run
 */
bool cmd_line::isTest2Run() const { return IsTest2Run; }

/** This method returns true if the command line ask for running
 *	predefined problem.
 *
 *  @return	true if predefined input has to be used
 */
bool cmd_line::isExp2Run() const { return IsExp2Run; }

/** This method returns true if the command line ask for running
 *	random generated input for the problem.
 *
 *  @return	true if random generated input has to be used
 */
bool cmd_line::isRandomExp2Run() const { return IsRandomExp2Run; }

/** This method returns the minimum number of attributes has to be
 *  used in experiment. The value is retrieved from the command line
 *
 *	@return	the minimum number of attributes has to be used in experiment
 */
int cmd_line::getMinNumberOfAttributes() const { return MinNumberOfAttributes; }

/** This method returns the maximum number of attributes has to be
 *  used in experiment. The value is retrieved from the command line
 *
 *	@return	the maximum number of attributes has to be used in experiment
 */
int cmd_line::getMaxNumberOfAttributes() const { return MaxNumberOfAttributes; }

/** This method returns true if only the greedy algorithm has to run, otherwise
 *  if both exaustive and greedy algoritms have to run returns false.
 *
 *  @return true if only the greedy algorithm has to run
 */
bool cmd_line::isOnlyGreedy() const { return OnlyGreedy; }

/** This method returns the prefix part of the output files.
 *
 *  @return	prefix part of the output files
 */
string cmd_line::getExperimentPath() const { return ExperimentPath; }

/** This method prints out in specified stream the program help.
 *
 *  @param OS	the output stream where to print out
 */
void cmd_line::printHelp(ostream &OS) const { Desc->print(OS); }

/** This method prints out in specified stream the problems
 *  found during command line parsing.
 *
 *  @param OS	the output stream where to print out
 */
void cmd_line::printCommandLineProblems(ostream &OS) {
	const vector<string>::const_iterator End = CommandLineProblems.end();
	for(vector<string>::iterator i = CommandLineProblems.begin(); i != End; ++i) {
		OS << *i << endl;
	}
}


// private methods

/** This private default constructor avoids to instanciated
 *  this class without using the singleton getInstance()
 *  method.
 *	This constructor initializes the class itself.
 */
cmd_line::cmd_line() {
	reset();

	Desc	= new options_description("Available options");
	Desc->add_options()
		("help",													"display help message")
		("run-test",												"run the case test")
		("run-exp",													"run predefined experiments")
		("random-exp",												"run randomn experiments")
		("min-num-of-attrs", value<int>(&MinNumberOfAttributes),	"set min number of attributes")
		("max-num-of-attrs", value<int>(&MaxNumberOfAttributes),	"set max number of attributes")
		("only-greedy",												"run only the greedy algorithm")
		("experiment", value<string>(&ExperimentPath),				"path of the experiment to run");

	VM	= new variables_map();
}

auto_ptr<cmd_line>	cmd_line::MyInstance	= auto_ptr<cmd_line>( new cmd_line() );
