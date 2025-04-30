#include "../../include/routes.h"
#include "../../include/parser.h"

void list_files(const httplib::Request& req, httplib::Response& res) {
    
    if (!redirect_url.empty()) {
        res.status = 302;
        res.set_header("Location", redirect_url);
        return;
    }

    if (!single_file.empty()) {
        std::ifstream file(single_file, std::ios::binary);
        if (file) {
            send_file(res,file);
        } else {
            res.status = 404;
            res.set_content("File not found", "text/plain");
        }
        return;
    }

    std::string dir = shared_directory;
    if (req.has_param("path") && recursive_mode) {
        dir = req.get_param_value("path");
    }

    std::string html = "<html><head><style>"
                       "body { font-family: Arial, sans-serif; padding: 20px; }"
                       "a { text-decoration: none; color: blue; font-size: 18px; }"
                       "ul { list-style: none; padding-left: 0; }"
                       "li { margin: 5px 0; }"
                       "form { margin-top: 20px; padding: 10px; border: 1px solid #ccc; border-radius: 5px; display: inline-block; background: #f9f9f9; }"
                       "</style></head><body>";

    html += "<h1>Browsing: " + dir + "</h1><ul>";

    for (const auto& entry : fs::directory_iterator(dir)) {
        std::string path_str = entry.path().string();
        std::string name = entry.path().filename().string();

        if (fs::is_directory(entry.status()) && recursive_mode) {
            html += "<li>[DIR] <a href='/?path=" + path_str + "'>" + name + "</a></li>";
        } else if (fs::is_regular_file(entry.status()) && allow_download) {
            html += "<li><a href='/" + path_str + "'>" + name + "</a></li>";
        }
    }

    html += "</ul>";

    if (allow_upload) {
        html += "<h2>Upload File</h2><form id='uploadForm' action='/upload' method='post' enctype='multipart/form-data'>";
        html += "<input type='file' name='file'><br><input type='submit' value='Upload'>";
        html += "<script>document.getElementById('uploadForm').onsubmit = async function(event) { event.preventDefault(); const formData = new FormData(this); const response = await fetch('/upload', { method: 'POST', body: formData }); if(response.ok) location.reload(); };</script>";
        html += "</form>";
    }

    html += "</body></html>";
    res.set_content(html, "text/html");
}