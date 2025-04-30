#include "../../include/utils.h"
#include <iostream>
#include <qrencode.h>

void generate_qr_code(const std::string& url) {
    QRcode* qrcode = QRcode_encodeString(url.c_str(), 0, QR_ECLEVEL_L, QR_MODE_8, 1);
    if (!qrcode) {
        std::cerr << "Failed to generate QR code" << std::endl;
        return;
    }
    for (int y = 0; y < qrcode->width; y++) {
        for (int x = 0; x < qrcode->width; x++) {
            std::cout << (qrcode->data[y * qrcode->width + x] & 1 ? "██" : "  ");
        }
        std::cout << std::endl;
    }
    QRcode_free(qrcode);
}