
#include "../../include/utils.h"
#include <ifaddrs.h>
#include <arpa/inet.h>
#include <httplib.h>

std::string get_local_ip() {
    struct ifaddrs *ifaddr, *ifa;
    char host[NI_MAXHOST];
    std::string local_ip = "127.0.0.1";
    
    if (getifaddrs(&ifaddr) == -1) {
        return local_ip;
    }
    
    for (ifa = ifaddr; ifa != NULL; ifa = ifa->ifa_next) {
        if (ifa->ifa_addr == NULL || ifa->ifa_addr->sa_family != AF_INET) {
            continue;
        }
        
        if (getnameinfo(ifa->ifa_addr, sizeof(struct sockaddr_in), host, NI_MAXHOST, NULL, 0, NI_NUMERICHOST) == 0) {
            if (std::string(ifa->ifa_name) != "lo") {
                local_ip = host;
                break;
            }
        }
    }
    
    freeifaddrs(ifaddr);
    return local_ip;
}