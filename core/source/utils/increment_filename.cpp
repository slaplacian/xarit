#include "../../include/utils.h"
#include <sstream>

std::string increment_filename(std::string filename) {
    
    int identificator = 0;
    std::ostringstream oss;

    std::size_t last_dir_pos = filename.find_last_of("/");
    std::size_t last_dot     = filename.find_last_of(".");
    std::string extension    = "";

    bool applicable = last_dot != std::string::npos && (last_dir_pos == std::string::npos || last_dir_pos + 1 < last_dot);  

    if(applicable) {
        extension = filename.substr(last_dot);
        filename  = filename.substr(0,last_dot);
    }

    oss << filename;

    while(validate_file(oss.str()+extension)) {
        oss.str("");
        oss.clear();
        oss << filename << "_" << identificator++;
    }

    filename = oss.str();

    if(applicable) {
        filename += extension;
    }

    return filename;
}