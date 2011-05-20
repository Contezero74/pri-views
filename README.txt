=== DataProtection - Fragmentation ===
Contributors: UniBG, UniMI
Tags: C++, encryption, fragmentation, greedy algorithm, Java, sensitive attributes, unlinkable fragments   
Requires at least: Java Run Time Environment (version 1.6 or later)
Tested up to: JRE v. 1.6
Stable tag: 1.0

This tool implements the greedy algorithm solving the problem of creating
unlinkable fragments in the storage of sensitive attributes.

== Description ==

This tool implements the greedy algorithm designed by UniBG and UniMI to solve
the problem of creating unlinkable fragments in the storage of sensitive
attributes. The presented algorithm departs from the use of encryption, while
usually in the literature, this kind of problem has been addressed using both
fragmentation and encryption. The tool is composed of two applications: the
first implements the proposed greedy algorithm (developed in C++), while the
second realizes its Graphical User Interface (developed in Java). 

= Getting Started ==

The implemented tool is composed of two distinct applications:
 1. the first one is a command line program developed in C++, which implements the greedy algorithm designed by UNIBG and UNIMI;
 2. the second one is the Graphical User Interface (GUI) developed in Java that simplifies the use of the first one.

The applications have been developed in standard C++ and Java, therefore they
should be compatible with almost all the operating systems (OS) currently in use.
Under MS Windows, the released version is ready to run, while under other
OSs (e.g., Linux), a few installation steps are required.

After the correct installation of the tool, the reader has to open the OS shell,
go to the directory where the program has been installed, and input the
following command at the prompt: java -jar Esorics09GUI.jar, to start the
graphical user interface.

== Installation ==

To install the tool, the first step is to uncompress the file fragmentation.zip
in directory selected by the reader (from now on called installation directory). 
Below, we briefly list and describe the files and directories contained in the
installation directory:

 * Esorics09GUI.jar : the Java GUI application;
 * esorics09.exe: the MS Windows command line application;
 * run.bat: the MS Windows script that can be used to start the GUI application;
 * run.sh: the Unix like script that can be used to start the GUI application (a bash shell is needed);
 * esorics09.properties: the preferences file of the GUI application;
 * Example.txt: the example project presented in in the ''How to Use the Tool'' section;
 * src: the directory containing the source code of the tool applications, structured as:
   * cpp: the directory containing the C++ application source code. The directory is a MS Visual Studio 2005 project, but there is also a Unix like makefile in subdirectory esorics09;
   * java: the directory containing the Java application source code. The directory is an Eclipse project.
    

== Frequently Asked Questions ==

= How can I try the tool? =

This section describes step by step a simple example showing how to use the tool.

After the start of the Java program, the reader has to create a new project
using the menu File > New.... Assuming the project is called Example and 7
attributes are used, the reader has to insert Example and 7 in the two fields of
the dialog, and then press the Ok button.

Now the reader can insert the following 5 constraints represented as a string of
bits where 1 means that the attribute is part of the constraint and the left
most value represents the first attribute:
 * 1 0 0 0 1 0 0
 * 1 0 1 0 0 0 0
 * 0 1 0 1 1 0 0
 * 0 1 1 1 0 0 0
 * 0 0 0 0 1 1 0
For each constraint, the reader has to click the menu
Project > Add Constraint..., check the checkboxes corresponding to the
attributes set to 1, and then click the Add button.

After that, the reader can insert the following 6 targets with the associated
weight:
 * 0 0 0 1 1 0 0, with weight 5
 * 0 1 0 0 1 0 0, with weight 4
 * 0 0 0 0 1 1 0, with weight 10
 * 0 0 1 0 1 0 0, with weight 1
 * 0 0 0 0 1 0 0, with weight 7
 * 0 0 1 1 0 0 1, with weight 7
For each target, the reader has to click the menu Project > Add Target..., 
insert the relative weight, and check the checkboxes corresponding to the 
attributes set to 1. Finally, the reader can click the Add button to save the 
target.

The last step is to run the experiment using the menu Project > Run.... Now,
the application asks to save the project, and then shows the dialog used to 
start the experiments. The reader has to insert the values 7 and 7 in the fields
of the dialog and then press the Ok button. The next figure shows the result of
the experiment. In the string of bits representing the solution, value 1 means
that the attribute belongs to Fo, the remaining attributes compose Fs.

= What is the target audience? =

Developers who want to use state of the art algorithms to implement protection
mechanisms based on fragmentation.

= Which are the future plans for this tool? =

We plan to extend the tool with future results of UniBG and UniMI research.

== Screenshots ==

== Changelog ==

= 1.0 =
 * First version of the tool

== Upgrade Notice ==
