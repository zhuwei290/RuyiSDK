#include <iostream>
#include "config.h"

int main(int argc, char **argv) {
	std::cout << "Hello World" << std::endl;
	std::cout << "Version " << cmake_VERSION_MAJOR << "." << cmake_VERSION_MINOR << std::endl;
	return 0;
}
