package bo.umss.app.inventorySp.configs.web;

public class Cors {

	private String[] origins;

	private String[] methods;

	private String[] headers;

	public String[] getOrigins() {
		return origins;
	}

	public void setOrigins(String[] origins) {
		this.origins = origins;
	}

	public String[] getMethods() {
		return methods;
	}

	public void setMethods(String[] methods) {
		this.methods = methods;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
}
