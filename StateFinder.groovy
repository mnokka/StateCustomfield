// mika.nokka1@gmail.com , June 2020, Hämeenlinna, Finland
// POC: Uses SCriptRunner "first time to state" custom field pair to calculate "how long was spent in first state"


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

//log.debug("---------- StateFinder started ---------------------------------------")

//def changeItems = ComponentAccessor.getChangeHistoryManager().getAllChangeItems(issue)

def field = customFieldManager.getCustomFieldObjectByName(open)
def OpenDate = issue.getCustomFieldValue(field)

def field2 = customFieldManager.getCustomFieldObjectByName(closed)
def CloseDate = issue.getCustomFieldValue(field2)

def totalhours="NotAvailableYet"
if (OpenDate == null || CloseDate == null) {
	def Text="NotClosedYet" 
    }
else {
  	//Text=CloseDate
    
    def CloseDateStr = CloseDate.toString()
    def OpenDateStr = OpenDate.toString()
    
    //def pattern = "dd.MM.yyyy hh:mm"
    // this was example to check function usages
	//def input = "17.06.2020 12:23"
	//def input2 = "19.06.2020 15:00"
	//def date = new SimpleDateFormat(pattern).parse(input)
	//def date2 = new SimpleDateFormat(pattern).parse(input2)
    //def minus=date2-date
    //log.debug( "First date: ${date}, SECOND date :${date2}")  
    //log.debug( "minus date: ${minus}")  
    //def duration = TimeCategory.minus(date2, date)
    //log.debug( "duration date: ${duration}")
    //def hours=duration.getHours()
    //def days=duration.getDays()
    //log.debug( "duration hours: ${hours}")
    
    def pattern = "yyyy-MM-dd hh:mm:ss.SSS"
    def start = new SimpleDateFormat(pattern).parse(OpenDateStr)
	def end = new SimpleDateFormat(pattern).parse(CloseDateStr)
    def duration = TimeCategory.minus(end, start)
    def hours=duration.getHours()
    def days=duration.getDays()
    
    if (days == null) {
                    days=0
                }
    if (hours== null) {         
                    hours=0
                }
            

    totalhours= days*24 + hours
    //log.debug( "duration totalhours: ${totalhours}")
    if (totalhours <0) {
       totalhours="MoreThanOneCycleDone"   // issue had been reopened, cant calcualte as first time to state ScriptRunner fields used                         
                 }


    
}
    
log.debug ("${issue}: Opened:${OpenDate}, Released(closed):${CloseDate}, Hours spent(open):${totalhours}")

//log.debug("---------- StateFinder exiting ---------------------------------------")
return totalhours