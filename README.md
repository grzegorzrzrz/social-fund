# BD2

To run database on default localhost:1521:
- Create an account at (only first time users)
 https://container-registry.oracle.com
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
