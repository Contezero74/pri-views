# Installation Guide #

The implemented tool is composed of two distinct applications:
  1. the first one is a command line program developed in C++, which implements the greedy algorithm designed by [UNIBG](http://www.unibg.it/en_index.asp) and [UNIMI](http://www.dti.unimi.it/);
  1. the second one is the Graphical User Interface (GUI) developed in Java that simplifies the use of the first one.

The applications have been developed in standard C++ and Java, therefore they should be compatible with almost all the operating systems (OS) currently in use. Under MS Windows, the released version is ready to run, while under other OSs (e.g., Linux), a few installation steps are required.

# How to Install #

After the correct installation of the tool, the reader has to open the OS shell, go to the directory where the program has been installed, and input the following command at the prompt: java -jar Esorics09GUI.jar, to start the graphical user interface.To install the tool, the first step is to uncompress the file fragmentation.zip in directory selected by the reader (from now on called installation directory). Below, we briefly list and describe the files and directories contained in the installation directory:
  * Esorics09GUI.jar : the Java GUI application;
  * esorics09.exe: the MS Windows command line application;
  * run.bat: the MS Windows script that can be used to start the GUI application;
  * run.sh: the Unix like script that can be used to start the GUI application (a bash shell is needed);
  * esorics09.properties: the preferences file of the GUI application;
  * Example.txt: the example project presented in in the How to Use the Tool section;
  * cpp: the directory containing the C++ application source code. The directory is a MS Visual Studio 2005 project, but there is also a Unix like makefile in subdirectory esorics09;
  * java: the directory containing the Java application source code. The directory is an Eclipse project.
  * src: the directory containing the source code of the tool applications, structured as:

## MS Windows ##

To start the tool under MS Windows, the reader has to double-click on the run.bat icon included in the installation directory. Alternatively, the reader can open the Windows shell, go to the installation directory, and input the command run.bat .

## Other Operating Systems ##

To use the tool under other OSs, the reader has to compile the C++ application for the specific OS. In the following, we provide the steps needed under Unix like OSs:
  1. open the OS shell and go to the installation directory;
  1. follow the directory sequence cpp > esorics09 to go where the makefile is stored;
  1. modify lines 5 and 6 of the makefile file, inserting the right path to the local installation of the Boost C++ Library;
  1. use the following command line: make;
  1. copy the generated file esorics09 in the installation directory;
  1. grant to file esorics09 the permission of execution, using the following command line in the shell: chmod u+x esorics09.
  1. run the GUI application using the command line: run.sh and configure it using the menu File > Preferences... (see Graphical User Interface section).