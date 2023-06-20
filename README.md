# BD2

To run database on default localhost:1521:
- Create an account at (only first time users)
 https://container-registry.oracle.com
- Accept terms at https://container-registry.oracle.com/ords/f?p=113:4:7543123033092:::4:P4_REPOSITORY,AI_REPOSITORY,AI_REPOSITORY_NAME,P4_REPOSITORY_NAME,P4_EULA_ID,P4_BUSINESS_AREA_ID:9,9,Oracle%20Database%20Enterprise%20Edition,Oracle%20Database%20Enterprise%20Edition,1,0&cs=3uXTNaVHBDOVrGhMaI4sGYNtLUKYu3bTOMbSDHvQGC4UMfaL3H6O68DSPVOzOZTUHfgD9T_MaDRZRbaZbxp6QqQ
- Launch oracle_login.ps1 and login
Or just `docker login container-registry.oracle.com`
- Launch run.sh

To change database:

Set up a connection in editor like SQLDeveloper

But preferably something else that actually works. Like https://www.dbvis.com/

![Setup](/images/oracle.png)

password is "oracle"



To hard reset docker run win_clean.sh

To get docker:
https://docs.docker.com/desktop/install/windows-install/
