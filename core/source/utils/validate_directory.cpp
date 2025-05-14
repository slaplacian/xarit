#include "../../include/utils.h"
#include <filesystem>

namespace fs = std::filesystem; 

bool validate_directory(const std::string &dir) {
    return (fs::exists(dir) && fs::is_directory(dir));
}