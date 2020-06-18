import com.atlassian.jira.ComponentManager
import com.atlassian.jira.issue.Issue
import com.atlassian.jira.issue.comments.Comment
import com.atlassian.jira.issue.comments.CommentManager
import com.atlassian.jira.component.ComponentAccessor
import java.text.SimpleDateFormat
import org.apache.log4j.Logger
import org.apache.log4j.Level
import com.atlassian.jira.issue.link.IssueLinkTypeManager
import com.atlassian.jira.issue.CustomFieldManager
import com.atlassian.jira.issue.fields.CustomField
import groovy.time.TimeCategory

def commentManager = ComponentAccessor.getCommentManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()

// set logging to Jira log
def log = Logger.getLogger("StateFinder") // change for customer system
log.setLevel(Level.DEBUG)

// Main issue custom fields
def open="First time in Open"
def closed ="First Closed time"

log.debug("---------- StateFinder started ---------------------------------------")

//def changeItems = ComponentAccessor.getChangeHistoryManager().getAllChangeItems(issue)

def field = customFieldManager.getCustomFieldObjectByName(open)
def OpenDate = issue.getCustomFieldValue(field)

def field2 = customFieldManager.getCustomFieldObjectByName(closed)
def CloseDate = issue.getCustomFieldValue(field2)

def Text="NotClosedYet"
if (OpenDate == null || CloseDate == null) {
	Text="NotClosedYet" 
    }
else {
  	Text=CloseDate
  	//result=TimeCategory.minus(CloseDate, OpenDate).days
    //def duration = OpenDate - CloseDate
    //log.debug( "result: ${result}")
    
    def pattern = "d.M.yyyy hh:mm"
	def input = "17.06.2020 12:23"
 
	def date = new SimpleDateFormat(pattern).parse(input)
   log.debug( "das date: ${date}")  
   
   
   def String pattern = "dd-MM-yyyy";
   SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
   def String simpletime = simpleDateFormat.format(input);
      log.debug( "TOKA date: ${simpletime}") 
    
}
    
  
    

log.debug ("open: ${OpenDate}")
log.debug ("closed: ${CloseDate}")


return Text