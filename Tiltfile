## trading
custom_build(
    'trading',
    'cd trading && pwd && ./gradlew dockerBuild && docker tag trading $EXPECTED_REF',
    deps=['./trading/src']
);

k8s_yaml('./deploy/trading.yaml')
k8s_resource('squer-trading', port_forwards=['8080:8080', '5010:5010']) # Remote debugger

## trading-mock
custom_build(
    'trading-mock',
    'cd trading-mock && pwd && ./gradlew dockerBuild && docker tag trading-mock $EXPECTED_REF',
    deps=['./trading-mock/src']
);
k8s_yaml('./deploy/trading-mock.yaml')
k8s_resource('squer-trading-mock', port_forwards=['5006:5006']) # Remote debugger

##redpanda
k8s_yaml('./deploy/redpanda.yaml')

## console
k8s_yaml('./deploy/redpandaconsole.yaml')
k8s_resource('redpanda-console', port_forwards='4040:8080')

## redis
k8s_yaml('./deploy/redis.yaml')

## redis commander
k8s_yaml('./deploy/rediscommander.yaml')
k8s_resource('rediscommander', port_forwards='8089:8081')

## postgres
k8s_yaml('./deploy/postgres.yaml')
