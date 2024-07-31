package org.acme;

import io.quarkus.cache.CacheResult;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Query;

@GraphQLApi
public class Service {

  @Query("hello")
  public DbModel hello(String name) {
    return dbCall(name);
  }

  @CacheResult(cacheName = "dbCall")
  DbModel dbCall(String name) {
    return DbModel.findByName(name);
  }
}

