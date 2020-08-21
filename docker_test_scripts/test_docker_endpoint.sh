# Test plugin list endpoint
str="curl 'http://localhost:8080/api/plugin/getPluginList'"
op=$(eval "$str")
if [[ -n $op ]]; then
    echo "Output is not null."
else
    echo "Output is null."
    exit 1
fi

# Test package generation
str="curl -X POST -H 'Content-Type: application/json' -d @simple-config.json http://localhost:8080/package/getPackageConfiguration"
op=$(eval "$str")
if [[ -n $op ]]; then
    echo "Output is not null."
else
    echo "Output is null."
    exit 1
fi

# Test package download
str="curl -X POST -H 'Content-Type: application/json' -d @simple-config.yml http://localhost:8080/package/downloadPackageConfiguration"
op=$(eval "$str")
if [[ -n $op ]]; then
    echo "Output is not null."
else
    echo "Output is null"
    exit 1
fi

