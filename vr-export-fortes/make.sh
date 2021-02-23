#!/bin/sh
files="${files} vrintegracao/vo/interfaces/fortes/FortesPARVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesNFMVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesPNMVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesPCEVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesINMVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesNVCVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesPNCVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesIVCVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesIIVVO.java"
files="${files} vrintegracao/vo/interfaces/fortes/FortesCTCVO.java"
files="${files} vrintegracao/dao/interfaces/Fortes158DAO.java"
files="${files} vrintegracao/dao/interfaces/Fortes161DAO.java"
files="${files} fortesplus/gui/MainForm.java"
files="${files} fortesplus/FortesPlus.java"

printf "\033[0;35m"

printf "Compiling files...\n"

javac ${files}

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
  
  rm -R ${files}

  jar cvfm fortes-plus-1.3.13.jar META-INF/MANIFEST.MF *

  cd ..
fi

printf "\033[0;32mOperation finished successfully!\n"


printf "\033[0m\n"