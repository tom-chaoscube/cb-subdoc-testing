/**
 * Created by tom on 03/03/16.
 */
package hello;

import com.couchbase.client.java.repository.annotation.Id;
import com.couchbase.client.java.repository.annotation.Field;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Greeting {

    @Id
    private final String id;

    @Version
    private long version;

    @Field
    private final String name;

    public Greeting(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
