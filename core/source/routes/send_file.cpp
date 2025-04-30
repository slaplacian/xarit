#include "../../include/routes.h"
#include "../../include/parser.h"
#include "../../include/utils.h"

void send_file(httplib::Response& res, std::ifstream &file) {
    std::ostringstream buffer;
    buffer << file.rdbuf();
    res.set_header("Content-Disposition", "attachment; filename=\"" + fs::path(single_file).filename().string() + "\"");
    res.set_content(buffer.str(), "application/octet-stream");
}

void send_file(httplib::Response& res, fs::path path) {
    std::ifstream ifs(path, std::ios::binary);
    send_file(res,ifs);
}