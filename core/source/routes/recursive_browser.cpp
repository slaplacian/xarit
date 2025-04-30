#include "../../include/routes.h"
#include "../../include/parser.h"
#include "../../include/utils.h"

void recursive_browser(const httplib::Request& req, httplib::Response& res) {

    std::string rel_path = req.matches[1];
    fs::path path = fs::weakly_canonical(fs::path(shared_directory) / rel_path);

    if(!recursive_mode) {

        if(validate_file(path.filename().string())) {
            send_file(res,path);
        } else {
            res.status = 404;
            res.set_content("File Not Found", "text/plain");
            return;
        }
    
    } else {

        if (!fs::exists(path)) {
            res.status = 404;
            res.set_content("Not Found", "text/plain");
            return;
        }

        if (fs::is_directory(path)) {
            std::string html = "<html><body><h1>Directory Listing</h1><ul>";
            for (const auto& entry : fs::directory_iterator(path)) {
                html += "<li><a href='/files/" + (rel_path + "/" + entry.path().filename().string()) + "'>" + entry.path().filename().string() + "</a></li>";
            }
            html += "</ul></body></html>";
            res.set_content(html, "text/html");
        } else if (fs::is_regular_file(path)) {
            send_file(res,path);
        } else {
            res.status = 403;
            res.set_content("Forbidden", "text/plain");
        }

    }
}