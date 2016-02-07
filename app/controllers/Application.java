package controllers;

import dao.CustomerDao;
import models.Schema;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import javax.inject.Inject;

public class Application extends Controller {

	Form<Schema> form = Form.form(Schema.class);

	@Inject
	CustomerDao customerDao;

	public Result index() {
		return ok(index.render(form, "schema_a", customerDao.getAll("schema_a")));
	}

	public Result submit() {
		Schema schema = form.bindFromRequest().get();
		return ok(index.render(form, schema.schemaId, customerDao.getAll(schema.schemaId)));
	}
}
