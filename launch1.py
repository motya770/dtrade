import os
import psutil

PROCNAME = "java"

for proc in psutil.process_iter():
    # check whether the process name matches
    if proc.name() == PROCNAME:
        proc.kill()
#os.system("ps -C java -o pid=|xargs kill -9")
#os.system('cd /work/dtrade_repo && git pull')
#os.system('cd /work/dtrade_repo && mvn clean install -DskipTests')

import shutil
shutil.copy2('/work/dtrade_repo/platform/target/platform-0.0.1-SNAPSHOT.jar', '/work/deploy') # complete target filename given
shutil.copy2('/work/dtrade_repo/engine/target/engine-0.0.1-SNAPSHOT.jar', '/work/deploy')
shutil.copy2('/work/dtrade_repo/simulator/target/simulator-0.0.1-SNAPSHOT.jar', '/work/deploy')
shutil.copy2('/work/dtrade_repo/backoffice/target/backoffice-0.0.1-SNAPSHOT.jar', '/work/deploy')

import subprocess
subprocess.Popen(['nohup', 'java', '-jar', '/work/deploy/platform-0.0.1-SNAPSHOT.jar', '&'])
subprocess.Popen(['nohup', 'java', '-jar', '/work/deploy/engine-0.0.1-SNAPSHOT.jar', '&'])
subprocess.Popen(['nohup', 'java', '-jar', '/work/deploy/backoffice-0.0.1-SNAPSHOT.jar', '&'])
subprocess.Popen(['nohup', 'java', '-jar', '/work/deploy/simulator-0.0.1-SNAPSHOT.jar', '&'])

#os.system('cd /work/deploy && java -jar platform-0.0.1-SNAPSHOT.jar')
#os.system('cd /work/deploy && java -jar engine-0.0.1-SNAPSHOT.jar')
#os.system('cd /work/deploy && java -jar backoffice-0.0.1-SNAPSHOT.jar')
