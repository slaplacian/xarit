#include "../../include/routes.h"
#include "../../include/parser.h"

void upload_file(const httplib::Request& req, httplib::Response& res) {
    if (!allow_upload) {
        res.set_content("Upload not allowed", "text/plain");
        return;
    }
    auto file = req.get_file_value("file");
    std::ofstream ofs(file.filename, std::ios::binary);
    ofs.write(file.content.data(), file.content.size());
    ofs.close();
    res.set_content("File uploaded!", "text/plain");
}