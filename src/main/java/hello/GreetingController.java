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
    //private static final String keyRoot = "mufc";
    private static final String element = "searchTerm";

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/localString")
    public Greeting localString(@RequestParam(value="keyRoot", defaultValue="world") String keyRoot) {
        return new Greeting(String.valueOf(counter.incrementAndGet()), String.format(template, "tomgreen"));
    }

    @RequestMapping("/fulldoc")
    public Greeting localStringJson(@RequestParam(value="keyRoot", defaultValue="world") String keyRoot) {
        JsonDocument tomTest = cbBucket.get(keyRoot);

        return new Greeting(
                String.valueOf(counter.incrementAndGet()),
               String.format(template, tomTest.content().get(element)));
    }

    @RequestMapping("/subdoc")
    public Greeting subdoc(@RequestParam(value="keyRoot", defaultValue="world") String keyRoot) {
        DocumentFragment<Lookup> tomTest = cbBucket
                .lookupIn(keyRoot)
                .get(element)
                .doLookup();

        return new Greeting(
                String.valueOf(counter.incrementAndGet()),
               String.format(template, tomTest.content(element, String.class)));
    }

    @RequestMapping("/asyncFulldoc")
    public Observable<Greeting> asyncFulldoc(@RequestParam(value="keyRoot", defaultValue="world") String keyRoot) {
        AsyncBucket asyncBucket = cbBucket.async();

        Observable<Greeting> tomTest = asyncBucket.get(keyRoot)
            .flatMap( new Func1<JsonDocument, Observable<Greeting>>() {
                @Override
                public Observable<Greeting> call(JsonDocument gotDoc) {
                    return Observable.just(new Greeting(
                            String.valueOf(counter.incrementAndGet()),
                            String.format(template, gotDoc.content().get(element))));
                }
             });

        return tomTest;
    }

    @RequestMapping("/asyncSubdoc")
    public Observable<Greeting> asyncSubdoc(@RequestParam(value="keyRoot", defaultValue="world") String keyRoot) {
        AsyncBucket asyncBucket = cbBucket.async();

        Observable<Greeting> tomTest = asyncBucket
            .lookupIn(keyRoot)
            .get(element)
            .doLookup()
            .flatMap( new Func1<DocumentFragment<Lookup>, Observable<Greeting>>() {
                @Override
                public Observable<Greeting> call(DocumentFragment<Lookup> fragment) {
                    return Observable.just(new Greeting(
                            String.valueOf(counter.incrementAndGet()),
                            String.format(template, fragment.content(element, String.class))));
                }
             });

        return tomTest;
    }

}
