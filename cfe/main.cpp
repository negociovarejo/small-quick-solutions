#include <iostream>
#include <fstream>
#include <vector>

int main(int argc, char* argv[])
{
    if (argc != 4) {
        std::cout << "Usage: progName <apiKey> <inputName> <outputName>" << std::endl;

        return -1;
    }

    std::string url = "https://cfe.sefaz.ce.gov.br:8443/portalcfews/mfe/fiscal-coupons/xml/";

    std::ifstream ifs{argv[2]};

    if (!ifs) {
        std::cerr << "Failed to open file for reading" << std::endl;
        return -1;
    }

    std::ofstream ofs{argv[3]};

    if (!ofs) {
        std::cerr << "Failed to open file for writing" << std::endl;
        return -1;
    }

    std::vector<std::string> lines;

    while (ifs) {
        std::string line;
        ifs >> line;
        lines.push_back(line);
    }

    for (const std::string& line : lines)
    {
        ofs << url << line << "?apiKey=" << argv[1] << std::endl;
    }

    return 0;
}
