import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dao.CustomerDao;
import dao.impl.CustomerDaoImpl;
import play.GlobalSettings;

public class Global extends GlobalSettings {

	private static final Injector INJECTOR = createInjector();

	@Override
	public <A> A getControllerInstance(Class<A> controllerClass) throws Exception {
		return INJECTOR.getInstance(controllerClass);
	}

	private static Injector createInjector() {
		return Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(CustomerDao.class).to(CustomerDaoImpl.class);
			}
		});
	}
}
