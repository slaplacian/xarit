#ifndef UTILS_H

#define UTILS_H

#include <string>

std::string get_local_ip();
std::string get_help_message();
bool validate_file(const std::string &file);
bool validate_directory(const std::string &dir);
void generate_qr_code(const std::string& url);
std::string increment_filename(std::string filename);

#endif /* UTILS_H */