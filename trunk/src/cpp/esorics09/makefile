# ESORICS09 makefile
# coded by Pedrini Eros (@2009)

# boost library path: remeber to change the following path to the right boost library path
BoostIncludePath=your path to Boost Include
BoostStaticLibPath=your path to the library libboost_program_options.a


# makefile constants
CCompiler=gcc
CppCompiler=g++
Linker=g++


# compiler options
StaticLibComp=-static -s -c -O3 -pedantic
StaticLibLink=-static -s

DynamicLibComp=-s -c -O3
DynamicLibLink=-s

all: clean static_link

static_link: static_cpp_compile
	$(Linker) $(StaticLibLink) -o ESORICS09 *.o $(BoostStaticLibPath)

static_cpp_compile: *.cpp
	$(CppCompiler) -I$(BoostIncludePath) $(StaticLibComp) *.cpp


all_dynamic: clean dynamic_link

dynamic_link: dynamic_cpp_compile
	$(Linker) $(DynamicLibLink) -o esorics09 *.o

dynamic_cpp_compile: *.cpp
	$(CppCompiler) -I$(BoostIncludePath) $(DynamicLibComp) *.cpp


clean:
	rm -rf *o esorics09

