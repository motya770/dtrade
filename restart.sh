cd /code/dtrade_repo/
git pull https://motya770:pragma1pragma1@bitbucket.org/crazy_team_007/dtrade_repo.git
mvn clean install -DskipTests dockerfile:build dockerfile:push
cd /work
docker-compose down
docker-compose up

