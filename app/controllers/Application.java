package controllers;

import dao.CustomerDao;
import encryption.EncryptionUtil;
import models.Context;
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

	public Result index() throws Exception {
		Context context = new Context("schema_a", EncryptionUtil.getTestKeyPair());
		return ok(index.render(form, "schema_a", customerDao.getAll(context)));
	}

	public Result submit() throws Exception {
		String schemaId = form.bindFromRequest().get().schemaId;
		Context context = new Context(schemaId, EncryptionUtil.getTestKeyPair());
		return ok(index.render(form, schemaId, customerDao.getAll(context)));
	}
}
