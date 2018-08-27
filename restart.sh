cd /code/dtrade_repo/
git pull https://jenkins@gitlab.x1team.com/fox/dtrade.git
mvn clean install -DskipTests dockerfile:build dockerfile:push
cd /work
docker-compose down
docker-compose up

