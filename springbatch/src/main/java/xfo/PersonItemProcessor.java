package xfo;
import xfo.beans.Person;
import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

public class PersonItemProcessor  implements ItemProcessor<Person, Person>{
    private final static Logger logger = Logger.getLogger(PersonItemProcessor.class);

    @Override
    public Person process(Person person) throws Exception {
        String firstName = person.getFirstName().toUpperCase();
        String lastName = person.getLastName().toUpperCase();
        Person transformedPerson =new Person(firstName,lastName);
        logger.info(String.format("Converting person %s into transformedPerson %s",person,transformedPerson));
        return transformedPerson;


    }
}
