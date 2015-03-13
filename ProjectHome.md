This project integrates Wicket (http://wicket.apache.org) with Bindgen (http://bindgen.org). Bindgen is an APT processor that generates Java objects that can be used to build property expressions, replacing code like this:

```
add(new Label("state", new PropertyModel(person, "address.state.code")));
```

with compile and type safe code like this:

```
add(new Label("state", new PropertyModel(person, new PersonBinding().address().state().code().getPath())));
```

bindgen-wicket then improves on this further by integrating Bindgen's concept of a `Binding` directly into core Wicket concepts such as models which leads to easier and more type-safe use:

```
add(new TextField<String>("state", BindingModel.of(person, new PersonBinding().address().state().code()));
```

The above adds additionaly type-safety checks:
  * `person` is of type `IModel<Person>` and binding is `BindingRoot<*Person*,String>`
  * `BindingModel` is of type `IModel<String>` which is what `TextField<String>` expects
  * Removes the verbosity of having to repeat types by using the convenience `of` factory method