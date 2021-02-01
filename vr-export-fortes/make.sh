#!/bin/sh
declare -a files=(
  "vrintegracao/vo/interfaces/fortes/FortesPARVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesNFMVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesPNMVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesPCEVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesINMVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesNVCVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesPNCVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesIVCVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesIIVVO.java"
  "vrintegracao/vo/interfaces/fortes/FortesCTCVO.java"
  "vrintegracao/dao/interfaces/Fortes158DAO.java"
  "vrintegracao/dao/interfaces/Fortes161DAO.java"
  "fortesplus/gui/MainForm.java"
  "fortesplus/FortesPlus.java"
)

printf "\033[0;35m"

printf "Compiling files...\n"

javac ${files[@]}

if [ "$1" = "build" ]
then
  if [ -e output/ ]
  then
    rm -R output
  fi

  mkdir output

  printf "Copying content into output\n"
  cp -r com/ fortesplus/ META-INF/ net/ org/ vrframework/ vrintegracao/ fortesplus.properties output/

  cd output/
  
  printf "Deleting source code from output...\n"
  
  rm -R ${files[@]}

  jar cvfm fortes-plus-1.3.2.jar META-INF/MANIFEST.MF *

  cd ..
fi

printf "\033[0;32mOperation finished successfully!\n"


printf "\033[0m\n"