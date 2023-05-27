# Account multiple database api

docker inspect  -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' "container id"