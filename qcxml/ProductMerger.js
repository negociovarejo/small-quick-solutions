const xmlreader = require('xmlreader');
const fs = require('fs');
const path = require('path');
const { spawnSync } = require("child_process");
 
if (process.argv.length !== 3) {
    console.log('Usage: node ProductMerger.js <folder>');
    return;
}

// Return a list of files of the specified fileTypes in the provided dir, 
// with the file path relative to the given dir
// dir: path of the directory you want to search the files for
const getFilesFromDir = dir => {
  var filesToReturn = [];
  function walkDir(currentPath) {
    var files = fs.readdirSync(currentPath);
    for (var i in files) {
      var curFile = path.join(currentPath, files[i]);      
      if (fs.statSync(curFile).isFile() && ['.xml'].indexOf(path.extname(curFile)) != -1) {
        filesToReturn.push(curFile.replace(dir, ''));
      } else if (fs.statSync(curFile).isDirectory()) {
       walkDir(curFile);
      }
    }
  };
  walkDir(dir);
  return filesToReturn; 
}

const files = getFilesFromDir(process.argv[2]);

const Query = query => {
    const script = process.platform === 'linux' ? "./database.sh" : "./database.bat";

    var result = spawnSync(script, [query], {
        cwd: process.cwd(),
        env: process.env,
        stdio: 'pipe',
        encoding: 'utf-8'
    });

    return result.stdout.split('\n');
};

const fetchProducts = async files => {
    const products = {};

    files.forEach((filePath) => {
        const path = `./${process.argv[2]}/${filePath.substr(1)}`;
        const data = fs.readFileSync(path, 'utf8');
        
        xmlreader.read(data, (err, res) => {
            if(err) {
                return console.log(err);
            }
        
            const inf = res.CFe.infCFe;
            for (let i = 0; i < inf.det.count(); ++i) {
                const prod = inf.det.at(i).prod; 
                products[prod.cProd.text()] = {
                    eancode: prod.cEAN ? prod.cEAN.text() : '-',
                    description: prod.xProd.text(),
                    currentCode: prod.cProd.text(),
                    newCode: '-'
                };

            }
        });
    });
    
    const array = [];
    Object.keys(products).forEach(key => {
        array.push(products[key]);
    });
    
    let content = 'Codigo de Barras\tDescricao\t\t\t\t\tCodigo Atual\t\tCodigo Novo\n';

    array.map(product => {
        while(product.description.length < 20) {
            product.description += ' ';
        }

        const res = Query(`select p.id from produto p inner join produtoautomacao pa on pa.id_produto = p.id where pa.codigobarras = '${product.eancode}'`);

        if (res.length === 4) {
            product.newCode = res[1];

            while(product.newCode.length < 14) {
                product.newCode = '0' + product.newCode;
            }
        }

        return product;
    })
    .sort((a, b) => b.eancode.localeCompare(a.eancode))
    .sort((a, b) => b.newCode.localeCompare(a.newCode))
    .forEach(product => {
        content += `${product.eancode !== '-' ? product.eancode : '-\t\t\t'}\t\t${product.description}\t\t${product.currentCode}\t\t${product.newCode}\n`;;
    });
    
    fs.writeFileSync('output.txt', content);
};


fetchProducts(files);