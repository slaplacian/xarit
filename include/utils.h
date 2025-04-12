#ifndef UTILS_H

#define UTILS_H

#include <string>

std::string get_local_ip();
std::string get_help_message();
bool validate_file(const std::string &file);
void generate_qr_code(const std::string& url);

#endif /* UTILS_H */