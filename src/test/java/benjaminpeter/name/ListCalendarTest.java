package benjaminpeter.name;

import java.util.List;
import java.util.Iterator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.Date;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.osaf.caldav4j.CalDAVCollection;
import org.osaf.caldav4j.CalDAVConstants;
import org.osaf.caldav4j.exceptions.CalDAV4JException;
import org.osaf.caldav4j.methods.CalDAV4JMethodFactory;
import org.osaf.caldav4j.methods.HttpClient;
import org.osaf.caldav4j.model.request.CalendarQuery;
import org.osaf.caldav4j.util.GenerateQuery;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class ListCalendarTest {
  private double calcDuration(VEvent ve) {
    return (    ve.getEndDate().getDate().getTime()
            - ve.getStartDate().getDate().getTime())
           / (1000. * 60. * 60.);
  }

  @Test
  public void testList() throws CalDAV4JException
  {
    HttpClient httpClient = new HttpClient();
    // I tried it with zimbra - but I had no luck using google calendar
    httpClient.getHostConfiguration().setHost("CALDAVHOST", 443, "https");
    String username = "username";
    UsernamePasswordCredentials httpCredentials = new UsernamePasswordCredentials(username, "secret");
    httpClient.getState().setCredentials(AuthScope.ANY, httpCredentials);
    httpClient.getParams().setAuthenticationPreemptive(true);
    // Need a proxy?
    //httpClient.getHostConfiguration().setProxy("phost", 8080);

    CalDAVCollection collection = new CalDAVCollection(
        "/dav/"+ username +"/Calendar",
        (HostConfiguration) httpClient.getHostConfiguration().clone(),
        new CalDAV4JMethodFactory(),
        CalDAVConstants.PROC_ID_DEFAULT
        );

    GenerateQuery gq=new GenerateQuery();
    // TODO you might want to adjust the date
		gq.setFilter("VEVENT [20131001T000000Z;20131010T000000Z] : STATUS!=CANCELLED");
    // Get the raw caldav query
    // System.out.println("Query: "+ gq.prettyPrint());
    CalendarQuery calendarQuery = gq.generate();
    List<Calendar>calendars = collection.queryCalendars(httpClient, calendarQuery);

    for (Calendar calendar : calendars) {
      ComponentList componentList = calendar.getComponents().getComponents(Component.VEVENT);
      Iterator<VEvent> eventIterator = componentList.iterator();
      while (eventIterator.hasNext()) {
        VEvent ve = eventIterator.next();
        System.out.println("Event: "+ ve.toString());
        System.out.println("Duration (h): "+ String.format("%.2f", calcDuration(ve)));
        System.out.println("\n\n");
      }
    }

    // Give us a good feeling
    assertTrue(true);
  }
}
