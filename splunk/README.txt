-- BACKUP AND RESTORE

backup_volumes and restore_volumes commands are used if you want to do a full export of your splunk data and configuration and migate that to another instance for backup or reporting

-- CHANGING SPLUNK CONFIG

You have two options to make changes to the splunk config

1) Small Changes

- Make the changes to the files here run build.cmd and check in the resulting .spl file.
- The next time you run the docker-compose command splunk will load the changes as a new application

2) Wholesale changes

If you want to make wholesale changes are you are probably better off making them in splunk and then exporting the SPL file to here

- Work as per the instructions here http://dev.splunk.com/view/webframework-developapps/SP-CAAAEMY 
- Get the .spl file off the docker instance by copying the file to your local machine.  backup_volumes.cmd shows and example of how to do something like this
- extract using the extract.cmd command once the spl file is in this directory so that you can check in the files

NOTE the build.cmd and extract.cmd commands require 7zip on windows and 7zip needs to be in your path so that it can be run on the command line