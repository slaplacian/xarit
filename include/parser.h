#ifndef PARSER_H

#define PARSER_H

#include <string>

extern int port;
extern bool allow_download;
extern bool allow_upload;
extern int run_time;
extern std::string shared_directory;
extern std::string single_file;
extern bool recursive_mode;
extern std::string redirect_url;

void parse_arguments(int argc, char** argv);

#endif /* PARSER_H */