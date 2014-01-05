vraptor-mustache
================

Handles download by reading from a mustache template.


Using it
--------

1.     In a Maven project's pom.xml file:

```xml	 
<dependency>
	<groupId>es.cenobit.vraptor</groupId>
  	<artifactId>vraptor-mustache</artifactId>
  	<version>1.0</version>
</dependency>
```

2.	Put vraptor-mustache-VERSION.jar and dependencies in your `WEB-INF/lib` folder.
4.	In your project create templates.

Template
--------

```mustache
ID;Name;Description;Price;Status;
{{items}}{{id}};{{name}};{{description}};{{price}};{{status}};
{{/items}}
```

Item
----

```java
public class Item {

	private Long id;
	private String name;
	private String url;
	private Double price;
	private Boolean status;

	public Item(Long id, String name, String url, Double price, Boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.price = price;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
}
```

Controller
--------

```java
@Resource
public class TemplateController {

	private final Result result;
	private ServletContext servletContext;

	public TemplateController(Result result, ServletContext servletContext) {
		this.result = result;
		this.servletContext = servletContext;
	}

	@Path("/download")
	public Download download() {
		List<Item> items = new ArrayList<Item>();
		items.add(new Item(1L, "VRaptor", "http://vraptor.caelum.com.br/", 22.50, true));
		items.add(new Item(2L, "Mustache", "http://mustache.github.io/", 34.40, true));
		items.add(new Item(3L, "Cenobit.es", "http://cenobit.es/", 6.66, true));
		
		Map<String, Object> scope = new HashMap<String, Object>();
		scope.put("items", items);

		File file = new File(this.servletContext.getRealPath("/WEB-INF/classes/templates/template.mustache"));
		return new MustacheDownload(file, scope, "application/vnd.ms-excel", "template.cvs");
	}
}
```

Project Information
-------------------

* __Author:__ Cenobit Technologies, Inc.
* __Version:__ 1.0 of 2013/10/15
* __License:__ [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html "Apache License 2.0")



