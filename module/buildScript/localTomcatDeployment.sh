#!/bin/bash

set -e

## Your local settings
tomcat_dir="/Applications/apache-tomcat-9.0.39";
scp_project_dir="/Users/yokiyang/Documents/MyGithub/WeightTracker/module";
scp_target_relative_dir="target";
scp_war_name="weighttracker"; # File name in the project
tomcat_war_name="weighttracker"; # File name in tomcat webapps

## Configuration
tomcat_webapp_dir="${tomcat_dir}/webapps";
tomcat_start_dir="${tomcat_dir}/bin/startup.sh";
tomcat_stop_dir="${tomcat_dir}/bin/shutdown.sh";
source_war_dir="${scp_project_dir}/${scp_target_relative_dir}/${scp_war_name}.war";
target_war_dir="${tomcat_webapp_dir}/"; #the name of scp and tomcat are same. So won't specific new name.

delete_folder_and_war () {
  local loc_file=$1;
  if [ -d "${loc_file}" ]; then
	  echo "Delete ${loc_file} and .war in current directory";
	  rm "${loc_file}.war"
	  rm -r "${loc_file}"
	  ls -lah
  else
    echo "No folder to delete.";
  fi
}

echo "1. Shutdown tomcat";
bash ${tomcat_stop_dir}
sleep 3s

echo "2. Clean up old files ${tomcat_war_name}";
cd ${tomcat_webapp_dir}
delete_folder_and_war ${tomcat_war_name}
sleep 2s

echo "3. Copy war files ${source_war_dir} to ${target_war_dir}"
cp ${source_war_dir} "${target_war_dir}"
ls -lah
sleep 3s

echo "4. Start tomcat";
bash ${tomcat_start_dir}
sleep 5s

echo "5. Done";
