interface Pizza {
	public void prepare();
	public void bake();
	public void cut();
	public void box();
}

class CheesePizza implements Pizza {
	public void prepare() {
		//TODO: implement
	}
	public void bake() {
	}
	public void cut() {
	}
	public void box() {
	}
}

class PepperoniPizza implements Pizza {
	public void prepare() {
	}
	public void bake() {
	}
	public void cut() {
	}
	public void box() {
	}
}

class ClamPizza implements Pizza {
	public void prepare() {
	}
	public void bake() {
	}
	public void cut() {
	}
	public void box() {
	}
}

class VeggiePizza implements Pizza {
	public void prepare() {
	}
	public void bake() {
	}
	public void cut() {
	}
	public void box() {
	}
}

abstract class PizzaStore {
	public abstract Pizza createPizza(String type);
	
	public Pizza orderPizza(String type) {
		Pizza pizza = createPizza(type);
		
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		
		return pizza;
	}
}
