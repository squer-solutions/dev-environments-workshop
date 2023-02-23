package io.squer.devenv;

import io.squer.devenv.containers.Postgres;
import io.squer.devenv.containers.PricesMock;
import io.squer.devenv.containers.Redis;
import io.squer.devenv.containers.RedisCommander;

import io.squer.devenv.containers.Redpanda;
import io.squer.devenv.containers.RedpandaConsole;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;

public class DevEnvJava {

  private static final Logger log = LoggerFactory.getLogger(DevEnvJava.class);

  public static List<GenericContainer<?>> containers = new ArrayList<>();

  public static void main(String[] args) {

    init(false);

    log.info("Stop after usage using Ctrl+C");
    log.info("###################################################");

    while (true) {
    }
  }

  public static void init(boolean isSystemtest) {

    Network defaultNetwork = Network.newNetwork();

    log.info("creating containers...");

    // Postgres
    var postgres = Postgres.getInstance(defaultNetwork);

    // redis
    var redis = Redis.getInstance(defaultNetwork);

    // redis commander
    var redisCommander = RedisCommander.getInstance(defaultNetwork);

    // redpanda
    var redpanda = Redpanda.getInstance(defaultNetwork);

    // redpanda console
    var redpandaConsole = RedpandaConsole.getInstance(defaultNetwork).dependsOn(redpanda);

    // prices mock
    var pricesMock = PricesMock.getInstance(defaultNetwork).dependsOn(redpanda);

    log.info("starting containers...");
    postgres.start();
    containers.add(postgres);
    redis.start();
    containers.add(redis);
    redisCommander.start();
    containers.add(redisCommander);
    redpanda.start();
    Redpanda.createTopics();
    containers.add(redpanda);
    redpandaConsole.start();
    containers.add(redpandaConsole);
    pricesMock.start();
    containers.add(pricesMock);

    log.info("###################################################");
    log.info("Startup Done");
    log.info("###################################################");
  }

  public static void stop() {
    containers.forEach(GenericContainer::stop);
  }

}