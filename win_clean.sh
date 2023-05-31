docker stop pzsp2-db-1
docker stop pzsp2-backend-1
docker rm pzsp2-db-1
docker rm pzsp2-backend-1
docker builder prune -a