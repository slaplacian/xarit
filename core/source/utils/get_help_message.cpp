#include "../../include/utils.h"
#include <sstream>

std::string get_help_message() {
    std::ostringstream oss;
    oss << "Xarit - Simple HTTP File Sharing Tool\n"
        << "Created by Léo Victor, 2025\n\n"
        << "Usage: xarit [options] [file]\n"
        << "Options:\n"
        << "  -p, --port [PORT]         Set the HTTP server port\n"
        << "  -q, --qrcode              Generate QR code for the server URL\n"
        << "  -t, --time [SECONDS]      Run server for limited time\n"
        << "  -d, --download            Enable only download mode\n"
        << "  -u, --upload              Enable only upload mode\n"
        << "  -D, --dir [DIR]           Set directory to share\n"
        << "  -f, --single-file [FILE]  Share only a specific file\n"
        << "  -r, --recursive           Enable recursive sharing (include subdirectories)\n"
        << "  -R, --redirect [URL]      Redirect all requests to a custom URL\n"
        << "  -h, --help                Show this help message\n";
    return oss.str();
}