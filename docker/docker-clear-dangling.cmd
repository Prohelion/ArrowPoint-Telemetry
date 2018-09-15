powershell docker rmi -f $(docker images -q -f dangling=true)

