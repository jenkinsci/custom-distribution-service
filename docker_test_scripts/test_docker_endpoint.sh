# Test plugin list endpoint
str="curl 'http://localhost:$1/api/plugin/getPluginList'"
op=$(eval "$str")
if [[ -n $op ]]; then
    echo "Output is not null."
else
    echo "Output is null."
    exit 1
fi

# Test package generation
str="curl -X POST -H 'Content-Type: application/json' -d @simple-config.json http://localhost:$1/package/getPackageConfiguration"
op=$(eval "$str")
if [[ -n $op ]]; then
    echo "Output is not null."
else
    echo "Output is null."
    exit 1
fi

# Test package download
str="curl -X POST -H 'Content-Type: application/json' -d @simple-config.yml http://localhost:$1/package/downloadPackageConfiguration"
op=$(eval "$str")
if [[ -n $op ]]; then
    echo "Output is not null."
else
    echo "Output is null"
    exit 1
fi

docker stop customdistributionservice_app-server_1
docker stop cds_front-end
