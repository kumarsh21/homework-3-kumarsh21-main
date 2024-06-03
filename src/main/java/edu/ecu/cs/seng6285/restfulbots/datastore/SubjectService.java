package edu.ecu.cs.seng6285.restfulbots.datastore;

import com.google.cloud.datastore.*;
import edu.ecu.cs.seng6285.restfulbots.models.Course;
import edu.ecu.cs.seng6285.restfulbots.models.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
public class SubjectService {

    private Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private static final String ENTITY_KIND = "Subject";
    private final KeyFactory keyFactory = datastore.newKeyFactory().setKind(ENTITY_KIND);

    public Key createSubject(Subject subject) {
        Key key = keyFactory.newKey(subject.getSubjectName());
        Entity entity = Entity.newBuilder(key)
                .set(Subject.SUBJECT_NAME, subject.getSubjectName())
                .build();

            datastore.put(entity);
            return key;
    }

    public List<Subject> getAllSubjects() {
        Query<Entity> query = Query.newEntityQueryBuilder()
                .setKind(ENTITY_KIND)
                .build();
        QueryResults<Entity> results = datastore.run(query);

        List<Subject> subjects = new ArrayList<>();
        while (results.hasNext())
        {
            Entity entity = results.next();
            subjects.add(entityToSubject(entity));
        }
        return subjects;

        // TODO: What code needs to be added here to retrieve all subjects?

        // TODO: Remove this return statement once you have something valid to return
        //return Collections.emptyList();
    }
    private Subject entityToSubject(Entity entity)
   {
       return new Subject.Builder()
               .withSubjectName(entity.getString(Subject.SUBJECT_NAME))
               .build();
   }
}
