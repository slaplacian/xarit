#include "../../include/routes.h"
#include "../../include/parser.h"
#include "../../include/utils.h"
#include <sstream>

void upload_file(const httplib::Request& req, httplib::Response& res) {
    if (!allow_upload) {
        res.set_content("Upload not allowed", "text/plain");
        return;
    }

    auto file = req.get_file_value("file");

    std::string filename = "";
    
    if (req.has_param("path") && recursive_mode) {
        std::string rel_path = req.get_param_value("path");
        fs::path path = fs::absolute(fs::current_path() / fs::path(shared_directory) / fs::path(rel_path));
        filename += path.string() + "/";
    }

    filename += file.filename;
    
    if(!override_mode) {
        filename = increment_filename(filename);
    }

    std::ofstream ofs(filename, std::ios::binary);
    ofs.write(file.content.data(), file.content.size());
    ofs.close();
    res.set_content("File uploaded!", "text/plain");
}