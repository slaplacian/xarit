#ifndef ROUTES_H

#define ROUTES_H

#include <httplib.h>
#include <filesystem>
#include <fstream>

namespace fs = std::filesystem;

void send_file(httplib::Response& res, std::ifstream &file);
void send_file(httplib::Response& res, fs::path path);
void list_files(const httplib::Request& req, httplib::Response& res);
void recursive_browser(const httplib::Request& req, httplib::Response& res) ;
void upload_file(const httplib::Request& req, httplib::Response& res);

#endif /* ROUTES_H */