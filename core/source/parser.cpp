#include "../include/parser.h"
#include "../include/utils.h"
#include <iostream>

int port = 8080;
bool allow_download = true;
bool allow_upload = true;
int run_time = 0;
std::string shared_directory = ".";
std::string single_file = "";
bool recursive_mode = false;
std::string redirect_url = "";
bool override_mode = false;

void parse_arguments(int argc, char** argv) {
    for (int i = 1; i < argc; i++) {
        std::string arg = argv[i];
        if (arg == "-p" || arg == "--port") {
            if (i + 1 < argc) port = std::atoi(argv[++i]);
        } else if (arg == "-q" || arg == "--qrcode") {
            generate_qr_code("http://"+ get_local_ip() + ":" + std::to_string(port));
        } else if (arg == "-t" || arg == "--time") {
            if (i + 1 < argc) run_time = std::atoi(argv[++i]);
        } else if (arg == "-d" || arg == "--download") {
            allow_upload = false;
        } else if (arg == "-u" || arg == "--upload") {
            allow_download = false;
        }else if (arg == "-o" || arg == "--override") {
            override_mode = true;
        } else if ((arg == "-D" || arg == "--dir") && i + 1 < argc) {
            shared_directory = argv[++i];
            if (!validate_directory(shared_directory)) {
                std::cerr << "This directory " + shared_directory + " does not exist!" << std::endl;
                exit(1);
            }
        } else if ((arg == "-f" || arg == "--single-file") && i + 1 < argc) {
            single_file = argv[++i];
            if (!validate_file(single_file)) {
                std::cerr << "This file " + single_file + " does not exist!" << std::endl;
                exit(1);
            }
        } else if (arg == "-r" || arg == "--recursive") {
            recursive_mode = true;
        } else if ((arg == "-R" || arg == "--redirect") && i + 1 < argc) {
            redirect_url = argv[++i];
        } else if (arg == "-h" || arg == "--help") {
            std::cout << get_help_message();
            exit(0);
        } else {
            single_file = argv[i];
            if(!validate_file(single_file)) {
                std::cerr << "This file " + single_file + " does not exist!" << std::endl;
                exit(1);
            }  
        }
    }
}