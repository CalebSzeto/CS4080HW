package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class LoxClass extends LoxInstance implements LoxCallable {
  final String name;
  final List<LoxClass> superclasses;
  private final Map<String, LoxFunction> methods;

  static class MethodWithClass {
    final LoxFunction method;
    final LoxClass declaringClass;
    
    MethodWithClass(LoxFunction method, LoxClass declaringClass) {
      this.method = method;
      this.declaringClass = declaringClass;
    }
  }

  LoxClass(String name, List<LoxClass> superclasses, Map<String, LoxFunction> methods) {
    super(null);
    this.superclasses = superclasses;
    this.name = name;
    this.methods = methods;
    this.klass = this;  
  }

  LoxFunction findMethod(String name) {
    for (LoxClass superclass : superclasses) {
      LoxFunction method = superclass.findMethod(name);
      if (method != null) {
        return method;
      }
    }

    if (methods.containsKey(name)) {
      return methods.get(name);
    }

    return null;
  }

  MethodWithClass findMethodWithClass(String name) {
    for (LoxClass superclass : superclasses) {
      MethodWithClass result = superclass.findMethodWithClass(name);
      if (result != null) {
        return result;
      }
    }

    if (methods.containsKey(name)) {
      return new MethodWithClass(methods.get(name), this);
    }

    return null;
  }

  LoxFunction findMethodBelowClass(String name, LoxClass declaringClass, LoxClass actualClass) {
    if (actualClass == declaringClass) {
      return null;
    }
    if (actualClass.methods.containsKey(name)) {
      return actualClass.methods.get(name);
    }
    for (LoxClass superclass : actualClass.superclasses) {
      LoxFunction method = findMethodBelowClass(name, declaringClass, superclass);
      if (method != null) {
        return method;
      }
    }

    return null;
  }

  @Override
  Object get(Token name, Interpreter interpreter) {
    if (fields.containsKey(name.lexeme)) {
      Object value = fields.get(name.lexeme);
      if (value instanceof LoxFunction) {
        LoxFunction function = (LoxFunction) value;
        if (function.isGetter()) {
          return function.call(interpreter, new ArrayList<>());
        }
      }
      return value;
    }

    throw new RuntimeError(name, "Undefined property '" + name.lexeme + "'.");
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    LoxInstance instance = new LoxInstance(this);
    MethodWithClass result = findMethodWithClass("init");
    if (result != null) {
      result.method.bind(instance, result.declaringClass).call(interpreter, arguments);
    }

    return instance;
  }

  @Override
  public int arity() {
    LoxFunction initializer = findMethod("init");
    if (initializer == null) return 0;
    return initializer.arity();
  }
}