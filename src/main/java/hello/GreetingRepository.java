package hello;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

/**
 * Created by tom on 09/03/16.
 */
interface GreetingRepository extends CouchbaseRepository<Greeting, String> {

}
