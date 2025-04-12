#include "../../include/utils.h"
#include <filesystem>

namespace fs = std::filesystem; 

bool validate_file(const std::string &file) {
    return (fs::exists(file) && fs::is_regular_file(file));
}