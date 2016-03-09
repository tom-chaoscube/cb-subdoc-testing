package hello;

import java.util.concurrent.atomic.AtomicLong;

import com.couchbase.client.core.message.kv.subdoc.multi.Lookup;
import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.subdoc.DocumentFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.functions.Func1;
//import rx.Observable;

/**
 * Created by tom on 03/03/16.
 */
@RestController
public class GreetingController {

    @Autowired
    private GreetingRepository repository;
    @Autowired
    private Bucket cbBucket;

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="world") String name) {
        return new Greeting(String.valueOf(counter.incrementAndGet()), String.format(template, name));
    }

    @RequestMapping("/cbgreeting")
    public Greeting cbgreeting() {
        JsonDocument tomTest = cbBucket.get("mufc");

        return new Greeting(
                String.valueOf(counter.incrementAndGet()),
               String.format(template, tomTest.content().get("name")));
    }

    @RequestMapping("/cbgreetingsubdoc")
    public Greeting cbgreetingsubdoc() {
        DocumentFragment<Lookup> tomTest = cbBucket
                .lookupIn("mufc")
                .get("name")
                .doLookup();

        return new Greeting(
                String.valueOf(counter.incrementAndGet()),
               String.format(template, tomTest.content("name", String.class)));
    }

    @RequestMapping("/asyncgreeting")
    public Observable<Greeting> asyncgreeting(@RequestParam(value="name", defaultValue="world") String name) {
        AsyncBucket asyncBucket = cbBucket.async();

        Observable<Greeting> tomTest = asyncBucket.get("mufc")
            .flatMap( new Func1<JsonDocument, Observable<Greeting>>() {
                @Override
                public Observable<Greeting> call(JsonDocument gotDoc) {
                    return Observable.just(new Greeting(
                            String.valueOf(counter.incrementAndGet()),
                            String.format(template, gotDoc.content().get("name"))));
                }
             });

        return tomTest;
    }


}
