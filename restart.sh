cd /code/dtrade_repo/
git pull https://jenkins:hjjxi~S[(X[z/2]X`;7n{@gitlab.x1team.com/fox/dtrade.git
mvn clean install -DskipTests dockerfile:build dockerfile:push
cd /work
docker-compose down
docker-compose up

