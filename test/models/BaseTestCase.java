package models;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import play.test.FakeApplication;
import play.test.Helpers;

import com.avaje.ebean.Ebean;

public class BaseTestCase {

    public static FakeApplication app;
    public static String createDdl = "";
    public static String dropDdl = "";

    @BeforeClass
    public static void startApp() throws IOException {
	app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
	Helpers.start(app);

	// Get our create evolution file for creating and destroying database
	String createContent = FileUtils.readFileToString(app
		.getWrappedApplication().getFile(
			"/conf/evolutions/default/1.sql"));

	// Split string to get Ups and Downs
	String[] splitContent = createContent.split("# --- !Ups");
	String[] upsAndDowns = splitContent[1].split("# --- !Downs");
	createDdl = upsAndDowns[0];
	dropDdl = upsAndDowns[1];

	// Get our insert evolution file for creating and destroying database
	String insertContent = FileUtils.readFileToString(app
		.getWrappedApplication().getFile(
			"/conf/evolutions/default/2.sql"));

	// Split string to get Ups and Downs
	splitContent = insertContent.split("# --- !Ups");
	upsAndDowns = splitContent[1].split("# --- !Downs");

	System.out.println("Dropping: "
		+ Ebean.execute(Ebean.createCallableSql(dropDdl)));
	System.out.println("Creating: "
		+ Ebean.execute(Ebean.createCallableSql(createDdl)));
    }

    @AfterClass
    public static void stopApp() {
	Helpers.stop(app);
    }

    // @Before
    // public void createCleanDb() {
    // System.out.println("createCleanDb running...");
    // System.out.println("Dropping: " +
    // Ebean.execute(Ebean.createCallableSql(dropDdl)));
    // System.out.println("Creating: " +
    // Ebean.execute(Ebean.createCallableSql(createDdl)));
    // }

}
