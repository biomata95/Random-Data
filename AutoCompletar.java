import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.AutoCompleteField;
import net.rim.device.api.collection.util.*;


public class AutoCompletar extends UiApplication 
{
    public static void main(String[] args) 
    {
        AutoCompleteFieldApp app = new AutoCompleteFieldApp();
        app.enterEventDispatcher();
    }
      
    AutoCompletar()
    {
    	   pushScreen(new HomeScreen());
    }
}

class HomeScreen extends MainScreen
{
    public HomeScreen()
    {
								setTitle("Autocomplete Text Field Demo");
        BasicFilteredList filterList = new BasicFilteredList();
        String[] days = {"Monday","Tuesday","Wednesday",
        		               "Thursday","Friday","Saturday","Sunday"};
        filterList.addDataSet(1,days,"days",
              BasicFilteredList.COMPARISON_IGNORE_CASE);
        AutoCompleteField autoCompleteField = 
              new AutoCompleteField(filterList);
        add(autoCompleteField);
    }
}