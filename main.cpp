#include "include/parser.h"
#include "include/utils.h"
#include "include/routes.h"
#include <iostream>
#include <httplib.h>
#include <thread>
#include <chrono>

int main(int argc, char* argv[]) {
    parse_arguments(argc, argv);
    
    httplib::Server svr;
    svr.Get("/", list_files);
    svr.Post("/upload", upload_file);
    svr.Get(R"(/(.*))", recursive_browser);

    std::cout << "Server running on http://"+ get_local_ip() + ":" << port << std::endl;
    
    std::thread server_thread([&](){ svr.listen("0.0.0.0", port); });
    
    if (run_time > 0) {
        std::this_thread::sleep_for(std::chrono::seconds(run_time));
        svr.stop();
    }
    
    server_thread.join();
    return 0;
}