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

        int identificator = 0;
        std::ostringstream oss;

        std::size_t last_dir_pos = filename.find_last_of("/");
        std::size_t last_dot     = filename.find_last_of(".");
        std::string extension    = "";

        bool applicable = last_dot != std::string::npos && (last_dir_pos == std::string::npos || last_dir_pos + 1 < last_dot);  

        if(applicable) {
            extension = filename.substr(last_dot);
            filename  = filename.substr(0,last_dot);
        }


        oss << filename;

        while(validate_file(oss.str()+extension)) {
            oss.str("");
            oss.clear();
            oss << filename << "_" << identificator++;
        }


        filename = oss.str();

        if(applicable) {
            filename += extension;
        }
    }

    std::ofstream ofs(filename, std::ios::binary);
    ofs.write(file.content.data(), file.content.size());
    ofs.close();
    res.set_content("File uploaded!", "text/plain");
}