import play.GlobalSettings;
import play.libs.F;
import play.libs.F.Promise;
import play.mvc.Result;
import play.mvc.Results;
import play.mvc.Http.RequestHeader;
import views.html.*;

/**
 * The Global object that allows some customization of app state.
 * 
 * @author <a href="mailto://awzurn@gmail.com">Andrew Zurn</a>
 * @version 1.0 9/25/2014
 */
public class Global extends GlobalSettings {

    /**
     * Send a user to the error 404 page when a 
     * HTTP 404 error is encountered.
     */
    @Override
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
	return F.Promise.pure((Result) Results.notFound(error404.render()));
    }
}