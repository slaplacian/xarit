#include "../../include/routes.h"
#include "../../include/parser.h"
#include "../../include/utils.h"

void recursive_browser(const httplib::Request& req, httplib::Response& res) {

    std::string rel_path = req.matches[1];
    fs::path path = fs::weakly_canonical(fs::path(".") / rel_path);
    fs::path shared_path = fs::weakly_canonical(fs::path(".") / shared_directory);

    std::size_t size        = shared_path.string().size();
    std::string prefix_path = path.string().substr(0,size);
    std::string sub_path    = path.string().substr(size+1);

    if(prefix_path != shared_path.string()) {
        res.status = 403;
        res.set_content("Forbidden","text/plain");
        return;
    }

    if(sub_path.find("/") != std::string::npos && !recursive_mode) {
        res.status = 403;
        res.set_content("Forbidden","text/plain");
        return;
    }

    if(!validate_file(path.string())) {
        res.status = 404;
        res.set_content("File Not Found", "text/plain");
        return;
    }

    send_file(res,path);
    return;
}